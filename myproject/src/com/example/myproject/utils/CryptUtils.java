package com.example.myproject.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import com.google.gwt.user.server.Base64Utils;

/**
 * Verschlüsselungsroutinen.
 * 
 * @author schmidt
 * @version 1.0.00
 */
public final class CryptUtils {
	private static final char[] PASSWORD = "enfld1S?gbn4%Lsn7&gDl4(2ksd!1gM".toCharArray();
    private static final byte[] SALT = {
        (byte) 0xde, (byte) 0x32, (byte) 0x12, (byte) 0x12,
        (byte) 0xde, (byte) 0x33, (byte) 0x12, (byte) 0x12,
    };


    private CryptUtils() {	}
	
	public static void main(String[] args) throws Exception {
        String originalPassword = "secret";
        System.out.println("Original password: " + originalPassword);
        String encryptedPassword = encrypt(originalPassword);
        System.out.println("Encrypted password: " + encryptedPassword);
        String decryptedPassword = decrypt(encryptedPassword);
        System.out.println("Decrypted password: " + decryptedPassword);
    }
	
	public static String encrypt(String property) {
        try {
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
			SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
			Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
			pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
			return Base64Utils.toBase64(pbeCipher.doFinal(property.getBytes("UTF-8")));
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return null;
    }
	
	public static String decrypt(String property) {
		try {
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
	        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
	        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
	        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
	        return new String(pbeCipher.doFinal(Base64Utils.fromBase64(property)), "UTF-8");
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }

}
