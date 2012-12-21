package be.tritschler.fix.core.message;

import java.util.LinkedHashMap;

import be.tritschler.fix.core.tags.BeginString;


public class Message {

	protected String msgtype;	
	private LinkedHashMap<String, String> tags = new LinkedHashMap<String, String>();
	
	public Message() {};
	public Message(String senderCompId, String targetCompId, int msgseqnum) {
		
	}
	public void clear() {
		tags.clear();
	}
	
	public String getMsgtype() {
		return msgtype;
	}

	public void setTag(String tagid, String value) {
		if (tagid != null && value != null) {
			tags.put(tagid, value);
			if (tagid.equals(MsgType.TAG)) msgtype = value;
		}
	}
	
	@Override
	public String toString() {
		String msg = "";
		
		if (!isValid()) return "invalid";
		setTag(BodyLength.TAG, computeBodyLength());
		for(String key: tags.keySet()) {
			msg += key + Constants.EQUAL + tags.get(key) + (char)Constants.SOH;
		}
		setTag(CheckSum.TAG, CheckSum.computeCheckSum(msg));   // not needed but to be complete
		msg += CheckSum.TAG + Constants.EQUAL + CheckSum.computeCheckSum(msg);
		return msg;
	}
	
	public boolean isValid() {
		return true;
	}
	
	protected void buildHeader(String msgType, String senderCompId, String targetCompId, String msgseqnum) {
		setTag(BeginString.TAG, BeginString.VERSION);
		setTag(BodyLength.TAG, "000"); // dummy length ... will be computed later
		setTag(MsgType.TAG, msgType);
		setTag(SenderCompID.TAG, senderCompId);
		setTag(TargetCompID.TAG, targetCompId);
		setTag(MsgSeqNum.TAG, msgseqnum);
		setTag(SendingTime.TAG, SendingTime.getSendingTime());
	}
	
//	private void buildTrailer() {
//		// SignatureLength
//		// Signature
//		setTag(CheckSum.TAG, "xxx");
//	}		 
	
	private String computeBodyLength() {
		int i=0;
		for(String key: tags.keySet()) {
			if (!key.equals(BeginString.TAG) &&
				!key.equals(BodyLength.TAG)  &&
				!key.equals(CheckSum.TAG)    &&
				!key.equals(Signature.TAG)   &&
				!key.equals(SignatureLength.TAG)) {
//				System.out.println(key + "(" + key.length() + ") " + tags.get(key) + "(" + tags.get(key).length() + ")");
				i+= key.length() + Constants.EQUAL.length() + tags.get(key).length() + 1;
			}
		}
		return i+"";
	}
	
	public void printTags() {
		for(String key: tags.keySet()) {
			System.out.println(key + ": " + tags.get(key));
		}
	}
	
	// optional fields of the header 
	public void setOnBehalfOfCompID(String value) {
		setTag(OnBehalfOfCompID.TAG, value);
	}
	
	public void setDeliverToCompID(String value) {
		setTag(DeliverToCompID.TAG, value);
	}
	
	public void setSecureData(String value) {
		setTag(SecureData.TAG, value);
		setTag(SecureDataLen.TAG, value.length()+"");
	}
	
	
	// optional fields of the trailer
	// Signature (+ length)
	
	public static void main(String ...args) {
		Message message = new Message();
		message.setTag(BeginString.TAG, "FIX.4.0");
		message.setTag("9", "65");
		
		System.out.println(message.toString());
	}
}
