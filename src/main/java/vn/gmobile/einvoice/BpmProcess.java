package vn.gmobile.einvoice;

import java.util.Map;
import java.util.logging.Logger;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;

public class BpmProcess {
	
	private final static Logger LOGGER = Logger.getLogger(BpmProcess.class.getName());

	public static final String INVOICE_GENERATE = "invoice-generate";
	public static final String INVOICE_REVENUE_REPORT = "invoice-revenue-report";
	public static final String INVOICE_GENERATE_NOTIMER = "invoice-generate-notimer";
	public static final String INVOICE_CONVERSION = "invoice-conversion";
	
//	public static final String INVOICE_GENERATE_MSG = "Msg_InvGen";

	/**
	 * createProcess function to create process from anywhere
	 * 
	 * @param processId String
	 * @param businessKey String
	 * @param variables Map<String, Object>
	 */
	public static void createProcessByKey(String processId, String businessKey, Map<String, Object> variables){
		
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getRuntimeService().startProcessInstanceByKey(processId, businessKey, variables);
		
	}
	
	public static void createProcessByMessage(String msgName, String businessKey, Map<String, Object> variables){
		RuntimeService runtimeService = ProcessEngines.getDefaultProcessEngine().getRuntimeService();
		ProcessInstance pi = runtimeService.startProcessInstanceByMessage(msgName, businessKey, variables);
	}
}
