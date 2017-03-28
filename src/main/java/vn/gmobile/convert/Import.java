package vn.gmobile.convert;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import vn.gmobile.einvoice.model.RedInvoice;
import vn.gmobile.einvoice.util.FileUtils;

public class Import {
	
	public Map importFromXML(String filePath) throws Exception{
		
		String inputXml = FileUtils.readFile(filePath);
		RedInvoice ri = XMLToJaxbObject(inputXml);

		Map beans = new HashMap();
        beans.put("RedInvoice", ri);
		
		return beans;
	}	
	
	public static RedInvoice XMLToJaxbObject(String xml) throws UnsupportedEncodingException {
		RedInvoice ri = null;
		InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
	    try {
	        JAXBContext context = JAXBContext.newInstance(RedInvoice.class);
	        Unmarshaller m = context.createUnmarshaller();
			ri = (RedInvoice) m.unmarshal(is);
	    } catch (JAXBException e) {
	        e.printStackTrace();
	    }
	    return ri;
	}
}
