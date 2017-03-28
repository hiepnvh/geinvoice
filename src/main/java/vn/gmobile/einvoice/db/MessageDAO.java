package vn.gmobile.einvoice.db;

import java.util.ArrayList;
import java.util.List;

import com.base.filter.StringEqualFilter;
import com.bean.base.BeanFilter;

import vn.gmobile.einvoice.conf.SystemParamGroup;
import vn.gmobile.einvoice.model.Message;

public class MessageDAO {

	public List<Message> getBeans(BeanFilter filter) throws Exception {
		DbAdapter dba = new DbAdapter();	
		List<Message> beans = dba.getBeans(filter);
		dba.close();
		return beans;
	}
	
	public Message getMessageByAcronym(String acronym){
		try {
			BeanFilter filter = new BeanFilter(Message.class);
			filter.setFilter(Message.ACRONYM, new StringEqualFilter(acronym));
			List<Message> msgList = this.getBeans(filter);
			return msgList.get(0);
		} catch (Exception e) {
			return getMessageByAcronym("UNKNOW_ERROR");
		}
	}
	
	public boolean messageByAcronymExist(String acronym){
		try {
			BeanFilter filter = new BeanFilter(Message.class);
			filter.setFilter(Message.ACRONYM, new StringEqualFilter(acronym));
			List<Message> msgList = this.getBeans(filter);
			if(msgList.size()>0)
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
//	public static void main(String[] args) {
//		MessageDAO mDAO = new MessageDAO();
//		List<Object> arr = new ArrayList<Object>();
//		Object[] objArr = null;
//		arr.add(SystemParamGroup.MAX_FILE_SIZE_UPLOAD);
//		objArr = arr.toArray();
//		System.out.println(String.format(mDAO.getMessageByAcronym("ERR_MAX_FILE_SIZE_UPLOAD").get(Message.TEXT),objArr));
//	}
}
