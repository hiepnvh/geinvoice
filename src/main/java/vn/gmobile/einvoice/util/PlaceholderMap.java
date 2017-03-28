package vn.gmobile.einvoice.util;

import java.util.*;

public abstract class PlaceholderMap {
	Map<String,String> _dataMap =  new HashMap<String,String>();
	
	public Set<String> keySet() {
		return _dataMap.keySet();
	}
	
	public String get(String key) {
		return _dataMap.get(key);
	}
	
	public void put(String key, String val) {
		_dataMap.put(key, val);
	}
}
