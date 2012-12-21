package be.tritschler.fix.core.tags;

public class BeginString extends Tag {
 		
	public static final String TAG = "8";
	public static final String NAME = "BeginString";
	
	public static final String VERSION = "FIX.4.0";
	
	public BeginString() {
		this.tagId = TAG;
		this.tagName = NAME;
	}
	
	@Override
	public boolean isValid(String tag) {
		if (!isValidStructure(tag)) {
			return false;
		}
		if (!getTagValue(tag).equals(VERSION)) {
			return false;
		}
		return true;
	}

	@Override
	public int getGroupId() {
		return Constants.HEADER;
	}
	
}
