/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenjava;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Odin
 */
class EncryptionFailedException extends Exception {

    public EncryptionFailedException() {
    }

    public EncryptionFailedException(String message) {
        super(message);
    }
}

class DecryptionFailedException extends Exception {

    public DecryptionFailedException() {
    }

    public DecryptionFailedException(String message) {
        super(message);
    }
}

public class CryptoAsym {

    static PrivateKey privKey;

    public static byte[] encrypt(String data, PublicKey pubKey) throws EncryptionFailedException {
        byte[] dataToEncrypt = data.getBytes();
        byte[] encryptedData = null;

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            encryptedData = cipher.doFinal(dataToEncrypt);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | InvalidKeyException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        if (encryptedData == null) {
            throw new EncryptionFailedException("Could not encrypt String: " + data);
        }

        return encryptedData;
    }

    public static String decrypt(byte[] data, PrivateKey privKey) throws DecryptionFailedException {
        byte[] decryptedData = null;

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privKey);
            decryptedData = cipher.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | InvalidKeyException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        if (decryptedData == null) {
            throw new DecryptionFailedException("Could not decrypt byte array: " + data);
        }

        return new String(decryptedData);
    }

    public static String sign(String text, PrivateKey privKey) {
        System.out.println("CryptoAsym.sign not yet implemented!!");
        return text;
    }

    public static String checkSignature(String text, PublicKey pubKey) {
        System.out.println("CryptoAsym.checkSignature not yet implemented!!");
        return text;
    }

    public static PublicKey readPublicKeyFromFile(String filename) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filename));
        ObjectInputStream ois = new ObjectInputStream(fis);

        BigInteger modulus = null;
        BigInteger exponent = null;
        try {
            modulus = (BigInteger) ois.readObject();
            exponent = (BigInteger) ois.readObject();
        } catch (ClassNotFoundException e) {
            //this should never happen '_>'
            e.printStackTrace();
        }

        RSAPublicKeySpec rsaPubKeySpec = new RSAPublicKeySpec(modulus, exponent);

        PublicKey pubKey = null;
        try {
            KeyFactory kFact = KeyFactory.getInstance("RSA");
            pubKey = kFact.generatePublic(rsaPubKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        ois.close();
        fis.close();

        return pubKey;
    }

    public static PrivateKey readPrivateKeyFromFile(String filename) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filename));
        ObjectInputStream ois = new ObjectInputStream(fis);

        BigInteger modulus = null;
        BigInteger exponent = null;
        try {
            modulus = (BigInteger) ois.readObject();
            exponent = (BigInteger) ois.readObject();
        } catch (ClassNotFoundException e) {
            //this should never happen '_>'
            e.printStackTrace();
        }

        PrivateKey privateKey = null;
        try {
            RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus, exponent);
            KeyFactory kFact = KeyFactory.getInstance("RSA");
            privateKey = kFact.generatePrivate(rsaPrivateKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        ois.close();
        fis.close();

        return privateKey;
    }

    public static void saveKeys(String filename, BigInteger mod, BigInteger exp) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(fos));

        oos.writeObject(mod);
        oos.writeObject(exp);

        oos.close();
        fos.close();
    }

    public static void generateKeysAndSaveToFiles(String pubKeyFileName, String privKeyFileName) throws IOException {
        generateKeysAndSaveToFiles(2048, pubKeyFileName, privKeyFileName);
    }

    public static void generateKeysAndSaveToFiles(int bitLenght, String pubKeyFileName, String privKeyFileName) throws IOException {
        KeyPairGenerator keyGen = null;
        try {
            keyGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        keyGen.initialize(bitLenght);
        KeyPair keyPair = keyGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        RSAPublicKeySpec rsaPubKeySpec = null;
        RSAPrivateKeySpec rsaPrivKeySpec = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            rsaPubKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            rsaPrivKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        saveKeys(pubKeyFileName, rsaPubKeySpec.getModulus(), rsaPubKeySpec.getPublicExponent());
        saveKeys(privKeyFileName, rsaPrivKeySpec.getModulus(), rsaPrivKeySpec.getPrivateExponent());
    }

    public static void testMe() throws Exception {
        String PUBLIC_KEY_FILE = "pub.key";
        String PRIVATE_KEY_FILE = "priv.key";

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();
        PublicKey pubKey = keyPair.getPublic();
        PrivateKey privKey = keyPair.getPrivate();

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec rsaPubKeySpec = keyFactory.getKeySpec(pubKey, RSAPublicKeySpec.class
        );
        RSAPrivateKeySpec rsaPrivKeySpec = keyFactory.getKeySpec(privKey, RSAPrivateKeySpec.class);

        saveKeys(PUBLIC_KEY_FILE, rsaPubKeySpec.getModulus(), rsaPubKeySpec.getPublicExponent());
        saveKeys(PRIVATE_KEY_FILE, rsaPrivKeySpec.getModulus(), rsaPrivKeySpec.getPrivateExponent());

        PublicKey pubKeyRead = readPublicKeyFromFile(PUBLIC_KEY_FILE);

        byte[] encryptedData = encrypt("Data to enc", pubKeyRead);

        PrivateKey privKeyRead = readPrivateKeyFromFile(PRIVATE_KEY_FILE);
        String decryptedData = decrypt(encryptedData, privKeyRead);

        if (decryptedData.equals("Data to enc")) {
            System.out.println("CryptoAsym seems to work fine!");
        } else {
            System.out.println("CryptoAsym messed up somewhere along the way: " + decryptedData + " != 'Data to enc'");
        }
    }

}
