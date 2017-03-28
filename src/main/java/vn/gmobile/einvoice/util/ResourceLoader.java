/**
 * 
 */
package vn.gmobile.einvoice.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author QuangN
 * 
 */
public class ResourceLoader {
	private static final Logger LOGGER = Logger.getLogger(ResourceLoader.class
			.getName());
	private static final String COMMENT = "#";
	private static final String DELIMITER = ":=";

	Hashtable<String, String> _properties;

	private ResourceLoader() {
		_properties = new Hashtable<String, String>();
	}

	public void setPropertiy(String name, String val) {
		_properties.put(name, val);
	}

	public String getString(String name) {
		return _properties.get(name);
	}

	public Integer getInt(String name) {
		if (_properties.containsKey(name))
			return Integer.parseInt(_properties.get(name));
		return null;
	}

	public Short getShort(String name) {
		if (_properties.containsKey(name))
			return Short.parseShort(_properties.get(name));
		return null;
	}

	public Byte getByte(String name) {
		if (_properties.containsKey(name))
			return (byte) Integer.parseInt(_properties.get(name));
		return null;
	}
	
	public Double getDouble(String name) {
		if (_properties.containsKey(name))
			return Double.parseDouble(_properties.get(name));
		return null;
	}

	public Boolean getBoolean(String name) {
		if (_properties.containsKey(name))
			return Boolean.parseBoolean(_properties.get(name));
		return null;
	}

	public static ResourceLoader loadFromXml(InputStream is) {
		ResourceLoader instance = new ResourceLoader();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(is);
			Element root = dom.getDocumentElement();
			NodeList nodeList = root.getElementsByTagName("property");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Element elem = (Element) nodeList.item(i);
				String name = elem.getAttribute("name").trim();
				String val = elem.getAttribute("value").trim();
				instance.setPropertiy(name, val);
			}

		} catch (Exception e) {
			LOGGER.severe("Properties loadFromXml error" + e.getMessage());
			e.printStackTrace();
		}
		return instance;
	}

	public static ResourceLoader loadFromText(InputStream is) {
		ResourceLoader instance = new ResourceLoader();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.startsWith(COMMENT))
					continue;
				String[] tokens = line.split(DELIMITER);
				String name = tokens[0].trim();
				String val = (tokens.length > 1 ? tokens[1] : "").trim();
				instance.setPropertiy(name, val);
			}
		} catch (Exception e) {
			LOGGER.severe("ResourceLoader loadFromText error" + e.getMessage());
			e.printStackTrace();
		}
		return instance;
	}
}
