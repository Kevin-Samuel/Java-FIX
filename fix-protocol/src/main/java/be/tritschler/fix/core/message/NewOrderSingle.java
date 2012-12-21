package be.tritschler.fix.core.message;

import be.tritschler.fix.tags.ClOrdID;
import be.tritschler.fix.tags.HandInst;
import be.tritschler.fix.tags.MsgType;
import be.tritschler.fix.tags.OrdType;
import be.tritschler.fix.tags.OrderQty;
import be.tritschler.fix.tags.Side;
import be.tritschler.fix.tags.Symbol;
import be.tritschler.fix.tags.Tag;

public class NewOrderSingle extends Message {
	
	public static final String TYPE = MsgType.TYPE_NEW_ORDER_SINGLE;
	
	public NewOrderSingle(String clorderid) {
		
		
		// body
		setTag(ClOrdID.TAG, clorderid+"");
		setTag(HandInst.TAG, HandInst.TYPE_AUTO_PUBLIC);
		setTag(Symbol.TAG, "IBM");
		setTag(Side.TAG, Side.SIDE_BUY);
		setTag(OrderQty.TAG, "10");
		setTag(OrdType.TAG, OrdType.MARKET);
	
	}
	
}
