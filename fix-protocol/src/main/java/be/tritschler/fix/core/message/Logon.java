package be.tritschler.fix.core.message;

import be.tritschler.fix.core.tags.EncryptMethod;
import be.tritschler.fix.core.tags.HeartBtInt;
import be.tritschler.fix.core.tags.MsgType;
import be.tritschler.fix.core.tags.RawData;
import be.tritschler.fix.core.tags.RawDataLength;



public class Logon extends Message {
	
	public static final String TYPE = MsgType.TYPE_LOGON;
	
	public Logon() {
		super();
	}
	
	public Logon (String senderCompId,
				  String targetCompId,			     
				  int msgseqnum,
				  String rawData) throws Exception {
		this(senderCompId, targetCompId, msgseqnum);
		if (rawData != null) setRawData(rawData);
	}
	
	public Logon(String senderCompId,
			     String targetCompId,			     
			     int msgseqnum) throws Exception {

		if (senderCompId == null || targetCompId == null) {
			System.out.println("internal error");
			throw new Exception();
		}
		
		buildHeader(Logon.TYPE, senderCompId, targetCompId, msgseqnum+"");
		// set default values of Logon message required fields
		setTag(EncryptMethod.TAG, EncryptMethod.TYPE_NONE);
		setTag(HeartBtInt.TAG, "60000");		
	
	}

	public void setRawData(String rawData) {
		setTag(RawData.TAG, rawData);		
		setRawDataLength(rawData.length());
	}
	
	public void setRawDataLength(int length) {
		setTag(RawDataLength.TAG, length+"");
	}

}
