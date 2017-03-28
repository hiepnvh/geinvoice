package vn.gmobile.einvoice.conf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bean.base.BeanFilter;

import vn.gmobile.einvoice.db.DbAdapter;
import vn.gmobile.einvoice.model.SystemParam;

public class SystemParamGroup {
	private static Map<String, String> SYSTEM_PARAMS;

	static {
		try {
			SYSTEM_PARAMS = new HashMap<String, String>();
			DbAdapter dba = new DbAdapter();
			BeanFilter paramFilter = new BeanFilter(SystemParam.class);
			List<SystemParam> params = dba.getBeans(paramFilter);
			for (SystemParam param : params) {
				SYSTEM_PARAMS.put(param.get(SystemParam.NAME), param.get(SystemParam.VALUE));
			}
			dba.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static final String INVOICE_NAME = SYSTEM_PARAMS.get("INVOICE_NAME");
	public static final Integer INVOICE_NO_LENGTH = Integer.parseInt(SYSTEM_PARAMS.get("INVOICE_NO_LENGTH"));
	public static final String INVOICE_MSG = SYSTEM_PARAMS.get("INVOICE_MSG");
	public static final String INVOICE_ADJUST_MSG = SYSTEM_PARAMS.get("INVOICE_ADJUST_MSG");
	public static final String WEBAPP_ID = SYSTEM_PARAMS.get("WEBAPP_ID");
	public static final boolean DB_PRODUCT = Boolean.valueOf(SYSTEM_PARAMS.get("DB_PRODUCT"));
}
