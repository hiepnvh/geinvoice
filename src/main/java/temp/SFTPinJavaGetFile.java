package temp;
/**
*
*/

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * @author kodehelp
 *
 */
public class SFTPinJavaGetFile {

	/**
	*
	*/
	public SFTPinJavaGetFile() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> iFiles = new ArrayList<String>();
//		getXmlFiles(iFiles);
	}
	
	public static void getXmlFiles(List<String> iFiles, String TARGETDIR) {

		String SFTPHOST = "10.16.17.27";
		int SFTPPORT = 22;
//		String SFTPUSER = "root";
//		String SFTPPASS = "!qaz2wsx3edc";
		String SFTPUSER = "hiepnvh";
		String SFTPPASS = "hiep@123";
		String SFTPWORKINGDIR = "/data/camunda/server/apache-tomcat-8.0.24/webapps/geinvoice-non-delete/";

		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;

		try {
			JSch jsch = new JSch();
			session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
			session.setPassword(SFTPPASS);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(SFTPWORKINGDIR);
			for (String fileName : iFiles) {
				System.out.println(TARGETDIR + fileName.substring(fileName.lastIndexOf("/"), fileName.length()));
				copyFile(channelSftp,fileName ,TARGETDIR + fileName.substring(fileName.lastIndexOf("/"), fileName.length()));
			}
			System.out.println("Done!");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	
	}
	
	public static void copyFile(ChannelSftp channelSftp, String src, String dest) {
		try {

			byte[] buffer = new byte[1024];
			BufferedInputStream bis = new BufferedInputStream(channelSftp.get(src));
			File newFile = new File(dest);
			OutputStream os = new FileOutputStream(newFile);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			int readCount;
			while ((readCount = bis.read(buffer)) > 0) {
				bos.write(buffer, 0, readCount);
			}
			bis.close();
			bos.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}