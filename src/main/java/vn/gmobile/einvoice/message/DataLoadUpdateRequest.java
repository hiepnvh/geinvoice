package vn.gmobile.einvoice.message;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import com.base.filter.BeanInnerJoinFilter;
import com.base.filter.StringEqualFilter;
import com.bean.base.BeanFilter;
import com.bean.json.JsonUtils;
import vn.gmobile.einvoice.conf.Consts;
import vn.gmobile.einvoice.conf.JsParams;
import vn.gmobile.einvoice.conf.ServerConfig;
import vn.gmobile.einvoice.conf.SystemParamGroup;
import vn.gmobile.einvoice.db.DataLoadDAO;
import vn.gmobile.einvoice.db.InvoiceAgencySerialRelationDAO;
import vn.gmobile.einvoice.db.MessageDAO;
import vn.gmobile.einvoice.model.DataLoad;
import vn.gmobile.einvoice.model.InvoiceAgencySerialRelation;
import vn.gmobile.einvoice.model.InvoiceAgencyUserRelation;
import vn.gmobile.einvoice.model.Message;
import vn.gmobile.einvoice.util.ExcelToJSON;
import vn.gmobile.einvoice.util.SumInWord;

public class DataLoadUpdateRequest extends JsonRequest {
	
	private final static Logger LOGGER = Logger.getLogger(DataLoadUpdateRequest.class.getName());
	/**
	 * @param jObj
	 * @throws Exception 
	 */
	public DataLoadUpdateRequest(JSONObject jObj) throws Exception {
		super(jObj);
		if (jObj.has(JsParams.DATA_LOAD_UPDATE_REQUEST.USERNAME)) {
			_username = jObj.getString(JsParams.DATA_LOAD_UPDATE_REQUEST.USERNAME);
		}
		if (jObj.has(JsParams.DATA_LOAD_UPDATE_REQUEST.file_path)) {
			_file_path = jObj.getString(JsParams.DATA_LOAD_UPDATE_REQUEST.file_path);
		}
		if(jObj.has(JsParams.DATA_LOAD_UPDATE_REQUEST.adjust)){
			adjust = jObj.getBoolean(JsParams.DATA_LOAD_GET_REQUEST.adjust);
		}
	}
	
	String _file_path = null;
	String _username = null;
	Boolean adjust = false;
	
	@Override
	public JsonResponse execute() {
		DataLoadUpdateResponse resp = new DataLoadUpdateResponse();
		MessageDAO mDAO = new MessageDAO();
		String moreInfo = "";
		List<Object> arr = new ArrayList<Object>();
		Object[] objArr = null;
		Set<Integer> adjustType = new HashSet<Integer>();
		adjustType.add(Consts.ADJUST_TYPE.INCREASE);
		adjustType.add(Consts.ADJUST_TYPE.DECREASE);
		try {
			if(_file_path == null) {
				throw new Exception("ERR_FILE_PATH_MISSING");
			}
			if(_username != null){
				/* DAO */
				DataLoadDAO dao = new DataLoadDAO();
				InvoiceAgencySerialRelationDAO iasrDAO = new InvoiceAgencySerialRelationDAO();
				
				/* FILTER */
				BeanFilter iaurFilter = new BeanFilter(InvoiceAgencyUserRelation.class);
				BeanFilter iasrFilter = new BeanFilter(InvoiceAgencySerialRelation.class);
				
				Set<String> serialPermisstionSet = new HashSet<String>();
				
				/* Get Serial By username */
				iaurFilter.setFilter(InvoiceAgencyUserRelation.username, new StringEqualFilter(_username));
				iasrFilter.setFilter(InvoiceAgencySerialRelation.invoice_agency_id, new BeanInnerJoinFilter(iaurFilter));
				List<InvoiceAgencySerialRelation> iasrList = iasrDAO.getBeans(iasrFilter);
				for (InvoiceAgencySerialRelation b : iasrList) {
					serialPermisstionSet.add(b.get(InvoiceAgencySerialRelation.serial));
				}
				
				JSONArray jsArr = ExcelToJSON.getJsonFromExcel(ServerConfig.getPhysicalDir()+_file_path);
				List<DataLoad> beans = new ArrayList<DataLoad>();
				if(adjust==false){
					for (int i=0;i<jsArr.length();i++) {
						JSONObject jsObj = new JSONObject(String.valueOf(jsArr.get(i)));
						/* With some fields, we have manual works */
						if(!jsObj.has("serial")){ 
							arr.add("serial");
							throw new Exception("COL_CANNOT_BLANK");
						} else if (!jsObj.has("grand_total")){
							arr.add("grand_total");
							throw new Exception("COL_CANNOT_BLANK");
						}
						
						DataLoad record = (DataLoad) JsonUtils.fromJsonToBean(jsObj, DataLoad.class);
						
						if(!serialPermisstionSet.contains(record.get(DataLoad.serial).trim())){
							throw new Exception("ERR_SERIAL_NO_PERMISSION");
						}
						if(record.get(DataLoad.parent_invoice_no)!=null || adjustType.contains(record.get(DataLoad.adjust_type))) {
							throw new Exception("ERR_INVOCE_ADJUST");
						}
						record.set(DataLoad.parent_invoice_no, Consts.DATA_LOAD.PARENT_ID_BLANK);
						beans.add(record);
					}
				} else if (adjust==true){
					for (int i=0;i<jsArr.length();i++) {
						JSONObject jsObj = new JSONObject(String.valueOf(jsArr.get(i)));
						DataLoad record = (DataLoad) JsonUtils.fromJsonToBean(jsObj, DataLoad.class);
						
						if(!serialPermisstionSet.contains(record.get(DataLoad.serial).trim())){
							throw new Exception("ERR_SERIAL_NO_PERMISSION");
						}
						if(record.get(DataLoad.parent_invoice_no)==null || !adjustType.contains(record.get(DataLoad.adjust_type))) {
							throw new Exception("ERR_INVOCE_NOT_ADJUST");
						}
						if(record.get(DataLoad.parent_invoice_no).length()!=SystemParamGroup.INVOICE_NO_LENGTH) {
							arr.add(SystemParamGroup.INVOICE_NO_LENGTH);
							arr.add(record.get(DataLoad.msisdn));
							throw new Exception("DATA_LOAD_PARENT_ID_LEN");
						}
						beans.add(record);
					}
				}
				for (DataLoad record : beans) {
					record.set(DataLoad.used, false);
					record.set(DataLoad.username, _username);
					record.set(DataLoad.sum_in_words, SumInWord.getText(Long.valueOf(record.get(DataLoad.grand_total))));
				}
				try {
					dao.updateBeans(beans);
				} catch (SQLException e) {
					// TODO: handle exception
					String errMsg = e.getMessage();
					LOGGER.info("e.getMessage()="+errMsg);
					e.printStackTrace();
					if(errMsg.contains("Duplicate")){
						moreInfo += errMsg.substring(errMsg.indexOf("'")+1, errMsg.indexOf("-"));
						throw new Exception("DATA_LOAD_EXISTED");
					} else if(errMsg.contains("cannot be null")){
						arr.add(errMsg.substring(errMsg.indexOf("'")+1, errMsg.lastIndexOf("'")));
						throw new Exception("COL_CANNOT_BLANK");
					}
				}
				resp.setSuccess(true);
			} else {
				throw new Exception("MISSING_PARAMS");
			}
		} catch (Exception exc) {
			LOGGER.info("exc.getMessage()="+exc.getMessage());
			exc.printStackTrace();
			resp.setSuccess(false);
			objArr = arr.toArray();
			resp.setInfo(String.format(mDAO.getMessageByAcronym(exc.getMessage()).get(Message.TEXT)+(moreInfo!=":" ? moreInfo : ""),objArr));
	}
	return resp;
	}
	
	public static void main(String[] args) {
		try {
			JSONObject jObj = new JSONObject("{\"mail\":\"hiep.nvh@gmobile.vn\",\"file_path\":\"1467099360326_1.DataLoad.xlsx\",\"userName\":\"anhta\",\"displayName\":\"Anh Ta Tuan\",\"mobile\":\"84996668000\"}");
			DataLoadUpdateRequest req = new DataLoadUpdateRequest(jObj);
			req.execute();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}

