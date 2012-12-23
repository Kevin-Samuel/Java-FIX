package be.tritschler.fix.core.tags;

import be.tritschler.fix.utils.StringUtils;


public class Side extends Tag {

	public static final String TAG = "54";
	public static final String NAME = "Side";
	
	public static final String SIDE_BUY          = "1";
	public static final String SIDE_SELL         = "2";
	public static final String SIDE_BUY_MINUS    = "3";
	public static final String SIDE_SELL_PLUS    = "4";
	public static final String SIDE_SELL_SHORT   = "5";
	public static final String SIDE_SELL_SHORT_E = "6";
	
	public Side () {
		this.tagId = TAG;
		this.tagName = NAME;
	}
	
	@Override
	public boolean isValid(String tag) {
		if (!isValidStructure(tag)) return false;
		return StringUtils.isInStringArray(getTagValue(tag),
				SIDE_BUY, SIDE_SELL, SIDE_BUY_MINUS, SIDE_SELL_PLUS, SIDE_SELL_SHORT, SIDE_SELL_SHORT_E);
	}

	@Override
	public int getGroupId() {
		return Constants.HEADER;
	}

}
