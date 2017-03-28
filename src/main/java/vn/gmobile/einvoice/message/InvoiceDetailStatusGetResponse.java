package vn.gmobile.einvoice.message;

import java.util.List;
import org.json.JSONArray;
import com.bean.json.JsonUtils;
import vn.gmobile.einvoice.conf.JsParams;
import vn.gmobile.einvoice.model.InvoiceDetailStatus;

public class InvoiceDetailStatusGetResponse extends JsonResponse {
	public void setResult(List<InvoiceDetailStatus> result) throws Exception {
		JSONArray jArr = JsonUtils.fromBeanListToJsonArray(result);
		write(JsParams.INVOICE_AGENCY_GET_RESPONSE.result, jArr);
	}
}
