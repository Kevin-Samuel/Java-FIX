package be.tritschler.fix.core.tags;

public class DeliverToCompID extends Tag {

	
	public static final String TAG = "128";
	public static final String NAME = "DeliverToCompID";		
	
	public DeliverToCompID() {
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
