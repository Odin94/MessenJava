/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenjava;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import javax.crypto.Cipher;

/**
 *
 * @author Odin
 */
public class CryptoAsym {

    public CryptoAsym(String pubKey, String privKey) {
        //create keys from strings
    }

    public static String encrypt(String text, RSAPublicKey pubKey) {
        byte[] byteText = null;

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byteText = cipher.doFinal(text.getBytes());
        } catch (Exception e) {
        }

        String encText = Base64.getEncoder().encodeToString(byteText);

        return encText;
    }

    public static String decrypt(String encText, RSAPrivateKey privKey) {
        byte[] crypted = Base64.getDecoder().decode(encText);
        byte[] cipherData = null;

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privKey);
            cipherData = cipher.doFinal(crypted);
        } catch (Exception e) {
        }

        return new String(cipherData);
    }

    public static String sign(String text, RSAPrivateKey privKey) {
        System.out.println("CryptoAsym.sign not yet implemented!!");
        return text;
    }

    public static String checkSignature(String text, RSAPublicKey pubKey) {
        System.out.println("CryptoAsym.checkSignature not yet implemented!!");
        return text;
    }

    public static void main(String[] args) {
        //TESTS
        String text = "Ick bin ein Berliner!";
    }
}
