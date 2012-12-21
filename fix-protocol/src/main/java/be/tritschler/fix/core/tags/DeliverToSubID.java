package be.tritschler.fix.core.tags;

public class DeliverToSubID extends Tag {
	
	public static final String TAG = "129";
	public static final String NAME = "DeliverToSubID";		
	
	public DeliverToSubID() {
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