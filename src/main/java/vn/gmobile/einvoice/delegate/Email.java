package vn.gmobile.einvoice.delegate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;

import com.gtel.email.EmailClient;

public class Email {
	
	private static final Logger LOGGER = Logger.getLogger(Email.class.getName());
	
	/**
	 * Creating emailHelper to send email with param input from, subject, content, recipient
	 * @param execution
	 * @param recipient
	 * @return
	 */
	public static boolean sendEmail(DelegateExecution execution, String recipient){
		try {
			// TODO Auto-generated method stub
//			if(ServerConfig.get_SEND_EMAIL())	{
				EmailClient email = new EmailClient();
				
				String from = execution.getVariable("from").toString();
				String subject = execution.getVariable("subject").toString();
				String content = execution.getVariable("content").toString();
				
//				for product
//				email.sendEmail(from, subject, content, recipient);

//				for testing
				List<String> recipients = new ArrayList<String>();
				recipients.add(recipient);
//				recipients.add("hiep.nvh@gmobile.vn");
				
				email.sendEmail(from, subject, content, recipients);
//			}
			
			LOGGER.info("Send email to "+ recipients);
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}
