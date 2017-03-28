package vn.gmobile.einvoice.message;

import org.json.JSONArray;
import vn.gmobile.einvoice.conf.JsParams;

public class InvoiceFileGetResponse extends JsonResponse {

	public void setUrl(String url) throws Exception {
		write(JsParams.INVOICE_FILE_GET_RESPONSE.url, url);
	}
}
