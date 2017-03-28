package vn.gmobile.einvoice.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import com.base.filter.BeanInnerJoinFilter;
import com.base.filter.FilterCriteria;
import com.base.filter.IntegerEqualFilter;
import com.base.filter.IntegerGreaterThanOrEqualFilter;
import com.base.filter.IntegerLessThanOrEqualFilter;
import com.base.filter.StringLikeFilter;
import com.bean.base.BeanFilter;
import vn.gmobile.einvoice.conf.JsParams;
import vn.gmobile.einvoice.db.InvoiceBatchDAO;
import vn.gmobile.einvoice.db.InvoiceDetailDAO;
import vn.gmobile.einvoice.model.InvoiceBatch;
import vn.gmobile.einvoice.model.InvoiceDetailMore;

public class InvoiceDetailGetRequest extends JsonRequest {

	/**
	 * @param jObj
	 * @throws Exception
	 */
	public InvoiceDetailGetRequest(JSONObject jObj) throws Exception {
		super(jObj);
		if(jObj.has(JsParams.INVOICE_DETAIL_GET_REQUEST.serial)) {
			serial = jObj.getString(JsParams.INVOICE_DETAIL_GET_REQUEST.serial);
		}
		if(jObj.has(JsParams.INVOICE_DETAIL_GET_REQUEST.invoice_detail_status)) {
			status = jObj.getInt(JsParams.INVOICE_DETAIL_GET_REQUEST.invoice_detail_status);
		}
		if(jObj.has(JsParams.INVOICE_DETAIL_GET_REQUEST.invoice_no_from)) {
				invoice_no_from = jObj.getInt(JsParams.INVOICE_DETAIL_GET_REQUEST.invoice_no_from);
		}
		if(jObj.has(JsParams.INVOICE_DETAIL_GET_REQUEST.invoice_no_to)) {
			invoice_no_to = jObj.getInt(JsParams.INVOICE_DETAIL_GET_REQUEST.invoice_no_to);
		}
		if(jObj.has(JsParams.INVOICE_DETAIL_GET_REQUEST.limit) && jObj.has(JsParams.INVOICE_DETAIL_GET_REQUEST.start)){
			offset = jObj.getInt(JsParams.INVOICE_DETAIL_GET_REQUEST.start);
			limit = jObj.getInt(JsParams.INVOICE_DETAIL_GET_REQUEST.limit);
		}
	}

	String serial = null;
	Integer status = null, invoice_no_from = null, invoice_no_to = null;
	int offset = 0, limit = 25;
	
	@Override
	public JsonResponse execute() {
		InvoiceDetailGetResponse resp = new InvoiceDetailGetResponse();
		InvoiceDetailDAO idDAO = new InvoiceDetailDAO();
		InvoiceBatchDAO ibDAO = new InvoiceBatchDAO();
		try {
			BeanFilter ibFilter = new BeanFilter(InvoiceBatch.class);
			if(serial!=null){
				ibFilter.setFilter(InvoiceBatch.serial, new StringLikeFilter(serial));
			}
			List<InvoiceBatch> ibList = ibDAO.getBeans(ibFilter);
			Map<Integer, InvoiceBatch> ibMap = new HashMap<Integer, InvoiceBatch>();
			for(InvoiceBatch e : ibList){
				ibMap.put(e.get(InvoiceBatch.invoice_batch_id), e);
			}
			
			BeanFilter idFilter = new BeanFilter(InvoiceDetailMore.class);
			if(status!=null){
				idFilter.setFilter(InvoiceDetailMore.status, new IntegerEqualFilter(status));
			}
			FilterCriteria invoiceNoCriteria = new FilterCriteria();
			if(invoice_no_from!=null)
				invoiceNoCriteria.addFilter(new IntegerGreaterThanOrEqualFilter(invoice_no_from));	
			if(invoice_no_to!=null)
				invoiceNoCriteria.addFilter(new IntegerLessThanOrEqualFilter(invoice_no_to));
			idFilter.setFilterCriteria(InvoiceDetailMore.invoice_no, invoiceNoCriteria);
			idFilter.setFilter(InvoiceDetailMore.invoice_batch_id, new BeanInnerJoinFilter(ibFilter));
			
			long total = idDAO.countBeans(idFilter);
			idFilter.setLimit(offset, limit);
			List<InvoiceDetailMore> beans = idDAO.getBeansMore(idFilter);
			for(InvoiceDetailMore e : beans) {
				e.set(InvoiceDetailMore.symbol_of_invoice, ibMap.get(e.get(InvoiceDetailMore.invoice_batch_id)).get(InvoiceBatch.symbol_of_invoice));
				e.set(InvoiceDetailMore.serial, ibMap.get(e.get(InvoiceDetailMore.invoice_batch_id)).get(InvoiceBatch.serial));
			}
			resp.setResult(beans);
			resp.setTotal(total);
			
			resp.setSuccess(true);
		} catch (Exception exc) {
			exc.printStackTrace();
			resp.setSuccess(false);
			resp.setInfo(exc.getMessage());
		}
		return resp;
	}
}
