package be.tritschler.fix.core.tags;

public class OnBehalfOfCompID extends Tag  {
	
	public static final String TAG = "115";
	public static final String NAME = "OnBehalfOfCompID"; 
	
	public OnBehalfOfCompID() {
		this.tagId = TAG;
		this.tagName = NAME;
	}

	@Override
	public int getGroupId() {
		return Constants.HEADER; 
	}

	@Override
	public boolean isValid(String tag) {
		if (!isValidStructure(tag)) return false;

		return true;
	}
	
	
	

}
