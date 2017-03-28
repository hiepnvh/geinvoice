package vn.gmobile.einvoice.message;

import org.json.JSONObject;

import vn.gmobile.einvoice.conf.JsParams;
import vn.gmobile.einvoice.model.UserLogin;

public class UserLoginInfoGetResponse extends JsonResponse {
	
	public void setUserLoginInfo(UserLogin result) throws Exception {
		JSONObject jsonObject = new JSONObject(result);
		write(JsParams.USER_LOGIN_INFO_GET_RESPONSE.USER_LOGIN_INFO, jsonObject);
	}
}
