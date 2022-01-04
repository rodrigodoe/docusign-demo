package com.example.demo.utils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class DocuSignUtils {

	public static RSAPublicKey getPublicKey(String pubKey) throws IOException, GeneralSecurityException {

		return getPublicKeyFromProperty(pubKey);
	}

	private static RSAPublicKey getPublicKeyFromProperty(String key) throws IOException, GeneralSecurityException {
		String publicKey = key;
		publicKey = publicKey.replace("-----BEGIN PUBLIC KEY-----", "");
		publicKey = publicKey.replace("-----END PUBLIC KEY-----", "");

		byte[] encoded = org.apache.commons.codec.binary.Base64.decodeBase64(publicKey);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(encoded));
		return pubKey;
	}

	public static RSAPrivateKey getPrivateKey(String privKey) throws IOException, GeneralSecurityException {

		return getPrivateKeyFromProperty(privKey);

	}

	private static RSAPrivateKey getPrivateKeyFromProperty(String key) throws IOException, GeneralSecurityException {
		String privateKey = key;
		privateKey = privateKey.replace("-----BEGIN RSA PRIVATE KEY-----", "");
		privateKey = privateKey.replace("-----END RSA PRIVATE KEY-----", "");

		byte[] encoded = org.apache.commons.codec.binary.Base64.decodeBase64(privateKey);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
		RSAPrivateKey privKey = (RSAPrivateKey) kf.generatePrivate(keySpec);
		return privKey;
	}

	public static Map<String, String> getKeys() {

		Map<String, String> keys = new HashMap<>();

		ResourceLoader resourceLoader = new DefaultResourceLoader();

		Resource privateResource = resourceLoader.getResource("classpath:privatekey");
		Resource publicResource = resourceLoader.getResource("classpath:publickey");

		keys.put("privateKey", ResourceReader.asString(privateResource));
		keys.put("publicKey", ResourceReader.asString(publicResource));

		return keys;
	}

	public static String createBearerToken(String token) {
		return String.format("Bearer %s", token);
	}

}
