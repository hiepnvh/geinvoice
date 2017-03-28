package vn.gmobile.einvoice.db;

import java.util.List;

import com.base.filter.IntegerEqualFilter;
import com.bean.base.BeanFilter;
import vn.gmobile.einvoice.model.InvoiceAgency;

public class InvoiceAgencyDAO {

	public List<InvoiceAgency> getBeans(BeanFilter filter) throws Exception {
		DbAdapter dba = new DbAdapter();	
		List<InvoiceAgency> beans = dba.getBeans(filter);
		dba.close();
		return beans;
	}
	
	public InvoiceAgency getAgencyById(Integer invoice_agency_id) throws Exception {
		BeanFilter filter = new BeanFilter(InvoiceAgency.class);
		filter.setFilter(InvoiceAgency.invoice_agency_id, new IntegerEqualFilter(invoice_agency_id));
		return getBeans(filter).get(0);
	}
	
	public long countBeans(BeanFilter filter) throws Exception {
		DbAdapter dba = new DbAdapter();	
		long quantity = dba.countBeans(filter);
		dba.close();
		return quantity;
	}
	
	public void updateBean(InvoiceAgency bean) throws Exception {
		DbAdapter dba = new DbAdapter();
		try {
			dba.processBeans(bean);
			dba.close();
		} finally {
			// TODO: handle finally clause
			dba.close();
		}
	}
	
	public static void main(String[] args){
		try {
//			InvoiceAgency bean = new InvoiceAgency();
//			bean.set(InvoiceAgency.seller, "anhta");
//			bean.set(InvoiceAgency.tel, "84996668000");
//			
			InvoiceAgencyDAO dao = new InvoiceAgencyDAO();
			BeanFilter filter = new BeanFilter(InvoiceAgency.class);
			System.out.println(dao.countBeans(filter));;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
