package be.tritschler.fix.core.tags;

public class RawData extends Tag {
	
	public static final String TAG = "96";
	public static final String NAME = "RawData";
	
	public RawData() {
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