package com.br.ott.common.util.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @author 陈登民
 * 
 */
public class AES2 {
	private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";// "算法/模式/补码方式"
	private static final String CHARSET = "utf-8";
	private static final String CIPHER = "AES";

	// 加密
	public static String encrypt(String sSrc, String sKey) throws Exception {
		if (sKey == null) {
			throw new Exception("Key为空null");
		}
		// 判断Key是否为16位
		if (sKey.length() != 16) {
			throw new Exception("Key长度不是16位");
		}
		byte[] raw = sKey.getBytes(CHARSET);
		SecretKeySpec skeySpec = new SecretKeySpec(raw, CIPHER);
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

		return parseByte2HexStr(encrypted);
	}

	// 解密
	public static String decrypt(String con, String sKey) throws Exception {
		try {
			// 判断Key是否正确
			if (sKey == null) {
				System.out.print("Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
				System.out.print("Key长度不是16位");
				return null;
			}
			byte[] raw = sKey.getBytes(CHARSET);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, CIPHER);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = parseHexStr2Byte(con);
			byte[] original = cipher.doFinal(encrypted1);
			return new String(original, CHARSET);
		} catch (Exception ex) {
			throw ex;
		}
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		/*
		 * 此处使用AES-128-ECB加密模式，key需要为16位。
		 */
		String cKey = "OTT1234567890OTT";
		// 需要加密的字串
		String cSrc = "1688000120130608105705001A95D18853";
		System.out.println("加密前：" + cSrc);
		// 加密
		String enString = encrypt(cSrc, cKey);
		System.out.println("加密后的字串是：" + enString);

		// 解密
		String DeString = decrypt(enString, cKey);
		System.out
				.println("解密后的字串是："
						+ DeString
						+ "sssssss==>"
						+ "BCA9F8E73107FCDACA3CBA9D96D6FCF636642A918E6156FFC894A1719F0C5EB1CCF954D0827D60E9FE933446A78FCE83"
								.length());
	}
}