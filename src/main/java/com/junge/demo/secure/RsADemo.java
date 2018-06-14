/**
 * 
 */
package com.junge.demo.secure;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

import javax.crypto.Cipher;

/**
 * 非对称加密算法 公钥加密，私钥解密
 * 
 * 参考地址:https://www.cnblogs.com/liemng/p/6699257.html
 * 
 * @author "liuxj"
 *
 */
public class RsADemo {

	private static final String PUBLIC_KEY_PATH = "D://public.txt";
	private static final String PRIVATE_KEY_PATH = "D://private.txt";

	/**
	 * 生成私钥 公钥
	 */
	public static void geration() {
		KeyPairGenerator keyPairGenerator;
		try {
			keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			SecureRandom secureRandom = new SecureRandom(new Date().toString().getBytes());
			keyPairGenerator.initialize(1024, secureRandom);
			KeyPair keyPair = keyPairGenerator.genKeyPair();
			byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
			FileOutputStream fos = new FileOutputStream(PUBLIC_KEY_PATH);
			fos.write(publicKeyBytes);
			fos.close();
			byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
			fos = new FileOutputStream(PRIVATE_KEY_PATH);
			fos.write(privateKeyBytes);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取公钥
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String filename) throws Exception {
		File f = new File(filename);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[(int) f.length()];
		dis.readFully(keyBytes);
		dis.close();
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}

	/**
	 * 获取私钥
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String filename) throws Exception {
		File f = new File(filename);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[(int) f.length()];
		dis.readFully(keyBytes);
		dis.close();
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		geration();

		String input = "!!!hello world!!!";
		RSAPublicKey pubKey;
		RSAPrivateKey privKey;
		byte[] cipherText;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("RSA");
			pubKey = (RSAPublicKey) getPublicKey(PUBLIC_KEY_PATH);
			privKey = (RSAPrivateKey) getPrivateKey(PRIVATE_KEY_PATH);

			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			cipherText = cipher.doFinal(input.getBytes());
			// 加密后的东西
			System.out.println("cipher: " + new String(cipherText));
			// 开始解密
			cipher.init(Cipher.DECRYPT_MODE, privKey);
			byte[] plainText = cipher.doFinal(cipherText);
			System.out.println("publickey: " + Base64.getEncoder().encode(cipherText));
			System.out.println("plain : " + new String(plainText));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
