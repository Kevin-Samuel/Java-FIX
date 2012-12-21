package be.tritschler.fix.core.tags;

public class RawDataLength extends Tag {
	
	public static final String TAG = "95";
	public static final String NAME = "RawDataLength";
	
	public RawDataLength() {
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
		return Constants.BODY;
	}
}
