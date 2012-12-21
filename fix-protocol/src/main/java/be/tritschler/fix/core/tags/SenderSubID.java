package be.tritschler.fix.core.tags;

public class SenderSubID extends Tag {
	
	public static final String TAG = "50";
	public static final String NAME = "SenderSubID";		
	
	public SenderSubID() {
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