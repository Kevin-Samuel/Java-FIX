package be.tritschler.fix.core.tags;

public class Symbol extends Tag {
	
	public static final String TAG = "55";
	public static final String NAME = "Symbol";
	
	public Symbol () {
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
