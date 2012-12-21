package be.tritschler.fix.core.tags;

public class TargetSubID extends Tag {
	
	public static final String TAG = "57";
	public static final String NAME = "TargetSubID";		
	
	public TargetSubID() {
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