package vn.gmobile.einvoice.message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.base.filter.BeanInnerJoinFilter;
import com.base.filter.BooleanEqualFilter;
import com.base.filter.DateEqualFilter;
import com.base.filter.DateGreaterThanOrEqualFilter;
import com.base.filter.DateLessThanOrEqualFilter;
import com.base.filter.FilterCriteria;
import com.base.filter.IntegerEqualFilter;
import com.base.filter.IntegerGreaterThanOrEqualFilter;
import com.base.filter.IntegerInFilter;
import com.base.filter.IntegerLessThanOrEqualFilter;
import com.base.filter.StringEqualFilter;
import com.base.filter.StringInFilter;
import com.bean.base.BeanFilter;
import com.bean.json.JsonUtils;

import vn.gmobile.einvoice.BpmProcess;
import vn.gmobile.einvoice.conf.Consts;
import vn.gmobile.einvoice.conf.JsParams;
import vn.gmobile.einvoice.db.DataLoadDAO;
import vn.gmobile.einvoice.db.InvoiceAgencyDAO;
import vn.gmobile.einvoice.db.InvoiceAgencySerialRelationDAO;
import vn.gmobile.einvoice.db.InvoiceDetailDAO;
import vn.gmobile.einvoice.db.MessageDAO;
import vn.gmobile.einvoice.model.DataLoad;
import vn.gmobile.einvoice.model.InvoiceAgency;
import vn.gmobile.einvoice.model.InvoiceAgencySerialRelation;
import vn.gmobile.einvoice.model.DataLoad;
import vn.gmobile.einvoice.model.InvoiceBatch;
import vn.gmobile.einvoice.model.InvoiceDetail;
import vn.gmobile.einvoice.model.Message;
import vn.gmobile.einvoice.util.DateUtils;

public class InvoiceRevenueReportRequest extends JsonRequest {
	private static final Logger LOGGER = Logger.getLogger(InvoiceRevenueReportRequest.class.getName());

	/**
	 * @param jObj
	 * @throws Exception
	 */
	public InvoiceRevenueReportRequest(JSONObject jObj) throws Exception {
		super(jObj);
		if (jObj.has(JsParams.INVOICE_REVENUE_REPORT_REQUEST.USERNAME)) {
			username = jObj.getString(JsParams.INVOICE_REVENUE_REPORT_REQUEST.USERNAME);
		}
		if (jObj.has(JsParams.INVOICE_REVENUE_REPORT_REQUEST.invoice_agency_id)) {
			invoice_agency_id = jObj.getInt(JsParams.INVOICE_REVENUE_REPORT_REQUEST.invoice_agency_id);
		}
		if(jObj.has(JsParams.INVOICE_CONVERSION_REQUEST.in_month)){
			SimpleDateFormat sdf = new SimpleDateFormat(Consts.DATE_FORMAT);
			in_month = sdf.parse(jObj.getString(JsParams.INVOICE_CONVERSION_REQUEST.in_month));
		}
	}

	Integer invoice_agency_id = 0;
	Date in_month = null;
	String username = null;

	@Override
	public JsonResponse execute() {
		InvoiceRevenueReportResponse resp = new InvoiceRevenueReportResponse();
		MessageDAO mDAO = new MessageDAO();
		String moreInfo = ":";
		try {
			/* MONTH GET INVOICE REVENUE */
			if(in_month == null) {
				throw new Exception("MISSING_PARAMS");
			}
			
			Date from_date = DateUtils.getFirstDayOfMonth(in_month);
			Date to_date = DateUtils.getLastDayOfMonth(in_month);

			/* GET SERIAL SET BY AGENCY ID IF EXISTS, GET ALL IF NOT EXIST */
			InvoiceAgencySerialRelationDAO iasrDAO = new InvoiceAgencySerialRelationDAO();
			BeanFilter iasrFilter = new BeanFilter(InvoiceAgencySerialRelation.class);
			if(invoice_agency_id != 0) {
				iasrFilter.setFilter(InvoiceAgencySerialRelation.invoice_agency_id, new IntegerEqualFilter(invoice_agency_id));
			}
			List<InvoiceAgencySerialRelation>  iasrList = iasrDAO.getBeans(iasrFilter);
			Set<String> serialSet = new HashSet<String>();
			for(InvoiceAgencySerialRelation e : iasrList) {
				serialSet.add(e.get(InvoiceAgencySerialRelation.serial));
			}
			
			/*CREATE DATE FILTER*/
			FilterCriteria dateCriteria = new FilterCriteria();
			dateCriteria.addFilter(new DateGreaterThanOrEqualFilter(from_date));
			dateCriteria.addFilter(new DateLessThanOrEqualFilter(to_date));
			
			/* CREATE FITLER FOR DATA LOAD*/
			BeanFilter _filter = new BeanFilter(DataLoad.class);
			/* Set Value for Filter */
			if(serialSet.size() != 0)
			_filter.setFilter(DataLoad.serial, new StringInFilter(serialSet));
			_filter.setFilterCriteria(DataLoad.create_date, dateCriteria);
			
			/* GET DATA FROM FILTER */
			DataLoadDAO dlDAO = new DataLoadDAO();
			long numRowOfData = dlDAO.countBeans(_filter);
			if(numRowOfData == 0)
				throw new Exception("REPORT_NO_DATA");
			else{
				/*PASSED ALL CONDITION BEFORE */
				/*CALL PROCESS TO GENERATE REPORT */
				/* creating a Task on BPM */
				
				String businessKey = BpmProcess.INVOICE_REVENUE_REPORT+ "-" + username;
				
				Map<String, Object> variables = new HashMap<String, Object>();
					variables.put(Consts.PROCESS_VARIABLES.username, username);
					variables.put(Consts.PROCESS_VARIABLES.in_month, in_month);
					variables.put(Consts.PROCESS_VARIABLES.invoice_agency_id, invoice_agency_id);
					variables.put(Consts.PROCESS_VARIABLES.process_unique, String.valueOf((new Date()).getTime()));
				
				/* start process */
				/* invoices will be generated in /geinvoice/src/main/java/vn/gmobile/einvoice/delegate/InvoiceRevenueReportGenerating.java class */
				BpmProcess.createProcessByKey(BpmProcess.INVOICE_REVENUE_REPORT, businessKey, variables);
			}
			resp.setSuccess(true);
		} catch (Exception exc) {
			exc.printStackTrace();
			resp.setSuccess(false);
			resp.setInfo(mDAO.getMessageByAcronym(exc.getMessage()).get(Message.TEXT)+(moreInfo!=":" ? moreInfo : ""));
		}
		return resp;
	}
}

