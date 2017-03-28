package vn.gmobile.einvoice.message;


import org.json.JSONObject;

import vn.gmobile.einvoice.conf.JsParams;

public  abstract class JsonRequest  {
	
	String _webapp_id;
	String _userName;
	
	public JsonRequest(JSONObject jObj) throws Exception {
		if (jObj.has(JsParams.GENERAL_REQUEST.USERNAME)) {
			_userName = jObj.getString(JsParams.GENERAL_REQUEST.USERNAME);
		}
		if (jObj.has(JsParams.GENERAL_REQUEST.WEBAPP_ID)) {
			_webapp_id = jObj.getString(JsParams.GENERAL_REQUEST.WEBAPP_ID);
		} else
			_webapp_id = null;
	}
	
	public final String getWebappId()  {
		return _webapp_id;
	}
	
	public abstract JsonResponse execute() ;

	
}
