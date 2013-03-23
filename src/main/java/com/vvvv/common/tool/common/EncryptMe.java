package com.vvvv.common.tool.common;
/**
 * 简单的自定义加密程序
 * @author hongyingxiang
 */
public class EncryptMe {

	/**
	 * 加密
	 * @param strInput
	 * @return
	 */
	public static int[] Encrypt1(String strInput) {
		int[] charFont = new int[strInput.length()];
		for (int i = 0; i < strInput.length(); i++) {
			char ch = strInput.charAt(i);
			charFont[i] = ~(ch + i);
		}
		return charFont;
	}
	/**
	 * 解密
	 * @param strInput
	 * @return
	 */
	public static String DeEncrypt1(int[] strInput) {
		char[] strArrary = new char[strInput.length];
		for (int i = 0; i < strInput.length; i++) {
			strArrary[i] = (char)((~strInput[i]) - i);
		}
		return new String(strArrary);
	}
	/**
	 * 字符串数据解密
	 * @param strInput 需解密的字符串
	 * @return 解密后的字符串数据
	 */
	public static String DeEncrypt(String strInput) {
		String strFont, strEnd;
		String strOutput;
		char[] charFont;
		int i, len, intFont;
		len = strInput.length();
		strFont = strInput.substring(1, len);
		strEnd = strInput.substring(0,1);
		charFont = strFont.toCharArray();
		for (i = 0; i < strFont.length(); i++) {
			intFont = (int) charFont[i] - 3;
			charFont[i] = (char) intFont;
		}
		strFont = "";
		for (i = 0; i < charFont.length; i++) {
			strFont += charFont[i];
		}
		strOutput = strFont + strEnd;
		return strOutput;
	}
	/**
	 * 加密
	 * @param strInput 加密字符串
	 * @return 加密后字符串
	 */
	public static String Encrypt(String strInput) {
		String strFont, strEnd;
		String strOutput;
		char[] charFont;
		int i, len, intFont;

		len = strInput.length();
		strFont = strInput.substring(0, len - 1);
		strEnd = strInput.substring(len - 1);
		charFont = strFont.toCharArray();
		int d = 0;
		for (i = 0; i < strFont.length(); i++) {
			intFont = (int) charFont[i] + 3;
			charFont[i] = (char) intFont;
			d = charFont[i];
		}
		strFont = "";
		for (i = 0; i < charFont.length; i++) {
			strFont += charFont[i];
		}
		strOutput = strEnd + strFont;
		return strOutput;
	}
	
	public static String toUnicode(String s) {
		String s1 = "";
		for (int i = 0; i < s.length(); i++) {
			s1 +="\\u" +  Integer.toHexString(s.charAt(i) & 0xffff);
		}
		return s1;
	}
	
	public static void main(String args[]){
		String str = "中a";
		System.out.println(EncryptMe.Encrypt(str));
//		str = StringUtil.encodeStringToUTF8(str);
//		
//		String encStr = EncryptMe.Encrypt(str);
//		String decStr = EncryptMe.DeEncrypt(encStr);
//		System.out.println(str);
//		System.out.println(encStr);
//		System.out.println(decStr);
//		System.out.println(StringUtil.urlDecode(decStr));
//		
//		String uniStr = EncryptMe.toUnicode(str);
//		encStr = EncryptMe.Encrypt(uniStr);
//		System.out.println(uniStr);
//		System.out.println(encStr);
//		System.out.println(EncryptMe.DeEncrypt(encStr));
	}
}
