package vn.gmobile.einvoice.message;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.json.JSONObject;

import com.base.filter.BaseFilter;
import com.base.filter.BeanInnerJoinFilter;
import com.base.filter.BooleanEqualFilter;
import com.base.filter.DateLessThanOrEqualFilter;
import com.base.filter.IntegerEqualFilter;
import com.base.filter.IsNullFilter;
import com.base.filter.StringEqualFilter;
import com.base.filter.StringInFilter;
import com.bean.base.BeanFilter;
import vn.gmobile.einvoice.BpmProcess;
import vn.gmobile.einvoice.conf.Consts;
import vn.gmobile.einvoice.conf.JsParams;
import vn.gmobile.einvoice.db.DataLoadDAO;
import vn.gmobile.einvoice.db.InvoiceAgencyDAO;
import vn.gmobile.einvoice.db.InvoiceAgencySerialRelationDAO;
import vn.gmobile.einvoice.db.InvoiceBatchDAO;
import vn.gmobile.einvoice.db.InvoiceDetailDAO;
import vn.gmobile.einvoice.db.InvoiceProcessDAO;
import vn.gmobile.einvoice.db.MessageDAO;
import vn.gmobile.einvoice.model.DataLoad;
import vn.gmobile.einvoice.model.InvoiceAgency;
import vn.gmobile.einvoice.model.InvoiceAgencySerialRelation;
import vn.gmobile.einvoice.model.InvoiceAgencyUserRelation;
import vn.gmobile.einvoice.model.InvoiceBatch;
import vn.gmobile.einvoice.model.InvoiceDetail;
import vn.gmobile.einvoice.model.InvoiceProcess;
import vn.gmobile.einvoice.model.Message;

public class InvoiceGenerateRequest extends JsonRequest {
	
	private final static Logger LOGGER = Logger.getLogger(InvoiceGenerateRequest.class.getName());
	/**
	 * @param jObj
	 * @throws Exception 
	 */
	public InvoiceGenerateRequest(JSONObject jObj) throws Exception {
		super(jObj);
		if (jObj.has(JsParams.INVOICE_GENERATE_REQUEST.USERNAME)) {
			_username = jObj.getString(JsParams.INVOICE_GENERATE_REQUEST.USERNAME);
		}
		if (jObj.has(JsParams.INVOICE_GENERATE_REQUEST.serial)) {
			serial = jObj.getString(JsParams.INVOICE_GENERATE_REQUEST.serial);
		}
		if(jObj.has(JsParams.INVOICE_GENERATE_REQUEST.adjust)){
			adjust = jObj.getBoolean(JsParams.INVOICE_GENERATE_REQUEST.adjust);
		}
	}
	
	String _username = null, serial = null;
	Boolean adjust = false;

	@Override
	public JsonResponse execute() {
		InvoiceGenerateResponse resp = new InvoiceGenerateResponse();
		
		MessageDAO mDAO = new MessageDAO();
		List<Object> arr = new ArrayList<Object>();
		Object[] objArr = null;
		try {
			if(_username != null && serial != null){
				/* Check process is running with serial */
				InvoiceProcessDAO ipDAO = new InvoiceProcessDAO();
				BeanFilter ipFilter = new BeanFilter(InvoiceProcess.class);
				ipFilter.setFilter(InvoiceProcess.serial, new StringEqualFilter(serial));
				ipFilter.setFilter(InvoiceProcess.invoice_process_type, new IntegerEqualFilter(Consts.INVOICE_PROCESS_TYPE.INVOICE_GENERATE));
				ipFilter.setFilter(InvoiceProcess.status, new IntegerEqualFilter(Consts.INVOICE_PROCESS.RUNNING));
				if(ipDAO.countBeans(ipFilter)>0){
					arr.add(serial);
					objArr = arr.toArray();
					throw new Exception("ERR_INVOICE_PROCESS_RUNNING");
				}
				
				/* DAO */
				DataLoadDAO dlDAO = new DataLoadDAO();
				InvoiceBatchDAO ibDAO = new InvoiceBatchDAO();
				InvoiceDetailDAO idDAO = new InvoiceDetailDAO();
				InvoiceAgencyDAO iaDAO = new InvoiceAgencyDAO();
				InvoiceAgencySerialRelationDAO iasrDAO = new InvoiceAgencySerialRelationDAO();
				
				/* FILTER */
				BeanFilter dlfilter = new BeanFilter(DataLoad.class);
				BeanFilter ibfilter = new BeanFilter(InvoiceBatch.class);
				BeanFilter idfilter = new BeanFilter(InvoiceDetail.class);
				BeanFilter iafilter = new BeanFilter(InvoiceAgency.class);
				BeanFilter iaurFilter = new BeanFilter(InvoiceAgencyUserRelation.class);
				BeanFilter iasrFilter = new BeanFilter(InvoiceAgencySerialRelation.class);
				
				/* serialPermisstionSet for Serials of User in Agency */
				Set<String> serialPermisstionSet = new HashSet<String>();
				/* Get Serial By username */
				iaurFilter.setFilter(InvoiceAgencyUserRelation.username, new StringEqualFilter(_username));
				iasrFilter.setFilter(InvoiceAgencySerialRelation.invoice_agency_id, new BeanInnerJoinFilter(iaurFilter));
				List<InvoiceAgencySerialRelation> iasrList = iasrDAO.getBeans(iasrFilter);
				for (InvoiceAgencySerialRelation b : iasrList) {
					serialPermisstionSet.add(b.get(InvoiceAgencySerialRelation.serial));
				}
				
				if(!serialPermisstionSet.contains(serial)) {
					throw new Exception("ERR_SERIAL_NO_PERMISSION");
				}
				
				/* RETRIEVE DATA InvoiceAgency */
				Map<Integer, InvoiceAgency> iaMap = new HashMap<Integer, InvoiceAgency>();
				List<InvoiceAgency> iaList = iaDAO.getBeans(iafilter);
				for (InvoiceAgency ia : iaList) {
					iaMap.put(ia.get(InvoiceAgency.invoice_agency_id), ia);
				}
				
				/* compare quantity of dataload & invoice */
				/* get quantity of data load */
				dlfilter.setFilter(DataLoad.username, new StringEqualFilter(_username));
				dlfilter.setFilter(DataLoad.serial, new StringEqualFilter(serial));
				dlfilter.setFilter(DataLoad.used, new BooleanEqualFilter(false));
				if(adjust) {
					BaseFilter<?> strinNotEqualFilter = new StringEqualFilter(Consts.DATA_LOAD.PARENT_ID_BLANK);
					strinNotEqualFilter.setNegated(true);
					dlfilter.setFilter(DataLoad.parent_invoice_no, strinNotEqualFilter);
				} else {
					dlfilter.setFilter(DataLoad.parent_invoice_no, new StringEqualFilter(Consts.DATA_LOAD.PARENT_ID_BLANK));
				}
				long dlQuantity = dlDAO.countBeans(dlfilter);
				if(dlQuantity==0) {
					throw new Exception("ERR_DATA_LOAD_OUT_STOCK");
				}
				
				/* get quantity of invoice */
				ibfilter.setFilter(InvoiceBatch.serial, new StringEqualFilter(serial));
				Date now = new Date(); // date allow for generate invoices before now
				ibfilter.setFilter(InvoiceBatch.invoice_created_date, new DateLessThanOrEqualFilter(now));
				idfilter.setFilter(InvoiceDetail.invoice_batch_id, new BeanInnerJoinFilter(ibfilter));
				idfilter.setFilter(InvoiceDetail.status, new IntegerEqualFilter(Consts.INVOICE_DETAIL_STATUS.INITIALIZATION));
				long idQuantity = idDAO.countBeans(idfilter);
				if(idQuantity==0) {
					throw new Exception("ERR_INVOICE_OUT_STOCK");
				}
				
				LOGGER.info("Generate Request check:"+"idQuantity="+idQuantity +";dlQuantity="+dlQuantity);
				
				if(idQuantity < dlQuantity ) {
					arr.add(idQuantity);
					arr.add(dlQuantity);
					objArr = arr.toArray();
					throw new Exception("ERR_INVOICE_NOT_ENOUGH");
				}
				
				/*PASSED ALL CONDITION BEFORE GENERATING INVOICE*/
				/*CALL PROCESS TO GENERATE INVOICE*/
				/* creating a Task on BPM for approval */
				
				String businessKey = BpmProcess.INVOICE_GENERATE + "-" + _username;
				
				Map<String, Object> variables = new HashMap<String, Object>();
					variables.put(Consts.PROCESS_VARIABLES.username, _username);
					variables.put(Consts.PROCESS_VARIABLES.serial, serial);
					variables.put(Consts.PROCESS_VARIABLES.adjust, adjust);
					variables.put(Consts.PROCESS_VARIABLES.process_unique, String.valueOf((new Date()).getTime()));
				
				/* start process */
				/* invoices will be generated in vn.gmobile.einvoice.delegate.InvoiceGenerating class */
				BpmProcess.createProcessByKey(BpmProcess.INVOICE_GENERATE_NOTIMER, businessKey, variables);
				resp.setSuccess(true);
			} else {
				resp.setSuccess(false);
				resp.setInfo("Missing params!");
			}
		} catch (Exception exc) {
			exc.printStackTrace();
			resp.setSuccess(false);
			resp.setInfo(String.format(mDAO.getMessageByAcronym(exc.getMessage()).get(Message.TEXT),objArr));
		}
	return resp;
	}
	
	public static void main(String[] args) throws Exception {
		JSONObject jObj = new JSONObject("{userName:anhta}");
		InvoiceGenerateRequest rq = new InvoiceGenerateRequest(jObj);
		rq.execute();
	}

}

