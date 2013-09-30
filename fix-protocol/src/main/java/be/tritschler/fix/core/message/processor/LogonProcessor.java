package be.tritschler.fix.core.message.processors;

import be.tritschler.fix.core.message.Logon;
import be.tritschler.fix.core.message.Message;

/**
 * LogonProcessor implements the business logic of a Logon message received.
 * 
 * @author tritsma
 *
 */
public class LogonProcessor implements MessageProcessor<Logon>{

	private Message message;
	
	public LogonProcessor(Message message) {
		this.message = message;
	}
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		// validate
		
		//
	}

}
