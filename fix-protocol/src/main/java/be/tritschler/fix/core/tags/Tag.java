package be.tritschler.fix.core.tags;

import be.tritschler.utils.StringUtils;

public abstract class Tag {
	
	protected String tagId;
	protected String tagName;
	
	public final boolean isValidStructure(String tag) {
		if (tag == null) {
			return false;
		}
		if (!isValidTagStructure(tag)) {
			return false;
		}
		if (getTagId(tag).equals(tagId)) {
			return false;
		}
		
		return true;
	}
		
	public static boolean isValidTagId(String tag, String fixVersion) {
		if (tag == null || fixVersion == null) {
			return false;
		}
		if (fixVersion.equals(BeginString.VERSION)) {
			// FIX 4.0 : valid tags from 1 to 140
			try {
				int i = Integer.parseInt(tag);
				if (i < 1 || i > 140) {
					return false;
				}
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			// other protocols not supported
			return false;
		}
	}
	public static final String getTagId(String tag) {
		return tag.substring(0, tag.indexOf(Constants.EQUAL));
	}
	
	public static final String getTagValue(String tag) {
		return tag.substring(tag.indexOf(Constants.EQUAL)+1);
	}
	
	public static final boolean isValidTagStructure(String tag) {
		if (tag == null) return false;
		if (tag.length() < 3) return false;
		String [] c = tag.split(Constants.EQUAL);
		if (c.length<2) return false;
		if (!isValidTagId(c[0], BeginString.VERSION)) return false;
		return true;
	}
	
	public static final boolean isTagInHeader(String tagid) {
		return StringUtils.isInStringArray(tagid, 
				BeginString.TAG,
				BodyLength.TAG,
				MsgType.TAG,
				SenderCompID.TAG,
				TargetCompID.TAG,
				OnBehalfOfCompID.TAG,
				DeliverToCompID.TAG,
				SecureDataLen.TAG,
				SecureData.TAG,
				MsgSeqNum.TAG,
				SenderSubID.TAG,
				TargetSubID.TAG,
				OnBehalfSubID.TAG,
				DeliverToSubID.TAG,
				PossDupFlag.TAG,
				PossResend.TAG,
				SendingTime.TAG,
				OrigSendingTime.TAG);		
	}
	public static final boolean isTagInBody(String tag) {
		return (!(isTagInHeader(tag) || isTagInTrailer(tag)));				
	}	
	public static final boolean isTagInTrailer(String tagid) {
		return StringUtils.isInStringArray(tagid, 
				Signature.TAG,
				SignatureLength.TAG,
				CheckSum.TAG);
	}
	public final String getName() {
		return this.tagName;
	}	
	public abstract boolean isValid(String tag);
	public abstract int getGroupId();	
}
