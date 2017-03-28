package vn.gmobile.einvoice.delegate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import com.base.filter.DateGreaterThanOrEqualFilter;
import com.base.filter.DateLessThanOrEqualFilter;
import com.base.filter.FilterCriteria;
import com.base.filter.IntegerEqualFilter;
import com.base.filter.StringInFilter;
import com.bean.base.BeanFilter;
import com.bean.db.DbParams;
import net.sf.jxls.transformer.XLSTransformer;
import vn.gmobile.einvoice.conf.Consts;
import vn.gmobile.einvoice.conf.ServerConfig;
import vn.gmobile.einvoice.db.DataLoadDAO;
import vn.gmobile.einvoice.db.InvoiceAgencyDAO;
import vn.gmobile.einvoice.db.InvoiceAgencySerialRelationDAO;
import vn.gmobile.einvoice.db.InvoiceProcessDAO;
import vn.gmobile.einvoice.db.MessageDAO;
import vn.gmobile.einvoice.model.DataLoad;
import vn.gmobile.einvoice.model.DataLoadPOJO;
import vn.gmobile.einvoice.model.InvoiceAgency;
import vn.gmobile.einvoice.model.InvoiceAgencyPOJO;
import vn.gmobile.einvoice.model.InvoiceAgencySerialRelation;
import vn.gmobile.einvoice.model.InvoiceProcess;
import vn.gmobile.einvoice.model.Message;
import vn.gmobile.einvoice.util.DateUtils;
import vn.gmobile.einvoice.util.ZipUtils;

public class InvoiceRevenueReportGenerating implements JavaDelegate {

	private static final Logger LOGGER = Logger.getLogger(InvoiceRevenueReportGenerating.class.getName());
	private static int rowsPerFile = 25000; 
	
	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
		
		Integer invoice_process_id = null;
		
		String username = execution.getVariable(Consts.PROCESS_VARIABLES.username) != null ? String.valueOf(execution.getVariable(Consts.PROCESS_VARIABLES.username)) : null;
		Date in_month = (Date) (execution.getVariable(Consts.PROCESS_VARIABLES.in_month) != null ? execution.getVariable(Consts.PROCESS_VARIABLES.in_month) : null);
		Integer invoice_agency_id = execution.getVariable(Consts.PROCESS_VARIABLES.invoice_agency_id) != null ? Integer.valueOf(execution.getVariable(Consts.PROCESS_VARIABLES.invoice_agency_id).toString()) : null;
		String process_unique = execution.getVariable(Consts.PROCESS_VARIABLES.process_unique) != null ? String.valueOf(execution.getVariable(Consts.PROCESS_VARIABLES.process_unique)) : null;
		
		try {
			InvoiceProcessDAO ipDAO = new InvoiceProcessDAO();
			BeanFilter ipFilter = new BeanFilter(InvoiceProcess.class);
			ipFilter.setFilter(InvoiceProcess.invoice_process_type, new IntegerEqualFilter(Consts.INVOICE_PROCESS_TYPE.INVOICE_REVENUE));
			ipFilter.setFilter(InvoiceProcess.status, new IntegerEqualFilter(Consts.INVOICE_PROCESS.RUNNING));
			
			if(ipDAO.countBeans(ipFilter)>0){
				LOGGER.info("==INVOICE_REVENUE still Running at "+DateUtils.getLongDate(new Date()));
				execution.setVariable(Consts.PROCESS_VARIABLES.finish, true);
				// We won't do nothing here!
			} else {
				/* Mark that Invoice Generate for Serial*/
				InvoiceProcess ip = new InvoiceProcess();
				ip.set(InvoiceProcess.invoice_process_type, Consts.INVOICE_PROCESS_TYPE.INVOICE_REVENUE);
				ip.set(InvoiceProcess.serial, "BLANK");
				ip.set(InvoiceProcess.status, Consts.INVOICE_PROCESS.RUNNING);
				ip.set(InvoiceProcess.username, username);
				ip.set(InvoiceProcess.process_unique, process_unique);
				ipDAO.updateBean(ip);
				invoice_process_id = ip.get(InvoiceProcess.invoice_process_id);
				
				/*Start time process*/
				Date startTime = new Date();
				long filePrefix = startTime.getTime();
				/*create date*/
				LOGGER.info("==INVOICE_REVENUE are being generated at:"+DateUtils.getLongDate(startTime));
				
				String resultFileName = null;
				String file_link = "";
				List<String> filePaths = new ArrayList<String>();
				
				if(username != null & username != ""){
					Date from_date = DateUtils.getFirstDayOfMonth(in_month);
					Date to_date = DateUtils.getLastDayOfMonth(in_month);

					/* GET SERIAL SET BY AGENCY ID IF EXISTS, GET ALL IF NOT EXIST */
					InvoiceAgencySerialRelationDAO iasrDAO = new InvoiceAgencySerialRelationDAO();
					BeanFilter iasrFilter = new BeanFilter(InvoiceAgencySerialRelation.class);
					if(invoice_agency_id != 0) {
						iasrFilter.setFilter(InvoiceAgencySerialRelation.invoice_agency_id, new IntegerEqualFilter(invoice_agency_id));
					}
					InvoiceAgencyPOJO agency = null;
					BeanFilter iaFilter = new BeanFilter(InvoiceAgency.class);
					if(invoice_agency_id!=0){
						iaFilter.setFilter(InvoiceAgency.invoice_agency_id, new IntegerEqualFilter(invoice_agency_id));
						InvoiceAgencyDAO iaDAO = new InvoiceAgencyDAO();
						InvoiceAgency ia = iaDAO.getBeans(iaFilter).get(0);
						agency = InvoiceAgencyPOJO.fromBean(ia);
					} else {
						agency = new InvoiceAgencyPOJO();
						agency.setSeller("All");
					}
					
					List<InvoiceAgencySerialRelation>  iasrList = iasrDAO.getBeans(iasrFilter);
					Set<String> serialSet = new HashSet<String>();
					for(InvoiceAgencySerialRelation e : iasrList) {
						serialSet.add(e.get(InvoiceAgencySerialRelation.serial));
					}
					
					/*CREATE DATE FILTER*/
					FilterCriteria dateCriteria = new FilterCriteria();
					dateCriteria.addFilter(new DateGreaterThanOrEqualFilter(from_date));
					dateCriteria.addFilter(new DateLessThanOrEqualFilter(to_date));
					
					/* CREATE FITLER FOR DATA LOAD*/
					BeanFilter _filter = new BeanFilter(DataLoad.class);
					/* Set Value for Filter */
					if(serialSet.size() != 0)
					_filter.setFilter(DataLoad.serial, new StringInFilter(serialSet));
					_filter.setFilterCriteria(DataLoad.create_date, dateCriteria);
					_filter.setFieldOrder(DataLoad.invoice_no, DbParams.ORDER_TYPE.ASC);
					
					/* GET DATA FROM FILTER */
					DataLoadDAO dlDAO = new DataLoadDAO();
					List<DataLoad> dlList;
					
					long size = dlDAO.countBeans(_filter);
					System.out.println("InvoiceRevenueReportGenerating size:"+size);
					int offset = 0, fileNumber = 1;
					while (offset < size){
						/* Get value will finish get rows data */
						long finish =  (offset + rowsPerFile) < size ?  (offset + rowsPerFile) : size;
						int limit  = (int) finish-offset;
						/* Get with limit value */
						_filter.setLimit(offset, limit);
						dlList = dlDAO.getBeans(_filter);
						
						/* Convert Dataload to POJO class */
						/* use some utils excel to export Data */
						Collection dataload = new HashSet();
						
						LOGGER.info("==Transforming to POJO " + dlList.size() + " records");
						for (DataLoad e : dlList) {
							dataload.add(DataLoadPOJO.fromBean(e));
						}
						
				        Map beans = new HashMap();
				        beans.put("dataload", dataload);
				        beans.put("agency", agency);
				        beans.put("dateReport", DateUtils.getShortDate(in_month).substring(3,10));
				        
				        XLSTransformer transformer = new XLSTransformer();
				        
				        String templateFileName = ServerConfig.getPhysicalDir() +"template/invoice-revenue.xlsx";
				        resultFileName = filePrefix + "_invoice-revenue"+"_"+fileNumber+".xlsx";
				        String destFileName = ServerConfig.getPhysicalDir() + resultFileName;
				        
				        transformer.transformXLS(templateFileName, beans, destFileName);
				        
				        /*Add file to list for zip*/
				        filePaths.add(destFileName);
						/* Set value for offset, file number for nextime */
						offset = (int) finish;
						fileNumber += 1;
					}
		        }
				execution.setVariable(Consts.PROCESS_VARIABLES.duration, "~"+((new Date()).getTime()-startTime.getTime())/1000/60);
				LOGGER.info("==INVOICE_REVENUE generated, done at "+DateUtils.getLongDate(new Date()));
				if(invoice_process_id!=null){
					InvoiceProcess iv = new InvoiceProcess();
					iv.set(InvoiceProcess.invoice_process_id, invoice_process_id);
					iv.set(InvoiceProcess.finish_time, new Date());
					iv.set(InvoiceProcess.status, Consts.INVOICE_PROCESS.FINISHED);
					ipDAO.updateBean(iv);
					LOGGER.info("==Invoice Process "+invoice_process_id+" is updated finish at "+DateUtils.getLongDate(new Date()));
				} else {
					LOGGER.info("==Invoice Process "+invoice_process_id+" cant not updated finish "+DateUtils.getLongDate(new Date()));
				}
				/* Have to send an email */
				execution.setVariable(Consts.PROCESS_VARIABLES.finish, false);
				/* Create file name for zip */
				String file_name = filePrefix + "_invoice-revenue.zip";
				/* Create link for zip file */
				file_link = ServerConfig.getNonDeleteDir() + file_name;
				ZipUtils.zipFile(filePaths, file_link);
				/* Set link in mail web */
				execution.setVariable("file_link", ServerConfig.getNonDeleteWebUrl() + file_name);
				LOGGER.info("==Invoice Revenue Report are generated, done!");
			}
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			MessageDAO mDAO = new MessageDAO();
			if(e.getMessage().contains("Duplicate")){
				LOGGER.info("==Avoid Duplicate Process at "+DateUtils.getLongDate(new Date()));
			} else if(!mDAO.messageByAcronymExist(e.getMessage())) {
				throw new BpmnError("ERROR-OCCURED-IRR", mDAO.getMessageByAcronym(e.getMessage()).get(Message.TEXT));
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		
		String resultFileName = null;
		Date in_month = new Date();
		int invoice_agency_id = 1;
		
		Date startTime = new Date();
		Date from_date = DateUtils.getFirstDayOfMonth(in_month);
		Date to_date = DateUtils.getLastDayOfMonth(in_month);

		/* GET SERIAL SET BY AGENCY ID IF EXISTS, GET ALL IF NOT EXIST */
		InvoiceAgencySerialRelationDAO iasrDAO = new InvoiceAgencySerialRelationDAO();
		BeanFilter iasrFilter = new BeanFilter(InvoiceAgencySerialRelation.class);
		if(invoice_agency_id != 0) {
			iasrFilter.setFilter(InvoiceAgencySerialRelation.invoice_agency_id, new IntegerEqualFilter(invoice_agency_id));
		}
		InvoiceAgencyPOJO agency = null;
		BeanFilter iaFilter = new BeanFilter(InvoiceAgency.class);
		if(invoice_agency_id!=0){
			iaFilter.setFilter(InvoiceAgency.invoice_agency_id, new IntegerEqualFilter(invoice_agency_id));
			InvoiceAgencyDAO iaDAO = new InvoiceAgencyDAO();
			InvoiceAgency ia = iaDAO.getBeans(iaFilter).get(0);
			agency = InvoiceAgencyPOJO.fromBean(ia);
		} else {
			agency = new InvoiceAgencyPOJO();
			agency.setSeller("All");
		}
		
		List<InvoiceAgencySerialRelation>  iasrList = iasrDAO.getBeans(iasrFilter);
		Set<String> serialSet = new HashSet<String>();
		for(InvoiceAgencySerialRelation e : iasrList) {
			serialSet.add(e.get(InvoiceAgencySerialRelation.serial));
		}
		
		/*CREATE DATE FILTER*/
		FilterCriteria dateCriteria = new FilterCriteria();
		dateCriteria.addFilter(new DateGreaterThanOrEqualFilter(from_date));
		dateCriteria.addFilter(new DateLessThanOrEqualFilter(to_date));
		
		/* CREATE FITLER FOR DATA LOAD*/
		BeanFilter _filter = new BeanFilter(DataLoad.class);
		/* Set Value for Filter */
		if(serialSet.size() != 0)
		_filter.setFilter(DataLoad.serial, new StringInFilter(serialSet));
		_filter.setFilterCriteria(DataLoad.create_date, dateCriteria);
		_filter.setFieldOrder(DataLoad.invoice_no, DbParams.ORDER_TYPE.ASC);
		
		/* GET DATA FROM FILTER */
		DataLoadDAO dlDAO = new DataLoadDAO();
		List<DataLoad> dlList;
		
		long size = dlDAO.countBeans(_filter), filePrefix = (new Date()).getTime();
		System.out.println("InvoiceRevenueReportGenerating size:"+size);
		int offset = 0, fileNumber = 1;
		while (offset < size){
			/* Get value will finish get rows data */
			long finish =  (offset + rowsPerFile) < size ?  (offset + rowsPerFile) : size;
			int limit  = (int) finish-offset;
			/* Get with limit value */
			_filter.setLimit(offset, limit);
			dlList = dlDAO.getBeans(_filter);
			
			/* Convert Dataload to POJO class */
			/* use some utils excel to export Data */
			Collection dataload = new HashSet();
			
			LOGGER.info("==Transforming to POJO " + dlList.size() + " records");
			for (DataLoad e : dlList) {
				dataload.add(DataLoadPOJO.fromBean(e));
			}
			
	        Map beans = new HashMap();
	        beans.put("dataload", dataload);
	        beans.put("agency", agency);
	        beans.put("dateReport", DateUtils.getShortDate(in_month).substring(3,10));
	        
	        XLSTransformer transformer = new XLSTransformer();
	        
	        String templateFileName = ServerConfig.getPhysicalDir() +"template/invoice-revenue.xlsx";
	        resultFileName = filePrefix + "_invoice-revenue"+"_"+fileNumber+".xlsx";
	        String destFileName = ServerConfig.getPhysicalDir() + resultFileName;
	        
	        transformer.transformXLS(templateFileName, beans, destFileName);
	        
			/* Set value for offset, file number for nextime */
			offset = (int) finish;
			fileNumber += 1;
		}
		
        System.out.println("Done");
        System.out.println("Tgian xu ly:" + ( "~"+((new Date()).getTime()-startTime.getTime())/1000/60));
	
	}
}