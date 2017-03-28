package vn.gmobile.convert;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.xml.sax.SAXException;
import net.sf.jxls.transformer.XLSTransformer;
import vn.gmobile.einvoice.conf.ServerConfig;

public class ExportConversion {
	public void doExport(String inputFilePath, String outputFilePath, String templateFilePath){
		try {
			Import importer = new Import();

			Map beans = importer.importFromXML(inputFilePath);
	        XLSTransformer transformer = new XLSTransformer();
	       	transformer.transformXLS(templateFilePath , beans, outputFilePath);
	       	// do not remove following line
	       	System.out.println(templateFilePath);
	    }
	    catch (IOException	e) {
	    	//e.printStackTrace();
	    	System.out.println("Input file not found or output file in use. Template file: " + templateFilePath);
	    	System.out.println("inputFilePath " + inputFilePath + " outputFilePath " + outputFilePath);
		}
	    catch (SAXException e) {
	    	System.out.println("Error parsing xml file");
		}
        catch (Throwable t) {
        	System.out.println(t.getMessage());
		}
	}
	
	
	public static void main(String[] args) {
		File file = new File(ServerConfig.getNonDeleteDir()+"xls-conversion/");
		if(!file.exists())
			file.mkdirs();
		ExportConversion exp = new ExportConversion();
		String inputFilePath = ServerConfig.getNonDeleteDir()+"xml/2016/5/RI_AA-16E_1_84996444110_2016-5_156700005.xml";
		String outputFilePath = ServerConfig.getNonDeleteDir()+"xls-conversion/RI_AA-16E_1_84996444110_2016-5_156700005.xls";
		String templateFilePath = ServerConfig.getPhysicalDir()+"template/"+ServerConfig.getInvConvTemplate();
		exp.doExport(inputFilePath, outputFilePath, templateFilePath);
	}
}
