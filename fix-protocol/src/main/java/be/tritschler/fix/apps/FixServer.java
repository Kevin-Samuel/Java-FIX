package be.tritschler.fix.apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import be.tritschler.fix.core.message.Logon;
import be.tritschler.fix.core.message.Message;
import be.tritschler.fix.core.message.Reject;
import be.tritschler.fix.core.message.processors.LogonProcessor;
import be.tritschler.fix.core.session.SessionState;
import be.tritschler.fix.core.tags.BodyLength;
import be.tritschler.fix.core.tags.CheckSum;
import be.tritschler.fix.core.tags.Constants;
import be.tritschler.fix.core.tags.MsgType;
import be.tritschler.fix.core.tags.Tag;
import be.tritschler.fix.core.tags.v40.BeginString;
import be.tritschler.fix.core.tags.v40.TagFactory;

public class FixServer extends Thread {
	
	public static final Logger logger = Logger.getLogger(FixClient.class); 
	
	private final String name;
	private BufferedReader buffIn;
	private Socket socket;
	private long nreceived = 0;
	private long nvalid = 0;
	private final String fixVersion;
	private InternalParseState parseState;
	
	private enum InternalParseState {
		WAIT_BEGINSTRING,		// expects tag  8 (always 1st tag)
		WAIT_BODYLENGTH,		// expects tag  9 (always 2nd tag)
		WAIT_MSG_TYPE,			// expects tag 35 (always 3rd tag)
		ST_IN_HEADER,
		ST_IN_BODY,
		ST_IN_TRAILER;
	}
   	
	
	public FixServer(String name, Socket socket, String fixVersion) {
		this.name = name;
		this.socket = socket;
		this.fixVersion = fixVersion;
//		FixSession fixsess = new FixSession(name);		
	}
	
	private void initLogging() {
		boolean init = true;
		// TODO 
		if (init) {
			logger.info("[" + this.name + "] : logging initialized.");
		} else {
			System.out.println("--------- error initializing the Logging system ------");
		}
	}	
		
	public void run() {
		parseState = InternalParseState.WAIT_BEGINSTRING;
		SessionState sessionState = SessionState.WAIT_LOGON;
		StringBuilder tag;
		String tagId = "";		
		Message message = new Message();				
		System.out.println("------- " + name + " started -------");
		initLogging();
		try {
			int c;
			String errMsg;
			buffIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));  				
			while (true) {
				tag = new StringBuilder();
				c=buffIn.read();
				while ((c != Constants.SOH) && (c != -1)) {
					tag.append((char)c);
					c=buffIn.read();
				}	           		
				if (c==-1) {					
					logger.info("[" + this.name + "]" + ": client disconnected.");
					return;
				}

				// read complete tag (id=value) ... validating it
				logger.debug("[" + this.name + "]" + "received tag: " + tag);
				errMsg = validateTag(tag.toString(), parseState);
				if (errMsg != null) {
					logger.error("[" + this.name + "]" + ": " + errMsg);
					sendReject(0, errMsg);
					continue;
				}
				tagId = Tag.getTagId(tag.toString());
				if ((message.getTags() != null) && (message.getTags().containsKey(tagId))) {
					logger.error("[" + this.name + "]" + ": error: tag already received");
					sendReject(0, errMsg);
					continue;
				} else {
					setNextParseState();
				}

				// valid tag ... continue processing. 
				message.addTag(tagId, Tag.getTagValue(tag.toString()));
				if (tagId.equals(CheckSum.TAG)) {
					// complete message received ... processing it
					logger.info("[" + this.name + "]" + ": received message :" + message.toString());
					errMsg = validateMessage(message, sessionState);
					if (errMsg != null) {
						processMessage(message);
					} else {
						// TODO process messages ...
						message.clear();
						parseState = InternalParseState.WAIT_BEGINSTRING;
						nreceived++;
						nvalid++;
					}
				}
			}

		} catch (IOException e) {
			logger.fatal("[" + this.name + "]" + ": severe I/O communication occured (" + e.getMessage() + "). Aborting. ", e);
		}
	}
	
	private void sendReject(int id, String text) {
		assert (text != null);
		Reject reject = new Reject.Builder(id).text(text).build();
		// send the reject ...
	}

	// TODO: move in the Tag class ??
	private String validateTag(final String tag, InternalParseState parseState) {
		assert ((tag != null) && (parseState != null));
		// syntax validation(tag=value)
		if (!Tag.isValidTagStructure(tag)) {	           			
			return "invalid tag ("+ tag + ") received";
		}
		String tagId = Tag.getTagId(tag.toString());

		// is the tag valid at this point of the stream ?
		switch (parseState) {
			case WAIT_BEGINSTRING:
				if (!tagId.equals(BeginString.TAG)) {
					return "expecting tag " + BeginString.TAG + " (" + BeginString.NAME + ")";
				}
				if (Tag.getTagValue(tagId).equals("FIX.4.0")) {
					return "invalid value for tag " + BeginString.TAG + " (" + BeginString.NAME + ") : received " + Tag.getTagValue(tagId) + " instead of " + fixVersion;
				}
				parseState = InternalParseState.WAIT_BODYLENGTH;
				return null;
			case WAIT_BODYLENGTH:
				if (!tagId.equals(BodyLength.TAG)) {
					return "expecting tag " + BodyLength.TAG + " (" + BeginString.NAME + ")";
				}
				return null;
			case WAIT_MSG_TYPE:
				if (!tagId.equals(MsgType.TAG)) {
					return "expecting tag " + MsgType.TAG + " (" + BeginString.NAME + ")";
				}
				if (new MsgType(Tag.getTagValue(tagId)).isValid(tagId)) {
					return "tag " + MsgType.TAG + " (" + BeginString.NAME + ") has invalid contents: " + Tag.getTagValue(tagId);
				}
				return null;
			case ST_IN_HEADER:
				// TODO: check given tag is allowed in header ...
			case ST_IN_BODY:
				// TODO : check given tag is allowed in body ...
			case ST_IN_TRAILER:	
				// TODO : check given tag is allowed in trailer ...
		}
		
		
		// TODO semantic validation
		//
		
		return null;
	}
	
	// TODO : move in the Message class ?
	private String validateMessage(Message message, SessionState sessionState) {
		assert ((message != null) && (sessionState != null));
		// validate the syntax
		switch (sessionState) {
		case WAIT_LOGON:
			// only a Logon must be received ....
		case WAIT_MESSAGE:
			// any message
		}
		return null;
	}
	
	private void processMessage(Message message) {
		assert ((message != null) && (message.getMsgtype() != null));
		String msgType = message.getMsgtype();	
		// wait java7 String in switches lol
		if (msgType.equals(Logon.TYPE)) {
			LogonProcessor processor = new LogonProcessor(message);
			processor.process();
		}
		//
	}

	// TODO : move into Enum ?
	private void setNextParseState() {
		String currentParseState = parseState.toString();
		if (parseState.equals(InternalParseState.WAIT_BEGINSTRING)) {
			parseState = InternalParseState.WAIT_BODYLENGTH;
		} else if (parseState.equals(InternalParseState.WAIT_BODYLENGTH)) {
			parseState = InternalParseState.WAIT_MSG_TYPE;
		} else if (parseState.equals(InternalParseState.ST_IN_HEADER)) {
			//
		}
		// TODO continue ...
		logger.debug("setNextState: " + currentParseState + " -> " + parseState.toString());
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
	            new FixServer("Server " + i, socket, "FIX.4.0").start();	            
	        }
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
