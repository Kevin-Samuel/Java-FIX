package be.tritschler.fix.core.tags;

public class OrigSendingTime extends Tag {
	
	public static final String TAG = "122";
	public static final String NAME = "OrigSendingTime";
	
	public OrigSendingTime () {
		this.tagId = TAG;
		this.tagName = NAME;
	}

	@Override
	public boolean isValid(String tag) {
		if (!isValidStructure(tag)) return false;
		// TODO check valid time
		return true;
	}

	@Override
	public int getGroupId() {
		return Constants.HEADER;
	}
}
