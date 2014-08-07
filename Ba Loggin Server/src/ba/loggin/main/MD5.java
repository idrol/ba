package ba.loggin.main;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static String generateMD5(String string) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		byte[] bytesOfMessage = string.getBytes("UTF-8");

		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] thedigest = md.digest(bytesOfMessage);
		return new String(thedigest, "UTF-8");
	}
}
