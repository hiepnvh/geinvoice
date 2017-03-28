package vn.gmobile.einvoice.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import com.bean.json.JsonUtils;

import vn.gmobile.einvoice.conf.Consts;
import vn.gmobile.einvoice.conf.JsParams;
import vn.gmobile.einvoice.conf.SystemParamGroup;
import vn.gmobile.einvoice.message.JsonRequest;
import vn.gmobile.einvoice.message.JsonResponse;

/** * Servlet implementation class LoginServlet */
public class ActionServlet extends HttpServlet {
	protected static final Logger LOGGER = Logger.getLogger(ActionServlet.class
			.getName());

	Map<String,Class> _messageClassMap; 
	
	
	public synchronized void doPost(HttpServletRequest request,
			HttpServletResponse response) {

		_messageClassMap =  new HashMap<String,Class> ();
		Enumeration<String> initNames = this.getInitParameterNames();
		
//		Enumeration<String> headerNames = request.getHeaderNames();
		String userName = null, displayName = null, mobile = null, mail = null;
		try{
			if(SystemParamGroup.DB_PRODUCT) {
//			if(1==0){
				userName = request.getHeader(Consts.HEADER.userName);
				displayName = request.getHeader(Consts.HEADER.displayName);
				mobile = request.getHeader(Consts.HEADER.mobile);
				mail = request.getHeader(Consts.HEADER.mail);
			} else {
				userName = "anhta";
				displayName = "Anh Ta Tuan";
				mobile = "84996668000";
				mail = "hiep.nvh@gmobile.vn";
			}
			
			LOGGER.info("userName : " + userName);
			if(userName ==null)
				throw new Exception("ERROR: Fail in authentication, null user id");
					
			while (initNames.hasMoreElements()) {
				String initName = initNames.nextElement();
				String messageClassName = this.getInitParameter(initName);
				try {
					_messageClassMap.put(initName, Class.forName(messageClassName));
				} catch (Exception exc) {
					LOGGER.info("Cannot create message class of parameter " + initName +" class =" + messageClassName +" error "+ exc);
				}
			}
			JsonUtils.setDateFormat(Consts.DATE_FORMAT);
			try {
				JsonUtils.setDateFormat(Consts.DATE_FORMAT);
				JSONObject jObj = JsonUtils.fromMapToJson(request.getParameterMap());
				
				jObj.put(JsParams.USER_LOGIN_INFO_GET_REQUEST.USERNAME, String.valueOf(userName));
				jObj.put(JsParams.USER_LOGIN_INFO_GET_REQUEST.DISPLAYNAME, String.valueOf(displayName));
				jObj.put(JsParams.USER_LOGIN_INFO_GET_REQUEST.MOBILE, String.valueOf(mobile));
				jObj.put(JsParams.USER_LOGIN_INFO_GET_REQUEST.MAIL, String.valueOf(mail));
				
				String pathInfo = request.getServletPath();
				LOGGER.info("Servlet "+ pathInfo + " input :"+jObj.toString());
				Class messageClass = _messageClassMap.get(pathInfo);
				if (messageClass==null) {
					LOGGER.info("Cannot find message class for path " + pathInfo);
					throw new Exception("System error");
				}
				Class[] paramsArray = {JSONObject.class};
				Constructor constrc = messageClass.getConstructor(paramsArray);
				Object[] input  = {jObj};
				
				LOGGER.info("Input:"+input.toString());
				JsonRequest jRequest = (JsonRequest) constrc.newInstance(input);
				JsonResponse jResponse  =  jRequest.execute();
				response.setHeader("Access-Control-Allow-Origin", "*");
				response.setContentType("text/html; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				LOGGER.info("Output :"+jResponse.getJsonObject().toString());
				response.getWriter().write(jResponse.getJsonObject().toString());

			} catch (Exception ex) {
				LOGGER.info("Action servlet error message:" + ex.getMessage());
				StringWriter sw = new StringWriter();
				ex.printStackTrace(new PrintWriter(sw));
				LOGGER.info("Action servlet error stacktrace:" + sw.toString());
			} 
		}catch(Exception exc){
			LOGGER.info("ERROR: Fail in authentication");
		}

	}

}
