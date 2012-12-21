package be.tritschler.fix.core.tags;

public class SecureData extends Tag {
	
	public static final String TAG = "90";
	public static final String NAME = "SecureData";		
	
	public SecureData() {
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


