package vn.gmobile.einvoice.db;

import java.util.List;

import com.base.filter.IntegerEqualFilter;
import com.base.filter.IsNullFilter;
import com.bean.base.BeanFilter;
import vn.gmobile.einvoice.model.InvoiceDetail;
import vn.gmobile.einvoice.model.InvoiceDetailMore;

public class InvoiceDetailDAO {

	public List<InvoiceDetail> getBeans(BeanFilter filter) throws Exception {
		DbAdapter dba = new DbAdapter();	
		List<InvoiceDetail> beans = dba.getBeans(filter);
		dba.close();
		return beans;
	}
	public List<InvoiceDetailMore> getBeansMore(BeanFilter filter) throws Exception {
		DbAdapter dba = new DbAdapter();	
		List<InvoiceDetailMore> beans = dba.getBeans(filter);
		dba.close();
		return beans;
	}
	
	public long countBeans(BeanFilter filter) throws Exception {
		DbAdapter dba = new DbAdapter();	
		long quantity = dba.countBeans(filter);
		dba.close();
		return quantity;
	}
	
	public void updateBean(InvoiceDetail bean) throws Exception {
		DbAdapter dba = new DbAdapter();
		try {
			dba.processBeans(bean);
			dba.close();
		} finally {
			// TODO: handle finally clause
			dba.close();
		}
	}
	
	public void updateBeans(List<InvoiceDetail> beans) throws Exception {
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
		InvoiceDetailDAO  dao = new InvoiceDetailDAO();
		BeanFilter filter = new BeanFilter(InvoiceDetail.class);
		filter.setLimit(1,2);
		
		List<InvoiceDetail> x = dao.getBeans(filter);
		for (int i = 0; i < x.size(); i++) {
			System.out.println(x.get(i).get(InvoiceDetail.invoice_detail_id)+"|"+x.get(i).get(InvoiceDetail.data_load_id));
		}
		
//		dao.updateBeans(x);
//		long x = dao.countBeans(filter);
//		System.out.println("Quantity="+x);
	}
}
