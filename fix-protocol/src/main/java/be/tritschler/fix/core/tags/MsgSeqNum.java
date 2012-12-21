package be.tritschler.fix.core.tags;

public class MsgSeqNum extends Tag {
	
	public static final String TAG = "34";
	public static final String NAME = "MsgSeqNum";	

	
	public MsgSeqNum() {
		this.tagId = TAG;
		this.tagName = NAME;
	}

	@Override
	public int getGroupId() {
		return Constants.HEADER;
	}

	@Override
	public boolean isValid(String tag) {
		try {
			int msgseqnum = Integer.parseInt(getTagValue(tag));
			if (msgseqnum < 0 || msgseqnum > 999999) return false;
			return true;
		} catch (Exception e) {
			return false;
		}
	}	
}
