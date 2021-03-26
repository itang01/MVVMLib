package com.itang.mvvm.utils;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class SecurityUtil {
    public static String DES = "AES"; // optional value AES/DES/DESede

    public static String CIPHER_ALGORITHM = "AES"; // optional value AES/DES/DESede


    public static Key getSecretKey(String key) throws Exception {
        SecretKey securekey = null;
        if (key == null) {
            key = "";
        }
        KeyGenerator keyGenerator = KeyGenerator.getInstance(DES);
        keyGenerator.init(new SecureRandom(key.getBytes()));
        securekey = keyGenerator.generateKey();
        return securekey;
    }

    public static String encrypt(String data, String key) throws Exception {
        SecureRandom sr = new SecureRandom();
        Key securekey = getSecretKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        byte[] bt = cipher.doFinal(data.getBytes());
        String strs = Base64.encode(bt);
        return strs;
    }


    public static String detrypt(String message, String key) throws Exception {
        SecureRandom sr = new SecureRandom();
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        Key securekey = getSecretKey(key);
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        byte[] res = Base64.decode(message);
        res = cipher.doFinal(res);
        return new String(res);
    }

    public static void main(String[] args) throws Exception {
        String message = "password";
        String key = "";
        String entryptedMsg = encrypt(message, key);
        System.out.println("encrypted message is below :");
        System.out.println(entryptedMsg);

        String decryptedMsg = detrypt(entryptedMsg, key);
        System.out.println("decrypted message is below :");
        System.out.println(decryptedMsg);
    }
}
