package vn.gmobile.einvoice.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import org.json.JSONObject;

import com.base.filter.BaseFilter;
import com.base.filter.BeanInnerJoinFilter;
import com.base.filter.DateGreaterThanOrEqualFilter;
import com.base.filter.DateLessThanOrEqualFilter;
import com.base.filter.FilterCriteria;
import com.base.filter.IntegerEqualFilter;
import com.base.filter.IntegerGreaterThanOrEqualFilter;
import com.base.filter.IntegerInFilter;
import com.base.filter.IntegerLessThanOrEqualFilter;
import com.base.filter.StringEqualFilter;
import com.bean.base.BeanFilter;
import vn.gmobile.einvoice.conf.Consts;
import vn.gmobile.einvoice.conf.JsParams;
import vn.gmobile.einvoice.db.DataLoadDAO;
import vn.gmobile.einvoice.db.DataLoadDelHisDAO;
import vn.gmobile.einvoice.db.InvoiceDetailDAO;
import vn.gmobile.einvoice.db.InvoiceFileDAO;
import vn.gmobile.einvoice.model.DataLoad;
import vn.gmobile.einvoice.model.DataLoadDelHis;
import vn.gmobile.einvoice.model.InvoiceBatch;
import vn.gmobile.einvoice.model.InvoiceDetail;
import vn.gmobile.einvoice.model.InvoiceFile;
import vn.gmobile.einvoice.util.DateUtils;

public class InvoiceUpdateRequest extends JsonRequest {
	
	private final static Logger LOGGER = Logger.getLogger(InvoiceUpdateRequest.class.getName());
	/**
	 * @param jObj
	 * @throws Exception 
	 */
	public InvoiceUpdateRequest(JSONObject jObj) throws Exception {
		super(jObj);
		if(jObj.has(JsParams.INVOICE_UPDATE_REQUEST.params)){
			JSONObject jsObj = jObj.getJSONObject(JsParams.INVOICE_UPDATE_REQUEST.params);
			if(jsObj.has(JsParams.INVOICE_UPDATE_REQUEST.serial)){
				Object o = jsObj.get(JsParams.INVOICE_UPDATE_REQUEST.serial);
				if(o instanceof String)
					serial = jsObj.getString(JsParams.INVOICE_UPDATE_REQUEST.serial);
			}
			if(jsObj.has(JsParams.INVOICE_UPDATE_REQUEST.invoice_no_from)){
				Object o = jsObj.get(JsParams.INVOICE_UPDATE_REQUEST.invoice_no_from);
				if(o instanceof String)
					invoice_no_from = jsObj.getInt(JsParams.INVOICE_UPDATE_REQUEST.invoice_no_from);
			}
			if(jsObj.has(JsParams.INVOICE_UPDATE_REQUEST.invoice_no_to)){
				Object o = jsObj.get(JsParams.INVOICE_UPDATE_REQUEST.invoice_no_to);
				if(o instanceof String)
					invoice_no_to = jsObj.getInt(JsParams.INVOICE_UPDATE_REQUEST.invoice_no_to);
			}
			if(jsObj.has(JsParams.INVOICE_UPDATE_REQUEST.old_status)){
				old_status = jsObj.getInt(JsParams.INVOICE_UPDATE_REQUEST.old_status);
			}
			if(jsObj.has(JsParams.INVOICE_UPDATE_REQUEST.status)){
				status = jsObj.getInt(JsParams.INVOICE_UPDATE_REQUEST.status);
			}
		}
	}
	
	String serial = null;
	Integer invoice_no_from = null, invoice_no_to = null, status = null, old_status = null;
	
	@Override
	public JsonResponse execute() {
		InvoiceUpdateResponse resp = new InvoiceUpdateResponse();
		try {
			if (status != null && old_status != null) {
				InvoiceDetailDAO idDAO = new InvoiceDetailDAO();

				BeanFilter ibFilter = new BeanFilter(InvoiceBatch.class);
				if(serial!=null) 
					ibFilter.setFilter(InvoiceBatch.serial, new StringEqualFilter(serial));
				FilterCriteria invoiceNoCriteria = new FilterCriteria();
				if(invoice_no_from!=null)
					invoiceNoCriteria.addFilter(new IntegerGreaterThanOrEqualFilter(invoice_no_from));
				if(invoice_no_to!=null)
					invoiceNoCriteria.addFilter(new IntegerLessThanOrEqualFilter(invoice_no_to));
				BeanFilter idFilter = new BeanFilter(InvoiceDetail.class);
				idFilter.setFilterCriteria(InvoiceDetail.invoice_no, invoiceNoCriteria);
				idFilter.setFilter(InvoiceDetail.status, new IntegerEqualFilter(old_status));
				idFilter.setFilter(InvoiceDetail.invoice_batch_id, new BeanInnerJoinFilter(ibFilter));
				
				switch (status) {
				case Consts.INVOICE_DETAIL_STATUS.INVOICE_DELETED:
					/* Only delete Invoice Mapped */
						BaseFilter<?> delFilter = new IntegerEqualFilter(Consts.DATA_LOAD.ID_BLANK);
						delFilter.setNegated(true);
					idFilter.setFilter(InvoiceDetail.data_load_id, delFilter);
						FilterCriteria dateCriteria = new FilterCriteria();
						dateCriteria.addFilter(new DateGreaterThanOrEqualFilter(DateUtils.getFirstDayOfMonth(new Date())));
						dateCriteria.addFilter(new DateLessThanOrEqualFilter(DateUtils.getLastDayOfMonth(new Date())));
					idFilter.setFilterCriteria(InvoiceDetail.action_date, dateCriteria);
						
					break;
				case Consts.INVOICE_DETAIL_STATUS.INVOICE_LOST:
					/* Only delete Invoice Mapped */
						BaseFilter<?> lostFilter = new IntegerEqualFilter(Consts.DATA_LOAD.ID_BLANK);
						lostFilter.setNegated(true);
					idFilter.setFilter(InvoiceDetail.data_load_id, lostFilter);
						
					break;
				case Consts.INVOICE_DETAIL_STATUS.INVOICE_DESTROYED:
					/* Only delete Invoice Not Mapped */
					idFilter.setFilter(InvoiceDetail.data_load_id, new IntegerEqualFilter(Consts.DATA_LOAD.ID_BLANK));
						
					break;
				}
				List<InvoiceDetail> idList = idDAO.getBeans(idFilter);
				Date action_date = new Date();
				
				/* Defind set dataload will remove to other table */
				if(status == Consts.INVOICE_DETAIL_STATUS.INVOICE_DELETED) {
					Set<Integer> dliSet = new HashSet<Integer>();
					for (InvoiceDetail e : idList) {
						e.set(InvoiceDetail.status, status);
						e.set(InvoiceDetail.action_date, action_date);
						dliSet.add(e.get(InvoiceDetail.data_load_id));
					}
					if(dliSet.size() != 0) {
						DataLoadDAO dlDAO = new DataLoadDAO();
						DataLoadDelHisDAO dldhDAO = new DataLoadDelHisDAO();
						
						/* Get Dataload */
						BeanFilter dlFitler = new BeanFilter(DataLoad.class);
						dlFitler.setFilter(DataLoad.data_load_id, new IntegerInFilter(dliSet));
						List<DataLoad> dlList = dlDAO.getBeans(dlFitler);
						List<DataLoadDelHis> dldhList = new ArrayList<DataLoadDelHis>();
						for (DataLoad d : dlList) {
							DataLoadDelHis e = fromBean(d);
							dldhList.add(e);
						}
						/* Insert to DataLoadDelHis Table */
						dldhDAO.updateBeans(dldhList);
						/* Remove from DataLoad Table */
						dlDAO.removeBeans(dlFitler);
						/* Remove from Invoice File Table */
						InvoiceFileDAO ifDAO = new InvoiceFileDAO();
						BeanFilter ifFilter = new BeanFilter(InvoiceFile.class);
						ifFilter.setFilter(InvoiceFile.data_load_id, new IntegerInFilter(dliSet));
						ifDAO.removeBeans(ifFilter);
					}
				} else {
					for (InvoiceDetail e : idList) {
						e.set(InvoiceDetail.status, status);
						e.set(InvoiceDetail.action_date, action_date);
					}
				}
				/* Update status */
				idDAO.updateBeans(idList);
				resp.setSuccess(true);
			} else {
				resp.setSuccess(false);
				resp.setInfo("Missing params!");
			}
		} catch (Exception exc) {
			exc.printStackTrace();
			resp.setSuccess(false);
			resp.setInfo(exc.getMessage());
		}
		return resp;
	}
	
	public static DataLoadDelHis fromBean(DataLoad d){
		DataLoadDelHis e = new DataLoadDelHis();
		e.set(DataLoadDelHis.data_load_id, d.get(DataLoad.data_load_id));
		e.set(DataLoadDelHis.create_date, d.get(DataLoad.create_date));
		e.set(DataLoadDelHis.symbol_of_invoice, d.get(DataLoad.symbol_of_invoice));
		e.set(DataLoadDelHis.serial, d.get(DataLoad.serial));
		e.set(DataLoadDelHis.invoice_no, d.get(DataLoad.invoice_no));
		e.set(DataLoadDelHis.customer_name, d.get(DataLoad.customer_name));
		e.set(DataLoadDelHis.company_name, d.get(DataLoad.company_name));
		e.set(DataLoadDelHis.tax_code, d.get(DataLoad.tax_code));
		e.set(DataLoadDelHis.address, d.get(DataLoad.address));
		e.set(DataLoadDelHis.msisdn, d.get(DataLoad.msisdn));
		e.set(DataLoadDelHis.customer_code, d.get(DataLoad.customer_code));
		e.set(DataLoadDelHis.erp_customer_code, d.get(DataLoad.erp_customer_code));
		e.set(DataLoadDelHis.from_date, d.get(DataLoad.from_date));
		e.set(DataLoadDelHis.to_date, d.get(DataLoad.to_date));
		e.set(DataLoadDelHis.kind_of_payment, d.get(DataLoad.kind_of_payment));
		e.set(DataLoadDelHis.kind_of_service_no, d.get(DataLoad.kind_of_service_no));
		e.set(DataLoadDelHis.kind_of_service, d.get(DataLoad.kind_of_service));
		e.set(DataLoadDelHis.unit, d.get(DataLoad.unit));
		e.set(DataLoadDelHis.quantity, d.get(DataLoad.quantity));
		e.set(DataLoadDelHis.unit_price, d.get(DataLoad.unit_price));
		e.set(DataLoadDelHis.amount, d.get(DataLoad.amount));
		e.set(DataLoadDelHis.total, d.get(DataLoad.total));
		e.set(DataLoadDelHis.vat_rate, d.get(DataLoad.vat_rate));
		e.set(DataLoadDelHis.vat_amount, d.get(DataLoad.vat_amount));
		e.set(DataLoadDelHis.grand_total, d.get(DataLoad.grand_total));
		e.set(DataLoadDelHis.sum_in_words, d.get(DataLoad.sum_in_words));
		e.set(DataLoadDelHis.used, d.get(DataLoad.used));
		e.set(DataLoadDelHis.username, d.get(DataLoad.username));
		e.set(DataLoadDelHis.adjust_type, d.get(DataLoad.adjust_type));
		e.set(DataLoadDelHis.parent_invoice_no, d.get(DataLoad.parent_invoice_no));
		e.set(DataLoadDelHis.import_date, d.get(DataLoad.import_date));
		return e;
	}
	
	public static void main(String[] args) {
		try {
			JSONObject jObj = new JSONObject("{\"mail\":\"hiep.nvh@gmobile.vn\",\"userName\":\"anhta\",\"params\":{\"status\":13,\"invoice_no_to\":\"0000001\",\"invoice_no_from\":\"0000001\",\"old_status\":0,\"serial\":\"DN/16E\"},\"displayName\":\"Anh Ta Tuan\",\"mobile\":\"84996668000\"}");
			InvoiceUpdateRequest req = new InvoiceUpdateRequest(jObj);
			req.execute();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}

