package vn.gmobile.einvoice.delegate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import com.base.filter.BaseFilter;
import com.base.filter.BeanInnerJoinFilter;
import com.base.filter.BooleanEqualFilter;
import com.base.filter.IntegerEqualFilter;
import com.base.filter.StringEqualFilter;
import com.bean.base.BeanFilter;
import com.bean.db.DbParams;
import vn.gmobile.digital.sign.SignFile;
import vn.gmobile.einvoice.conf.Consts;
import vn.gmobile.einvoice.conf.ServerConfig;
import vn.gmobile.einvoice.conf.SystemParamGroup;
import vn.gmobile.einvoice.db.DataLoadDAO;
import vn.gmobile.einvoice.db.InvoiceAgencyDAO;
import vn.gmobile.einvoice.db.InvoiceDetailDAO;
import vn.gmobile.einvoice.db.InvoiceFileDAO;
import vn.gmobile.einvoice.db.InvoiceProcessDAO;
import vn.gmobile.einvoice.db.MessageDAO;
import vn.gmobile.einvoice.model.DataLoad;
import vn.gmobile.einvoice.model.InvoiceAgency;
import vn.gmobile.einvoice.model.InvoiceAgencySerialRelation;
import vn.gmobile.einvoice.model.InvoiceBatch;
import vn.gmobile.einvoice.model.InvoiceDetail;
import vn.gmobile.einvoice.model.InvoiceFile;
import vn.gmobile.einvoice.model.InvoiceProcess;
import vn.gmobile.einvoice.model.Message;
import vn.gmobile.einvoice.model.RedInvoice;
import vn.gmobile.einvoice.util.DateUtils;
import vn.gmobile.einvoice.util.FileUtils;
import vn.gmobile.einvoice.util.StringUtils;
import vn.gmobile.einvoice.util.XMLUtils;

public class InvoiceGenerating implements JavaDelegate {

	private static final Logger LOGGER = Logger.getLogger(InvoiceGenerating.class.getName());
	private static String file_separator = "/";
	
	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
		Integer invoice_process_id = null;
		long dlQuantity = 0, idQuantity = 0, quantityRs = 0;
		
		String username = execution.getVariable(Consts.PROCESS_VARIABLES.username) != null ? String.valueOf(execution.getVariable(Consts.PROCESS_VARIABLES.username)) : null;
		String serial = execution.getVariable(Consts.PROCESS_VARIABLES.serial) != null ? String.valueOf(execution.getVariable(Consts.PROCESS_VARIABLES.serial)) : null;
		Boolean adjust = execution.getVariable(Consts.PROCESS_VARIABLES.adjust) != null ? Boolean.valueOf(String.valueOf(execution.getVariable(Consts.PROCESS_VARIABLES.adjust))) : null;
		String process_unique = execution.getVariable(Consts.PROCESS_VARIABLES.process_unique) != null ? String.valueOf(execution.getVariable(Consts.PROCESS_VARIABLES.process_unique)) : null;
		
		try {
			InvoiceProcessDAO ipDAO = new InvoiceProcessDAO();
			BeanFilter ipFilter = new BeanFilter(InvoiceProcess.class);
			ipFilter.setFilter(InvoiceProcess.serial, new StringEqualFilter(serial));
			ipFilter.setFilter(InvoiceProcess.invoice_process_type, new IntegerEqualFilter(Consts.INVOICE_PROCESS_TYPE.INVOICE_GENERATE));
			ipFilter.setFilter(InvoiceProcess.status, new IntegerEqualFilter(Consts.INVOICE_PROCESS.RUNNING));
			
			if(ipDAO.countBeans(ipFilter)>0){
				LOGGER.info("Invoice Generate still Running at "+DateUtils.getLongDate(new Date()));
				execution.setVariable(Consts.PROCESS_VARIABLES.finish, true);
				// We won't do nothing here!
			} else {
				/* Mark that Invoice Generate for Serial*/
				InvoiceProcess ip = new InvoiceProcess();
				ip.set(InvoiceProcess.invoice_process_type, Consts.INVOICE_PROCESS_TYPE.INVOICE_GENERATE);
				ip.set(InvoiceProcess.serial, serial);
				ip.set(InvoiceProcess.status, Consts.INVOICE_PROCESS.RUNNING);
				ip.set(InvoiceProcess.username, username);
				ip.set(InvoiceProcess.process_unique, process_unique);
				ipDAO.updateBean(ip);
				invoice_process_id = ip.get(InvoiceProcess.invoice_process_id);
				
				/*Start time process*/
				Date startTime = new Date();
				/*create date*/
				Date create_date = DateUtils.truncateTime(new Date());
				LOGGER.info("Invoices are being generated at:"+DateUtils.getLongDate(startTime));
				
				if(username != null && serial != null && adjust != null){
				
					Calendar cal = Calendar.getInstance();
					int _batchQuantity = 2000;
					/* DAO */
					DataLoadDAO dlDAO = new DataLoadDAO();
					InvoiceDetailDAO idDAO = new InvoiceDetailDAO();
					InvoiceAgencyDAO iaDAO = new InvoiceAgencyDAO();
					InvoiceFileDAO ifDAO = new InvoiceFileDAO();
					
					/* FILTER */
					BeanFilter dlFilter = new BeanFilter(DataLoad.class);
					BeanFilter ibFilter = new BeanFilter(InvoiceBatch.class);
					BeanFilter idFilter = new BeanFilter(InvoiceDetail.class);
					BeanFilter iaFilter = new BeanFilter(InvoiceAgency.class);
					BeanFilter iasrFilter = new BeanFilter(InvoiceAgencySerialRelation.class);
					
					/* RETRIEVE DATA InvoiceAgency */
//					Map<Integer, InvoiceAgency> iaMap = new HashMap<Integer, InvoiceAgency>();
					iasrFilter.setFilter(InvoiceAgencySerialRelation.serial, new StringEqualFilter(serial));
					iaFilter.setFilter(InvoiceAgency.invoice_agency_id, new BeanInnerJoinFilter(iasrFilter));
					InvoiceAgency ia = iaDAO.getBeans(iaFilter).get(0);
							
					/* compare quantity of dataload & invoice */
					/* get quantity of data load */
					dlFilter.setFilter(DataLoad.username, new StringEqualFilter(username));
					dlFilter.setFilter(DataLoad.serial, new StringEqualFilter(serial));
					dlFilter.setFilter(DataLoad.used, new BooleanEqualFilter(false));
					if(adjust) {
						BaseFilter<?> strinNotEqualFilter = new StringEqualFilter(Consts.DATA_LOAD.PARENT_ID_BLANK);
						strinNotEqualFilter.setNegated(true);
						dlFilter.setFilter(DataLoad.parent_invoice_no, strinNotEqualFilter);
					} else {
						dlFilter.setFilter(DataLoad.parent_invoice_no, new StringEqualFilter(Consts.DATA_LOAD.PARENT_ID_BLANK));
					}
					dlQuantity = dlDAO.countBeans(dlFilter);
					if(dlQuantity==0) {
						throw new Exception("ERR_DATA_LOAD_OUT_STOCK");
					}
					
					/* get quantity of invoice */
					ibFilter.setFilter(InvoiceBatch.serial, new StringEqualFilter(serial));
					idFilter.setFilter(InvoiceDetail.invoice_batch_id, new BeanInnerJoinFilter(ibFilter));
					idFilter.setFilter(InvoiceDetail.status, new IntegerEqualFilter(Consts.INVOICE_DETAIL_STATUS.INITIALIZATION));
					idQuantity = idDAO.countBeans(idFilter);
					if(idQuantity==0) {
						throw new Exception("ERR_INVOICE_OUT_STOCK");
					}
					
					LOGGER.info("Generate Delegate check:"+"idQuantity="+idQuantity +";dlQuantity="+dlQuantity+" "+DateUtils.getLongDate(new Date()));
					
					if(idQuantity < dlQuantity ) {
						throw new Exception("ERR_INVOICE_NOT_ENOUGH");
					}
			
					/* Get DataLoad & InvoiceDetail List*/
					dlFilter.setLimit(_batchQuantity);
					List<DataLoad> dlList = dlDAO.getBeans(dlFilter);
					
					idFilter.setFieldOrder(InvoiceDetail.invoice_no, DbParams.ORDER_TYPE.ASC);
					idFilter.setLimit(_batchQuantity);
					List<InvoiceDetail> idList = idDAO.getBeans(idFilter);
					
					/* Save file path after generate files */
					List<InvoiceFile> ifList = null;
					
					List<String> xmlFilePaths = new ArrayList<String>();
					List<String> xmlFileContents = new ArrayList<String>();
					while (dlList.size() > 0 && idList.size() > 0) {
						/* list save file path */
						ifList = new ArrayList<InvoiceFile>();
						
						quantityRs += dlList.size();
						
						InvoiceDetail id = null;
						DataLoad dl = null;
						
						for (int i=0;i<dlList.size();i++){
							/*update fields of Invoice Detail*/
							id = idList.get(i);
							dl = dlList.get(i);
							
							id.set(InvoiceDetail.data_load_id, dl.get(DataLoad.data_load_id));
							id.set(InvoiceDetail.status, Consts.INVOICE_DETAIL_STATUS.MAPPED);
							id.set(InvoiceDetail.action_date, create_date);
							/*update fields of Data Load*/
							dl.set(DataLoad.used, true);
							dl.set(DataLoad.create_date, create_date);
							dl.set(DataLoad.invoice_no, StringUtils.padLeft(SystemParamGroup.INVOICE_NO_LENGTH, id.get(InvoiceDetail.invoice_no), '0'));
							
							idList.set(i, id);
							dlList.set(i, dl);
						}
						
						/* UPDATE MAPPED AND STATUS MAPPED */
						idDAO.updateBeans(idList);
						dlDAO.updateBeans(dlList);
						
						/* CREATE RED INVOICE */
						/*Prepare some data*/
						String arisingDate = DateUtils.getLongDate(create_date),
								invoiceName = SystemParamGroup.INVOICE_NAME,
								filePath, fileName;
						InvoiceFile e;
						RedInvoice ri;
						int year, month;
						String content = (adjust) ? SystemParamGroup.INVOICE_ADJUST_MSG : SystemParamGroup.INVOICE_MSG;
						for (int i=0;i<dlList.size();i++){
							ri = new RedInvoice();
							
							dl = dlList.get(i);
							id = idList.get(i);
							
							ri.setInvoiceName(invoiceName);
							
							ri.setArisingDate(arisingDate);
							ri.setInvoicePattern(dl.get(DataLoad.symbol_of_invoice));
							ri.setSerialNo(dl.get(DataLoad.serial));
							ri.setInvoiceNo(dl.get(DataLoad.invoice_no));
							ri.setKind_of_Payment(dl.get(DataLoad.kind_of_payment));
							
							ri.setComName(ia.get(InvoiceAgency.seller));
							ri.setComTaxCode(ia.get(InvoiceAgency.tin));
							ri.setComAddress(ia.get(InvoiceAgency.address));
							ri.setComPhone(ia.get(InvoiceAgency.tel));
							ri.setComFax(ia.get(InvoiceAgency.fax));
							
							ri.setCusCode(dl.get(DataLoad.customer_code));
							ri.setCusERPCode(dl.get(DataLoad.erp_customer_code));
							ri.setCusName(dl.get(DataLoad.customer_name));
							ri.setCusTaxCode(dl.get(DataLoad.tax_code));
							ri.setCusPhone(dl.get(DataLoad.msisdn));
							ri.setCusAddress(dl.get(DataLoad.address));
							
							ri.setFromDate(DateUtils.getShortDate(dl.get(DataLoad.from_date)));
							ri.setToDate(DateUtils.getShortDate(dl.get(DataLoad.to_date)));
							
							ri.setKindOfService(dl.get(DataLoad.kind_of_service));
							ri.setAmount(dl.get(DataLoad.amount));
							ri.setVAT_Rate(dl.get(DataLoad.vat_rate));
							ri.setVAT_Amount(dl.get(DataLoad.vat_amount));
							ri.setGrandTotal(dl.get(DataLoad.grand_total));
							ri.setSum_in_words(dl.get(DataLoad.sum_in_words));
							
							cal.setTime(dl.get(DataLoad.from_date));
							year = cal.get(Calendar.YEAR);
							month = cal.get(Calendar.MONTH) + 1;
							
							fileName = "xml"
								+ file_separator + year
								+ file_separator + month
								+ file_separator 
								+ "RI" + "_"
								+ ri.getSerialNo().replaceAll("/", "-") + "_"
								+ ri.getInvoiceNo() + "_"
								+ ri.getCusPhone() + "_"
								+ year + "-" + month + "_" 
								+ ri.getCusCode()
								+ ".xml";
							
							filePath = ServerConfig.getNonDeleteDir() + fileName;
							
							/* add filePath, Content to list will be write & signed */
							xmlFilePaths.add(filePath);
							xmlFileContents.add(XMLUtils.jaxbCusToXML(ri));
							
							e = new InvoiceFile();
							e.set(InvoiceFile.invoice_detail_id, id.get(InvoiceDetail.invoice_detail_id));
							e.set(InvoiceFile.data_load_id, dl.get(DataLoad.data_load_id));
							e.set(InvoiceFile.msisdn, dl.get(DataLoad.msisdn));
							e.set(InvoiceFile.create_date, content + month+file_separator+year);
							e.set(InvoiceFile.from_date, dl.get(DataLoad.from_date));
							e.set(InvoiceFile.to_date, dl.get(DataLoad.to_date));
							e.set(InvoiceFile.file_name_xml, fileName);
							ifList.add(e);
						}
						/* save file information of msisdn */
						ifDAO.updateBeans(ifList);
						
						/*Write file*/
						for (int i = 0; i < xmlFilePaths.size(); i++) {
							FileUtils.writeFile(xmlFilePaths.get(i), xmlFileContents.get(i));
						}
						/*Sign file ... */
						SignFile.signXmlFiles(xmlFilePaths);
						xmlFilePaths.clear();
						xmlFileContents.clear();
						
						/* After generate & sign file change status */
						for (InvoiceDetail item : idList) {
							if(item.get(InvoiceDetail.status) == Consts.INVOICE_DETAIL_STATUS.MAPPED)
								item.set(InvoiceDetail.status, Consts.INVOICE_DETAIL_STATUS.INVOICE_GERNERTED);
						}
						idDAO.updateBeans(idList);
						
						/* REPEAT RETRIEVE DATA */
						dlList = dlDAO.getBeans(dlFilter);
						idList = idDAO.getBeans(idFilter);
					}
				}
				execution.setVariable(Consts.PROCESS_VARIABLES.quantity, quantityRs);
				execution.setVariable(Consts.PROCESS_VARIABLES.duration, "~"+((new Date()).getTime()-startTime.getTime())/1000/60);
				LOGGER.info("Invoices are generated, done at "+DateUtils.getLongDate(new Date()));
				if(invoice_process_id!=null){
					InvoiceProcess iv = new InvoiceProcess();
					iv.set(InvoiceProcess.invoice_process_id, invoice_process_id);
					iv.set(InvoiceProcess.finish_time, new Date());
					iv.set(InvoiceProcess.quantity, (int) dlQuantity);
					iv.set(InvoiceProcess.status, Consts.INVOICE_PROCESS.FINISHED);
					ipDAO.updateBean(iv);
					LOGGER.info("Invoice Process "+invoice_process_id+" is updated finish at "+DateUtils.getLongDate(new Date()));
				} else {
					LOGGER.info("Invoice Process "+invoice_process_id+" cant not updated finish "+DateUtils.getLongDate(new Date()));
				}
				/* Have to send an email */
				execution.setVariable(Consts.PROCESS_VARIABLES.finish, false);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			MessageDAO mDAO = new MessageDAO();
			if(e.getMessage().contains("Duplicate")){
				LOGGER.info("Avoid Duplicate Process at "+DateUtils.getLongDate(new Date()));
			} else if(!mDAO.messageByAcronymExist(e.getMessage())) {
				throw new BpmnError("ERROR-OCCURED", mDAO.getMessageByAcronym(e.getMessage()).get(Message.TEXT));
			}
		}
	}
}