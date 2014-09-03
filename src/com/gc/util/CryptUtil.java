package com.gc.util;

import java.sql.Blob;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CryptUtil {

	public final static int AES_128_KEYBITS = 16;
	public final static int AES_192_KEYBITS = 24;
	public final static int AES_256_KEYBITS = 32;

	public static byte[] AESDecrypt(byte[] text, String key) {
		return AESDecrypt(text, key, AES_128_KEYBITS);
	}

	public static byte[] AESDecrypt(byte[] text, String key, int bits) {
		if(text == null) return null;
		byte[] b = null;
		try {
			byte[] raw = key.getBytes("ASCII");
			byte[] rawKey = new byte[bits];
			for(int i = 0; i < raw.length; i++) rawKey[i] = raw[i];
			SecretKeySpec skeySpec = new SecretKeySpec(rawKey, 0, bits, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			b = cipher.doFinal(text);
		} catch(Exception e) {
//			e.printStackTrace();
		}
		return b;
	}


	public static String AESDecrypt(Blob blob, String key, String charSet) {
		return AESDecrypt(blob, key, AES_128_KEYBITS, charSet);
	}

	public static String AESDecrypt(Blob blob, String key, int bits, String charSet) {
		String r = null;
		try {
			r = AESDecrypt(blob.getBytes((long)1, (int) blob.length()), key, bits, charSet);
		} catch(Exception e) {
//			e.printStackTrace();
		}
		return r;
	}

	public static String AESDecrypt(byte[] text, String key, String charSet) {
		return AESDecrypt(text, key, AES_128_KEYBITS, charSet);
	}

	public static String AESDecrypt(byte[] text, String key, int bits, String charSet) {
		String r = null;
		try {
			if(text != null) {
				byte[] b = AESDecrypt(text, key, bits);
				if(b != null) r = new String(b, charSet);
			}
		} catch(Exception e) {
//			e.printStackTrace();
		}
		return r;
	}

	public static byte[] AESEncrypt(String text, String key) {
		return AESEncrypt(text, key, AES_128_KEYBITS);
	}

	public static byte[] AESEncrypt(String text, String key, int bits) {
		return AESEncrypt(text, key, "", bits);
	}

	public static byte[] AESEncrypt(String text, String key, String charSet) {
		return AESEncrypt(text, key, charSet, AES_128_KEYBITS);
	}

	public static byte[] AESEncrypt(String text, String key, String charSet, int bits) {
		if(text == null) return null;
		byte[] b = null;
		try {
			byte[] raw = key.getBytes("ASCII");
			byte[] rawKey = new byte[bits];
			for(int i = 0; i < raw.length; i++) rawKey[i] = raw[i];
			SecretKeySpec skeySpec = new SecretKeySpec(rawKey, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			if(charSet != null && charSet.trim().length() > 0) {
				b = cipher.doFinal(text.getBytes(charSet));
			} else {
				b = cipher.doFinal(text.getBytes());
			}
		} catch(Exception e) {
//			e.printStackTrace();
		}
		return b;
	}

}
