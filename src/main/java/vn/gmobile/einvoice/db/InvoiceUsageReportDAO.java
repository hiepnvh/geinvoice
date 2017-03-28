package vn.gmobile.einvoice.db;

import java.util.List;
import com.bean.base.BeanFilter;
import vn.gmobile.einvoice.model.InvoiceUsageReport;

public class InvoiceUsageReportDAO {
	
	public List<InvoiceUsageReport> getBeans(BeanFilter filter) throws Exception {
		DbAdapter dba = new DbAdapter();
		List<InvoiceUsageReport> beans = dba.getBeans(filter);
		dba.close();
		return beans;
	}
	
	public long countBeans(BeanFilter filter) throws Exception {
		DbAdapter dba = new DbAdapter();	
		long quantity = dba.countBeans(filter);
		dba.close();
		return quantity;
	}
	
	public void updateBean(InvoiceUsageReport bean) throws Exception {
			DbAdapter dba = new DbAdapter();
		try {
			dba.processBeans(bean);
		} finally {
			dba.close();
		}
	}
	
	public void updateBeans(List<InvoiceUsageReport> beans) throws Exception {
			DbAdapter dba = new DbAdapter();
		try {
			dba.processBeans(beans);
		} finally {
			dba.close();
		}
	}
	
	public void removeBeans(BeanFilter filter) throws Exception {
		DbAdapter dba = new DbAdapter();
		try {
			dba.removeBeans(filter);
		} finally {
			dba.close();
		}
	}
}
