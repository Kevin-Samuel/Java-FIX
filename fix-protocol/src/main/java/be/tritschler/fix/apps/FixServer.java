package be.tritschler.fix.apps;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import be.tritschler.fix.core.message.Message;
import be.tritschler.fix.core.tags.BeginString;
import be.tritschler.fix.core.tags.CheckSum;
import be.tritschler.fix.core.tags.Constants;
import be.tritschler.fix.core.tags.MsgType;
import be.tritschler.fix.core.tags.Tag;

public class FixServer extends Thread {
	
	//	private int count = 1;
	private String name;
	private BufferedReader buffIn;
	private Socket socket;
	private long nreceived = 0;
	private int sessionState;
	
	private final int ST_START_NEW_MESSAGE = 0;
	private final int ST_IN_HEADER         = 1;
	private final int ST_IN_BODY           = 2;
	private final int ST_IN_TRAILER        = 3;
   	
	private final int SESSION_INIT = 0;
	private final int SESSION_ESTABLISHED = 1;
	
	public FixServer(String name, Socket socket) {
		this.name = name;
		this.socket = socket;
//		FixSession fixsess = new FixSession(name);
		
	}
	public void processMessage(Message message) {
		System.out.println("Received message " + nreceived + " : " + MsgType.getMessageName(message.getMsgtype()));
		// validate the syntax
//		System.out.println(MessageHeader.isValidHeader(message));
		
		if (sessionState == SESSION_INIT) {
			if (!message.getMsgtype().equals(MsgType.TYPE_LOGON)) {
				// expecting a Logon !
				// TODO send Reject ?
			} else {
				// TODO send Logon
				sessionState = SESSION_ESTABLISHED;
				// extract HeartBeat Interval
				// create the FixSession thread
			}
		}
		
		
	}
	
	
	public void run() {
		int c;				
//		sessionState = SESSION_INIT;
		int state = ST_START_NEW_MESSAGE;
		String msgIn = "";
		String tag = "";
		String tagId = "";
		Message message = new Message();
		
		while(true) {
			message.clear();
   			nreceived++;
   			msgIn = "";
			System.out.println("------- " + name + " started -------");
			try {
				buffIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));            
		        while (true) {
		        	tag = "";
		        	c=buffIn.read();
	           		while (c != Constants.SOH && c != -1) {
	           			tag+= (char)c;
	           			c=buffIn.read();
	           		}	           		
	           		if (c==-1) {
	           			// client disconnected
	           			System.out.println("Client gone");
	           			return;
	           		}
	           		if (!Tag.isValidTagStructure(tag)) {
	           			// invalid tag received
	           			System.out.println("invalid tag ("+ tag + ") received");
	           			// TODO send Reject
	           			continue;
	           		}
	           		tagId = Tag.getTagId(tag);
	           		if (state == ST_START_NEW_MESSAGE) {
	           			if (!tagId.equals(BeginString.TAG)) {
		           			// expecting tag 8
		           			System.out.println("expecting tag " + BeginString.TAG + " (" + BeginString.NAME + ")");
		           			// TODO send Reject		           		
		           			continue;
	           			}
	           			state = ST_IN_HEADER;
	           		} else if (state == ST_IN_HEADER) {	           			
	           			if (Tag.isTagInTrailer(tagId)) {
	           				// may not receive a Trailer tag in header
	           				System.out.println("Invalid tag received in header (" + tag + ")");
	           				//TODO send Reject
	           				continue;
	           			} else if (Tag.isTagInBody(tagId)) {
	           				state = ST_IN_BODY;
	           			}
	           		} else if (state == ST_IN_BODY) {
	           			if (Tag.isTagInHeader(tagId)) {
	           				// may not receive a Header tag in the Body
	           				System.out.println("Invalid tag received in body (" + tag + ")");
	           				//TODO send Reject
	           				continue;	           				
	           			} else if (Tag.isTagInTrailer(tagId)) {
	           				state = ST_IN_TRAILER;
	           			}
	           			
	           		} else if (state == ST_IN_TRAILER) {
	           			if (!Tag.isTagInTrailer(tagId)) {
	           				// may not receive a tag in Header or Body
	           				System.out.println("Invalid tag received in trailer (" + tag + ")");
	           				//TODO send Reject
	           				tag = "";
	           				continue;	
	           			}
	           		}		        
	           		
	           		msgIn += tag + (char)Constants.SOH;
	           		message.setTag(tagId, Tag.getTagValue(tag));
	           		if (tagId.equals(CheckSum.TAG)) {
	           			// last tag	           			
	           			processMessage(message);
	           			// TODO send Response if needed
	           			message.clear();
	           			state = ST_START_NEW_MESSAGE;
	           			msgIn = "";
	           			nreceived++;
	           		}
		        }
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
