package com.example.my_project;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionHelper {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String SECRET_KEY = "MySuperSecretKey"; // In a real app, use a secure key exchange!
    private static final String SALT = "MySalt";

    public static String encrypt(String message) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec keySpec = generateKey(SECRET_KEY);
        IvParameterSpec ivSpec = generateIv(SALT);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }

    public static String decrypt(String encryptedMessage) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec keySpec = generateKey(SECRET_KEY);
        IvParameterSpec ivSpec = generateIv(SALT);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decodedBytes = Base64.decode(encryptedMessage, Base64.DEFAULT);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    private static SecretKeySpec generateKey(String key) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
        digest.update(bytes);
        byte[] keyBytes = new byte[32]; // AES-256 needs 32 bytes
        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
        return new SecretKeySpec(keyBytes, "AES");
    }

    private static IvParameterSpec generateIv(String salt) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] bytes = salt.getBytes(StandardCharsets.UTF_8);
        digest.update(bytes);
        byte[] ivBytes = new byte[16]; // AES block size is 16 bytes
        System.arraycopy(digest.digest(), 0, ivBytes, 0, ivBytes.length);
        return new IvParameterSpec(ivBytes);
    }
}
