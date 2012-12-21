package be.tritschler.fix.core.tags;

public class TargetCompID extends Tag {
	
	public static final String TAG = "56";
	public static final String NAME = "TargetCompID";
	
	public TargetCompID() {
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
