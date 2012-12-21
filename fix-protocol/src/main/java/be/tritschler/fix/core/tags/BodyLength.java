package be.tritschler.fix.core.tags;

public class BodyLength extends Tag {

	public static final String TAG = "9";
	public static final String NAME = "BodyLength";
	
	public BodyLength() {
		this.tagId = TAG;
		this.tagName = NAME;
	}

	@Override
	public boolean isValid(String tag) {
		if (!isValidStructure(tag)) return false;
		int bodylength = Integer.parseInt(getTagValue(tag));
		if (bodylength < 1) return false;
		// TODO compare bodylength with body lenght lol 
		return false;
	}

	@Override
	public int getGroupId() {		
		return Constants.HEADER;
	}	

}
