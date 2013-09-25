package be.tritschler.fix.core.message;

import be.tritschler.fix.core.tags.BodyLength;
import be.tritschler.fix.core.tags.MsgSeqNum;
import be.tritschler.fix.core.tags.MsgType;
import be.tritschler.fix.core.tags.SenderCompID;
import be.tritschler.fix.core.tags.SendingTime;
import be.tritschler.fix.core.tags.TargetCompID;
import be.tritschler.fix.core.tags.v40.BeginString;

public class Header {

	// Mandatory fields	
	BeginString beginString;
	BodyLength bodyLength;
	MsgType msgType;		 	// always unencrypted, must be third field in message
	SenderCompID senderCompID; 	// always unencrypted
	TargetCompID targetCompID; 	// always unencrypted
	MsgSeqNum msgSeqNum;
	SendingTime sendingTime;
	
	// TODO Optional fields
	//115 OnBehalfOfCompID	
	//128 DeliverToCompID	
	//90 SecureDataLen
//	91 SecureData
}
