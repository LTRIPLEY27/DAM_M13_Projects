package com.reseed.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtils {

	public EncryptUtils(){

	}

	/**
	 * Método para encriptar con MD5, usado basicamente para guardar el password en {@link com.reseed.LoginActivity}
	 * y comprovarlo en {@link com.reseed.FragmentUserConfig}.
	 * @param originalString String que se quiere encriptar.
	 * @return devuelve un String encriptado, si no funciona devuelve un String vacio.
	 */
	public final String encryptPasswordMd5 (final String originalString) {
		final String MD5 = "MD5";
		try {
			// Create MD5 Hash
			MessageDigest digest = MessageDigest
					.getInstance(MD5);
			digest.update(originalString.getBytes());
			byte[] messageDigest = digest.digest();

			// Create Hex String
			StringBuilder hexString = new StringBuilder();
			for (byte aMessageDigest : messageDigest) {
				StringBuilder h = new StringBuilder(Integer.toHexString(0xFF & aMessageDigest));
				while (h.length() < 2)
					h.insert(0, "0");
				hexString.append(h);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}


}
