/**
 * 
 */
package vn.gmobile.einvoice.conf;

public class Consts {
	
	public static final String DEFAULT_PASSWORD ="123456";
	public static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
	public static final String NUMBER_FORMAT = "(8499|84199)(\\d{7})";
	public static final String CURRENCY_FORMAT = "#.### VND";
	
	public static class STATE {
		public static final int ACTIVE= 1;
		public static final int INACTIVE= 0;
	}	
	
	/*HEADER*/
	public static class HEADER{
		public static final String userName = "X-GTEL-UserName";
		public static final String displayName = "X-GTEL-UserDisplayName";
		public static final String mobile = "X-GTEL-UserMobile";
		public static final String mail = "X-GTEL-UserMail";
	}
		
	/* INVOICE DETAIL STATUS */
	public static class INVOICE_DETAIL_STATUS {
		public static final int INITIALIZATION = 0;
		public static final int MAPPED = 1;
		public static final int INVOICE_GERNERTED = 2;
		public static final int INVOICE_TRANSFORMED = 3;
		public static final int INVOICE_DELETED = 11;
		public static final int INVOICE_LOST = 12;
		public static final int INVOICE_DESTROYED = 13;
	}
	
	/* DATA LOAD */
	public static class DATA_LOAD {
		public static final int ID_BLANK = 0;
		public static final String PARENT_ID_BLANK = "0";
	}
	
	/* ADJUST TYPE */
	public static class ADJUST_TYPE {
		public static final int INCREASE = 1;
		public static final int DECREASE = -1;
	}
	
	/* INVOICE PROCESS STATUS */
	public static class INVOICE_PROCESS {
		public static final int RUNNING = 1;
		public static final int FINISHED = 3;
	}
	
	/* INVOICE PROCESS TYPE */
	public static class INVOICE_PROCESS_TYPE {
		public static final int INVOICE_GENERATE = 1;
		public static final int INVOICE_REVENUE = 2;
	}
	
	/* ADJUST TYPE */
	public static class PROCESS_VARIABLES {
		public static final String username = "username";
		public static final String serial = "serial";
		public static final String adjust = "adjust";
		public static final String process_unique = "process_unique";
		public static final String in_month = "in_month";
		public static final String file_path = "file_path";
		public static final String file_link = "file_link";
		public static final String quantity = "quantity";
		public static final String duration = "duration";
		public static final String finish = "finish";
		public static final String invoice_agency_id = "invoice_agency_id";
	}
}
