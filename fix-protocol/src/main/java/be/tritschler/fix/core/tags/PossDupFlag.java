package be.tritschler.fix.core.tags;

public class PossDupFlag extends Tag {
	
	public static final String TAG = "43";
	public static final String NAME = "PossDupFlag";		
	
	public PossDupFlag() {
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