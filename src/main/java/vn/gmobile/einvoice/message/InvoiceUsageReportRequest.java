package vn.gmobile.einvoice.message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import com.base.filter.BeanInnerJoinFilter;
import com.base.filter.DateEqualFilter;
import com.base.filter.DateGreaterThanOrEqualFilter;
import com.base.filter.DateLessThanOrEqualFilter;
import com.base.filter.FilterCriteria;
import com.base.filter.IntegerEqualFilter;
import com.base.filter.IntegerInFilter;
import com.base.filter.StringEqualFilter;
import com.base.filter.StringInFilter;
import com.bean.base.BeanFilter;
import com.bean.db.DbParams;

import net.sf.jxls.transformer.XLSTransformer;
import vn.gmobile.einvoice.conf.Consts;
import vn.gmobile.einvoice.conf.JsParams;
import vn.gmobile.einvoice.conf.ServerConfig;
import vn.gmobile.einvoice.db.InvoiceAgencyDAO;
import vn.gmobile.einvoice.db.InvoiceAgencySerialRelationDAO;
import vn.gmobile.einvoice.db.InvoiceBatchDAO;
import vn.gmobile.einvoice.db.InvoiceDetailDAO;
import vn.gmobile.einvoice.db.InvoiceUsageReportDAO;
import vn.gmobile.einvoice.model.InvoiceAgency;
import vn.gmobile.einvoice.model.InvoiceAgencySerialRelation;
import vn.gmobile.einvoice.model.InvoiceBatch;
import vn.gmobile.einvoice.model.InvoiceDetail;
import vn.gmobile.einvoice.model.InvoiceDetailUsageRpPOJO;
import vn.gmobile.einvoice.model.InvoiceUsageReport;
import vn.gmobile.einvoice.model.InvoiceUsageRpPOJO;
import vn.gmobile.einvoice.util.DateUtils;

public class InvoiceUsageReportRequest extends JsonRequest {

	/**
	 * @param jObj
	 * @throws Exception
	 */
	public InvoiceUsageReportRequest(JSONObject jObj) throws Exception {
		super(jObj);
		if(jObj.has(JsParams.INVOICE_USAGE_REPORT_REQUEST.in_month)){
			SimpleDateFormat sdf = new SimpleDateFormat(Consts.DATE_FORMAT);
			in_month = sdf.parse(jObj.getString(JsParams.INVOICE_USAGE_REPORT_REQUEST.in_month));
		}
		if (jObj.has(JsParams.INVOICE_USAGE_REPORT_REQUEST.invoice_agency_id)
				&& jObj.get(JsParams.INVOICE_USAGE_REPORT_REQUEST.invoice_agency_id)!=null) {
			invoice_agency_id = jObj.getInt(JsParams.INVOICE_USAGE_REPORT_REQUEST.invoice_agency_id);
		}
		if(jObj.has(JsParams.INVOICE_USAGE_REPORT_REQUEST.in_month)){
			SimpleDateFormat sdf = new SimpleDateFormat(Consts.DATE_FORMAT);
			in_month = sdf.parse(jObj.getString(JsParams.INVOICE_USAGE_REPORT_REQUEST.in_month));
		}
		if(jObj.has(JsParams.INVOICE_USAGE_REPORT_REQUEST.fixed)){
			fixed = jObj.getBoolean(JsParams.INVOICE_USAGE_REPORT_REQUEST.fixed);
		}
	}
	
	Date in_month = null;
	Integer invoice_agency_id = 0;
	Boolean fixed = false;

	@Override
	public JsonResponse execute() {
		InvoiceUsageReportResponse resp = new InvoiceUsageReportResponse();
		try {
			List<InvoiceUsageRpPOJO> invoices;
			List<InvoiceBatch> ibList;
			List<InvoiceDetail> idList;
			List<InvoiceAgencySerialRelation> iasrList;
			
			InvoiceBatchDAO ibDAO = new InvoiceBatchDAO();
			InvoiceDetailDAO idDAO = new InvoiceDetailDAO();
			InvoiceAgencyDAO iaDAO = new InvoiceAgencyDAO();
			InvoiceUsageReportDAO iurDAO = new InvoiceUsageReportDAO();
			InvoiceAgencySerialRelationDAO iasrDAO = new InvoiceAgencySerialRelationDAO();
			
			BeanFilter ibFilter, idFilter, iurFilter, iasrFilter;
			Set<String> serialSet = null;
			
			/*Input params*/
			Date monthReport = null;
			if(in_month!=null)
				monthReport = in_month;
			else
				monthReport = new Date();
			Date firstDate = DateUtils.getFirstDayOfMonth(monthReport);
			Date lastDate = DateUtils.getLastDayOfMonth(monthReport);
			FilterCriteria dateCriterial = new FilterCriteria();
			dateCriterial.addFilter(new DateGreaterThanOrEqualFilter(firstDate));
			dateCriterial.addFilter(new DateLessThanOrEqualFilter(lastDate));
			
			iurFilter = new BeanFilter(InvoiceUsageReport.class);
			iurFilter.setFilter(InvoiceUsageReport.from_date, new DateEqualFilter(firstDate));
			serialSet = new HashSet<String>();
			/*Get all serial in system */
			iasrFilter = new BeanFilter(InvoiceAgencySerialRelation.class);
			if(invoice_agency_id!=0)
				iasrFilter.setFilter(InvoiceAgencySerialRelation.invoice_agency_id, new IntegerEqualFilter(invoice_agency_id));
			iasrList = iasrDAO.getBeans(iasrFilter);
			for (InvoiceAgencySerialRelation e : iasrList) {
				serialSet.add(e.get(InvoiceAgencySerialRelation.serial));
			}
			iurFilter.setFilter(InvoiceUsageReport.serial, new StringInFilter(serialSet));
			List<InvoiceUsageReport> iurList = iurDAO.getBeans(iurFilter);
			/* Get Invoice Usage Report saved*/
			if(iurList.size()!=0){
				int no = 0;
				invoices = new ArrayList<InvoiceUsageRpPOJO>();
				for (InvoiceUsageReport a : iurList) {
					InvoiceUsageRpPOJO invoice = new InvoiceUsageRpPOJO(a.get(InvoiceUsageReport.invoice_name),a.get(InvoiceUsageReport.symbol_of_invoice),a.get(InvoiceUsageReport.serial));
					no+=1;
					invoice.setNo(no);
					invoice.setOpening_stock_number_from(a.get(InvoiceUsageReport.opening_stock_number_from));
					invoice.setOpening_stock_number_to(a.get(InvoiceUsageReport.opening_stock_number_to));
					invoice.setReceiving_stock_number_from(a.get(InvoiceUsageReport.receiving_stock_number_from));
					invoice.setReceiving_stock_number_to(a.get(InvoiceUsageReport.receiving_stock_number_to));
					invoice.setTotal_invoice_from(a.get(InvoiceUsageReport.total_invoice_from));
					invoice.setTotal_invoice_to(a.get(InvoiceUsageReport.total_invoice_to));
					invoice.setInvoice_used(a.get(InvoiceUsageReport.invoice_used));
					invoice.setRemain_invoice_from(a.get(InvoiceUsageReport.remain_invoice_from));
					invoice.setRemain_invoice_to(a.get(InvoiceUsageReport.remain_invoice_to));
					
					int invoice_in_stock = 0;
					if(invoice.getOpening_stock_number_to() != 0) {
						invoice_in_stock += invoice.getOpening_stock_number_to() - invoice.getOpening_stock_number_from() + 1;
					}
					if(invoice.getReceiving_stock_number_to() != 0) {
						invoice_in_stock += invoice.getReceiving_stock_number_to() - invoice.getReceiving_stock_number_from() + 1;
					}
					invoice.setInvoice_in_stock(invoice_in_stock);
					
					 /* Set to Invoice Details */
					/* Get all invoice Lost, Delete, Destroy */
					Set<Integer> setStatus = new HashSet<Integer>();
					setStatus.add(Consts.INVOICE_DETAIL_STATUS.INVOICE_DELETED);
					setStatus.add(Consts.INVOICE_DETAIL_STATUS.INVOICE_DESTROYED);
					setStatus.add(Consts.INVOICE_DETAIL_STATUS.INVOICE_LOST);
					
					ibFilter = new BeanFilter(InvoiceBatch.class);
					idFilter = new BeanFilter(InvoiceDetail.class);
					ibFilter.setFilter(InvoiceBatch.serial, new StringEqualFilter(a.get(InvoiceUsageReport.serial)));
					idFilter.setFilterCriteria(InvoiceDetail.action_date, dateCriterial);
					idFilter.setFilter(InvoiceDetail.invoice_batch_id, new BeanInnerJoinFilter(ibFilter));
					idFilter.setFilter(InvoiceDetail.status, new IntegerInFilter(setStatus));
					idFilter.setFieldOrder(InvoiceDetail.status, DbParams.ORDER_TYPE.ASC);
					idFilter.setFieldOrder(InvoiceDetail.invoice_no, DbParams.ORDER_TYPE.ASC);
					idList = idDAO.getBeans(idFilter);
					List<InvoiceDetailUsageRpPOJO> idUsageRpList = new ArrayList<InvoiceDetailUsageRpPOJO>();
					for(InvoiceDetail e : idList){
						switch (e.get(InvoiceDetail.status)) {
						case Consts.INVOICE_DETAIL_STATUS.INVOICE_DELETED:
							idUsageRpList.add(new InvoiceDetailUsageRpPOJO(1,e.get(InvoiceDetail.invoice_no),null,null,null,null));
							break;
						case Consts.INVOICE_DETAIL_STATUS.INVOICE_LOST:
							idUsageRpList.add(new InvoiceDetailUsageRpPOJO(null,null,1,e.get(InvoiceDetail.invoice_no),null,null));
							break;
						case Consts.INVOICE_DETAIL_STATUS.INVOICE_DESTROYED:
							idUsageRpList.add(new InvoiceDetailUsageRpPOJO(null,null,null,null,1,e.get(InvoiceDetail.invoice_no)));
							break;
						}
					}
			        invoice.setInvDetails(idUsageRpList);
					invoices.add(invoice);
				}
				
			} else {
				serialSet = new HashSet<String>();
				/*Get all serial in system */
				iasrFilter = new BeanFilter(InvoiceAgencySerialRelation.class);
				if(invoice_agency_id!=0)
					iasrFilter.setFilter(InvoiceAgencySerialRelation.invoice_agency_id, new IntegerEqualFilter(invoice_agency_id));
				iasrList = iasrDAO.getBeans(iasrFilter);
				for (InvoiceAgencySerialRelation e : iasrList) {
					serialSet.add(e.get(InvoiceAgencySerialRelation.serial));
				}
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(monthReport);
				cal.add(Calendar.MONTH, -1);
				Date lastmonth = cal.getTime();
				Date firstDateLastMonth = DateUtils.getFirstDayOfMonth(lastmonth);
				Date lastDateLastMonth = DateUtils.getLastDayOfMonth(lastmonth);
				
				String invoice_name = "Hóa đơn GTGT";
				String template_name = "01GTKT0-001";
				
				invoices = new ArrayList<InvoiceUsageRpPOJO>();
				int no = 0;
				for (String serial : serialSet) {
					InvoiceUsageRpPOJO invoice = new InvoiceUsageRpPOJO(invoice_name, template_name , serial);
					no+=1; invoice.setNo(no);
					/*Opening_stock_number*/
					int opening_stock_number_from = 0, opening_stock_number_to = 0;
					iurFilter = new BeanFilter(InvoiceUsageReport.class);
					iurFilter.setFilter(InvoiceUsageReport.serial, new StringEqualFilter(serial));
					iurFilter.setFilter(InvoiceUsageReport.from_date, new DateEqualFilter(firstDateLastMonth));
					iurFilter.setFilter(InvoiceUsageReport.to_date, new DateEqualFilter(lastDateLastMonth));
					iurList = iurDAO.getBeans(iurFilter);
					if(iurList.size() != 0) {
						opening_stock_number_from = iurList.get(0).get(InvoiceUsageReport.remain_invoice_from);
						opening_stock_number_to = iurList.get(0).get(InvoiceUsageReport.remain_invoice_to);
					}
					
					invoice.setOpening_stock_number_from(opening_stock_number_from);
					invoice.setOpening_stock_number_to(opening_stock_number_to);
					
					/*Receiving_stock_number*/
					ibFilter = new BeanFilter(InvoiceBatch.class);
					ibFilter.setFilter(InvoiceBatch.serial, new StringEqualFilter(serial));
					ibFilter.setFilterCriteria(InvoiceBatch.create_date, dateCriterial);
					ibList = ibDAO.getBeans(ibFilter);
					int minReceiving_stock_number = 0, maxReceiving_stock_number = 0;
					if(ibList.size() > 0) {
						InvoiceBatch ibE = ibList.get(0);
						minReceiving_stock_number = ibE.get(InvoiceBatch.start_no);
						maxReceiving_stock_number = ibE.get(InvoiceBatch.start_no) + ibE.get(InvoiceBatch.quantity) - 1;
						for (InvoiceBatch ib : ibList) {
							if(minReceiving_stock_number > ib.get(InvoiceBatch.start_no) )
								minReceiving_stock_number = ib.get(InvoiceBatch.start_no);
							if(maxReceiving_stock_number < ibE.get(InvoiceBatch.start_no) + ibE.get(InvoiceBatch.quantity) - 1 )
								maxReceiving_stock_number = ibE.get(InvoiceBatch.start_no) + ibE.get(InvoiceBatch.quantity) - 1;
						}
					}
					
					invoice.setReceiving_stock_number_from(minReceiving_stock_number);
					invoice.setReceiving_stock_number_to(maxReceiving_stock_number);
					
					int invoice_in_stock = 0;
					if(opening_stock_number_to != 0) {
						invoice_in_stock += opening_stock_number_to - opening_stock_number_from + 1;
					}
					if(maxReceiving_stock_number != 0) {
						invoice_in_stock += maxReceiving_stock_number - minReceiving_stock_number + 1;
					}
					invoice.setInvoice_in_stock(invoice_in_stock);
					
					/*Total_invoice_number*/
					ibFilter = new BeanFilter(InvoiceBatch.class);
					idFilter = new BeanFilter(InvoiceDetail.class);
					ibFilter.setFilter(InvoiceBatch.serial, new StringEqualFilter(serial));
					idFilter.setFilterCriteria(InvoiceDetail.action_date, dateCriterial);
					idFilter.setFilter(InvoiceDetail.invoice_batch_id, new BeanInnerJoinFilter(ibFilter));
					idFilter.setFieldOrder(InvoiceDetail.invoice_detail_id, DbParams.ORDER_TYPE.ASC);
					
					long totalInvoice = idDAO.countBeans(idFilter);
					
					idFilter.setLimit(1);
					idList = idDAO.getBeans(idFilter);
					
					int total_invoice_from = 0, total_invoice_to = 0;
					if(idList.size() > 0){
						total_invoice_from = idList.get(0).get(InvoiceDetail.invoice_no);
						total_invoice_to = (int) (idList.get(0).get(InvoiceDetail.invoice_no) + totalInvoice - 1);
					}
					
					invoice.setTotal_invoice_from(total_invoice_from);
					invoice.setTotal_invoice_to(total_invoice_to);
					if(total_invoice_to == 0){
						invoice.setTotal_invoice_subtract(0);
					} else {
						invoice.setTotal_invoice_subtract(total_invoice_to - total_invoice_from + 1);
					}
					 /* Set to Invoice Details */
					/* Get all invoice Lost, Delete, Destroy */
					Set<Integer> setStatus = new HashSet<Integer>();
					setStatus.add(Consts.INVOICE_DETAIL_STATUS.INVOICE_DELETED);
					setStatus.add(Consts.INVOICE_DETAIL_STATUS.INVOICE_DESTROYED);
					setStatus.add(Consts.INVOICE_DETAIL_STATUS.INVOICE_LOST);
					
					ibFilter = new BeanFilter(InvoiceBatch.class);
					idFilter = new BeanFilter(InvoiceDetail.class);
					ibFilter.setFilter(InvoiceBatch.serial, new StringEqualFilter(serial));
					idFilter.setFilterCriteria(InvoiceDetail.action_date, dateCriterial);
					idFilter.setFilter(InvoiceDetail.invoice_batch_id, new BeanInnerJoinFilter(ibFilter));
					idFilter.setFilter(InvoiceDetail.status, new IntegerInFilter(setStatus));
					idFilter.setFieldOrder(InvoiceDetail.status, DbParams.ORDER_TYPE.ASC);
					idFilter.setFieldOrder(InvoiceDetail.invoice_no, DbParams.ORDER_TYPE.ASC);
					idList = idDAO.getBeans(idFilter);
					List<InvoiceDetailUsageRpPOJO> idUsageRpList = new ArrayList<InvoiceDetailUsageRpPOJO>();
					for(InvoiceDetail e : idList){
						switch (e.get(InvoiceDetail.status)) {
						case Consts.INVOICE_DETAIL_STATUS.INVOICE_DELETED:
							idUsageRpList.add(new InvoiceDetailUsageRpPOJO(1,e.get(InvoiceDetail.invoice_no),null,null,null,null));
							break;
						case Consts.INVOICE_DETAIL_STATUS.INVOICE_LOST:
							idUsageRpList.add(new InvoiceDetailUsageRpPOJO(null,null,1,e.get(InvoiceDetail.invoice_no),null,null));
							break;
						case Consts.INVOICE_DETAIL_STATUS.INVOICE_DESTROYED:
							idUsageRpList.add(new InvoiceDetailUsageRpPOJO(null,null,null,null,1,e.get(InvoiceDetail.invoice_no)));
							break;
						}
					}
			        invoice.setInvDetails(idUsageRpList);
			        int invoice_used = invoice.getTotal_invoice_subtract()-idUsageRpList.size();
			        invoice.setInvoice_used(invoice_used);
			        /* Add invoice to Invoice List */
			        invoices.add(invoice);
			        
			        /* Invoice Remain */
			        int remain_invoice_from = 0, remain_invoice_to = 0;
		    		if(total_invoice_to !=0)
			        	remain_invoice_from = total_invoice_to+1;
			        if(maxReceiving_stock_number!=0)
			        	remain_invoice_to = maxReceiving_stock_number;
			        invoice.setRemain_invoice_from(remain_invoice_from);
			        invoice.setRemain_invoice_to(remain_invoice_to);
			        
			        if(fixed){
			        	InvoiceUsageReport iur = new InvoiceUsageReport();
			        	iur.set(InvoiceUsageReport.invoice_name, invoice_name);
			        	iur.set(InvoiceUsageReport.symbol_of_invoice, template_name);
			        	iur.set(InvoiceUsageReport.serial, serial);
			        	iur.set(InvoiceUsageReport.from_date, firstDate);
			        	iur.set(InvoiceUsageReport.to_date, lastDate);
			        	iur.set(InvoiceUsageReport.opening_stock_number_from, opening_stock_number_from);
			        	iur.set(InvoiceUsageReport.opening_stock_number_to, opening_stock_number_to);
			        	iur.set(InvoiceUsageReport.receiving_stock_number_from, minReceiving_stock_number);
			        	iur.set(InvoiceUsageReport.receiving_stock_number_to, maxReceiving_stock_number);
			        	iur.set(InvoiceUsageReport.total_invoice_from, total_invoice_from);
			        	iur.set(InvoiceUsageReport.total_invoice_to, total_invoice_to);
			        	iur.set(InvoiceUsageReport.invoice_used, invoice_used);
			        	iur.set(InvoiceUsageReport.remain_invoice_from, remain_invoice_from);
			        	iur.set(InvoiceUsageReport.remain_invoice_to, remain_invoice_to);
			        	iur.set(InvoiceUsageReport.fixed, true);
			        	iurDAO.updateBean(iur);
			        }
				}
			}
			
			//...  initialization is skipped here
	        Map beans = new HashMap();
	        beans.put("invoices", invoices);
	        beans.put("monthReport", DateUtils.getShortDate(monthReport).substring(3,10));
	        if(invoice_agency_id!=0)
	        	beans.put("agencyName", iaDAO.getAgencyById(invoice_agency_id).get(InvoiceAgency.seller));
	        else
	        	beans.put("agencyName", "All");
	        XLSTransformer transformer = new XLSTransformer();
	        
	        String templateFileName = ServerConfig.getPhysicalDir() + "template/invoice-usage-template.xlsx";
	        String fileName = (new Date()).getTime()+"_invoice-usage-template-result.xlsx";
	        String destFileName = ServerConfig.getPhysicalDir() + fileName;
	        
	        transformer.transformXLS(templateFileName, beans, destFileName);
	        resp.setFilePath(fileName);
			resp.setSuccess(true);
		} catch (Exception exc) {
			exc.printStackTrace();
			resp.setSuccess(false);
			resp.setInfo(exc.getMessage());
		}
		return resp;
	}
	
//	public static void main(String[] args) throws Exception {
//		/*Input params*/
//		Date monthReport = new Date();
//		int agencyId = 1;
//		
//		Set<String> serialSet = new HashSet<String>();
//		/*Get all serial in system */
//		InvoiceAgencySerialRelationDAO iasrDAO = new InvoiceAgencySerialRelationDAO();
//		BeanFilter iasrFilter = new BeanFilter(InvoiceAgencySerialRelation.class);
//		if(agencyId!=0)
//			iasrFilter.setFilter(InvoiceAgencySerialRelation.invoice_agency_id, new IntegerEqualFilter(agencyId));
//		List<InvoiceAgencySerialRelation> iasrList = iasrDAO.getBeans(iasrFilter);
//		for (InvoiceAgencySerialRelation e : iasrList) {
//			serialSet.add(e.get(InvoiceAgencySerialRelation.serial));
//		}
//		System.out.println("serialSet:"+serialSet);
//		
//		Date firstDate = DateUtils.getFirstDayOfMonth(monthReport);
//		Date lastDate = DateUtils.getLastDayOfMonth(monthReport);
//		
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(monthReport);
//		cal.add(Calendar.MONTH, -1);
//		Date lastmonth = cal.getTime();
//		Date firstDateLastMonth = DateUtils.getFirstDayOfMonth(lastmonth);
//		Date lastDateLastMonth = DateUtils.getLastDayOfMonth(lastmonth);
//		System.out.println("firstDate:"+firstDate);
//		System.out.println("lastDate:"+lastDate);
//		
//		System.out.println("firstDate lastMonth:"+firstDateLastMonth);
//		System.out.println("lastDate  lastMonth:"+lastDateLastMonth);
//		
//		String invoice_name = "Hóa đơn GTGT";
//		String template_name = "01GTKT0-001";
//		
//		BeanFilter ibFilter, idFilter, iurFilter;
//		List<InvoiceBatch> ibList;
//		List<InvoiceDetail> idList;
//		List<InvoiceUsageReport> iurList;
//		
//		InvoiceBatchDAO ibDAO = new InvoiceBatchDAO();
//		InvoiceDetailDAO idDAO = new InvoiceDetailDAO();
//		InvoiceUsageReportDAO iurDAO = new InvoiceUsageReportDAO();
//		InvoiceAgencyDAO iaDAO = new InvoiceAgencyDAO();
//		
//		FilterCriteria dateCriterial = new FilterCriteria();
//		dateCriterial.addFilter(new DateGreaterThanOrEqualFilter(firstDate));
//		dateCriterial.addFilter(new DateLessThanOrEqualFilter(lastDate));
//		
//		List<InvoiceUsageRpPOJO> invoices = new ArrayList<InvoiceUsageRpPOJO>();
//		int no = 0;
//		for (String serial : serialSet) {
//			InvoiceUsageRpPOJO invoice = new InvoiceUsageRpPOJO(invoice_name, template_name , serial);
//			no+=1; invoice.setNo(no);
//			/*Opening_stock_number*/
//			int opening_stock_number_from = 0, opening_stock_number_to = 0;
//			iurFilter = new BeanFilter(InvoiceUsageReport.class);
//			iurFilter.setFilter(InvoiceUsageReport.serial, new StringEqualFilter(serial));
//			iurFilter.setFilter(InvoiceUsageReport.from_date, new DateEqualFilter(firstDateLastMonth));
//			iurFilter.setFilter(InvoiceUsageReport.to_date, new DateEqualFilter(lastDateLastMonth));
//			iurList = iurDAO.getBeans(iurFilter);
//			if(iurList.size() != 0) {
//				opening_stock_number_from = iurList.get(0).get(InvoiceUsageReport.remain_invoice_from);
//				opening_stock_number_to = iurList.get(0).get(InvoiceUsageReport.remain_invoice_to);
//			}
//			
//			invoice.setOpening_stock_number_from(opening_stock_number_from);
//			invoice.setOpening_stock_number_to(opening_stock_number_to);
//			
//			/*Receiving_stock_number*/
//			ibFilter = new BeanFilter(InvoiceBatch.class);
//			ibFilter.setFilter(InvoiceBatch.serial, new StringEqualFilter(serial));
//			ibFilter.setFilterCriteria(InvoiceBatch.create_date, dateCriterial);
//			ibList = ibDAO.getBeans(ibFilter);
//			int minReceiving_stock_number = 0, maxReceiving_stock_number = 0;
//			if(ibList.size() > 0) {
//				InvoiceBatch ibE = ibList.get(0);
//				minReceiving_stock_number = ibE.get(InvoiceBatch.start_no);
//				maxReceiving_stock_number = ibE.get(InvoiceBatch.start_no) + ibE.get(InvoiceBatch.quantity) - 1;
//				for (InvoiceBatch ib : ibList) {
//					if(minReceiving_stock_number > ib.get(InvoiceBatch.start_no) )
//						minReceiving_stock_number = ib.get(InvoiceBatch.start_no);
//					if(maxReceiving_stock_number < ibE.get(InvoiceBatch.start_no) + ibE.get(InvoiceBatch.quantity) - 1 )
//						maxReceiving_stock_number = ibE.get(InvoiceBatch.start_no) + ibE.get(InvoiceBatch.quantity) - 1;
//				}
//			}
//			
//			invoice.setReceiving_stock_number_from(minReceiving_stock_number);
//			invoice.setReceiving_stock_number_to(maxReceiving_stock_number);
//			
//			int invoice_in_stock = 0;
//			if(opening_stock_number_to != 0) {
//				invoice_in_stock += opening_stock_number_to - opening_stock_number_from + 1;
//			}
//			if(maxReceiving_stock_number != 0) {
//				invoice_in_stock += maxReceiving_stock_number - minReceiving_stock_number + 1;
//			}
//			invoice.setInvoice_in_stock(invoice_in_stock);
//			
//			/*Total_invoice_number*/
//			ibFilter = new BeanFilter(InvoiceBatch.class);
//			idFilter = new BeanFilter(InvoiceDetail.class);
//			ibFilter.setFilter(InvoiceBatch.serial, new StringEqualFilter(serial));
//			idFilter.setFilterCriteria(InvoiceDetail.action_date, dateCriterial);
//			idFilter.setFilter(InvoiceDetail.invoice_batch_id, new BeanInnerJoinFilter(ibFilter));
//			idFilter.setFieldOrder(InvoiceDetail.invoice_detail_id, DbParams.ORDER_TYPE.ASC);
//			
//			long totalInvoice = idDAO.countBeans(idFilter);
//			
//			idFilter.setLimit(1);
//			idList = idDAO.getBeans(idFilter);
//			
//			int total_invoice_from = 0, total_invoice_to = 0;
//			if(idList.size() > 0){
//				total_invoice_from = idList.get(0).get(InvoiceDetail.invoice_no);
//				total_invoice_to = (int) (idList.get(0).get(InvoiceDetail.invoice_no) + totalInvoice - 1);
//			}
//			
//			invoice.setTotal_invoice_from(total_invoice_from);
//			invoice.setTotal_invoice_to(total_invoice_to);
//			if(total_invoice_to == 0){
//				invoice.setTotal_invoice_subtract(0);
//			} else {
//				invoice.setTotal_invoice_subtract(total_invoice_to - total_invoice_from + 1);
//			}
//			 /* Set to Invoice Details */
//			/* Get all invoice Lost, Delete, Destroy */
//			Set<Integer> setStatus = new HashSet<Integer>();
//			setStatus.add(Consts.INVOICE_DETAIL_STATUS.INVOICE_DELETED);
//			setStatus.add(Consts.INVOICE_DETAIL_STATUS.INVOICE_DESTROYED);
//			setStatus.add(Consts.INVOICE_DETAIL_STATUS.INVOICE_LOST);
//			
//			ibFilter = new BeanFilter(InvoiceBatch.class);
//			idFilter = new BeanFilter(InvoiceDetail.class);
//			ibFilter.setFilter(InvoiceBatch.serial, new StringEqualFilter(serial));
//			idFilter.setFilterCriteria(InvoiceDetail.action_date, dateCriterial);
//			idFilter.setFilter(InvoiceDetail.invoice_batch_id, new BeanInnerJoinFilter(ibFilter));
//			idFilter.setFilter(InvoiceDetail.status, new IntegerInFilter(setStatus));
//			idFilter.setFieldOrder(InvoiceDetail.status, DbParams.ORDER_TYPE.ASC);
//			idFilter.setFieldOrder(InvoiceDetail.invoice_no, DbParams.ORDER_TYPE.ASC);
//			idList = idDAO.getBeans(idFilter);
//			List<InvoiceDetailUsageRpPOJO> idUsageRpList = new ArrayList<InvoiceDetailUsageRpPOJO>();
//			for(InvoiceDetail e : idList){
//				switch (e.get(InvoiceDetail.status)) {
//				case Consts.INVOICE_DETAIL_STATUS.INVOICE_DELETED:
//					idUsageRpList.add(new InvoiceDetailUsageRpPOJO(1,e.get(InvoiceDetail.invoice_no),null,null,null,null));
//					break;
//				case Consts.INVOICE_DETAIL_STATUS.INVOICE_LOST:
//					idUsageRpList.add(new InvoiceDetailUsageRpPOJO(null,null,1,e.get(InvoiceDetail.invoice_no),null,null));
//					break;
//				case Consts.INVOICE_DETAIL_STATUS.INVOICE_DESTROYED:
//					idUsageRpList.add(new InvoiceDetailUsageRpPOJO(null,null,null,null,1,e.get(InvoiceDetail.invoice_no)));
//					break;
//				}
//			}
//	        invoice.setInvDetails(idUsageRpList);
//	        invoice.setInvoice_used(invoice.getTotal_invoice_subtract()-idUsageRpList.size());
//	        /* Add invoice to Invoice List */
//	        invoices.add(invoice);
//	        
//	        /* Invoice Remain */
//	        int remain_invoice_from = 0, remain_invoice_to = 0;
//    		if(total_invoice_to !=0)
//	        	remain_invoice_from = total_invoice_to+1;
//	        if(maxReceiving_stock_number!=0)
//	        	remain_invoice_to = maxReceiving_stock_number;
//	        invoice.setRemain_invoice_from(remain_invoice_from);
//	        invoice.setRemain_invoice_to(remain_invoice_to);
//		}
//		
//		//...  initialization is skipped here
//        Map beans = new HashMap();
//        beans.put("invoices", invoices);
//        beans.put("monthReport", DateUtils.getShortDate(monthReport).substring(3,10));
//        if(agencyId!=0)
//        	beans.put("agencyName", iaDAO.getAgencyById(agencyId).get(InvoiceAgency.seller));
//        else
//        	beans.put("agencyName", "All");
//        XLSTransformer transformer = new XLSTransformer();
//        
//        String templateFileName = ServerConfig.getPhysicalDir() + "template/invoice-usage-template.xlsx";
//        String destFileName = ServerConfig.getPhysicalDir() + "template/"+(new Date()).getTime()+"_invoice-usage-template-result.xlsx";
//        
//        transformer.transformXLS(templateFileName, beans, destFileName);
//        System.out.println("Done!");
//	}
}
