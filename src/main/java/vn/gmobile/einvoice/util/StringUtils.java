package vn.gmobile.einvoice.util;

import java.text.Normalizer;

/**
 * @author anhta
 * Strip Accents from String
 */
public class StringUtils {
	/*PADDING STRING*/
	/**
	 * @param totalLen Length of Final String
	 * @param origin Original String
	 * @param repChar Replace Char
	 * @return
	 */
	public static String padLeft(Integer totalLen, Object origin, char repChar){
		return String.format("%"+totalLen+"s", origin).replace(' ', repChar);
	}
	/**
	 * @param totalLen Length of Final String
	 * @param origin Original String
	 * @param repChar Replace Char
	 * @return
	 */
	public static String padRight(Integer totalLen, Object origin, char repChar){
		return String.format("%-"+totalLen+"s", origin).replace(' ', repChar);
	}
	
	
	/**
	 * @param s String
	 * @return a string after strip
	 */
	public static String stripAccents(String s) {
		// strip except 2 special accents in vietnamese
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
	    s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	    
	    // strip 2 special accents in vietnamese
	    s = s.replace('\u0111', 'd');
        s = s.replace('\u0110', 'd');
        
        // return string
        return s;
	}
}
