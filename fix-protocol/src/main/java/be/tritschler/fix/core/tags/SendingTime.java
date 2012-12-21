package be.tritschler.fix.core.tags;

import java.util.Date;

import be.tritschler.fix.messages.DataType;

public class SendingTime extends Tag {
	
	public static final String TAG = "52";
	public static final String NAME = "SendingTime";
	
	public SendingTime () {
		this.tagId = TAG;
		this.tagName = NAME;
	}

	public static String getSendingTime() {
		return DataType.setTime(new Date());
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
