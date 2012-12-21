package be.tritschler.fix.core.tags;

import be.tritschler.fix.messages.DataType;

public class HeartBtInt extends Tag {

	public static final String TAG = "108";
	public static final String NAME = "HeartBtInt";
	
	public HeartBtInt() {
		this.tagId = TAG;
		this.tagName = NAME;
	}
	
	@Override
	public boolean isValid(String tag) {
		if (!isValidStructure(tag)) return false;		
		Integer I = DataType.getInt(getTagValue(tag)); 
		if (I == null) return false;
		return true;
	}

	@Override
	public int getGroupId() {
		return Constants.HEADER;
	}
	
}
