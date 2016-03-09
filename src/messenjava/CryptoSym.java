/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenjava;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Odin
 */
public class CryptoSym {

    private SecretKey sharedSecret;

    public CryptoSym(String secret) {
        //create AESKey from secret
    }

    public String encrypt(String initVector, String text) {
        System.out.println("CryptoSym.encrypt not yet implemented!");
        return text;
    }

    public String decrypt(String initVector, String text) {
        System.out.println("CryptoSym.decrypt not yet implemented!");
        return text;
    }

}
