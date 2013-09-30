package be.tritschler.fix.core.message.processors;

import be.tritschler.fix.core.message.Message;

public interface MessageProcessor<T extends Message> {

	void validate();
	
	void process();
}
