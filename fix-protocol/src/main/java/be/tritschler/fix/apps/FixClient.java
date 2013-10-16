package be.tritschler.fix.apps;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import be.tritschler.fix.core.message.Message;
import be.tritschler.fix.core.message.NewOrderSingle;
import be.tritschler.fix.core.session.FixSession;
import be.tritschler.fix.core.tags.ClOrdID;
import be.tritschler.fix.core.tags.HandInst;
import be.tritschler.fix.core.tags.OrdType;			
import be.tritschler.fix.core.tags.OrderQty;
import be.tritschler.fix.core.tags.Side;
import be.tritschler.fix.core.tags.Symbol;


public class FixClient {
		
	private static int clorderid = 1;
	
	
	
	private static Message buildNewOrderSingle() {		
		
		NewOrderSingle message = new NewOrderSingle();
		
		// body
		message.addTag(ClOrdID.TAG, clorderid+"");
		message.addTag(HandInst.TAG, HandInst.TYPE_AUTO_PUBLIC);
		message.addTag(Symbol.TAG, "IBM");
		message.addTag(Side.TAG, Side.SIDE_BUY);
		message.addTag(OrderQty.TAG, "10");
		message.addTag(OrdType.TAG, OrdType.MARKET);		
		clorderid++;

		return message;
	}
	
	public static void main(String argv[]) throws Exception {
				
		Socket clientSocket = new Socket("localhost", 8080);		
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "US-ASCII"));
//		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), Charset.US-ASCII)); Java 7
		BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "US-ASCII"));
		
  
		// creates the session
		FixSession session1 = new FixSession("Session 1", "CLCB", "BONY", writer, reader);
		session1.start();
		
		// send business messages
		for (int i=1; i<10; i++) {			
			session1.sendMessage(buildNewOrderSingle());
		}
		
		clientSocket.close();
	}
} 

