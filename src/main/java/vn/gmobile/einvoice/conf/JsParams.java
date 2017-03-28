package vn.gmobile.einvoice.conf;

public class JsParams {
	
	public static class GENERAL_REQUEST {
		public static final String WEBAPP_ID = "webapp_id";
		public static final String USERNAME = "userName";
	}
	
	public static class GENERAL_RESPONSE {
		public static final String SUCCESS = "success";
		public static final String INFO = "info";
		public static final String ERR_CODE = "err_code";
	}	

	public static class PAGING extends GENERAL_REQUEST  {
		public static final String START = "start";
		public static final String LIMIT = "limit";
	}
	
	/*USER LOGIN INFO*/
	public static class USER_LOGIN_INFO_GET_REQUEST extends GENERAL_REQUEST  {
		public static final String USERNAME = "userName";
		public static final String DISPLAYNAME = "displayName";
		public static final String MOBILE = "mobile";
		public static final String MAIL = "mail";
	}
	public static class USER_LOGIN_INFO_GET_RESPONSE extends GENERAL_RESPONSE{
		public static final String USER_LOGIN_INFO = "user_login_info";
	}
	
	/* FILE */
	public static class FILE_PREVIEW_REQUEST extends GENERAL_REQUEST  {
		public static final String file_path = "file_path";
	}
	public static class FILE_PREVIEW_RESPONSE extends GENERAL_RESPONSE{
		public static final String preview_list = "preview_list";
		public static final String url = "url";
	}
	
	/***************************************************************/
	/* DATA LOAD */
	public static class DATA_LOAD_UPDATE_REQUEST extends GENERAL_REQUEST  {
		public static final String file_path = "file_path";
		public static final String adjust = "adjust";
	}
	public static class DATA_LOAD_UPDATE_RESPONSE extends GENERAL_RESPONSE{
	}
	public static class DATA_LOAD_GET_REQUEST extends GENERAL_REQUEST  {
		public static final String msisdn = "msisdn";
		public static final String used = "used";
		public static final String serial = "serial";
		
		public static final String start = "start";
		public static final String limit = "limit";
		public static final String adjust = "adjust";
		public static final String adjust_type = "adjust_type";
	}
	public static class DATA_LOAD_GET_RESPONSE extends GENERAL_RESPONSE{
		public static final String result = "result";
		public static final String total = "total";
	}
	
	/* INVOICE AGENCY */
	public static class INVOICE_AGENCY_GET_REQUEST extends GENERAL_REQUEST{
	}
	
	public static class INVOICE_AGENCY_GET_RESPONSE extends GENERAL_RESPONSE{
		public static final String result = "result";
		public static final String total = "total";
	}
	
	/* INVOICE */
	public static class INVOICE_BATCH_UPDATE_REQUEST extends GENERAL_REQUEST  {
		public static final String invoice_batch = "invoice_batch";
	}
	public static class INVOICE_BATCH_UPDATE_RESPONSE extends GENERAL_RESPONSE{
	}
	
	public static class INVOICE_BATCH_GET_REQUEST extends GENERAL_REQUEST  {
		public static final String serial = "serial";
	}
	public static class INVOICE_BATCH_GET_RESPONSE extends GENERAL_RESPONSE{
		public static final String result = "result";
	}
	
	public static class INVOICE_DETAIL_GET_REQUEST extends GENERAL_REQUEST  {
		public static final String params = "params";
		public static final String serial = "serial";
		public static final String invoice_detail_status = "invoice_detail_status";
		public static final String invoice_no_from = "invoice_no_from";
		public static final String invoice_no_to = "invoice_no_to";
		public static final String start = "start";
		public static final String limit = "limit";
	}
	public static class INVOICE_DETAIL_GET_RESPONSE extends GENERAL_RESPONSE{
		public static final String result = "result";
	}
	
	public static class INVOICE_UPDATE_REQUEST extends GENERAL_REQUEST  {
		public static final String params = "params";
		public static final String serial = "serial";
		public static final String invoice_no_from = "invoice_no_from";
		public static final String invoice_no_to = "invoice_no_to";
		public static final String old_status = "old_status";
		public static final String status = "status";
	}
	public static class INVOICE_BATCH_REMOVE_RESPONSE extends GENERAL_RESPONSE{
	}
	
	public static class INVOICE_CONVERSION_REQUEST extends GENERAL_REQUEST  {
		public static final String in_month = "in_month";
		public static final String file_path = "file_path";
		public static final String msisdn = "msisdn";
		public static final String msisdn_list = "msisdn_list";
	}
	public static class INVOICE_CONVERSION_RESPONSE extends GENERAL_RESPONSE{
	}
	
	public static class INVOICE_GENERATE_REQUEST extends GENERAL_REQUEST  {
		public static final String serial = "serial";
		public static final String adjust = "adjust";
	}
	public static class INVOICE_GENERATE_RESPONSE extends GENERAL_RESPONSE{
	}
	
	public static class INVOICE_FILE_GET_REQUEST extends GENERAL_REQUEST  {
		public static final String file_path = "file_path";
		public static final String in_month = "in_month";
	}
	public static class INVOICE_FILE_GET_RESPONSE extends GENERAL_RESPONSE{

		public static final String url = "url";
	}
	
	/* INVOICE REPORT */
	public static class INVOICE_REVENUE_REPORT_REQUEST extends GENERAL_REQUEST  {
		public static final String invoice_agency_id = "invoice_agency_id";
		public static final String from_create_date = "from_create_date";
		public static final String to_create_date = "to_create_date";
		public static final String symbol_of_invoice = "symbol_of_invoice";
		public static final String serial = "serial";
	}
	public static class INVOICE_REVENUE_REPORT_RESPONSE extends GENERAL_RESPONSE{
	}
	
	public static class INVOICE_USAGE_REPORT_REQUEST extends GENERAL_REQUEST  {
		public static final String invoice_agency_id = "invoice_agency_id";
		public static final String in_month = "in_month";
		public static final String fixed = "fixed";
	}
	public static class INVOICE_USAGE_REPORT_RESPONSE extends GENERAL_RESPONSE{
		public static final String file_path = "file_path";
	}
	
	/* SERIAL */
	public static class SERIAL_GET_REQUEST extends GENERAL_REQUEST  {
	}
	public static class SERIAL_GET_RESPONSE extends GENERAL_RESPONSE{
		public static final String result = "result";
		public static final String total = "total";
	}
}
