package vn.gmobile.einvoice.message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import com.bean.json.JsonUtils;
import vn.gmobile.einvoice.conf.JsParams;
import vn.gmobile.einvoice.model.DataLoad;

public class DataLoadGetResponse extends JsonResponse {
	public void setResult(List<DataLoad> result) throws Exception {
		JSONArray jArr = JsonUtils.fromBeanListToJsonArray(result);
		for(int i =0;i< jArr.length();i++){
			JSONObject e = jArr.getJSONObject(i);
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			SimpleDateFormat targetSdf = new SimpleDateFormat("dd-MM-yyyy");
			
			List<String> dateList = new ArrayList<String>();
			dateList.add("create_date");
			dateList.add("from_date");
			dateList.add("to_date");
			
			for (String dateStr : dateList) {
				if(e.has(dateStr)){
					Date date = sdf.parse(e.getString(dateStr));
					e.put(dateStr, targetSdf.format(date));
				}
			}
		}
		write(JsParams.DATA_LOAD_GET_RESPONSE.result, jArr);
	}
	public void setTotal(long total) throws Exception {
		write(JsParams.DATA_LOAD_GET_RESPONSE.total, total);
	}
}
