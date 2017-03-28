package vn.gmobile.einvoice.message;

import java.util.List;
import org.json.JSONArray;
import com.bean.json.JsonUtils;
import vn.gmobile.einvoice.conf.JsParams;
import vn.gmobile.einvoice.model.InvoiceAgencySerialRelation;

public class SerialGetResponse extends JsonResponse {
	public void setResult(List<InvoiceAgencySerialRelation> result) throws Exception {
		JSONArray jArr = JsonUtils.fromBeanListToJsonArray(result);
		write(JsParams.SERIAL_GET_RESPONSE.result, jArr);
	}
}
