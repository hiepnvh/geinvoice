package vn.gmobile.einvoice.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileUtils {
	
	public static String readFile(String filePath) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF8"));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.getProperty("line.separator"));
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
	
	public static void writeFile(String filePath, String content) throws IOException {
		File file = new File(filePath);
		file.getParentFile().mkdirs();
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF8"));
		out.append(content).append(System.getProperty("line.separator"));
		out.flush();
		out.close();
	}
}
