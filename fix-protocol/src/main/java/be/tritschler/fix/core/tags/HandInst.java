package be.tritschler.fix.core.tags;

public class HandInst extends Tag {

	public static final String TAG = "21";
	public static final String NAME = "HandInst";
	
	public static final String TYPE_AUTO_PRIVATE = "1";
	public static final String TYPE_AUTO_PUBLIC  = "2";
	public static final String TYPE_MANUAL       = "3";
	
	public HandInst() {
		this.tagId = TAG;
		this.tagName = NAME;
	}
	
	@Override
	public int getGroupId() {
		return Constants.BODY;
	}

	@Override
	public boolean isValid(String tag) {
		if (tag==null || !isValidStructure(tag)) return false;
		if (!tag.equals(TYPE_AUTO_PRIVATE) && 
			!tag.equals(TYPE_AUTO_PUBLIC)  &&
			!tag.equals(TYPE_MANUAL))
			return false;
		else
			return true;
	}
	
}
