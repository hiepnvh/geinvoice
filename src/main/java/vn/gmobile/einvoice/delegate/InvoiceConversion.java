package vn.gmobile.einvoice.delegate;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.json.JSONArray;
import org.json.JSONObject;
import com.base.filter.BeanInnerJoinFilter;
import com.base.filter.DateGreaterThanOrEqualFilter;
import com.base.filter.DateLessThanOrEqualFilter;
import com.base.filter.FilterCriteria;
import com.base.filter.IntegerEqualFilter;
import com.base.filter.IntegerInFilter;
import com.base.filter.StringInFilter;
import com.bean.base.BeanFilter;
import vn.gmobile.convert.ExportConversion;
import vn.gmobile.einvoice.conf.Consts;
import vn.gmobile.einvoice.conf.JsParams;
import vn.gmobile.einvoice.conf.ServerConfig;
import vn.gmobile.einvoice.db.InvoiceDetailDAO;
import vn.gmobile.einvoice.db.InvoiceFileDAO;
import vn.gmobile.einvoice.db.MessageDAO;
import vn.gmobile.einvoice.model.DataLoad;
import vn.gmobile.einvoice.model.InvoiceDetail;
import vn.gmobile.einvoice.model.InvoiceFile;
import vn.gmobile.einvoice.model.Message;
import vn.gmobile.einvoice.util.DateUtils;
import vn.gmobile.einvoice.util.ExcelToJSON;
import vn.gmobile.einvoice.util.ZipUtils;

public class InvoiceConversion implements JavaDelegate {

	private static final Logger LOGGER = Logger.getLogger(InvoiceConversion.class.getName());
	
	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
		try {

			LOGGER.info("Invoices Revenue Conversion are being generated!");
			
			String resultFileName = null;
			String file_link = "";
			
			String username = String.valueOf(execution.getVariable(Consts.PROCESS_VARIABLES.username));
			String file_path = String.valueOf(execution.getVariable(Consts.PROCESS_VARIABLES.file_path));
			Date in_month = (Date) execution.getVariable(Consts.PROCESS_VARIABLES.in_month);
			
			JSONArray jsArr = ExcelToJSON.getJsonFromExcel(ServerConfig.getPhysicalDir()+file_path);
			Set<String> msisdnSet = new HashSet<String>();
			for (int i=0;i<jsArr.length()  && i<1000;i++) {
				JSONObject jsObj = new JSONObject(String.valueOf(jsArr.get(i)));
				if(jsObj.has(JsParams.INVOICE_CONVERSION_REQUEST.msisdn))
					msisdnSet.add(jsObj.getString(JsParams.INVOICE_CONVERSION_REQUEST.msisdn));
			}
			
			Date from_date = DateUtils.getFirstDayOfMonth(in_month);
			Date to_date = DateUtils.getLastDayOfMonth(in_month);

			InvoiceDetailDAO idDAO = new InvoiceDetailDAO();
			InvoiceFileDAO ifDAO = new InvoiceFileDAO();
			
			BeanFilter idFilter = new BeanFilter(InvoiceDetail.class);
			BeanFilter ifFilter = new BeanFilter(InvoiceFile.class);
			
			/*CREATE DATE FILTER*/
			FilterCriteria dateCriteria = new FilterCriteria();
			dateCriteria.addFilter(new DateGreaterThanOrEqualFilter(from_date));
			dateCriteria.addFilter(new DateLessThanOrEqualFilter(to_date));
			
			idFilter.setFilter(InvoiceDetail.status, new IntegerEqualFilter(Consts.INVOICE_DETAIL_STATUS.INVOICE_GERNERTED));
			
			ifFilter.setFilterCriteria(InvoiceFile.from_date, dateCriteria);
			ifFilter.setFilter(InvoiceFile.msisdn, new StringInFilter(msisdnSet));
			ifFilter.setFilter(InvoiceFile.invoice_detail_id, new BeanInnerJoinFilter(idFilter));
			
			/* Invoice Conversion here*/
			/* GET DATA FROM FILTER */
			Set<Integer> invDetailIDSet = new HashSet<Integer>();
			List<InvoiceFile> ifList = ifDAO.getBeans(ifFilter);
			List<String> ifListGenerated = new ArrayList<String>();
			String inputFilePath = "", outputFilePath = "", fileName, tempStr = "";
			String templateFilePath = ServerConfig.getPhysicalDir()+"template/"+ServerConfig.getInvConvTemplate();
			/* Directory where files were generated! */
			File file = new File(ServerConfig.getNonDeleteDir()+"xls-conversion/");
			if(!file.exists()) {file.mkdirs();}
			
			ExportConversion exp = new ExportConversion();
			for (InvoiceFile e : ifList) {
				tempStr = e.get(InvoiceFile.file_name_xml);
				inputFilePath = ServerConfig.getNonDeleteDir() + tempStr;
				fileName = tempStr.split("/")[tempStr.split("/").length-1];
				outputFilePath = ServerConfig.getNonDeleteDir()+"xls-conversion/"+fileName.replace(".xml", ".xls");
				exp.doExport(inputFilePath, outputFilePath, templateFilePath);
				ifListGenerated.add(outputFilePath);
				invDetailIDSet.add(e.get(InvoiceFile.invoice_detail_id));
			}
			
			idFilter = new BeanFilter(InvoiceDetail.class);
			idFilter.setFilter(InvoiceDetail.invoice_detail_id, new IntegerInFilter(invDetailIDSet));
			List<InvoiceDetail> idList = idDAO.getBeans(idFilter);
			
			for (InvoiceDetail e : idList) {
				e.set(InvoiceDetail.status, Consts.INVOICE_DETAIL_STATUS.INVOICE_TRANSFORMED);
				e.set(InvoiceDetail.invoice_conversion_user, username);
			}
			/* Update invoice status */
			idDAO.updateBeans(idList);
			
			/* Zip file before send email */
			resultFileName = "xls-conversion/"
									+ (new Date().getTime())
									+ "-"+username+"-"
									+ "InvoiceConversionResult.zip";
			String targetZipFilePath = ServerConfig.getNonDeleteDir()
									+ resultFileName;
			
			if(ZipUtils.zipFile(ifListGenerated, targetZipFilePath))
				System.out.println("Zip "+targetZipFilePath+" success!");
			else
				System.out.println("Zip "+targetZipFilePath+" failed!");
			
			System.out.println(ServerConfig.getNonDeleteWebUrl() +  resultFileName);
			execution.setVariable("file_link", ServerConfig.getNonDeleteWebUrl() +  resultFileName);
			LOGGER.info("Invoice Revenue Conversion are generated, done!");
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			MessageDAO mDAO = new MessageDAO();
			if(mDAO.messageByAcronymExist(e.getMessage())) {
				throw new BpmnError("ERROR-OCCURED", mDAO.getMessageByAcronym(e.getMessage()).get(Message.TEXT));
			}
		}
	}
	
	public static void main(String[] args) {

		try {

			LOGGER.info("Invoices Revenue Conversion are being generated!");
			
			String resultFileName = null;
			String file_link = "";
			
			String username = String.valueOf("anhta");
			String file_path = "invoice_conversion_msisdn_template.xlsx";
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH);
			cal.set(Calendar.MONTH,month -1);
			Date in_month = cal.getTime();
			
			JSONArray jsArr = ExcelToJSON.getJsonFromExcel(ServerConfig.getPhysicalDir()+file_path);
			Set<String> msisdnSet = new HashSet<String>();
			for (int i=0;i<jsArr.length();i++) {
				JSONObject jsObj = new JSONObject(String.valueOf(jsArr.get(i)));
				if(jsObj.has(JsParams.INVOICE_CONVERSION_REQUEST.msisdn))
					msisdnSet.add(jsObj.getString(JsParams.INVOICE_CONVERSION_REQUEST.msisdn));
			}
			
			Date from_date = DateUtils.getFirstDayOfMonth(in_month);
			Date to_date = DateUtils.getLastDayOfMonth(in_month);

			InvoiceDetailDAO idDAO = new InvoiceDetailDAO();
			InvoiceFileDAO ifDAO = new InvoiceFileDAO();
			
			BeanFilter idFilter = new BeanFilter(InvoiceDetail.class);
			BeanFilter ifFilter = new BeanFilter(InvoiceFile.class);
			
			/*CREATE DATE FILTER*/
			FilterCriteria dateCriteria = new FilterCriteria();
			dateCriteria.addFilter(new DateGreaterThanOrEqualFilter(from_date));
			dateCriteria.addFilter(new DateLessThanOrEqualFilter(to_date));
			
			idFilter.setFilter(InvoiceDetail.status, new IntegerEqualFilter(Consts.INVOICE_DETAIL_STATUS.INVOICE_GERNERTED));
			
			ifFilter.setFilterCriteria(InvoiceFile.from_date, dateCriteria);
			ifFilter.setFilter(InvoiceFile.msisdn, new StringInFilter(msisdnSet));
			ifFilter.setFilter(InvoiceFile.invoice_detail_id, new BeanInnerJoinFilter(idFilter));
			
			
			
			/* Invoice Conversion here*/
			/* GET DATA FROM FILTER */
			Set<Integer> invDetailIDSet = new HashSet<Integer>();
			List<InvoiceFile> ifList = ifDAO.getBeans(ifFilter);
			List<String> ifListGenerated = new ArrayList<String>();
			String inputFilePath = "", outputFilePath = "", fileName, tempStr = "";
			String templateFilePath = ServerConfig.getPhysicalDir()+"template/"+ServerConfig.getInvConvTemplate();
			/* Directory where files were generated! */
			File file = new File(ServerConfig.getNonDeleteDir()+"xls-conversion/");
			if(!file.exists()) {file.mkdirs();}
			
			ExportConversion exp = new ExportConversion();
			System.out.println(ifList.size());
			for (InvoiceFile e : ifList) {
				tempStr = e.get(InvoiceFile.file_name_xml);
				inputFilePath = ServerConfig.getNonDeleteDir() + tempStr;
				fileName = tempStr.split("/")[tempStr.split("/").length-1];
				outputFilePath = ServerConfig.getNonDeleteDir()+"xls-conversion/"+fileName.replace(".xml", ".xls");
				exp.doExport(inputFilePath, outputFilePath, templateFilePath);
				ifListGenerated.add(outputFilePath);
				invDetailIDSet.add(e.get(InvoiceFile.invoice_detail_id));
			}
			
			idFilter = new BeanFilter(InvoiceDetail.class);
			System.out.println(invDetailIDSet.size());
			idFilter.setFilter(InvoiceDetail.invoice_detail_id, new IntegerInFilter(invDetailIDSet));
			List<InvoiceDetail> idList = idDAO.getBeans(idFilter);
			
			for (InvoiceDetail e : idList) {
				e.set(InvoiceDetail.status, Consts.INVOICE_DETAIL_STATUS.INVOICE_TRANSFORMED);
				e.set(InvoiceDetail.invoice_conversion_user, username);
			}
			/* Update invoice status */
			idDAO.updateBeans(idList);
			
			/* Zip file before send email */
			resultFileName = "xls-conversion/"
									+ (new Date().getTime())
									+ "-"+username+"-"
									+ "InvoiceConversionResult.zip";
			String targetZipFilePath = ServerConfig.getNonDeleteDir()
									+ resultFileName;
			
			if(ZipUtils.zipFile(ifListGenerated, targetZipFilePath))
				System.out.println("Zip "+targetZipFilePath+" success!");
			else
				System.out.println("Zip "+targetZipFilePath+" failed!");
			
			System.out.println(ServerConfig.getNonDeleteWebUrl() +  resultFileName);
//			execution.setVariable("file_link", ServerConfig.getNonDeleteWebUrl() +  resultFileName);
			LOGGER.info("Invoice Revenue Conversion are generated, done!");
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			MessageDAO mDAO = new MessageDAO();
			if(mDAO.messageByAcronymExist(e.getMessage())) {
				throw new BpmnError("ERROR-OCCURED", mDAO.getMessageByAcronym(e.getMessage()).get(Message.TEXT));
			}
		}
	
	}
}