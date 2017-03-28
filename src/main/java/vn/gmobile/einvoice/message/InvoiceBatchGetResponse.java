package vn.gmobile.einvoice.message;

import java.util.List;
import org.json.JSONArray;
import com.bean.json.JsonUtils;
import vn.gmobile.einvoice.conf.JsParams;
import vn.gmobile.einvoice.model.InvoiceBatch;

public class InvoiceBatchGetResponse extends JsonResponse {
	public void setResult(List<InvoiceBatch> result) throws Exception {
		JSONArray jArr = JsonUtils.fromBeanListToJsonArray(result);
		write(JsParams.INVOICE_BATCH_GET_RESPONSE.result, jArr);
	}
}
