package com.ischen.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	/**
	 * 使用md5的算法进行加密
	 */
	public static String md5(String plainText) {
		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(
					plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("没有md5这个算法！");
		}
		String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
		// 如果生成数字未满32位，需要前面补0
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}

	public static void main(String[] args) {
//		System.out.println(md5("123456"));
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String s = encoder.encode("admin");
		System.out.println(s);

		// 进行判断
		boolean b = encoder.matches("123456",
				"$2a$10$kOR6W.AzvnBW0cE1xz2qP.ToMP4OaUAHoExBtCehR9c6ceWVanGfS");
		System.out.println(b);
	}

}