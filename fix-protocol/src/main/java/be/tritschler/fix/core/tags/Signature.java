package be.tritschler.fix.core.tags;

public class Signature extends Tag {
	
	public static final String TAG = "89";
	public static final String NAME = "Signature";
	
	public Signature() {
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
		return Constants.TRAILER;
	}
}
