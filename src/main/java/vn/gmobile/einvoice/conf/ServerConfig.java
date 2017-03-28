package vn.gmobile.einvoice.conf;

import java.io.InputStream;

import vn.gmobile.einvoice.util.ResourceLoader;

public class ServerConfig {
	static ResourceLoader LOADER;
	static String ACTIVE_CONFIG="testbed";
	static {
		try {
			String resourceName = "/conf/server.conf";
			InputStream is = DbConfig.class.getResourceAsStream(resourceName);
			LOADER = ResourceLoader.loadFromText(is);
			ACTIVE_CONFIG = LOADER.getString("active");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getNonDeleteDir() {
		return LOADER.getString(ACTIVE_CONFIG+"."+"non-delete-dir");
	}
	
	public static String getNonDeleteVirtualDir() {
		return LOADER.getString(ACTIVE_CONFIG+"."+"non-delete-virtual-dir");
	}
	
	public static String getPhysicalDir() {
		return LOADER.getString(ACTIVE_CONFIG+"."+"physical-dir");
	}
	
	public static String getVirtualDir() {
		return LOADER.getString(ACTIVE_CONFIG+"."+"virtual-dir");
	}
	
	public static String getWebUrl() {
		return LOADER.getString(ACTIVE_CONFIG+"."+"web-url");
	}
	
	public static String getNonDeleteWebUrl() {
		return LOADER.getString(ACTIVE_CONFIG+"."+"non-delete-web-url");
	}
	
	public static String getInvConvTemplate() {
		return LOADER.getString(ACTIVE_CONFIG+"."+"invoice_conversion_template");
	}
	
	public static String getRITemplate() {
		return LOADER.getString(ACTIVE_CONFIG+"."+"ri_template");
	}

}
