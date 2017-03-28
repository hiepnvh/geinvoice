package vn.gmobile.einvoice.db;

import java.util.List;
import com.bean.base.BeanFilter;
import vn.gmobile.einvoice.model.InvoiceSerialNo;

public class InvoiceSerialNoDAO {

	public List<InvoiceSerialNo> getBeans(BeanFilter filter) throws Exception {
		DbAdapter dba = new DbAdapter();	
		List<InvoiceSerialNo> beans = dba.getBeans(filter);
		dba.close();
		return beans;
	}
	
	public void updateBean(InvoiceSerialNo bean) throws Exception {
		DbAdapter dba = new DbAdapter();
		try {
			dba.processBeans(bean);
			dba.close();
		} finally {
			// TODO: handle finally clause
			dba.close();
		}
	}
	
	public void updateBeans(List<InvoiceSerialNo> beans) throws Exception {
		DbAdapter dba = new DbAdapter();
		try {
			dba.processBeans(beans);
			dba.close();
		} finally {
			// TODO: handle finally clause
			dba.close();
		}
	}
}
