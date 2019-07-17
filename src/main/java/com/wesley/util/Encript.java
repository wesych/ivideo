package com.wesley.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encript {
	
	public static String SHA256(String str) {
		return SHA(str, "SHA-256");
	}
	
	public static String SHA512(String str) {
		return SHA(str, "SHA-512");
	}

	private static String SHA(String strText, final String strType) {
		String strResult = null;

		strText += "@wesley";
		if (strText != null && strText.length() > 0) {
			try {
				MessageDigest messageDigest = MessageDigest.getInstance(strType);
				messageDigest.update(strText.getBytes());
				byte byteBuffer[] = messageDigest.digest();
				StringBuffer strHexString = new StringBuffer();
				for (int i = 0; i < byteBuffer.length; i++) {
					String hex = Integer.toHexString(0xff & byteBuffer[i]);
					if (hex.length() == 1) {
						strHexString.append('0');
					}
					strHexString.append(hex);
				}
				strResult = strHexString.toString();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}

		return strResult;
	}
	
	public static void main(String[] args) {
		System.out.println(Encript.SHA256("304721"));
		System.out.println(Encript.SHA256("304721"));
	}
}
