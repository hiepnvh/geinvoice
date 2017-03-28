package vn.gmobile.einvoice.message;

import java.util.List;
import org.json.JSONObject;
import com.base.filter.BeanInnerJoinFilter;
import com.base.filter.StringEqualFilter;
import com.bean.base.BeanFilter;
import vn.gmobile.einvoice.conf.JsParams;
import vn.gmobile.einvoice.db.InvoiceAgencySerialRelationDAO;
import vn.gmobile.einvoice.model.InvoiceAgencySerialRelation;
import vn.gmobile.einvoice.model.InvoiceAgencyUserRelation;

public class SerialGetRequest extends JsonRequest {

	/**
	 * @param jObj
	 * @throws Exception
	 */
	public SerialGetRequest(JSONObject jObj) throws Exception {
		super(jObj);
		if(jObj.has(JsParams.SERIAL_GET_REQUEST.USERNAME)){
			username = jObj.getString(JsParams.SERIAL_GET_REQUEST.USERNAME);
		}
	}

	String username = null;

	@Override
	public JsonResponse execute() {
		SerialGetResponse resp = new SerialGetResponse();
		try {
			if(username != null) {
				InvoiceAgencySerialRelationDAO iasrDAO = new InvoiceAgencySerialRelationDAO();
				
				BeanFilter iaurFilter = new BeanFilter(InvoiceAgencyUserRelation.class);
				iaurFilter.setFilter(InvoiceAgencyUserRelation.username, new StringEqualFilter(username));
				
				BeanFilter iasrFilter = new BeanFilter(InvoiceAgencySerialRelation.class);
				iasrFilter.setFilter(InvoiceAgencySerialRelation.invoice_agency_id, new BeanInnerJoinFilter(iaurFilter));
				
				List<InvoiceAgencySerialRelation> beans = iasrDAO.getBeans(iasrFilter);
				resp.setResult(beans);
			}
			resp.setSuccess(true);
		} catch (Exception exc) {
			exc.printStackTrace();
			resp.setSuccess(false);
			resp.setInfo(exc.getMessage());
		}
		return resp;
	}
}
