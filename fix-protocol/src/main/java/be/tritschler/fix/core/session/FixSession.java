package be.tritschler.fix.core.session;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import be.tritschler.fix.apps.FixClient;
import be.tritschler.fix.core.message.Logon;
import be.tritschler.fix.core.message.Message;
import be.tritschler.fix.core.tags.EncryptMethod;


public class FixSession {
	
	private static final Logger logger = Logger.getLogger(FixSession.class);
	
	private String name;
	private String senderCompId;
	private String targetCompId;			     
	private	int nbMessagesReceived = 0;
	private int nbMessagesSent = 0;
	private BufferedWriter writer;
	private BufferedReader reader;
	
	// optional fields of the header
	private String onBehalfOfCompId;
	private String deliverToCompId;
	private String secureData;
	private String senderSubId;
	private String targetSubId;
	private String onBehalfOfSubId;
	private String deliverToSubId;
	
	public FixSession(String name, String senderCompId, String targetCompId, BufferedWriter os, BufferedReader is) {
		this.name = name;		
		this.senderCompId = senderCompId;
		this.targetCompId = targetCompId;
		this.nbMessagesReceived = 1;
		this.writer = os;
		this.reader = is;
	}

	public void start() {		
		Logon logon;
		String message = null;
		try {
			logon = new Logon(senderCompId, targetCompId, nbMessagesReceived);		
			logon.addTag(EncryptMethod.TAG, EncryptMethod.TYPE_DES_EBC);
			logon.setRawData("hhh");	
			sendMessage(logon);

			logger.info("sent message :" + message);
			
			// wait answer ...
			
		} catch (Exception e) {
			logger.error("exception sending message: " + message, e);			
		}		
	}
	public void sendMessage(Message message) {
		try {
//			outStream.writeBytes(message.toString());
			writer.write(message.marshall());
			writer.flush();
			nbMessagesSent++;
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
