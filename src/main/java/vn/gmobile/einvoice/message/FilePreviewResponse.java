package vn.gmobile.einvoice.message;

import org.json.JSONArray;
import vn.gmobile.einvoice.conf.JsParams;

public class FilePreviewResponse extends JsonResponse {
	public void setResultList(JSONArray jArr) throws Exception {
		write(JsParams.FILE_PREVIEW_RESPONSE.preview_list, jArr);
	}
}
