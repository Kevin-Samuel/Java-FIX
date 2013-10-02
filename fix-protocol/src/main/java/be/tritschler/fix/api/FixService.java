package be.tritschler.fix.api;

import java.util.Date;

public interface FixService {

	// session
	void startSession();
	void endSession();
	
	// trade
	void buy(String instrument, long maxPrice, long quantity, Date validity);
	void sell();
}
