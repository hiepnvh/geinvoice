package vn.gmobile.einvoice.db;

import java.util.List;
import com.bean.base.BeanFilter;
import vn.gmobile.einvoice.model.InvoiceFile;

public class InvoiceFileDAO {

	public List<InvoiceFile> getBeans(BeanFilter filter) throws Exception {
		DbAdapter dba = new DbAdapter();	
		List<InvoiceFile> beans = dba.getBeans(filter);
		dba.close();
		return beans;
	}
	
	public long countBeans(BeanFilter filter) throws Exception {
		DbAdapter dba = new DbAdapter();	
		long quantity = dba.countBeans(filter);
		dba.close();
		return quantity;
	}
	
	public void updateBean(InvoiceFile bean) throws Exception {
		DbAdapter dba = new DbAdapter();
		try {
			dba.processBeans(bean);
			dba.close();
		} finally {
			// TODO: handle finally clause
			dba.close();
		}
	}
	
	public void updateBeans(List<InvoiceFile> beans) throws Exception {
		DbAdapter dba = new DbAdapter();
		try {
			dba.processBeans(beans);
			dba.close();
		} finally {
			// TODO: handle finally clause
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
	
	public static void main(String[] args) throws Exception {
		InvoiceFileDAO  dao = new InvoiceFileDAO();
		BeanFilter filter = new BeanFilter(InvoiceFile.class);
		filter.setLimit(1,2);
		List<InvoiceFile> x = dao.getBeans(filter);
	}
}
