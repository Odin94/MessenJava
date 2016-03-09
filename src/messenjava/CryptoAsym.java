/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenjava;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 *
 * @author Odin
 */
public class CryptoAsym {

    public CryptoAsym(String pubKey, String privKey) {
        //create keys from strings
    }

    public static String encrypt(String text, RSAPublicKey pubKey) {
        System.out.println("CryptoAsym.encrypt not yet implemented!!");
        return text;
    }

    public static String decrypt(String text, RSAPrivateKey privKey) {
        System.out.println("CryptoAsym.decrypt not yet implemented!!");
        return text;
    }
}
