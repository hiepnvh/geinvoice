package vn.gmobile.einvoice.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import vn.gmobile.einvoice.conf.Consts;
import vn.gmobile.einvoice.conf.ServerConfig;

public class SubscriberFilter {
	protected static final Logger LOGGER = Logger
			.getLogger(SubscriberFilter.class.getName());
	private Set<String> _blackList;
	private Set<String>   _whiteList;
	private static Pattern _msdnPattern = Pattern.compile(Consts.NUMBER_FORMAT);
	
	public SubscriberFilter(String whiteListPath, String blackListPath) throws FileNotFoundException {
		if (whiteListPath!=null && whiteListPath.length()>0) {
			_whiteList =  new HashSet<String>();
			Scanner in = new Scanner(new File(ServerConfig.getPhysicalDir() + whiteListPath));
			while (in.hasNext()) {
				String msisdn = in.next();
				_whiteList.add(Formatter.toInternational(msisdn));
			}
			in.close();
		}
		if (blackListPath!=null && blackListPath.length()>0) {
			_blackList =  new HashSet<String>();
			Scanner in = new Scanner(new File(ServerConfig.getPhysicalDir() + blackListPath));
			while (in.hasNext()) {
				String msisdn = in.next();
				_blackList.add(Formatter.toInternational(msisdn));
			}
			in.close();
		}
	}
	
	public boolean pass(String msisdn) throws Exception{
		boolean whiteRes =  passWhiteListFilter(msisdn) ;
		boolean blackRes = passBlackListFilter(msisdn);
//		if (!whiteRes)
//			LOGGER.info(msisdn +" not pass white list");
//		if (!blackRes)
//			LOGGER.info(msisdn +" not pass black list");
		return whiteRes && blackRes;
	}
	
	public static boolean passFormat(String msisdn) throws Exception{
		if (!_msdnPattern.matcher(msisdn).matches())
			return false;
		return true;
	}
	
	public static boolean passFormatVN(String msisdn) {
		if (msisdn.startsWith("84"))
			return true;
		return false;
	}
	
	private boolean passWhiteListFilter(String msisdn) {
		if (!_msdnPattern.matcher(msisdn).matches())
			return false;
		if (_whiteList==null)
			return true;
		return _whiteList.contains(Formatter.toInternational(msisdn));
	}
	
	private boolean passBlackListFilter(String msisdn) {
		if (!_msdnPattern.matcher(msisdn).matches())
			return false;
		if (_blackList==null)
			return true;
		return !_blackList.contains(Formatter.toInternational(msisdn));
	}
	
	

	
}
