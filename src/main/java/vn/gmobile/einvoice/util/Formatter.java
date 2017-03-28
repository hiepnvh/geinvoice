package vn.gmobile.einvoice.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {
	public static Date toDate(String val, String pattern) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat();
		df.applyPattern(pattern);
		return df.parse(val);
	}

	public static String fromDate(Date val, String pattern) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat();
		df.applyPattern(pattern);
		return df.format(val);
	}
	
	public static String toCurrency(int value,String pattern) {
		NumberFormat nf = new DecimalFormat();
		return nf.format(value);
	}
	
	public static String toCurrency(double value,String pattern) {
		NumberFormat nf = new DecimalFormat();
		return nf.format(value);
	}
	
	public static String toInternational(String msisdn) {
		if (msisdn.startsWith("0"))
			return "84" + msisdn.substring(1);
		return msisdn;
	}
}
