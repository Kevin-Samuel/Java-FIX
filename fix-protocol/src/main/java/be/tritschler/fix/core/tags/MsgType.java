package be.tritschler.fix.core.tags;

import be.tritschler.utils.StringUtils;

public class MsgType extends Tag {
	
	public static final String TAG = "35";
	public static final String NAME = "MsgType"; 
	
	public static final String TYPE_HEARTBEAT          = "0";
	public static final String TYPE_TEST_REQ           = "1";
	public static final String TYPE_RESEND_REQ         = "2";
	public static final String TYPE_REJECT             = "3";
	public static final String TYPE_SEQ_RESET          = "4";
	public static final String TYPE_LOGOUT             = "5";
	public static final String TYPE_IOI                = "6";
	public static final String TYPE_ADVERT             = "7";
	public static final String TYPE_EXEC_REPORT        = "8";
	public static final String TYPE_ORDER_CX_REJ       = "9";
	public static final String TYPE_LOGON              = "A";
	public static final String TYPE_NEWS               = "B";
	public static final String TYPE_EMAIL              = "C";
	public static final String TYPE_NEW_ORDER_SINGLE   = "D";
	public static final String TYPE_ORDER_LIST         = "E";	
	public static final String TYPE_ORDER_CX_REQ       = "F";
	public static final String TYPE_ORDER_CX_REPL_REQ  = "G";
	public static final String TYPE_ORDER_STATUS_REQ   = "H";
	public static final String TYPE_ALLOCATION         = "J";
	public static final String TYPE_LIST_CX_REQ        = "K";
	public static final String TYPE_LIST_EXECUTE       = "L";
	public static final String TYPE_LIST_STATUS_REQ    = "M";
	public static final String TYPE_LIST_STATUS        = "N";
	public static final String TYPE_ALLOCATION_ACK     = "P";
	public static final String TYPE_DONT_KNOW_TRADE    = "Q";
	public static final String TYPE_QUOTE_REQ          = "R";
	public static final String TYPE_QUOTE              = "S";
	public static final String TYPE_PRIVATE            = "U";	
	
	public MsgType() {
		this.tagId = TAG;
		this.tagName = NAME;
	}
	
	@Override
	public boolean isValid(String tag) {
		if (!isValidStructure(tag)) return false;			
		if (StringUtils.isInStringArray(getTagValue(tag), 
				TYPE_HEARTBEAT,	TYPE_TEST_REQ, TYPE_RESEND_REQ, TYPE_REJECT, TYPE_SEQ_RESET,
				TYPE_LOGOUT, TYPE_IOI, TYPE_ADVERT, TYPE_EXEC_REPORT, TYPE_ORDER_CX_REJ,
				TYPE_LOGON,	TYPE_NEWS, TYPE_EMAIL, TYPE_NEW_ORDER_SINGLE, TYPE_ORDER_LIST,	
				TYPE_ORDER_CX_REQ, TYPE_ORDER_CX_REPL_REQ, TYPE_ORDER_STATUS_REQ, TYPE_ALLOCATION,
				TYPE_LIST_CX_REQ, TYPE_LIST_EXECUTE, TYPE_LIST_STATUS_REQ, TYPE_LIST_STATUS,
				TYPE_ALLOCATION_ACK, TYPE_DONT_KNOW_TRADE, TYPE_QUOTE_REQ, TYPE_QUOTE,TYPE_PRIVATE))
			return true;
		else
			return false;
	}

	@Override
	public int getGroupId() {		
		return Constants.HEADER;
	}

	public static String getMessageName(String msgType) {
		if (msgType.equals(TYPE_HEARTBEAT)) return "HeartBeat";
		if (msgType.equals(TYPE_TEST_REQ)) return "TestRequest";
		if (msgType.equals(TYPE_RESEND_REQ)) return "ResendRequest";
		if (msgType.equals(TYPE_REJECT)) return "Reject";
		if (msgType.equals(TYPE_SEQ_RESET)) return "SequenceReset";
		if (msgType.equals(TYPE_LOGOUT)) return "Logout";
		if (msgType.equals(TYPE_IOI)) return "IndicationOfInterest";
		if (msgType.equals(TYPE_ADVERT)) return "Advertisement";
		if (msgType.equals(TYPE_EXEC_REPORT)) return "ExecutionReport";
		if (msgType.equals(TYPE_ORDER_CX_REJ)) return "OrderCancelReject";
		if (msgType.equals(TYPE_LOGON)) return "Logon";
		if (msgType.equals(TYPE_NEWS)) return "News";
		if (msgType.equals(TYPE_EMAIL)) return "Email";
		if (msgType.equals(TYPE_NEW_ORDER_SINGLE)) return "Order Single";
		if (msgType.equals(TYPE_ORDER_LIST)) return "Order List";	
		if (msgType.equals(TYPE_ORDER_CX_REQ)) return "Order Cancel Request";
		if (msgType.equals(TYPE_ORDER_CX_REPL_REQ)) return "Order Cancel Replace Request";
		if (msgType.equals(TYPE_ORDER_STATUS_REQ)) return "Order Status Request";
		if (msgType.equals(TYPE_ALLOCATION)) return "Allocation";
		if (msgType.equals(TYPE_LIST_CX_REQ)) return "List Cancel Request";
		if (msgType.equals(TYPE_LIST_EXECUTE)) return "List Execute";
		if (msgType.equals(TYPE_LIST_STATUS_REQ)) return "List Status Request";
		if (msgType.equals(TYPE_LIST_STATUS)) return "List Status";
		if (msgType.equals(TYPE_ALLOCATION_ACK)) return "Alocation ACK";
		if (msgType.equals(TYPE_DONT_KNOW_TRADE)) return "Don't Know Tarde";
		if (msgType.equals(TYPE_QUOTE_REQ)) return "Quote Request";
		if (msgType.equals(TYPE_QUOTE)) return "Quote";
		if (msgType.equals(TYPE_PRIVATE)) return "Private";
		return "Unknown";
	}
}
