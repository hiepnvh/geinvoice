/**
 * 
 */
package vn.gmobile.einvoice.conf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bean.base.BeanFilter;

import vn.gmobile.einvoice.db.DbAdapter;
import vn.gmobile.einvoice.model.Message;




/**
 * @author QuangN
 * 
 */
public class MessageGroup {
		private static Map<String,String> MESSAGE_MAP;
		static {
			try {
				MESSAGE_MAP =  new HashMap<String,String>();
				DbAdapter dba = new DbAdapter();
				BeanFilter msgFilter = new BeanFilter(Message.class); 
				List<Message> messages =  dba.getBeans(msgFilter);
				for (Message msg: messages) {
					MESSAGE_MAP.put(msg.get(Message.ACRONYM),msg.get(Message.TEXT));
				}

				dba.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
			public static final String USER_INVALID = MESSAGE_MAP.get("USER_INVALID");
			public static final String NOT_LOGIN = MESSAGE_MAP.get("NOT_LOGIN");
			public static final String INVALID_USERNAME_PASSWORD = MESSAGE_MAP.get("INVALID_USERNAME_PASSWORD");
			public static final String USER_INACTIVE = MESSAGE_MAP.get("USER_INACTIVE");
			public static final String SYSTEM_ERROR = MESSAGE_MAP.get("SYSTEM_ERROR");
			public static final String USERNAME_EXIST = MESSAGE_MAP.get("USERNAME_EXIST");
			public static final String VIEW_ONLY = MESSAGE_MAP.get("VIEW_ONLY");
			public static final String LOOKUP_MAX_TIMES  = MESSAGE_MAP.get("LOOKUP_MAX_TIMES");
			public static final String MSISDN_NOT_EXIST = MESSAGE_MAP.get("MSISDN_NOT_EXIST");
			public static final String MAX_LOGIN_FAIL = MESSAGE_MAP.get("MAX_LOGIN_FAIL");
			public static final String FILE_NOT_EXIST = MESSAGE_MAP.get("FILE_NOT_EXIST");
			public static final String USER_NOT_EXIST_OR_EXPR = MESSAGE_MAP.get("USER_NOT_EXIST_OR_EXPR");
			public static final String OVER_QUOTA = MESSAGE_MAP.get("OVER_QUOTA");
}
