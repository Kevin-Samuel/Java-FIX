package be.tritschler.fix.core.tags;

public class SenderCompID extends Tag {
	
	public static final String TAG = "49";
	public static final String NAME = "SenderCompID";
		
	public SenderCompID () {
		this.tagId = TAG;
		this.tagName = NAME;
	}

	@Override
	public boolean isValid(String tag) {
		if (!isValidStructure(tag)) return false;
		
		return true;
	}

	@Override
	public int getGroupId() {
		return Constants.HEADER;
	}	
}

