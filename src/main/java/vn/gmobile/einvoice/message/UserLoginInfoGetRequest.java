package vn.gmobile.einvoice.message;

import java.util.logging.Logger;
import org.json.JSONObject;

import vn.gmobile.einvoice.conf.JsParams;
import vn.gmobile.einvoice.model.UserLogin;

public class UserLoginInfoGetRequest extends JsonRequest {
	
	private static Logger LOGGER = Logger.getLogger(UserLoginInfoGetRequest.class.getName());
	
	public UserLoginInfoGetRequest(JSONObject jObj) throws Exception {
		super(jObj);
		
		if (jObj.has(JsParams.USER_LOGIN_INFO_GET_REQUEST.USERNAME)) {
			userName = jObj.getString(JsParams.USER_LOGIN_INFO_GET_REQUEST.USERNAME);
		}
		if (jObj.has(JsParams.USER_LOGIN_INFO_GET_REQUEST.DISPLAYNAME)) {
			displayName = jObj.getString(JsParams.USER_LOGIN_INFO_GET_REQUEST.DISPLAYNAME);
		}
		if (jObj.has(JsParams.USER_LOGIN_INFO_GET_REQUEST.MOBILE)) {
			mobile = jObj.getString(JsParams.USER_LOGIN_INFO_GET_REQUEST.MOBILE);
		}
		if (jObj.has(JsParams.USER_LOGIN_INFO_GET_REQUEST.MAIL)) {
			mail = jObj.getString(JsParams.USER_LOGIN_INFO_GET_REQUEST.MAIL);
		}
	}
	
	String userName, displayName, mobile, mail;
	
	
	@Override
	public JsonResponse execute() {
		UserLoginInfoGetResponse resp = new UserLoginInfoGetResponse();
		try {
			UserLogin result = new UserLogin();
			
			result.setUserName(userName);
			result.setDisplayName(displayName);
			result.setMobile(mobile);
			result.setMail(mail);
		    
			resp.setUserLoginInfo(result);
			resp.setSuccess(true);
		} catch (Exception exc) {
		exc.printStackTrace();
		resp.setSuccess(false);
		resp.setInfo(exc.getMessage());
	}
	return resp;
	}
}
