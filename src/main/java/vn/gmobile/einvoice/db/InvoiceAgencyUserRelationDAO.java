package vn.gmobile.einvoice.db;

import java.util.List;
import com.bean.base.BeanFilter;
import vn.gmobile.einvoice.model.InvoiceAgencyUserRelation;

public class InvoiceAgencyUserRelationDAO {

	public List<InvoiceAgencyUserRelation> getBeans(BeanFilter filter) throws Exception {
		DbAdapter dba = new DbAdapter();	
		List<InvoiceAgencyUserRelation> beans = dba.getBeans(filter);
		dba.close();
		return beans;
	}
	
	public void updateBean(InvoiceAgencyUserRelation bean) throws Exception {
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
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
