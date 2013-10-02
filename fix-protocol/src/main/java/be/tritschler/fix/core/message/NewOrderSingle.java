package be.tritschler.fix.core.message;

import be.tritschler.fix.core.tags.ClOrdID;
import be.tritschler.fix.core.tags.HandInst;
import be.tritschler.fix.core.tags.MsgType;
import be.tritschler.fix.core.tags.OrdType;
import be.tritschler.fix.core.tags.OrderQty;
import be.tritschler.fix.core.tags.Side;
import be.tritschler.fix.core.tags.Symbol;



public class NewOrderSingle extends Message {
	
	public static final String TYPE = MsgType.TYPE_NEW_ORDER_SINGLE;
	
	public NewOrderSingle() {
		
		// header
		buildHeader(NewOrderSingle.TYPE);
		
		// body
		addTag(ClOrdID.TAG, "xxx123");
		addTag(HandInst.TAG, HandInst.TYPE_AUTO_PUBLIC);
		addTag(Symbol.TAG, "IBM");
		addTag(Side.TAG, Side.SIDE_BUY);
		addTag(OrderQty.TAG, "10");
		addTag(OrdType.TAG, OrdType.MARKET);
	
	}
	
}
