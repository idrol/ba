package ba.idrol.net.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static String generateMD5(String string) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.reset();
		m.update(string.getBytes());
		byte[] digest = m.digest();
		
		BigInteger bigInt = new BigInteger(1,digest);
		String hashtext = bigInt.toString(16);
		// Now we need to zero pad it if you actually want the full 32 chars.
		while(hashtext.length() < 32 ){
		  hashtext = "0"+hashtext;
		}
		return hashtext;
	}
}
