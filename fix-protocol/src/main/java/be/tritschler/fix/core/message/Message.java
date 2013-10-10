package be.tritschler.fix.core.message;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import be.tritschler.fix.core.tags.BodyLength;
import be.tritschler.fix.core.tags.CheckSum;
import be.tritschler.fix.core.tags.Constants;
import be.tritschler.fix.core.tags.DeliverToCompID;
import be.tritschler.fix.core.tags.MsgSeqNum;
import be.tritschler.fix.core.tags.MsgType;
import be.tritschler.fix.core.tags.OnBehalfOfCompID;
import be.tritschler.fix.core.tags.SecureData;
import be.tritschler.fix.core.tags.SecureDataLen;
import be.tritschler.fix.core.tags.SenderCompID;
import be.tritschler.fix.core.tags.SendingTime;
import be.tritschler.fix.core.tags.Signature;
import be.tritschler.fix.core.tags.SignatureLength;
import be.tritschler.fix.core.tags.TargetCompID;
import be.tritschler.fix.core.tags.v40.BeginString;


public class Message {

	protected Header header;
	protected Trailer trailer; 	
	protected String msgtype;	
	
	private Map<String, String> tags = new LinkedHashMap<String, String>();
			
	public void clear() {
		tags.clear();
	}
	
	public String getMsgtype() {
		return msgtype;
	}

	public void addTag(String tagid, String value) {
		if ((tagid != null) && (value != null)) {
			tags.put(tagid, value);
			if (tagid.equals(MsgType.TAG)) {
				msgtype = value;
			}
		}
	}
	
	@Override
	public String toString() {
		// TODO IMPLEMENT better
		StringBuilder sbuilder = new StringBuilder();				
		// computes the body lenght and checksum if needed (1st call of the method)
		addTag(BodyLength.TAG, computeBodyLength()+"");
		addTag(CheckSum.TAG, CheckSum.computeCheckSum(sbuilder.toString()));
		for(String key: tags.keySet()) {
			sbuilder.append(key).append(Constants.EQUAL).append(tags.get(key)).append((char)Constants.SOH);
		}
		return sbuilder.toString();
	}
	
	// TODO: implement
	public Message unmarshall(byte[] stream) {
		return null;
	}
	
	public byte[] marshall() {
		StringBuilder sbuilder = new StringBuilder();		
//		if (!isValid()) { return "invalid"; }
		addTag(BodyLength.TAG, computeBodyLength()+"");
		addTag(CheckSum.TAG, CheckSum.computeCheckSum(sbuilder.toString()));
		for(String key: tags.keySet()) {
			sbuilder.append(key).append(Constants.EQUAL).append(tags.get(key)).append(Constants.SOH);
		}
		//return sbuilder.toString().getBytes(StandardCharsets.US_ASCII);   ONLY with Java 1.7 :-(
		try {
			return sbuilder.toString().getBytes("US_ASCII");
		} catch (UnsupportedEncodingException e) {
			// DO NOTHIING because US_ASCII is valid ... remove the try/cath when upgradinde
			return null;
		}	 
	}
	
	public boolean isValid() {
		return true;
	}
	
	protected void buildHeader(String msgType, String senderCompId, String targetCompId, String msgseqnum) {
		addTag(BeginString.TAG, BeginString.VERSION);
		addTag(BodyLength.TAG, "000"); // dummy length ... will be computed later
		addTag(MsgType.TAG, msgType);
		addTag(SenderCompID.TAG, senderCompId);
		addTag(TargetCompID.TAG, targetCompId);
		addTag(MsgSeqNum.TAG, msgseqnum);
		addTag(SendingTime.TAG, SendingTime.getSendingTime());
	}
	
	protected void buildHeader(String msgType) {
		addTag(BeginString.TAG, BeginString.VERSION);
		addTag(BodyLength.TAG, "000"); // dummy length ... will be computed later
		addTag(MsgType.TAG, msgType);
//		addTag(SenderCompID.TAG, senderCompId);
//		addTag(TargetCompID.TAG, targetCompId);
//		addTag(MsgSeqNum.TAG, msgseqnum);
		addTag(SendingTime.TAG, SendingTime.getSendingTime());
	}
//	private void buildTrailer() {
//		// SignatureLength
//		// Signature
//		setTag(CheckSum.TAG, "xxx");
//	}		 
	
	private int computeBodyLength() {
		int i=0;
		for(String key: tags.keySet()) {
			if ((!key.equals(BeginString.TAG)) &&
				(!key.equals(BodyLength.TAG))  &&
				(!key.equals(CheckSum.TAG))    &&
				(!key.equals(Signature.TAG))   &&
				(!key.equals(SignatureLength.TAG))) {
				i+= key.length() + Constants.EQUAL.length() + tags.get(key).length() + 1;
			}
		}
		return i;
	}
	
//	public void printTags() {
//		for(String key: tags.keySet()) {
//			System.out.println(key + ": " + tags.get(key));
//		}
//	}
	
	// optional fields of the header 
	public void setOnBehalfOfCompID(String value) {
		addTag(OnBehalfOfCompID.TAG, value);
	}
	
	public void setDeliverToCompID(String value) {
		addTag(DeliverToCompID.TAG, value);
	}
	
	public void setSecureData(String value) {
		addTag(SecureData.TAG, value);
		addTag(SecureDataLen.TAG, value.length()+"");
	}
	
	
	// optional fields of the trailer
	// Signature (+ length)
	public Map<String, String> getTags() {
		return tags;
	}
}
