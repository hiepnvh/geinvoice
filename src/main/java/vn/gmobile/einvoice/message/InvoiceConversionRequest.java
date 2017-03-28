package vn.gmobile.einvoice.message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.xmlbeans.impl.jam.mutable.MSourcePosition;
import org.json.JSONArray;
import org.json.JSONObject;

import com.base.filter.BeanInnerJoinFilter;
import com.base.filter.DateEqualFilter;
import com.base.filter.DateGreaterThanOrEqualFilter;
import com.base.filter.DateLessThanOrEqualFilter;
import com.base.filter.FilterCriteria;
import com.base.filter.IntegerEqualFilter;
import com.base.filter.IntegerInFilter;
import com.base.filter.StringEqualFilter;
import com.base.filter.StringInFilter;
import com.bean.base.BeanFilter;
import com.bean.json.JsonUtils;

import vn.gmobile.einvoice.BpmProcess;
import vn.gmobile.einvoice.conf.Consts;
import vn.gmobile.einvoice.conf.JsParams;
import vn.gmobile.einvoice.conf.ServerConfig;
import vn.gmobile.einvoice.conf.SystemParamGroup;
import vn.gmobile.einvoice.db.DataLoadDAO;
import vn.gmobile.einvoice.db.InvoiceBatchDAO;
import vn.gmobile.einvoice.db.InvoiceDetailDAO;
import vn.gmobile.einvoice.db.InvoiceFileDAO;
import vn.gmobile.einvoice.db.MessageDAO;
import vn.gmobile.einvoice.model.DataLoad;
import vn.gmobile.einvoice.model.InvoiceBatch;
import vn.gmobile.einvoice.model.InvoiceDetail;
import vn.gmobile.einvoice.model.InvoiceFile;
import vn.gmobile.einvoice.model.Message;
import vn.gmobile.einvoice.util.DateUtils;
import vn.gmobile.einvoice.util.ExcelToJSON;
import vn.gmobile.einvoice.util.StringUtils;

public class InvoiceConversionRequest extends JsonRequest {
	
	private final static Logger LOGGER = Logger.getLogger(InvoiceConversionRequest.class.getName());
	/**
	 * @param jObj
	 * @throws Exception 
	 */
	public InvoiceConversionRequest(JSONObject jObj) throws Exception {
		super(jObj);
		if(jObj.has(JsParams.INVOICE_CONVERSION_REQUEST.USERNAME)){
			username = jObj.getString(JsParams.INVOICE_CONVERSION_REQUEST.USERNAME);
		}
		if(jObj.has(JsParams.INVOICE_CONVERSION_REQUEST.in_month)){
			SimpleDateFormat sdf = new SimpleDateFormat(Consts.DATE_FORMAT);
			in_month = sdf.parse(jObj.getString(JsParams.INVOICE_CONVERSION_REQUEST.in_month));
		}
		if (jObj.has(JsParams.DATA_LOAD_UPDATE_REQUEST.file_path)) {
			file_path = jObj.getString(JsParams.DATA_LOAD_UPDATE_REQUEST.file_path);
		}
	}
	
	String username = null;
	Date in_month = null;
	String file_path = null;
	
	@Override
	public JsonResponse execute() {
		InvoiceBatchUpdateResponse resp = new InvoiceBatchUpdateResponse();
		MessageDAO mDAO = new MessageDAO();
		try {
			if(file_path == null) {
				throw new Exception("ERR_FILE_PATH_MISSING");
			}
			if(in_month == null) {
				throw new Exception("MISSING_PARAMS");
			}
			
			JSONArray jsArr = ExcelToJSON.getJsonFromExcel(ServerConfig.getPhysicalDir()+file_path);
			Set<String> msisdnSet = new HashSet<String>();
			for (int i=0;i<jsArr.length() && i<1000;i++) {
				JSONObject jsObj = new JSONObject(String.valueOf(jsArr.get(i)));
				if(jsObj.has(JsParams.INVOICE_CONVERSION_REQUEST.msisdn))
					msisdnSet.add(jsObj.getString(JsParams.INVOICE_CONVERSION_REQUEST.msisdn));
			}
			if(msisdnSet.size() == 0)
				throw new Exception("NO_DATA");
			
			Date from_date = DateUtils.getFirstDayOfMonth(in_month);
			Date to_date = DateUtils.getLastDayOfMonth(in_month);

			InvoiceFileDAO ifDAO = new InvoiceFileDAO();
			
			BeanFilter idFilter = new BeanFilter(InvoiceDetail.class);
			BeanFilter ifFilter = new BeanFilter(InvoiceFile.class);
			
			/*CREATE DATE FILTER*/
			FilterCriteria dateCriteria = new FilterCriteria();
			dateCriteria.addFilter(new DateGreaterThanOrEqualFilter(from_date));
			dateCriteria.addFilter(new DateLessThanOrEqualFilter(to_date));
			
			idFilter.setFilter(InvoiceDetail.status, new IntegerEqualFilter(Consts.INVOICE_DETAIL_STATUS.INVOICE_GERNERTED));
			
			ifFilter.setFilterCriteria(InvoiceFile.from_date, dateCriteria);
			ifFilter.setFilter(InvoiceFile.msisdn, new StringInFilter(msisdnSet));
			ifFilter.setFilter(InvoiceFile.invoice_detail_id, new BeanInnerJoinFilter(idFilter));
			/* GET DATA FROM FILTER */
			long numRowOfData = ifDAO.countBeans(ifFilter);
			if(numRowOfData == 0)
				throw new Exception("NO_DATA");
			else{
				/*PASSED ALL CONDITION BEFORE */
				/*CALL PROCESS TO GENERATE REPORT */
				/* creating a Task on BPM */
				String businessKey = BpmProcess.INVOICE_CONVERSION+ "-" + username;
				
				Map<String, Object> variables = new HashMap<String, Object>();
					variables.put(Consts.PROCESS_VARIABLES.username, username);
					variables.put(Consts.PROCESS_VARIABLES.in_month, in_month);
					variables.put(Consts.PROCESS_VARIABLES.file_path, file_path);
				/* start process */
				BpmProcess.createProcessByKey(BpmProcess.INVOICE_CONVERSION, businessKey, variables);
			}
			resp.setSuccess(true);
		} catch (Exception exc) {
			exc.printStackTrace();
			resp.setSuccess(false);
			resp.setInfo(String.format(mDAO.getMessageByAcronym(exc.getMessage()).get(Message.TEXT)));
	}
	return resp;
	}
	
	public static void main(String[] args) {
		try {
			JSONObject jObj = new JSONObject("{\"mail\":\"hiep.nvh@gmobile.vn\",\"file_path\":\"1467891375022_invoice_conversion_msisdn_template.xlsx\",\"in_month\":\"07-06-2016 00:00:00\",\"userName\":\"anhta\",\"displayName\":\"Anh Ta Tuan\",\"mobile\":\"84996668000\"}");
			InvoiceConversionRequest req = new InvoiceConversionRequest(jObj);
			req.execute();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}

