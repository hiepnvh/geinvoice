package vn.gmobile.einvoice.db;

import java.util.List;
import com.bean.base.BeanFilter;
import com.bean.db.DbParams;

import vn.gmobile.einvoice.model.InvoiceBatch;

public class InvoiceBatchDAO {

	public List<InvoiceBatch> getBeans(BeanFilter filter) throws Exception {
		DbAdapter dba = new DbAdapter();	
		List<InvoiceBatch> beans = dba.getBeans(filter);
		dba.close();
		return beans;
	}
	
	public long countBeans(BeanFilter filter) throws Exception {
		DbAdapter dba = new DbAdapter();	
		long quantity = dba.countBeans(filter);
		dba.close();
		return quantity;
	}
	
	public void updateBean(InvoiceBatch bean) throws Exception {
		DbAdapter dba = new DbAdapter();
		try {
			dba.processBeans(bean);
			dba.close();
		} finally {
			// TODO: handle finally clause
			dba.close();
		}
	}
	
	public void updateBeans(List<InvoiceBatch> beans) throws Exception {
		DbAdapter dba = new DbAdapter();
		try {
			dba.processBeans(beans);
			dba.close();
		} finally {
			// TODO: handle finally clause
			dba.close();
		}
	}
	
	public static void main(String[] args) throws Exception {
		InvoiceBatchDAO dao = new InvoiceBatchDAO();
		
		BeanFilter filter = new BeanFilter(InvoiceBatch.class);
//		filter.setFieldOrder(InvoiceBatch.id, DbParams.ORDER_TYPE.DESC);
//		filter.setLimit(5);
		
//		List<InvoiceBatch> beans = dao.getBeans(filter);
//		for (InvoiceBatch b : beans) {
//			System.out.println(b.get(InvoiceBatch.invoice_batch_id));
//		}
		
		System.out.println("Quantity="+dao.countBeans(filter));
	}
}
