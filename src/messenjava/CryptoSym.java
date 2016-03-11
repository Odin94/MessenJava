/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenjava;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
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

    public static byte[] encrypt(String data, SecretKey sharedSecret) throws EncryptionFailedException {
        byte[] dataToEncrypt = data.getBytes();
        byte[] encryptedData = null;

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, sharedSecret);
            encryptedData = cipher.doFinal(dataToEncrypt);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | InvalidKeyException | IllegalBlockSizeException e) {
            e.printStackTrace(System.out);
        }

        if (encryptedData == null) {
            throw new EncryptionFailedException("Could not AES encrypt String: " + data);
        }

        return encryptedData;
    }

    public static String decrypt(byte[] encData, SecretKey sharedSecret) throws DecryptionFailedException {
        byte[] decryptedData = null;

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, sharedSecret);
            decryptedData = cipher.doFinal(encData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | InvalidKeyException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        if (decryptedData == null) {
            throw new DecryptionFailedException("Could not RSA decrypt byte array: " + encData);
        }

        return new String(decryptedData);
    }

    // can't make keys larger than 128 because of really stupid reasons: http://www.javamex.com/tutorials/cryptography/unrestricted_policy_files.shtml
    // 128 bit keylength for AES is estimated to be secure until after 2030; should be fine for now
    public static SecretKey generateKey(char[] password, int bitLength) {
        SecureRandom secRandom = new SecureRandom();
        byte[] salt = new byte[8];
        secRandom.nextBytes(salt);

        SecretKeyFactory factory = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        KeySpec spec = new PBEKeySpec(password, salt, 65536, bitLength);

        SecretKey tmpKey = null;
        try {
            tmpKey = factory.generateSecret(spec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        SecretKey secret = new SecretKeySpec(tmpKey.getEncoded(), "AES");

        return secret;
    }

    public static void testMe() throws Exception {
        String messageToEnc = "Winners don't do drugs!";
        SecretKey testKey = generateKey("MySecretKey".toCharArray(), 128);

        byte[] encMessage = encrypt(messageToEnc, testKey);
        String decMessage = decrypt(encMessage, testKey);

        if (decMessage.equals(messageToEnc)) {
            System.out.println("CryptoSym seems to be working!");
        } else {
            System.out.println("Seems like CryptoSym messed up somewhere along the way :(");
        }
    }
}
