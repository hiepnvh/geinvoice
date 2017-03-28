package vn.gmobile.einvoice.message;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.json.JSONObject;
import com.base.filter.StringEqualFilter;
import com.bean.base.BeanFilter;
import com.bean.json.JsonUtils;

import vn.gmobile.einvoice.conf.Consts;
import vn.gmobile.einvoice.conf.JsParams;
import vn.gmobile.einvoice.conf.SystemParamGroup;
import vn.gmobile.einvoice.db.InvoiceBatchDAO;
import vn.gmobile.einvoice.db.InvoiceDetailDAO;
import vn.gmobile.einvoice.model.InvoiceBatch;
import vn.gmobile.einvoice.model.InvoiceDetail;
import vn.gmobile.einvoice.util.StringUtils;

public class InvoiceBatchUpdateRequest extends JsonRequest {
	
	private final static Logger LOGGER = Logger.getLogger(InvoiceBatchUpdateRequest.class.getName());
	/**
	 * @param jObj
	 * @throws Exception 
	 */
	public InvoiceBatchUpdateRequest(JSONObject jObj) throws Exception {
		super(jObj);
		if(jObj.has(JsParams.INVOICE_BATCH_UPDATE_REQUEST.invoice_batch)){
			JSONObject jsObj = jObj.getJSONObject(JsParams.INVOICE_BATCH_UPDATE_REQUEST.invoice_batch);
			_invoice_batch =(InvoiceBatch) JsonUtils.fromJsonToBean(jsObj, InvoiceBatch.class);
		}
	}
	
	InvoiceBatch _invoice_batch = null;
	
	@Override
	public JsonResponse execute() {
		InvoiceBatchUpdateResponse resp = new InvoiceBatchUpdateResponse();
		try {
			/* INSERT DATA TO INVOICE BATCH */
			if(_invoice_batch != null){
				/* CHECK QUANTITY BEFORE UPDATE */
				InvoiceBatchDAO ibDAO = new InvoiceBatchDAO();
				BeanFilter filter = new BeanFilter(InvoiceBatch.class);
				filter.setFilter(InvoiceBatch.serial, new StringEqualFilter(_invoice_batch.get(InvoiceBatch.serial).trim()));
				List<InvoiceBatch> iblist = ibDAO.getBeans(filter);
				int totalQuantity = 0;
				for(InvoiceBatch ib : iblist){
					totalQuantity += ib.get(InvoiceBatch.quantity);
				}
				/* Set value to start_no */
				_invoice_batch.set(InvoiceBatch.start_no, totalQuantity + 1);
				
				ibDAO.updateBean(_invoice_batch);
				
				int startInvoice = _invoice_batch.get(InvoiceBatch.start_no);
				int endInvoice = startInvoice + _invoice_batch.get(InvoiceBatch.quantity);
				int invoiceBatchId = _invoice_batch.get(InvoiceBatch.invoice_batch_id);
				
				InvoiceDetailDAO idDAO = new InvoiceDetailDAO();
				List<InvoiceDetail> beans = new ArrayList<InvoiceDetail>();
				/* GENERATE INVOICE DETAIL */
				for (int i = startInvoice; i < endInvoice; i++) {
					InvoiceDetail e = new InvoiceDetail();
					e.set(InvoiceDetail.invoice_no, i);
					e.set(InvoiceDetail.status, Consts.INVOICE_DETAIL_STATUS.INITIALIZATION);
					e.set(InvoiceDetail.invoice_batch_id, invoiceBatchId);
					e.set(InvoiceDetail.data_load_id, Consts.DATA_LOAD.ID_BLANK);
					beans.add(e);
				}
				idDAO.updateBeans(beans);
				resp.setSuccess(true);
			} else {
				throw new Exception("MISSING_PARAMS");
			}
		} catch (Exception exc) {
			exc.printStackTrace();
			resp.setSuccess(false);
			resp.setInfo(exc.getMessage());
	}
	return resp;
	}

}

