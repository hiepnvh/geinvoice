package vn.gmobile.einvoice.util;

import java.util.Random;

public class OneTimePassWord {

	static final String AB = "abcdefghijklmnopqrstuvwxyz";
	static final String NO = "0123456789";
	static final int pasLen = 6;
	static Random rnd = new Random();
	
	public static String randomString( int len ){
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
		   if(i%2==0)
			   sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		   else
			   sb.append( NO.charAt( rnd.nextInt(NO.length()) ) );
	   return sb.toString();
	}

	 public static void main(String[] args) throws Exception {
		  for(int i=5;i>0;i--)
			  System.out.println(randomString(pasLen));
		
	 }
	  

}
