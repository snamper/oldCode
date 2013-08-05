package common;

import java.security.*;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/*加密解密示例

  				  String s1 = DES.encrypt(k, CradNo);
				  String s2 = DES.encrypt(k, CardPassword);
				  CradNo = DES.decrypt(k, CradNo);
				  CardPassword = DES.decrypt(k, CardPassword);

 */
public class DES {

	private static String DES = "DES";

	/**
	 * 加密
	 * @param src
	 *            数据源
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 返回加密后的数据
	 * @throws Exception
	 */

	public static byte[] encrypt(byte[] src, byte[] key) throws Exception {

		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		return cipher.doFinal(src);

	}

	/**
	 * 
	 * 解密
	 * @param src
	 *            数据源
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 返回解密后的原始数据
	 * @throws Exception
	 * 
	 */

	public static byte[] decrypt(byte[] src, byte[] key) throws Exception {

		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		return cipher.doFinal(src);

	}

	/**
	 * 
	 * 密码解密
	 * @param data
	 * @return
	 * @throws Exception
	 * 
	 */

	public final static String decrypt(String key, String data) {
		try {
			String s = new String(decrypt(hex2byte(data.getBytes()), key.getBytes()));
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}

	/**
	 * 
	 * 密码加密
	 * @param password
	 * @return
	 * @throws Exception
	 * 
	 */

	public final static String encrypt(String key, String password) {

		try {
			return byte2hex(encrypt(password.getBytes(), key.getBytes()));
		} catch (Exception e) {
		}

		return "";

	}

	/**
	 * 
	 * 二行制转字符串
	 * @param b
	 * @return
	 * 
	 */

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

}
