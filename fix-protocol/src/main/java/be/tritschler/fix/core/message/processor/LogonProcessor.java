package be.tritschler.fix.core.message.processors;

import org.apache.log4j.Logger;

import be.tritschler.fix.core.message.Logon;
import be.tritschler.fix.core.message.Message;

/**
 * LogonProcessor implements the business logic of a Logon message received.
 * 
 * @author tritsma
 *
 */
public class LogonProcessor implements MessageProcessor<Logon>{

	private static final Logger logger = Logger.getLogger(LogonProcessor.class);
	
	private Message message;
	
	public LogonProcessor(Message message) {
		this.message = message;
	}
	
	@Override
	public void validate() {
		logger.debug("validating message ...");
		
	}

	@Override
	public void process() {	
		validate();
		logger.debug("processing " + message);
	}

}
