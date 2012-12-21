package be.tritschler.fix.core.tags;

import be.tritschler.utils.StringUtils;

public class OrdType extends Tag {	
 	
	public static final String TAG = "40";
	public static final String NAME = "OrdType";
	
	public static final String MARKET             = "1";
	public static final String LIMIT              = "2";
	public static final String STOP               = "3";
	public static final String STOP_LIMIT         = "4";
	public static final String MKT_ON_CLOSE       = "5";
	public static final String WITH_WITHOUT       = "6";
	public static final String LIMIT_OR_BETTER    = "7";
	public static final String LIMIT_WITH_WITHOUT = "8";
	public static final String ON_BASIS           = "9"; 
	public static final String ON_CLOSE           = "A";
	public static final String LIMIT_ON_CLOSE     = "B";
	public static final String FOREX              = "C";
	public static final String PREV_QUOTED        = "D";
	public static final String PREV_INDICATED     = "E";
	public static final String PEGGED             = "P";
		
	public OrdType() {
		this.tagId = TAG;
		this.tagName = NAME;
	}
	
	@Override
	public boolean isValid(String tag) {
		if (!isValidStructure(tag)) return false;
		return StringUtils.isInStringArray(getTagValue(tag),
				MARKET, LIMIT, STOP, STOP_LIMIT, MKT_ON_CLOSE, WITH_WITHOUT, LIMIT_OR_BETTER,
				LIMIT_WITH_WITHOUT, ON_BASIS, ON_CLOSE, LIMIT_ON_CLOSE, FOREX, PREV_QUOTED,
				PREV_INDICATED, PEGGED);	
	}

	@Override
	public int getGroupId() {
		return Constants.HEADER;
	}
	
}
