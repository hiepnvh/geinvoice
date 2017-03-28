package vn.gmobile.einvoice.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JavaObjAndJSON {
	/**
	 * author : anhta
	 * @param Object obj
	 * @return JSON String
	 */
	public static String javaObjToJSONString(Object obj){
		String jsonString = null;
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
		try {
			/*Convert Java object to JSON string*/
			jsonString = gson.toJson(obj);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return jsonString;
	}
	
	/**
	 * author : anhta
	 * @param <T>
	 * @param Object obj
	 * @return JSON String
	 */
	public static <T> Object jsonStringToJavaObject(String jsonString, Class<T> objClass){
		Object obj = null;
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
		try {
			/*Convert JSON string to Java Obj*/
			obj = gson.fromJson(jsonString, objClass);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return obj;
	}
}
