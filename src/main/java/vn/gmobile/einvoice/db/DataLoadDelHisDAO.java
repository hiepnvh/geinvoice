package vn.gmobile.einvoice.db;

import java.util.List;
import com.bean.base.BeanFilter;
import vn.gmobile.einvoice.model.DataLoadDelHis;

public class DataLoadDelHisDAO {
	
	public List<DataLoadDelHis> getBeans(BeanFilter filter) throws Exception {
		DbAdapter dba = new DbAdapter();
		List<DataLoadDelHis> beans = dba.getBeans(filter);
		dba.close();
		return beans;
	}
	
	public long countBeans(BeanFilter filter) throws Exception {
		DbAdapter dba = new DbAdapter();	
		long quantity = dba.countBeans(filter);
		dba.close();
		return quantity;
	}
	
	public void updateBean(DataLoadDelHis bean) throws Exception {
			DbAdapter dba = new DbAdapter();
		try {
			dba.processBeans(bean);
		} finally {
			dba.close();
		}
	}
	
	public void updateBeans(List<DataLoadDelHis> beans) throws Exception {
			DbAdapter dba = new DbAdapter();
		try {
			dba.processBeans(beans);
		} finally {
			dba.close();
		}
	}
}
