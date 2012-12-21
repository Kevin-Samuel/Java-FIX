package be.tritschler.fix.core.tags;

public class PossResend extends Tag {
	
	public static final String TAG = "97";
	public static final String NAME = "PossResend";
	
	public PossResend () {
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
