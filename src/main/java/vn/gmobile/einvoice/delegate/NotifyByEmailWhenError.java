package vn.gmobile.einvoice.delegate;

import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import com.gtel.ldap.bpmn.GLdapHelper;
import com.gtel.ldap.model.User;

public class NotifyByEmailWhenError implements JavaDelegate {

	private static final Logger LOGGER = Logger.getLogger(NotifyByEmailWhenError.class.getName());
			
	/** Notifying email to employees when they have task 
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		String username = (String) execution.getVariable("username");
		GLdapHelper ldap = new GLdapHelper();
		User user = ldap.getUserBySAMAccountName(username);
		String email = user.getMail();
		LOGGER.info("Got email="+email);
		
		Email.sendEmail(execution, email);
	}
}