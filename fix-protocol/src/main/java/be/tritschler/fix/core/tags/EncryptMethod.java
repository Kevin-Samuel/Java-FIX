package be.tritschler.fix.core.tags;

import be.tritschler.utils.StringUtils;

public class EncryptMethod extends Tag {

	public static final String TYPE_NONE        = "0";
	public static final String TYPE_PKCS        = "1";
	public static final String TYPE_DES_EBC     = "2";
	public static final String TYPE_PKCS_DES    = "3";
	public static final String TYPE_PGP_DES     = "4";
	public static final String TYPE_PGP_DES_MD5 = "5";
	public static final String TYPE_PEM_DES_MD5 = "6";
	
	public static final String TAG = "98";
	public static final String NAME = "EncryptMethod";
	
	public EncryptMethod() {
		this.tagId = TAG;
		this.tagName = NAME;
	}
	
	@Override
	public boolean isValid(String tag) {
		if (!isValidStructure(tag)) return false;			
		if (StringUtils.isInStringArray(getTagValue(tag), 
				TYPE_NONE, TYPE_PKCS, TYPE_DES_EBC, TYPE_PKCS_DES, TYPE_PGP_DES,
				TYPE_PGP_DES_MD5, TYPE_PEM_DES_MD5))
			return true;
		else
			return false;
	}

	@Override
	public int getGroupId() {
		return Constants.HEADER;
	}
}
