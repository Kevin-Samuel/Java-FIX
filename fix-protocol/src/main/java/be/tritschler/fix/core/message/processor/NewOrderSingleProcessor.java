package be.tritschler.fix.core.message.processors;

import org.apache.log4j.Logger;

import be.tritschler.fix.core.message.Message;
import be.tritschler.fix.core.message.NewOrderSingle;

public class NewOrderSingleProcessor implements MessageProcessor<NewOrderSingle> {
	
	private static final Logger logger = Logger.getLogger(NewOrderSingleProcessor.class);
	
	private Message message;
	
	public NewOrderSingleProcessor(Message message) {
		this.message = message;
	}
	
	@Override
	public void validate() {
		// sysntax
		
		// business
		
	}

	@Override
	public void process() {

		validate();
		logger.debug("processing " + message);
	}

}
