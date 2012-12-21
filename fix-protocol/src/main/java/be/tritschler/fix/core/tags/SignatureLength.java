package be.tritschler.fix.core.tags;

public class SignatureLength extends Tag {
	
	public static final String TAG = "93";
	public static final String NAME = "SignatureLength";
	
	public SignatureLength() {
		this.tagId = TAG; 
		this.tagName = "SignatureLength"; 
	}

	@Override
	public boolean isValid(String tag) {
		
		if (!isValidStructure(tag)) return false;		
		return true;
	}

	@Override
	public int getGroupId() {
		return Constants.TRAILER;
	}
}
