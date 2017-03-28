package vn.gmobile.einvoice.message;

import java.util.List;
import org.json.JSONArray;
import com.bean.json.JsonUtils;
import vn.gmobile.einvoice.conf.JsParams;
import vn.gmobile.einvoice.model.InvoiceDetail;
import vn.gmobile.einvoice.model.InvoiceDetailMore;

public class InvoiceDetailGetResponse extends JsonResponse {
	public void setResult(List<InvoiceDetailMore> result) throws Exception {
		JSONArray jArr = JsonUtils.fromBeanListToJsonArray(result);
		write(JsParams.INVOICE_BATCH_GET_RESPONSE.result, jArr);
	}
	
	public void setTotal(long total) throws Exception {
		write(JsParams.DATA_LOAD_GET_RESPONSE.total, total);
	}
}
