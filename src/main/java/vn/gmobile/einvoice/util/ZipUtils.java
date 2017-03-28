package vn.gmobile.einvoice.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
	/**
	 * Zip a list of file into one zip file.
	 * 
	 * @param files
	 *            files to zip
	 * @param targetZipFile
	 *            target zip file
	 * @throws IOException
	 *             IO error exception can be thrown when copying ...
	 */
	public static boolean zipFile(final File[] files, final File targetZipFile) throws IOException {
		try {
			FileOutputStream fos = new FileOutputStream(targetZipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);
			byte[] buffer = new byte[128];
			for (int i = 0; i < files.length; i++) {
				File currentFile = files[i];
				if (!currentFile.isDirectory()) {
					ZipEntry entry = new ZipEntry(currentFile.getName());
					FileInputStream fis = new FileInputStream(currentFile);
					zos.putNextEntry(entry);
					int read = 0;
					while ((read = fis.read(buffer)) != -1) {
						zos.write(buffer, 0, read);
					}
					zos.closeEntry();
					fis.close();
				}
			}
			zos.close();
			fos.close();
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("File not found : " + e);
			return false;
		}
	}
	
	/**
	 * 
	 * Zip a list of file into one zip file.
	 * 
	 * @param filePaths
	 * 				List<String> of file Path to zip
	 * @param targetZipFilePath
	 * 				String of zip file Path
	 * @return	boolean result
	 * 				success(true) or not(false)
	 * @throws IOException
	 */
	public static boolean zipFile(List<String> filePaths, String targetZipFilePath) throws IOException {
		try {
			List<File> files = new ArrayList<File>();
			for (String filePath : filePaths) {
				File file = new File(filePath);
				files.add(file);
			}
			return zipFiles(files, targetZipFilePath);
		} catch (FileNotFoundException e) {
			System.out.println("File not found : " + e);
			return false;
		}
	}
	
	public static boolean zipFiles(List<File> files, String targetZipFilePath) throws IOException {
		try {
			FileOutputStream fos = new FileOutputStream(new File(targetZipFilePath));
			ZipOutputStream zos = new ZipOutputStream(fos);
			byte[] buffer = new byte[128];
			for (int i = 0; i < files.size(); i++) {
				File currentFile = files.get(i);
				if (!currentFile.isDirectory() && currentFile.exists()) {
					ZipEntry entry = new ZipEntry(currentFile.getName());
					FileInputStream fis = new FileInputStream(currentFile);
					zos.putNextEntry(entry);
					int read = 0;
					while ((read = fis.read(buffer)) != -1) {
						zos.write(buffer, 0, read);
					}
					zos.closeEntry();
					fis.close();
				}
			}
			zos.close();
			fos.close();
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("File not found : " + e);
			return false;
		}
	}
	
	public static void main(String[] args) throws IOException {

		// #test zipFile #1
//		File[] files;
//		List<File> fileList = new ArrayList<>();
//		File fileXml = new File("D:/test.pdf");
//		File fileXls = new File("D:/mau hoa don gtel.xlsx");
//		File targetZipFile = new File("D:/Result.zip");
//		fileList.add(fileXml);
//		fileList.add(fileXls);
//		files = fileList.toArray(new File[fileList.size()]);
//		if(zipFile(files, targetZipFile))
//			System.out.println("Zip file success!");
//		else
//			System.out.println("Zip file failed!");

		// #test zipFile #2
		List<String> filePaths = new ArrayList<String>();
		filePaths.add("D:/mau hoa don gtel.xlsx");
		filePaths.add("D:/test.pdf");
		String targetZipFilePath = "D:/Result.zip";
		
		if(zipFile(filePaths, targetZipFilePath))
			System.out.println("Zip file success!");
		else
			System.out.println("Zip file failed!");
	}
}