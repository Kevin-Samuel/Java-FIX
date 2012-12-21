package be.tritschler.fix.core.message;

import java.util.LinkedHashMap;

import be.tritschler.fix.tags.*;
import be.tritschler.utils.*;

public class MessageHeader {

	private String sender;
	private String target;
	private int msgseqnum;
	
	public MessageHeader(String s, String t, int n) {
		sender = s;
		target = t;
		msgseqnum = n;
	}
	public static boolean isValidHeader(LinkedHashMap<String, String> message) {
		
		return true;
	}
	
	public static boolean isValidHeader(String header) {
		boolean isSenderCompID = false;
		boolean isTargetCompID = false;
		boolean isMsgSeqNum = false;
		boolean isSendingTime = false;
		
		String tagid;
		
		if (header == null) return false;
		String [] tags = header.split(""+(char)Constants.SOH);
		if (tags.length < 7) return false;
		if (!new BeginString().isValid(tags[0])) return false;	// BeginString MUST be 1st tag
		if (!new BodyLength().isValid(tags[1])) return false;		// BodyLength MUST be 2nd tag
		if (!new MsgType().isValid(tags[2])) return false;		// MsgType MUST be 3rd tag
		for (int i=3; i<tags.length; i++) {
//			if (!isValidHeaderTagId(tags[i], Constants.FIX40)) {
//				// found a tag not belonging to the header -> assume end of header
//				break;
//			}
			tagid = Tag.getTagId(tags[i]);
			if (tagid.equals(SenderCompID.TAG)) {
				if (!new SenderCompID().isValid(tags[i])) return false;
				isSenderCompID=true;
				continue;
			}
			if (tagid.equals(TargetCompID.TAG)) {
				if (!new TargetCompID().isValid(tags[i])) return false;
				isTargetCompID=true;
				continue;
			}
			if (tagid.equals(MsgSeqNum.TAG)) {
				if (!new MsgSeqNum().isValid(tags[i])) return false;
				isMsgSeqNum=true;
				continue;
			}
			if (tagid.equals(SendingTime.TAG)) {
				if (!new SendingTime().isValid(tags[i])) return false;
				isSendingTime=true;
				continue;
			}
			
			// TODO remaining optional tags
		}
		
		if (!isSenderCompID || !isTargetCompID || !isMsgSeqNum || !isSendingTime) return false;
		return true;
	}
	
}
