/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenjava;

import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Odin
 */
public class CryptoSym {

    public CryptoSym(String secret) {
        //create AESKey from secret
    }

    public static String encrypt(String text, SecretKey sharedSecret) {
        byte[] byteText = null;

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, sharedSecret);
            byteText = cipher.doFinal(text.getBytes());
        } catch (Exception e) {
        }

        String encText = Base64.getEncoder().encodeToString(byteText);

        return encText;
    }

    public static String decrypt(String encText, SecretKey sharedSecret) {
        byte[] crypted = Base64.getDecoder().decode(encText);
        byte[] cipherData = null;

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, sharedSecret);
            cipherData = cipher.doFinal(crypted);
        } catch (Exception e) {
        }

        return new String(cipherData);
    }

    public static void testMe() {
        try {
            byte[] salt = new byte[]{(byte) 0xe0};
            char[] password = new char['p'];

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);

            byte[] iv = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
            byte[] ciphertext = cipher.doFinal("Hello, World!".getBytes("UTF-8"));

// reinit cypher using param spec
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("es gayt net");
        }
    }
}
