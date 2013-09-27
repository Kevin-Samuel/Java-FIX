package be.tritschler.fix.core.message;

import be.tritschler.fix.core.tags.MsgType;

/**
 * Builds the REJECT Message using the Builder pattern.
 * 
 * @author marc@tritschler.be
 *
 */
public final class Reject extends Message {

	public static final String TYPE = MsgType.TYPE_REJECT;
		
	private final int refSeqNum;
	private final String refTagID;
	private final String refMsgType;
	private final String sessionRejectReason;
	private final String text;
	private final String encodedTextLen;
	private final String encodedText;
	
	public static class Builder {
		// required parameter
		private final int refSeqNum;
		// optional parameters
		private String refTagID = null;
		private String refMsgType = null;
		private String sessionRejectReason = null;
		private String text  = null;
		private String encodedTextLen  = null;
		private String encodedText  = null;
		
		public Builder(int refSeqNum) {
			this.refSeqNum = refSeqNum;
		}
		
		public Builder refTagID(String val) {
			this.refTagID = val;
			return this;
		}
		
		public Builder refMsgType(String val) {
			this.refMsgType = val;
			return this;
		}
		
		public Builder sessionRejectReason(String val) {
			this.sessionRejectReason = val;
			return this;
		}
		
		public Builder text(String val) {
			this.text = val;
			return this;
		}
		
		public Builder encodedTextLen(String val) {
			this.encodedTextLen = val;
			return this;
		}
		
		public Builder encodedText(String val) {
			this.encodedText = val;
			return this;
		}
		
		public Reject build() {
			return new Reject(this);
		}
	}
	
	private Reject(Builder builder) {
		refSeqNum = builder.refSeqNum;
		refTagID = builder.refTagID;
		refMsgType = builder.refMsgType;
		sessionRejectReason = builder.sessionRejectReason;
		text = builder.text;
		encodedTextLen = builder.encodedTextLen;
		encodedText = builder.encodedText;
	}
}
