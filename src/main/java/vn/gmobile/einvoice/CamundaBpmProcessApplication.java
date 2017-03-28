package vn.gmobile.einvoice;

import java.util.logging.Logger;

import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;
import org.camunda.bpm.engine.ProcessEngine;

/**
 * Process Application exposing this application's resources the process engine. 
 */
@ProcessApplication
public class CamundaBpmProcessApplication extends ServletProcessApplication {
	
	private static final Logger LOGGER = Logger.getLogger(CamundaBpmProcessApplication.class.getName());

	private static final String PROCESS_DEFINITION_KEY = "electronic.invoice";

	/**
	 * In a @PostDeploy Hook you can interact with the process engine and access
	 * the processes the application has deployed.
	 */
	@PostDeploy
	public void onDeploymentFinished(ProcessEngine processEngine) {
		LOGGER.info(PROCESS_DEFINITION_KEY + " is deployed successfully!");
	}
}
