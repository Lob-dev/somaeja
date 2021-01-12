package com.somaeja.user.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Utils {

	public static String encode(final String string) {
		MessageDigest digest;
		String encodeString = "";

		try {
			digest = MessageDigest.getInstance("SHA-256");
			digest.update(string.getBytes(StandardCharsets.UTF_8));
			byte[] encodedHash = digest.digest();

			encodeString = bytesToHex(encodedHash);
		} catch (NoSuchAlgorithmException e) {
			// MessageDigest.getInstance() 메서드에 잘못된 인자(알고리즘 유형)을 전달해서 발생했다는 의미
			throw new IllegalArgumentException("Encoding Exception in SHA256Utils", e);
		}

		return encodeString;
	}

	private static String bytesToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder(2 * hash.length);

		for (byte h : hash) {
			// 0xff는 2진수로 1111 1111, 255 (부호 없음), -127 (부호 있음) 을 나타낸다
			// toHexString 는 부호 없는 정수로 리턴하는 메서드이다.
			// 부호가 있는 경우를 상정하고 필요한 값(8비트 값)만을 AND 연산을 통해 걸러낸다.
			String hex = Integer.toHexString(0xff & h);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}
