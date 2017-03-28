package vn.gmobile.einvoice.util;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

public class ExcelToJSON {
	
	private static Logger LOGGER = Logger.getLogger(ExcelToJSON.class.getName());
	
	/* To DB Mysql */
	public static JSONArray getJsonFromExcel(String filePath) {
		String df = "dd-MM-yyyy hh:mm:ss";
		SimpleDateFormat sdfm = new SimpleDateFormat(df);
		try {
			File myFile = new File(filePath);
            FileInputStream fis = new FileInputStream(myFile);
            
            // Finds the workbook instance for XLSX file
            XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
           
            // Return first sheet from the XLSX workbook
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            // Iterate through the rows.
		    JSONArray mySheetJSON = new JSONArray();
           
            // Get iterator to all the rows in current sheet
            Iterator<Row> rowIterator = mySheet.iterator();
            
            // 1st Row is header
            List<String> attributes = new ArrayList<String>();
            if(rowIterator.hasNext()) {
            	Row row = rowIterator.next();
            	Iterator<Cell> cellIterator = row.cellIterator();
            	while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();

                    String value = String.valueOf(cell.getStringCellValue());
                    if(value.trim().length() != 0){
                    	attributes.add(value);
                    }
                }
            }
           
            // Traversing over each row of XLSX file
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                JSONObject rowJSON = new JSONObject();

                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();
                    Object value = getCellValueWithDateFormat(cell,sdfm);
                    if(value != null){
                    	rowJSON.put(attributes.get(cell.getColumnIndex()), value);
                    }
                }
                if(rowJSON.length() !=0 )
                	mySheetJSON.put(rowJSON);
            }
            
            myWorkBook.close();
            
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            String prettyJson = gson.toJson(mySheetJSON);
//            return prettyJson;
            
            return mySheetJSON;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static JSONArray getJsonFromExcelPreview(String filePath, Integer rowPreviewNum) {
		String df = "dd/MM/yyyy";
		SimpleDateFormat sdfm = new SimpleDateFormat(df);
		try {
			File myFile = new File(filePath);
            FileInputStream fis = new FileInputStream(myFile);
            
            // Finds the workbook instance for XLSX file
            XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
           
            // Return first sheet from the XLSX workbook
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            // Iterate through the rows.
		    JSONArray mySheetJSON = new JSONArray();
           
            // Get iterator to all the rows in current sheet
            Iterator<Row> rowIterator = mySheet.iterator();
            
            // 1st Row is header
            List<String> attributes = new ArrayList<String>();
            if(rowIterator.hasNext()) {
            	Row row = rowIterator.next();
            	Iterator<Cell> cellIterator = row.cellIterator();
            	while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();

                    String value = String.valueOf(cell.getStringCellValue());
                    if(value.trim().length() != 0){
                    	attributes.add(value);
                    }
                }
            }
           
            int numOfRows = 0;
            // Traversing over each row of XLSX file
			while (rowIterator.hasNext() && numOfRows < rowPreviewNum) {
            	// increasing number of rows
            	numOfRows+=1;
            	
                Row row = rowIterator.next();
                JSONObject rowJSON = new JSONObject();

                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    
                	Cell cell = cellIterator.next();
                    Object value = getCellValueWithDateFormat(cell,sdfm);
                    if(value != null){
                    	rowJSON.put(attributes.get(cell.getColumnIndex()), value);
                    }
                }
                if(rowJSON.length() !=0 )
                	mySheetJSON.put(rowJSON);
            }
            
            myWorkBook.close();
            
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            String prettyJson = gson.toJson(mySheetJSON);
//            System.out.println(prettyJson);
            
            return mySheetJSON;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Object getCellValueWithDateFormat(Cell cell, SimpleDateFormat sdfm){
		Object value = null;
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
        	value = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_NUMERIC:
        	if (HSSFDateUtil.isCellDateFormatted(cell)) {
        		value = sdfm.format(cell.getDateCellValue());
            } else {
            	// way #1
//            	cell.setCellType(Cell.CELL_TYPE_STRING);
//            	value = cell.getStringCellValue();
            	// way #2
//            	value = new BigDecimal(cell.getNumericCellValue()).toPlainString();
            	// way #3
            	value = String.valueOf(new BigDecimal(cell.getNumericCellValue()));
            }
            break;
        case Cell.CELL_TYPE_BOOLEAN:
        	value = String.valueOf(cell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_FORMULA:
        	switch(cell.getCachedFormulaResultType()) {
            case Cell.CELL_TYPE_NUMERIC:
            	value = String.valueOf(new BigDecimal(cell.getNumericCellValue()));
                break;
            case Cell.CELL_TYPE_STRING:
            	value = String.valueOf(cell.getRichStringCellValue());
                break;
        	}
        	break;
        default :
        	return null;
        }
        return value;
	}
	
	public static void main(String[] args) {
		String filePath = "D:/Work/Webapps/electronic-invoice-payment/1.DataLoad.xlsx";
//		System.out.println(getJsonFromExcel(filePath));
		
		Date date1 = new Date();
//		getJsonFromExcel(filePath);
//		getJsonFromExcelPreview(filePath,3);
		System.out.println(getJsonFromExcelPreview(filePath,1));
		Date date2 = new Date();
		System.out.println((date2.getTime()-date1.getTime()));
	}

}
