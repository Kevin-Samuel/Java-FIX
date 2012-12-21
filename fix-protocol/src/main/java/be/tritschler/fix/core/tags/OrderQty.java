package be.tritschler.fix.core.tags;

public class OrderQty extends Tag {
	
	public static final String TAG = "38";
	public static final String NAME = "OrderQty";
	
	public OrderQty() {
		this.tagId = TAG;
		this.tagName = NAME;
	}
	
	@Override
	public boolean isValid(String tag) {
		if (!isValidStructure(tag)) return false;
		try {
			long q = Long.parseLong(getTagValue(tag));
			if (q<0 || q>1000000000) return false;			
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public int getGroupId() {
		return Constants.HEADER;
	}
}
