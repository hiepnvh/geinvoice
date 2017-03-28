package vn.gmobile.einvoice.util;

import vn.gmobile.einvoice.model.RedInvoice;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class XMLUtils {
	
	public static void main(String[] args) {
		
	}
	
	public static String jaxbCusToXML(RedInvoice model) {
		return jaxbObjectToXML(model, RedInvoice.class);
	}
	
	private static String jaxbObjectToXML(Object object, Class customClass) {
	    String xmlString = "";
	    try {
	        JAXBContext context = JAXBContext.newInstance(customClass);
	        Marshaller m = context.createMarshaller();

	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML

	        StringWriter sw = new StringWriter();
	        m.marshal(object, sw);
	        xmlString = sw.toString().trim();

	    } catch (JAXBException e) {
	        e.printStackTrace();
	    }

	    return xmlString;
	}

}
