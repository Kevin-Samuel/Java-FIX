package be.tritschler.fix.core.tags;

public class CheckSum extends Tag {
	
	public static final String TAG = "10";
	public static final String NAME = "CheckSum"; 
	
	public CheckSum() {
		this.tagId = TAG;
		this.tagName = NAME;
	}
	
	public static String computeCheckSum(String message) {		
		int c=0;
		String checksum="";
		byte[] buff = message.getBytes();
				
		for (int i=0; i<buff.length;i++) {
			c+= buff[i];
		}
		c = c %256;
		if (c<10) checksum = "00" + c;
		else if (c<100) checksum = "0" + c;
		else checksum = c + "";
		
		return checksum;
	}

	@Override
	public boolean isValid(String tag) {
		if (!isValidStructure(tag)) return false;
		if (getTagValue(tag).length() < 3) return false;
		//TODO compare with real message length
		return true;
	}

	@Override
	public int getGroupId() {
		return Constants.TRAILER;
	}
	
}
