package vn.gmobile.einvoice.message;

import java.util.List;
import org.json.JSONObject;
import com.bean.base.BeanFilter;

import vn.gmobile.einvoice.db.InvoiceAgencyDAO;
import vn.gmobile.einvoice.model.InvoiceAgency;

public class InvoiceAgencyGetRequest extends JsonRequest {

	/**
	 * @param jObj
	 * @throws Exception
	 */
	public InvoiceAgencyGetRequest(JSONObject jObj) throws Exception {
		super(jObj);
		_filter = new BeanFilter(InvoiceAgency.class);
	}

	BeanFilter _filter;

	@Override
	public JsonResponse execute() {
		InvoiceAgencyGetResponse resp = new InvoiceAgencyGetResponse();
		try {
			InvoiceAgencyDAO dao = new InvoiceAgencyDAO();
			List<InvoiceAgency> beans = dao.getBeans(_filter);
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
