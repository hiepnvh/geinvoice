package vn.gmobile.einvoice.message;

import java.util.List;
import org.json.JSONObject;
import com.bean.base.BeanFilter;
import vn.gmobile.einvoice.db.InvoiceDetailStatusDAO;
import vn.gmobile.einvoice.model.InvoiceDetailStatus;

public class InvoiceDetailStatusGetRequest extends JsonRequest {

	/**
	 * @param jObj
	 * @throws Exception
	 */
	public InvoiceDetailStatusGetRequest(JSONObject jObj) throws Exception {
		super(jObj);
		_filter = new BeanFilter(InvoiceDetailStatus.class);
	}

	BeanFilter _filter;

	@Override
	public JsonResponse execute() {
		InvoiceDetailStatusGetResponse resp = new InvoiceDetailStatusGetResponse();
		try {
			InvoiceDetailStatusDAO dao = new InvoiceDetailStatusDAO();
			List<InvoiceDetailStatus> beans = dao.getBeans(_filter);
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
