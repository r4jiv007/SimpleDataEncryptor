/*
 * 
 * this class is meant to encrypt and decrypt user info before storing it
 * into shared prefrences.
 * Singleton approach is followed to design this class
 * 
 */

package my.rajiv.dataencryptor;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

public class DataEncryptor {

	private KeyGenerator mKeyGenerator;
	private SecretKey mSecretKey;

	private String mSecretString;

	Cipher desCipher;

	private DataEncryptor(String secretKey) {
		try {
			mKeyGenerator = KeyGenerator.getInstance("DES");
			mSecretString = secretKey;
			mSecretKey = new SecretKeySpec(mSecretString.getBytes("UTF8"),
					"DES");

			desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Create the cipher
	}

	public String getEncryptedData(String text) {
		if (text != null) {
			byte[] textEncrypted = null;

			try {
				desCipher.init(Cipher.ENCRYPT_MODE, mSecretKey);
				textEncrypted = Base64.encode(
						desCipher.doFinal(text.getBytes("UTF-8")),
						Base64.DEFAULT);
				return new String(textEncrypted);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public String getDecryptedData(String text) {
		if (text != null) {
			byte[] textDecrypted = null;
			try {
				desCipher.init(Cipher.DECRYPT_MODE, mSecretKey);
				textDecrypted = desCipher.doFinal(Base64.decode(
						text.getBytes("UTF-8"), Base64.DEFAULT));// text.getBytes("UTF-8"));
				return new String(textDecrypted);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	private static DataEncryptor mDataEncryptor = null;

	public static DataEncryptor getDataEncryptor(String secretKey) {

		if (mDataEncryptor == null) {
			mDataEncryptor = new DataEncryptor(secretKey);
		}
		return mDataEncryptor;

	}

}
