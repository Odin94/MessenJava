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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
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

    // SHA 512 = SHA-2 (I think so anyways, but how important can it be, rite??)
    public static byte[] secureHash(String data) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        md.update(data.getBytes());
        byte[] digest = md.digest();

        return digest;
    }

    public static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

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
            throw new EncryptionFailedException("Could not RSA encrypt String: " + data);
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
            throw new DecryptionFailedException("Could not RSA decrypt byte array: " + data);
        }

        return new String(decryptedData);
    }

    public static byte[] sign(String data, PrivateKey privKey) {
        byte[] signedBytes = null;
        try {
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initSign(privKey);
            sig.update(data.getBytes());
            signedBytes = sig.sign();
        } catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
            e.printStackTrace(System.out);
        }

        return signedBytes;
    }

    public static boolean verifySignature(String checkValue, byte[] signature, PublicKey pubKey) {
        boolean sigIsValid = false;

        try {
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initVerify(pubKey);
            sig.update(checkValue.getBytes());
            sigIsValid = sig.verify(signature);
        } catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
            e.printStackTrace(System.out);
        }

        return sigIsValid;
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

        String PUBLIC_KEY_FILE2 = "pub2.key";
        String PRIVATE_KEY_FILE2 = "priv2.key";

        //Generate keys
        /*KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

         keyGen.initialize(2048);
         KeyPair keyPair = keyGen.generateKeyPair();
         PublicKey pubKey = keyPair.getPublic();
         PrivateKey privKey = keyPair.getPrivate();

         KeyFactory keyFactory = KeyFactory.getInstance("RSA");
         RSAPublicKeySpec rsaPubKeySpec = keyFactory.getKeySpec(pubKey, RSAPublicKeySpec.class
         );
         RSAPrivateKeySpec rsaPrivKeySpec = keyFactory.getKeySpec(privKey, RSAPrivateKeySpec.class);

         saveKeys(PUBLIC_KEY_FILE, rsaPubKeySpec.getModulus(), rsaPubKeySpec.getPublicExponent());
         saveKeys(PRIVATE_KEY_FILE, rsaPrivKeySpec.getModulus(), rsaPrivKeySpec.getPrivateExponent());*/
        generateKeysAndSaveToFiles(PUBLIC_KEY_FILE, PRIVATE_KEY_FILE);
        generateKeysAndSaveToFiles(PUBLIC_KEY_FILE2, PRIVATE_KEY_FILE2);

        PublicKey pubKey2 = readPublicKeyFromFile(PUBLIC_KEY_FILE2);
        PrivateKey privKey2 = readPrivateKeyFromFile(PRIVATE_KEY_FILE2);

        //Encrypt and Decrypt (after loading keys from files)
        PublicKey pubKey = readPublicKeyFromFile(PUBLIC_KEY_FILE);
        byte[] encryptedData = encrypt("Data to enc", pubKey);

        PrivateKey privKey = readPrivateKeyFromFile(PRIVATE_KEY_FILE);
        String decryptedData = decrypt(encryptedData, privKey);

        // Hash test
        String hashme = "hashmefriendo";
        byte[] hashedHashme = secureHash(hashme);

        String hashed = "7912f366fde83598f32d781c5e5769fcff40123cc6568dd5ad204773e379ec272f9e"
                + "430016cf8aab8b198d51efc142e87c4db27ecdc050bacbe304dec2929317";

        //Sign and verify
        String signMe = "Signme, senhor!";
        byte[] signedValue = sign(signMe, privKey);

        boolean signatureValid1 = verifySignature(signMe, signedValue, pubKey);
        boolean sinatureValid2 = !verifySignature(signMe, signedValue, pubKey2);
        boolean cryptingWorks = decryptedData.equals("Data to enc");
        boolean hashWorks = hashed.equals(toHexString(hashedHashme));

        if (cryptingWorks && hashWorks && signatureValid1 && sinatureValid2) {
            System.out.println("CryptoAsym seems to work fine!");
        } else {
            if (!cryptingWorks) {
                System.out.println("Enc/Dec didn't work: " + decryptedData + " != 'Data to enc'."
                        + " Might also be an error with loading from file");
            } else if (!hashWorks) {
                System.out.println("Hash didn't work: " + hashedHashme);
            } else if (!signatureValid1) {
                System.out.println("Signature/Verification didn't work.");
            } else if (sinatureValid2) {
                System.out.println("Signature/Verification returns true even for a false pubKey. Real bad.");
            } else {
                System.out.println("CryptoAsym messed up somewhere along the way");

            }
        }
    }
}
