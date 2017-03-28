package vn.gmobile.einvoice.conf;

import java.io.InputStream;
import java.sql.Driver;
import java.sql.DriverManager;

import vn.gmobile.einvoice.util.ResourceLoader;

public class DbConfig {
	static ResourceLoader LOADER;
	static String ACTIVE_DB="testbed";
	static {
		try {
			String resourceName = "/conf/db.conf";
			InputStream is = DbConfig.class.getResourceAsStream(resourceName);
			LOADER = ResourceLoader.loadFromText(is);
			ACTIVE_DB = LOADER.getString("active");
			Class c = Class.forName(getDbDriver());
			Driver driver = (Driver)c.newInstance();		
			DriverManager.registerDriver(driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getDbDriver() {
		return LOADER.getString(ACTIVE_DB+"."+"drivers");
	}
	
	public static String getPoolName() {
		return LOADER.getString(ACTIVE_DB+"."+"name");
	}

	public static String getlUrl() {
		return LOADER.getString(ACTIVE_DB+"."+"pool.url");
	}

	
	public static String getUser() {
		return LOADER.getString(ACTIVE_DB+"."+"pool.user");
	}
	
	public static String getPassword() {
		return LOADER.getString(ACTIVE_DB+"."+"pool.password");
	}
	
	public static Integer getMinPool() {
		return LOADER.getInt(ACTIVE_DB+"."+"pool.minpool");
	}
	
	public static Integer getMaxPool() {
		return LOADER.getInt(ACTIVE_DB+"."+"pool.maxpool");
	}
	
	public static Integer getMaxSize() {
		return LOADER.getInt(ACTIVE_DB+"."+"pool.maxsize");
	}
	
	public static Integer getIdleTimeout() {
		return LOADER.getInt(ACTIVE_DB+"."+"pool.idleTimeout");
	}
}
