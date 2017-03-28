package vn.gmobile.einvoice.db;

import java.util.List;
import com.bean.base.BeanFilter;
import vn.gmobile.einvoice.model.DataLoad;

public class DataLoadDAO {
	
	public List<DataLoad> getBeans(BeanFilter filter) throws Exception {
		DbAdapter dba = new DbAdapter();
		List<DataLoad> beans = dba.getBeans(filter);
		dba.close();
		return beans;
	}
	
	public long countBeans(BeanFilter filter) throws Exception {
		DbAdapter dba = new DbAdapter();	
		long quantity = dba.countBeans(filter);
		dba.close();
		return quantity;
	}
	
	public void updateBean(DataLoad bean) throws Exception {
			DbAdapter dba = new DbAdapter();
		try {
			dba.processBeans(bean);
		} finally {
			dba.close();
		}
	}
	
	public void updateBeans(List<DataLoad> beans) throws Exception {
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
	
	public static void main(String[] agrs) throws Exception{
//		DataLoad a = new DataLoad();
//		a.set(DataLoad.msisdn, "84996668000");
//		a.set(DataLoad.username, "anhta");
		
//		Date date = new Date();
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.DAY_OF_MONTH, 1);
//		cal.set(Calendar.MONTH, 6-1);
//		cal.set(Calendar.HOUR_OF_DAY, 0);
//		cal.set(Calendar.MINUTE, 0);
//		cal.set(Calendar.SECOND, 0);
//		cal.set(Calendar.MILLISECOND, 0);
//		
//		System.out.println(cal.getTime());
		
		DataLoadDAO dao = new DataLoadDAO();
		BeanFilter filter = new BeanFilter(DataLoad.class);
//		filter.setFilter(DataLoad.from_date, new DateEqualFilter(cal.getTime()));
		List<DataLoad> dlList = dao.getBeans(filter);
		for (DataLoad dataLoad : dlList) {
			System.out.println(dataLoad.get(DataLoad.unit));
		}
	}
}
