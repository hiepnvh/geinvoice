package vn.gmobile.einvoice.util;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Iterator; 
import java.util.List; 
import java.util.ArrayList;

public class SumInWord
{

	/* UTF-8 */
	private static final String tens = " mươi";
	private static final String lastOne = " mốt";

	private static final String currency = " đồng";
	private static final String dot = ".";
	private static final String negative = "Âm ";
	/* UTF-8 */
	private static final String[] numNames = { "", " một", " hai", " ba", " bốn", " năm", " sáu", " bảy", " tám", " chín", " mười" };
	private static long currentNum;
	
	/**
	 * Factor being the last hundred being matched
	 * 
	 * @param number
	 * @param factor
	 * @return
	 */
	private static String convertLessThanOneThousand(int number, int factor,
			String fullStr)
	{
		int tempNum = number;
		String strTempNum = Integer.toString(tempNum);
		String soFar = "";
		if (number == 0)
			return "";
		//System.out.println("caught last hundred " + number + " factor is " + factor + " fullStr = " + fullStr);
		soFar = numNames[number % 10];

		number /= 10;
		if ((number % 10) == 1)
			soFar = numNames[10] + soFar;
		else if ((number % 10) == 0)
		{
			if (tempNum % 10 > 0)
				soFar = " linh" + soFar;
		}
		else
		{
			if (number % 10 > 1 && tempNum % 10 == 1)
				soFar = numNames[number % 10] + tens + lastOne;
			else
				soFar = numNames[number % 10] + tens + soFar;
		}
		number /= 10;
		if (number == 0)
			return " không trăm" + soFar;
		return numNames[number] + " trăm" + soFar;
	}

	public static String convert(long number)
	{
		currentNum = number;
		boolean negativeNumber = false;

		if(number < 0 ) {
			negativeNumber = true;
			number = Math.abs(number);
		} else if (number == 0) {
			return "không";
		} 
		String snumber = Long.toString(number);
		// pad with "0"
		String mask = "000000000000";
		DecimalFormat df = new DecimalFormat(mask);
		snumber = df.format(number);
		//System.out.println("snumber = " + snumber);
		// XXXnnnnnnnnn
		String strBillions = snumber.substring(0, 3);
		int billions = Integer.parseInt(strBillions);
		// nnnXXXnnnnnn
		String strTrieus = snumber.substring(3, 6);
		int trieus = Integer.parseInt(strTrieus);
		// nnnnnnXXXnnn
		String strHundredK = snumber.substring(6, 9);
		int hundredThousands = Integer.parseInt(strHundredK);
		// nnnnnnnnnXXX
		String strThousands = snumber.substring(9, 12);
		int thousands = Integer.parseInt(strThousands);
		String tradBillions;
		switch (billions)
		{
			case 0:
				tradBillions = "";
				break;
			case 1:
				tradBillions = convertLessThanOneThousand(billions, 3, strBillions)
						+ " tỷ ";
				break;
			default:
				tradBillions = convertLessThanOneThousand(billions, 3, strBillions)
						+ " tỷ";
		}
		String result = tradBillions;
		String tradtrieus;
		switch (trieus)
		{
			case 0:
				tradtrieus = "";
				break;
			case 1:
				tradtrieus = convertLessThanOneThousand(trieus, 2, strTrieus)
						+ " triệu ";
				break;
			default:
				tradtrieus = convertLessThanOneThousand(trieus, 2, strTrieus)
						+ " triệu ";
		}
		result = result + tradtrieus;
		String tradHundredThousands;
		switch (hundredThousands)
		{
			case 0:
				tradHundredThousands = "";
				break;
			case 1:
				tradHundredThousands = "một nghìn ";
				break;
			default:
				tradHundredThousands = convertLessThanOneThousand(hundredThousands,
						1, strHundredK)
						+ " nghìn ";
		}
		result = result + tradHundredThousands;
		String tradThousand;
		tradThousand = convertLessThanOneThousand(thousands, 0, strThousands);
		result = result + tradThousand;
		// remove extra spaces!
		result = result.replaceAll("^\\s+không trăm", "").replaceAll(
				"không trăm$", "");
		result = result.replaceAll("^\\s+linh", "");

		result = result.replaceAll("^\\s+", "").replaceAll("$\\s+", "")
				.replaceAll("\\b\\s{2,}\\b", " ");
		
		if(negativeNumber){
			result = negative + result;
		}
		
		String firstChar = result.substring(0, 1);
		
		result = result.replaceFirst(firstChar, firstChar.toUpperCase());
		
		return (result);
	}
	
	public static String getText(long number){
		return SumInWord.convert(number) + currency + dot;
	}
	
	public static void main(String[] agrs){
		System.out.println(SumInWord.getText(68000));
	}
	
}

