package be.tritschler.fix.core.tags;

public class SecureDataLen extends Tag {
	
	public static final String TAG = "90";
	public static final String NAME = "SecureDataLen";		
	
	public SecureDataLen() {
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
