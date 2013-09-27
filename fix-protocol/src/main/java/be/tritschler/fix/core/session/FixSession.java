package be.tritschler.fix.core.session;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

import be.tritschler.fix.core.message.Logon;
import be.tritschler.fix.core.message.Message;
import be.tritschler.fix.core.tags.EncryptMethod;


public class FixSession {
	private String name;
	private String senderCompId;
	private String targetCompId;			     
	private	int msgseqnum;
	private DataOutputStream outStream;
	private BufferedReader inStream;
	
	// optional fields of the header
	private String onBehalfOfCompId;
	private String deliverToCompId;
	private String secureData;
	private String senderSubId;
	private String targetSubId;
	private String onBehalfOfSubId;
	private String deliverToSubId;
	
	
	public FixSession(String name, String senderCompId, String targetCompId, DataOutputStream os, BufferedReader is) {
		this.name = name;		
		this.senderCompId = senderCompId;
		this.targetCompId = targetCompId;
		this.msgseqnum = 1;
		this.outStream = os;
		this.inStream = is;
	}

	public void start() {		
		Logon logon;
		try {
			logon = new Logon(senderCompId, targetCompId, msgseqnum);		
			logon.setTag(EncryptMethod.TAG, EncryptMethod.TYPE_DES_EBC);
			logon.setRawData("hhh");
			logon.printTags();
			System.out.println(logon.toString());
			
			outStream.writeBytes(logon.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	public void sendMessage(Message message) {
		try {
			outStream.writeBytes(message.toString());
			msgseqnum++;
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	public String getOnBehalfOfCompId() {
		return onBehalfOfCompId;
	}


	public void setOnBehalfOfCompId(String onBehalfOfCompId) {
		this.onBehalfOfCompId = onBehalfOfCompId;
	}


	public String getDeliverToCompId() {
		return deliverToCompId;
	}


	public void setDeliverToCompId(String deliverToCompId) {
		this.deliverToCompId = deliverToCompId;
	}


	public String getSecureData() {
		return secureData;
	}


	public void setSecureData(String secureData) {
		this.secureData = secureData;
	}


	public String getSenderSubId() {
		return senderSubId;
	}


	public void setSenderSubId(String senderSubId) {
		this.senderSubId = senderSubId;
	}


	public String getTargetSubId() {
		return targetSubId;
	}


	public void setTargetSubId(String targetSubId) {
		this.targetSubId = targetSubId;
	}


	public String getOnBehalfOfSubId() {
		return onBehalfOfSubId;
	}


	public void setOnBehalfOfSubId(String onBehalfOfSubId) {
		this.onBehalfOfSubId = onBehalfOfSubId;
	}


	public String getDeliverToSubId() {
		return deliverToSubId;
	}


	public void setDeliverToSubId(String deliverToSubId) {
		this.deliverToSubId = deliverToSubId;
	}
	


}
