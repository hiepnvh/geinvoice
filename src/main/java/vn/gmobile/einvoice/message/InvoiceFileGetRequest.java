package vn.gmobile.einvoice.message;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import com.base.filter.StringInFilter;
import com.base.filter.StringLikeFilter;
import com.bean.base.BeanFilter;
import com.gtel.invoice.rigenerator.Export;
import com.mysql.fabric.xmlrpc.base.Array;

import temp.SFTPinJavaGetFile;
import vn.gmobile.einvoice.conf.JsParams;
import vn.gmobile.einvoice.conf.ServerConfig;
import vn.gmobile.einvoice.db.InvoiceFileDAO;
import vn.gmobile.einvoice.model.InvoiceFile;
import vn.gmobile.einvoice.util.ExcelToJSON;
import vn.gmobile.einvoice.util.ZipUtils;

public class InvoiceFileGetRequest extends JsonRequest {
	
	private static Logger LOGGER = Logger.getLogger(InvoiceFileGetRequest.class.getName());
	
	public InvoiceFileGetRequest(JSONObject jObj) throws Exception {
		super(jObj);
		
		if (jObj.has(JsParams.INVOICE_FILE_GET_REQUEST.file_path)) {
			_file_path = jObj.getString(JsParams.INVOICE_FILE_GET_REQUEST.file_path);
		}
		if (jObj.has(JsParams.INVOICE_FILE_GET_REQUEST.in_month)) {
			_in_month = jObj.getString(JsParams.INVOICE_FILE_GET_REQUEST.in_month);
		}
	}
	
	String _file_path = null;
	String _in_month = null;
	static String filePath = "D:/Invoice/List.xlsx";
	static List<String> fileList = new ArrayList<String>();

	@Override
	public JsonResponse execute() {
		InvoiceFileGetResponse resp = new InvoiceFileGetResponse();
		try {
			if(_file_path != null) {
				_file_path = ServerConfig.getPhysicalDir() + _file_path;
				JSONArray jsArr = ExcelToJSON.getJsonFromExcel(_file_path);
				System.out.println(jsArr.length());
				JSONObject json = null;
				Set<String> msisdnList = new HashSet<String>();
//				msisdnList.add("84995581559");
				for (Object obj : jsArr) {
					json = new JSONObject(String.valueOf(obj));
					msisdnList.add(json.getString("MSISDN"));
				}
				//get files from invoice_files
				InvoiceFileDAO iDao = new InvoiceFileDAO();
				BeanFilter iFilter = new BeanFilter(InvoiceFile.class);
				iFilter.setFilter(InvoiceFile.msisdn, new StringInFilter(msisdnList));
				iFilter.setFilter(InvoiceFile.file_name_xml, new StringLikeFilter("_" + _in_month + "_"));
				List<String> iFiles = new ArrayList<String>();
				List<InvoiceFile> files = iDao.getBeans(iFilter);
				for(InvoiceFile f : files){
					iFiles.add(f.get(InvoiceFile.file_name_xml));
				}
				
				//Create new folder to store files
				Calendar cal = Calendar.getInstance();
	            String fileFolderName = cal.getTimeInMillis() + "";
	            File fileFolder = new File(ServerConfig.getPhysicalDir() + fileFolderName);
	            fileFolder.mkdir();
				
				/* Get Xml files */
//				SFTPinJavaGetFile.getXmlFiles(iFiles, (new File(filePath)).getParent());
				SFTPinJavaGetFile.getXmlFiles(iFiles, fileFolder.getAbsolutePath());
				
				/* Get Xls files */
				listFilesForFolder(fileFolder);
				genExcelFromXmls(fileList, fileFolder.getAbsolutePath());
				System.out.println("Done xls");
				
				File[] allFilesToGet = fileFolder.listFiles();
				
				/* Zip all */
				Boolean b = ZipUtils.zipFiles(Arrays.asList(allFilesToGet), fileFolder.getAbsolutePath() + "/HoaDon.zip");
				
				if(b){
					LOGGER.info("Get zip file ok!");
					resp.setSuccess(true);
					String url = "files/" + fileFolderName + "/HoaDon.zip";
					resp.setUrl(url);
				}
				else
					resp.setSuccess(false);
			} else {
				resp.setSuccess(false);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
			resp.setSuccess(false);
			resp.setInfo(exc.getMessage());
		}
		return resp;
	}
	
	public static void genExcelFromXmls(List<String> inputFilePaths, String fileFolder) {
		Export exporter = new Export();
		String templateFile = ServerConfig.getPhysicalDir() + "template/" + ServerConfig.getRITemplate();
		for (String inputFilePath : inputFilePaths) {
			inputFilePath = fileFolder + "/" + inputFilePath;
			String outputFilePath = inputFilePath.replace(".xml", ".xls");
			exporter.doExport(inputFilePath, outputFilePath, templateFile);
		}
	}
	
	public static void listFilesForFolder(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	fileList.add(fileEntry.getName());
	        }
	    }
	}
	
	public static void main(String[] args) throws Exception {
		String jsonStr = "{\"file_path\":\""
				+ "List.xlsx"
				+ "\",\"in_month\":\"2016-8\"}";
		JSONObject jObj = new JSONObject(jsonStr);
		InvoiceFileGetRequest ifgr = new InvoiceFileGetRequest(jObj);
		ifgr.execute();
		
	}
}
