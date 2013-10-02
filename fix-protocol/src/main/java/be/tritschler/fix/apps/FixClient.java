package be.tritschler.fix.apps;



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import be.tritschler.fix.core.message.HeartBeat;
import be.tritschler.fix.core.message.Message;
import be.tritschler.fix.core.session.FixSession;
import be.tritschler.fix.core.tags.CheckSum;
import be.tritschler.fix.core.tags.ClOrdID;
import be.tritschler.fix.core.tags.HandInst;
import be.tritschler.fix.core.tags.OrdType;
import be.tritschler.fix.core.tags.OrderQty;
import be.tritschler.fix.core.tags.Side;
import be.tritschler.fix.core.tags.Symbol;


public class FixClient {
		
	private static int clorderid = 1;
	
	private static HeartBeat buildHeartBeat() {
		HeartBeat heartBeat = null;
		try {
			
		} catch (Exception e) {
			
		}
		return heartBeat;
	}
	
	
	private static Message buildNewOrderSingle() {
		Message message = new Message();
		
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
		DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  
		FixSession session1 = new FixSession("Session 1", "CLCB", "BONY", os, is);
		session1.start();
		
		for (int i=1; i<10; i++) {			
			session1.sendMessage(buildNewOrderSingle());
		}
		
		clientSocket.close();
	}
} 

