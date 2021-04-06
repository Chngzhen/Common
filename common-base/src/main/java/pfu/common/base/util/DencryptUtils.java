package pfu.common.base.util;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 加解密工具，包括加密、解密、签名及验签。其中，加密是为了防窥视，签名是为了防篡改。
 *
 * @author chngzhen@outlook.com
 * @since 2019-09-20
 */
public class DencryptUtils {

	private DencryptUtils() {}

	private static final String ENC_ALG_MD5 = "MD5";
	private static final String ENC_ALG_RSA = "RSA";

	private static final String SGN_ALG_SHA1WithRSA = "SHA1WithRSA";

	private static final String DEFAULT_CHARSET = "UTF-8";

	/**
	 * XXTea加密。
	 *
	 * @param plainText 明文
	 * @param key 密钥
	 * @return 密文
	 * @throws Exception XXTea加密异常
	 */
	public static String xxTeaEncrypt(String plainText, String key) throws Exception {
		return byteArrayToHexString(xxTeaEncrypt(plainText.getBytes(DEFAULT_CHARSET), key.getBytes(DEFAULT_CHARSET)));
	}

	/**
	 * XXTea解密。
	 *
	 * @param cipherText 小写十六进制密文
	 * @param key 密钥
	 * @return 明文
	 * @throws Exception XXTea解密异常
	 */
	public static String xxTeaDecrypt(String cipherText, String key) throws Exception {
		return new String(xxTeaDecrypt(hexStringToByteArray(cipherText), key.getBytes(DEFAULT_CHARSET)));
	}

	/**
	 * XXTea加密。
	 *
	 * @param plainData 明文
	 * @param key 密钥
	 * @return 密文
	 */
	public static byte[] xxTeaEncrypt(byte[] plainData, byte[] key) {
		if (plainData == null || plainData.length == 0 || key == null) return null;

		int[] pia = byteArraytoIntArray(plainData, true);
		int[] kia = byteArraytoIntArray(key, false);

		int n = pia.length - 1;
		if (n < 1) return intArraytoByteArray(pia, false);

		if (kia.length < 4) {
			int[] k = new int[4];
			System.arraycopy(kia, 0, k, 0, kia.length);
			kia = k;
		}

		int z = pia[n], y, delta = -1640531527, sum = 0;

		int e, p;
		for(int var9 = 6 + 52 / (n + 1); var9-- > 0; z = pia[n] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (kia[p & 3 ^ e] ^ z)) {
			sum += delta;
			e = sum >>> 2 & 3;

			for(p = 0; p < n; ++p) {
				y = pia[p + 1];
				z = pia[p] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (kia[p & 3 ^ e] ^ z);
			}

			y = pia[0];
		}
		return intArraytoByteArray(pia, false);
	}

	/**
	 * XXTea解密。
	 *
	 * @param cipherData 密文
	 * @param key 密钥
	 * @return 明文
	 */
	public static byte[] xxTeaDecrypt(byte[] cipherData, byte[] key) {
		if (cipherData == null || cipherData.length == 0 || key == null) return null;

		int[] cia = byteArraytoIntArray(cipherData, false);
		int[] kia = byteArraytoIntArray(key, false);

		int n = cia.length - 1;
		if (n < 1) return intArraytoByteArray(cia, true);

		if (kia.length < 4) {
			int[] k = new int[4];
			System.arraycopy(kia, 0, k, 0, kia.length);
			kia = k;
		}

		int y = cia[0], delta = -1640531527, q = 6 + 52 / (n + 1);
		for(int sum = q * delta; sum != 0; sum -= delta) {
			int e = sum >>> 2 & 3;

			int p, z;
			for(p = n; p > 0; --p) {
				z = cia[p - 1];
				y = cia[p] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (kia[p & 3 ^ e] ^ z);
			}

			z = cia[n];
			y = cia[0] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (kia[p & 3 ^ e] ^ z);
		}
		return intArraytoByteArray(cia, true);
	}

	/**
	 * RSA加密。
	 *
	 * @param secretKey 密钥。当isPublic=true时提供公钥，否则提供私钥。
	 * @param plainText 明文
	 * @param isPublic 是否为公钥加密：true（公钥）、false（私钥）
	 * @return 小写十六进制密文
	 * @throws Exception RSA加密失败
	 */
	public static String rsaEncrypt(String secretKey, String plainText, boolean isPublic) throws Exception {
		Key key = isPublic ? loadPublicKey(secretKey) : loadPrivateKey(secretKey);
		byte[] plainTextData = plainText.getBytes();
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return byteArrayToHexString(cipher.doFinal(plainTextData));
	}

	/**
	 * RSA解密。
	 *
	 * @param secretKey 密钥。当isPublic=true时提供公钥，否则提供私钥。
	 * @param cipherText 小写十六进制密文
	 * @param isPublic 是否为公钥解密：true（公钥）、false（私钥）
	 * @return 明文
	 * @throws Exception RSA解密失败
	 */
	public static String rsaDecrypt(String secretKey, String cipherText, boolean isPublic) throws Exception {
		Key key = isPublic ? loadPublicKey(secretKey) : loadPrivateKey(secretKey);
		byte[] cipherData = hexStringToByteArray(cipherText);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return new String(cipher.doFinal(cipherData));
	}

	/**
	 * RSA私钥解密长密文。
	 *
	 * @param privateKey 私钥
	 * @param cipherText 密文
	 * @return 明文
	 * @throws Exception RSA私钥解密失败
	 */
	public static String rsaDecryptForLongStr(String privateKey, String cipherText) throws Exception {
		byte[] encryptedData = hexStringToByteArray(cipherText);
		Cipher cipher = Cipher.getInstance(ENC_ALG_RSA);
		cipher.init(Cipher.DECRYPT_MODE, loadPrivateKey(privateKey));

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] cache;
		int len = encryptedData.length, offSet = 0, i = 0;
		// 对数据分段解密
		while (len - offSet > 0) {
			if (len - offSet > 128) {
				cache = cipher.doFinal(encryptedData, offSet, 128);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, len - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * 128;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return new String(decryptedData);
	}

	/**
	 * RSA私钥签名。
	 *
	 * @param sourceInfo 源信息
	 * @param privateKey 私钥
	 * @return 小写十六进制签名
	 * @throws Exception 签名异常
	 */
	public static String rsaSign(String sourceInfo, String privateKey) throws Exception {
		Signature signature = Signature.getInstance(SGN_ALG_SHA1WithRSA);
		signature.initSign(loadPrivateKey(privateKey));
		signature.update(sourceInfo.getBytes(DEFAULT_CHARSET));
		byte[] signed = signature.sign();
		return byteArrayToHexString(signed);
	}

	/**
	 * RSA公钥验签。
	 *
	 * @param sourceInfo 待验信息
	 * @param sign 小写十六进制签名
	 * @param publicKey 公钥
	 * @return true - 信息未被修改
	 * @throws Exception 验签异常
	 */
	public static boolean rsaVerify(String sourceInfo, String sign, String publicKey) throws Exception {
		Signature signature = Signature.getInstance(SGN_ALG_SHA1WithRSA);
		signature.initVerify(loadPublicKey(publicKey));
		signature.update(sourceInfo.getBytes(DEFAULT_CHARSET));
		return signature.verify(hexStringToByteArray(sign));
	}

	/**
	 * MD5加密。
	 *
	 * @param plainText 明文
	 * @return 小写十六进制密文
	 * @throws Exception 加密失败
	 */
	public static String md5Encrypt(String plainText) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		return md5Encrypt(plainText, DEFAULT_CHARSET);
	}

	/**
	 * MD5加密。
	 *
	 * @param plainText 明文
	 * @param charset 字符编码。默认：{@link #DEFAULT_CHARSET}
	 * @return 小写十六进制密文
	 * @throws Exception 加密失败
	 */
	public static String md5Encrypt(String plainText, String charset) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance(ENC_ALG_MD5);
		if(null == charset || "".equals(charset.trim())) {
			return byteArrayToHexString(md.digest(plainText.getBytes()));
		}else {
			return byteArrayToHexString(md.digest(plainText.getBytes(charset.trim())));
		}
	}

	/**
	 * 字节数组转小写十六进制字符串。
	 *
	 * @param ba 字节数组
	 * @return 小写十六进制字符串
	 */
	private static String byteArrayToHexString(byte[] ba) {
		StringBuilder sb = new StringBuilder(ba.length << 1);

		for (byte b : ba) {
			String hs = Integer.toHexString(255 & b);
			if (hs.length() < 2) {
				sb.append(0);
			}
			sb.append(hs);
		}
		return sb.toString();
	}

	/**
	 * 小写十六进制字符串转字节数组。
	 *
	 * @param hs 小写十六进制字符串
	 * @return 字节数组
	 */
	private static byte[] hexStringToByteArray(String hs) {
		if (hs == null) return null;

		char[] ha = hs.toCharArray();
		int length = ha.length / 2;
		byte[] raw = new byte[length];

		for (int i = 0; i < length; ++i) {
			int high = Character.digit(ha[i * 2], 16);
			int low = Character.digit(ha[i * 2 + 1], 16);
			int value = high << 4 | low;
			if (value > 127) {
				value -= 256;
			}
			raw[i] = (byte) value;
		}
		return raw;
	}

	/**
	 * 加载公钥。
	 *
	 * @param publicKey 公钥字符串
	 * @return 公钥
	 * @throws Exception 公钥加载异常
	 */
	private static PublicKey loadPublicKey(String publicKey) throws Exception {
		Base64.Decoder base64Decoder = Base64.getDecoder();
		byte[] buffer = base64Decoder.decode(publicKey);
		return KeyFactory.getInstance(ENC_ALG_RSA).generatePublic(new X509EncodedKeySpec(buffer));
	}

	/**
	 * 加载私钥。
	 *
	 * @param privateKey 私钥字符串
	 * @return 私钥
	 * @throws Exception 私钥加载异常
	 */
	private static PrivateKey loadPrivateKey(String privateKey) throws Exception {
		Base64.Decoder base64Decoder = Base64.getDecoder();
		byte[] buffer = base64Decoder.decode(privateKey);
		return KeyFactory.getInstance(ENC_ALG_RSA).generatePrivate(new PKCS8EncodedKeySpec(buffer));
	}

	/**
	 * 整型数组转字节数组。
	 *
	 * @param ia 整型数组
	 * @param includeLength
	 * @return 字节数组
	 */
	private static byte[] intArraytoByteArray(int[] ia, boolean includeLength) {
		int n = ia.length << 2;
		if (includeLength) {
			int m = ia[ia.length - 1];
			if (m > n || m <= 0) {
				return null;
			}
			n = m;
		}

		byte[] result = new byte[n];
		for(int i = 0; i < n; ++i) {
			result[i] = (byte) (ia[i >>> 2] >>> ((i & 3) << 3) & 255);
		}
		return result;
	}

	/**
	 * 字节数组转整型数组。
	 *
	 * @param ba 字节数组
	 * @param includeLength
	 * @return 整型数组
	 */
	private static int[] byteArraytoIntArray(byte[] ba, boolean includeLength) {
		int n = (ba.length & 3) == 0 ? ba.length >>> 2 : (ba.length >>> 2) + 1;
		int[] result;
		if (includeLength) {
			result = new int[n + 1];
			result[n] = ba.length;
		}else result = new int[n];

		n = ba.length;
		for(int i = 0; i < n; ++i) {
			result[i >>> 2] |= (255 & ba[i]) << ((i & 3) << 3);
		}
		return result;
	}
}
