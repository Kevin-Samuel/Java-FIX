package be.tritschler.fix.apps;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import be.tritschler.fix.core.message.Message;
import be.tritschler.fix.core.message.Reject;
import be.tritschler.fix.core.session.SessionState;
import be.tritschler.fix.core.tags.CheckSum;
import be.tritschler.fix.core.tags.Constants;
import be.tritschler.fix.core.tags.MsgType;
import be.tritschler.fix.core.tags.Tag;
import be.tritschler.fix.core.tags.v40.BeginString;
import be.tritschler.fix.core.tags.v40.TagFactory;

public class FixServer extends Thread {
	
	public static final Logger logger = Logger.getLogger(FixClient.class); 
	
	private String name;
	private BufferedReader buffIn;
	private Socket socket;
	private long nreceived = 0;
//	private int sessionState;
	
	// internal parsing state
	private enum InternalParseState {
		ST_START_NEW_MESSAGE, ST_IN_HEADER, ST_IN_BODY,ST_IN_TRAILER;
	}
   	
	private final int SESSION_INIT = 0;
	private final int SESSION_ESTABLISHED = 1;
	
	public FixServer(String name, Socket socket) {
		this.name = name;
		this.socket = socket;
//		FixSession fixsess = new FixSession(name);		
	}
	
	private static void initLogging() {
		boolean init = true;
		// TODO 
		if (init) {
			logger.info("logging initialized.");
		} else {
			System.out.println("--------- error initializing the Logging system ------");
		}
	}	
		
	public void run() {
		InternalParseState parseState = InternalParseState.ST_START_NEW_MESSAGE;
		SessionState sessionState = SessionState.WAIT_LOGON;
		StringBuilder tag;
		StringBuilder msgIn = new StringBuilder();
		String tagId = "";		
		Message message = new Message();				
		System.out.println("------- " + name + " started -------");
		initLogging();
			try {
				int c;
				String errMsg;
				buffIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));  				
		        while (true) {
		        	if (parseState.equals(InternalParseState.ST_START_NEW_MESSAGE)) {
		        		msgIn.setLength(0);
		        	}
		        	tag = new StringBuilder();
		        	c=buffIn.read();
	           		while ((c != Constants.SOH) && (c != -1)) {
	           			tag.append((char)c);
	           			c=buffIn.read();
	           		}	           		
	           		if (c==-1) {
	           			System.out.println("Client disconnected ...");
	           			return;
	           		}
	           		
	           		// read complete tag (id=value)
	           		logger.debug("received " + tag);
	           		errMsg = validateTag(tag.toString());
	           		if (errMsg != null) {
	           			logger.error(errMsg);
	           			sendReject(0, errMsg);
	           			continue;
	           		}
	           		tagId = Tag.getTagId(tag.toString());
	           		if (message.getTags().containsKey(tagId)) {
	           			logger.error("error: tag already received");
	           			sendReject(0, errMsg);
	           		}
	           		
	           		message.addTag(tagId, Tag.getTagValue(tag.toString())); 
	           			           	
	           		
	           		if (parseState.equals(InternalParseState.ST_START_NEW_MESSAGE)) {
	           			if (!tagId.equals(BeginString.TAG)) {
		           			// expecting tag 8
		           			System.out.println("expecting tag " + BeginString.TAG + " (" + BeginString.NAME + ")");
		           			// TODO send Reject		           		
		           			continue;
	           			}
	           			parseState = InternalParseState.ST_IN_HEADER;
	           		} else if (parseState.equals(InternalParseState.ST_IN_HEADER)) {	           			
	           			if (Tag.isTagInTrailer(tagId)) {
	           				// may not receive a Trailer tag in header
	           				System.out.println("Invalid tag received in header (" + tag + ")");
	           				//TODO send Reject
	           				continue;
	           			} else if (Tag.isTagInBody(tagId)) {
	           				parseState = InternalParseState.ST_IN_BODY;
	           			}
	           		} else if (parseState.equals(InternalParseState.ST_IN_BODY)) {
	           			if (Tag.isTagInHeader(tagId)) {
	           				// may not receive a Header tag in the Body
	           				System.out.println("Invalid tag received in body (" + tag + ")");
	           				//TODO send Reject
	           				continue;	           				
	           			} else if (Tag.isTagInTrailer(tagId)) {
	           				parseState = InternalParseState.ST_IN_TRAILER;
	           			}
	           			
	           		} else if (parseState.equals(InternalParseState.ST_IN_TRAILER)) {
	           			if (!Tag.isTagInTrailer(tagId)) {
	           				// may not receive a tag in Header or Body
	           				System.out.println("Invalid tag received in trailer (" + tag + ")");
	           				//TODO send Reject
	           				tag.setLength(0);
	           				continue;	
	           			}
	           		}		        
	           		
	           		msgIn.append((char)Constants.SOH);
	           		message.addTag(tagId, Tag.getTagValue(tag.toString()));
	           		if (tagId.equals(CheckSum.TAG)) {
	           			// last tag	           		
	           			errMsg = validateMessage(message, sessionState);
	           			if (errMsg != null) {
	           				
	           			}
	           			message.clear();
	           			parseState = InternalParseState.ST_START_NEW_MESSAGE;
	           			msgIn.setLength(0);
	           			nreceived++;
	           			// TODO process messages ...
	           		}
		        }
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private void sendReject(int id, String text) {
		Reject reject = new Reject.Builder(id).text(text).build();
		// send the reject ...
	}

	private String validateTag(String tag) {
		// syntax validation
		if (!Tag.isValidTagStructure(tag)) {	           			
   			return "invalid tag ("+ tag + ") received";
   		}
		// TODO semantic validation
		
		return null;
	}
	
	public String validateMessage(Message message, SessionState sessionState) {
	System.out.println("Received message " + nreceived + " : " + MsgType.getMessageName(message.getMsgtype()));
	// validate the syntax
	//	System.out.println(MessageHeader.isValidHeader(message));	
	
	
		switch (sessionState) {
		case WAIT_LOGON:
			// only a Logon must be received ....
		case WAIT_MESSAGE:
			// any message
		}
	
	return null;
}
	
	public static void main(String[] args) {
		int port = 8080;
		int i = 0;
		try {			
        	ServerSocket serverSocket = new ServerSocket(port);
	        while(true) {
	        	System.out.println("Listening on port "+ port + " ...");
	            Socket socket = serverSocket.accept();
	            i++;
	            System.out.println("Client connected " + socket.getRemoteSocketAddress());
	            new FixServer("Server " + i, socket).start();	            
	        }
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
