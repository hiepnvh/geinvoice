package vn.gmobile.einvoice.message;

import java.util.List;
import org.json.JSONObject;
import com.base.filter.StringLikeFilter;
import com.bean.base.BeanFilter;
import vn.gmobile.einvoice.conf.JsParams;
import vn.gmobile.einvoice.db.InvoiceBatchDAO;
import vn.gmobile.einvoice.model.InvoiceBatch;

public class InvoiceBatchGetRequest extends JsonRequest {

	/**
	 * @param jObj
	 * @throws Exception
	 */
	public InvoiceBatchGetRequest(JSONObject jObj) throws Exception {
		super(jObj);
		_filter = new BeanFilter(InvoiceBatch.class);
		if(jObj.has(JsParams.INVOICE_BATCH_GET_REQUEST.serial)){
			_filter.setFilter(InvoiceBatch.serial, new StringLikeFilter(jObj.getString(JsParams.INVOICE_BATCH_GET_REQUEST.serial)));
		}
	}

	BeanFilter _filter;

	@Override
	public JsonResponse execute() {
		InvoiceBatchGetResponse resp = new InvoiceBatchGetResponse();
		try {
			InvoiceBatchDAO dao = new InvoiceBatchDAO();
			List<InvoiceBatch> beans = dao.getBeans(_filter);
			resp.setResult(beans);
			resp.setSuccess(true);
		} catch (Exception exc) {
			exc.printStackTrace();
			resp.setSuccess(false);
			resp.setInfo(exc.getMessage());
		}
		return resp;
	}
}
