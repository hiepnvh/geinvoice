package vn.gmobile.einvoice.db;

import java.util.List;
import com.bean.base.BeanFilter;

import vn.gmobile.einvoice.conf.Consts;
import vn.gmobile.einvoice.model.InvoiceProcess;

public class InvoiceProcessDAO {
	
	public List<InvoiceProcess> getBeans(BeanFilter filter) throws Exception {
		DbAdapter dba = new DbAdapter();
		List<InvoiceProcess> beans = dba.getBeans(filter);
		dba.close();
		return beans;
	}
	
	public long countBeans(BeanFilter filter) throws Exception {
		DbAdapter dba = new DbAdapter();	
		long quantity = dba.countBeans(filter);
		dba.close();
		return quantity;
	}
	
	public void updateBean(InvoiceProcess bean) throws Exception {
			DbAdapter dba = new DbAdapter();
		try {
			dba.processBeans(bean);
		} finally {
			dba.close();
		}
	}
	
	public void updateBeans(List<InvoiceProcess> beans) throws Exception {
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
	
	public static void main(String[] args) throws Exception {
		InvoiceProcessDAO ipDAO = new InvoiceProcessDAO();
		
		InvoiceProcess ip = new InvoiceProcess();
		ip.set(InvoiceProcess.serial, "NN/09");
		ip.set(InvoiceProcess.status, Consts.INVOICE_PROCESS.RUNNING);
		ip.set(InvoiceProcess.username, "anhta");
		ipDAO.updateBean(ip);
	}
	
}
