package be.tritschler.fix.core.tags;

public class OnBehalfSubID extends Tag {
	
	public static final String TAG = "116";
	public static final String NAME = "OnBehalfSubID";		
	
	public OnBehalfSubID() {
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