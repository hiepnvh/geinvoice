package vn.gmobile.einvoice.message;

import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import vn.gmobile.einvoice.conf.JsParams;
import vn.gmobile.einvoice.conf.ServerConfig;
import vn.gmobile.einvoice.util.ExcelToJSON;

public class FilePreviewRequest extends JsonRequest {
	
	private static Logger LOGGER = Logger.getLogger(FilePreviewRequest.class.getName());
	
	public FilePreviewRequest(JSONObject jObj) throws Exception {
		super(jObj);
		
		if (jObj.has(JsParams.FILE_PREVIEW_REQUEST.file_path)) {
			_file_path = jObj.getString(JsParams.FILE_PREVIEW_REQUEST.file_path);
		}
	}
	
	String _file_path = null;
	int _rowPreviewNum = 20;
	
	@Override
	public JsonResponse execute() {
		FilePreviewResponse resp = new FilePreviewResponse();
		try {
			
			if(_file_path != null) {
				JSONArray result = ExcelToJSON.getJsonFromExcelPreview(ServerConfig.getPhysicalDir()+_file_path,_rowPreviewNum);
				resp.setResultList(result);
				resp.setSuccess(true);
			} else {
				resp.setSuccess(false);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
			resp.setSuccess(false);
			resp.setInfo(exc.getMessage());
		}
		return resp;
	}
}
