/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenjava;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Odin
 */
public class CryptoAsym {

    static PrivateKey privKey;

    public static String encrypt(String text, PublicKey pubKey) {
        byte[] byteText = null;

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byteText = cipher.doFinal(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String encText = Arrays.toString(Base64.encodeBase64(byteText));

        return encText;
    }

    public static String decrypt(String encText, PrivateKey privKey) {
        byte[] encBytes = Base64.decodeBase64(encText.getBytes());
        byte[] cipherData = null;

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privKey);
            cipherData = cipher.doFinal(encBytes);
        } catch (Exception e) {
        }

        return new String(cipherData);
    }

    public static String sign(String text, PrivateKey privKey) {
        System.out.println("CryptoAsym.sign not yet implemented!!");
        return text;
    }

    public static String checkSignature(String text, PublicKey pubKey) {
        System.out.println("CryptoAsym.checkSignature not yet implemented!!");
        return text;
    }

    public static PublicKey stringToPubKey(String pubKeyString) {
       /* try {
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(pubKeyString.getBytes());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);

            return publicKey;
        } catch (Exception e) {
            System.out.println("fml my life");
            throw new IllegalArgumentException("things are fucked m8");
        }*/
        byte[] publicBytes = Base64.encodeBase64(pubKeyString.getBytes());
         PublicKey pubKey = null;

         X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
         try {
         KeyFactory keyFactory = KeyFactory.getInstance("RSA");
         pubKey = keyFactory.generatePublic(keySpec);
         } catch (Exception e) {
         }

         return pubKey;
    }

    public static PrivateKey stringToPrivKey(String privKeyString) {
        byte[] privateKeyBytes = Base64.decodeBase64(privKeyString.getBytes());
        PrivateKey privateKey = null;

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            KeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            privateKey = keyFactory.generatePrivate(privateKeySpec);
        } catch (Exception e) {
        }

        return privateKey;
    }

    public static void testMe() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        
        //TESTS
        String text = "Ick bin ein Berliner!";

        String pubKeyString = "-----BEGIN PUBLIC KEY-----\n"
                + "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3Sqn2pfQTymObJhdZYP7v/vmK\n"
                + "0J1aQc+WQSMw+MFVNHn5IzjIDt8s+GR5fo8ta//1Z9mZkPGs6CoX6BdAL/prje6q\n"
                + "kxYuvkuhUu4wsZN+m7QEdtcFAcTeWjOCY9ciW89m6RKdDknq7C0iVnYRkBZTi1QC\n"
                + "YLeQiZULE9LHezxEcwIDAQAB\n"
                + "-----END PUBLIC KEY-----";
        
        //wrong key here but who careas hhaha
        pubKeyString = "-----BEGIN PUBLIC KEY-----MIGeMA0GCSqGSIb3DQEBAQUAA4GMADCBiAKBgEfgW1xv55NBXLQJu+Kzh6taqHf4SrJ+xN3Z7Wu5T1mPWYIg6Ne0bFQUyqAe4bYk3lPvchYbDZky8snuyopZq7g2GcxbrxLMiJN8X1iD4WYAaRJ4EBgAuSUfp2i7r1P7c6yPeLdEQDtgTihY4jcIpRp+GxJXCp88jR4YOLIeslFnAgMBAAE=-----END PUBLIC KEY-----";

        String privKeyString = "-----BEGIN RSA PRIVATE KEY-----MIICXAIBAAKBgQC3Sqn2pfQTymObJhdZYP7v/vmK0J1aQc+WQSMw+MFVNHn5IzjIDt8s+GR5fo8ta//1Z9mZkPGs6CoX6BdAL/prje6qkxYuvkuhUu4wsZN+m7QEdtcFAcTeWjOCY9ciW89m6RKdDknq7C0iVnYRkBZTi1QCYLeQiZULE9LHezxEcwIDAQABAoGBAKZxpeA2GWwzWLpWH9PpUfisiP02rf19T735TrS04BO4wJ6uDm0VnDKhKdRuSiSMM3YtpRtf1ScX+MmW2C5Qo0yj6FlEuK2SAfd6F7TSwnKbHoJNFGCSnGrNewclNoBdZAgFY83rIYHzUg0fn8EaLNGrBfW44xSwsa9i2A1CsmWRAkEA/VtsJF4smkwTeQwyZDGflUHFH23oFaZu+Gf7JWUsQdSJmQ1JgNoTBDbdgSsjGUNR6GmMHd0z+OV/xFtYtswfuwJBALk0IpnYvxbDqCRhjp9kCrsp76BWMRr8EoCxqzZ0T9MHKBo0WHt9JdfHiNsAXWF3nILdy35LBq9hVAP/afAP1qkCQCZTgCZ4QJMO0xtKpwzVHOj9TA6XF7a+uKbHRAzCI1HKDw6iHe5qDtpiWlvB5MtbjbZ00QdrgQMz5IIVt3PfqSUCQCp7fAhgQIz/On2F50pGj3OZTf8wZjkzYyckGgr2qSzV8mv4X+eLsHeLrKfXsJPf9QXLzJberNzj7XqxRuKrL2kCQA1x+1wMwBUXF36DFCnjKbD+9ZDIiJfzlH3HE88F6tPFiDJy7CiZJMKLL8V+PYaExUjxtrPiCZyZ6dT4LAnBRDM=-----END RSA PRIVATE KEY-----";

        privKeyString = "-----BEGIN RSA PRIVATE KEY-----MIIBOgIBAAJBAJ+RrtT2AgExP8ivuY5ApTRLDrxTkqFUdYUCslOLZCGWhyTDbapFb0TxhW40WOiJIcohKjPTUq/Qkx3DMOFGhFcCAwEAAQJAG/JR4m5rj3Xmq+lK4EUmKfBzVjx009iM9IyyWrtxAAisHnv9AjUsmT/JB8nfb7rgPB7TsXIkM8SWIvVVK+fKwQIhANW5ISKFxAJd5zNEB+qCoLXGtZrdjPv4d+1vl6rIWkqxAiEAvyI1VGqfshQhJ06Ng3YjTdY1olVi5SYLH0oVZVQBcYcCIB3In9p8w7UEuwyE5YmDzLuoRnSffV874BKho4Q0SYjxAiEAg0wgjmjgYxho3fOcSt5wyhuIpIc7dGZ55Xii0gSvKSkCIELy6ngqKVLrFEeyNUTjx/UpNxYW73iZP5FBmkAW/dVM-----END RSA PRIVATE KEY-----";
        
        PublicKey pubKey = stringToPubKey(pubKeyString);
        PrivateKey privateKey = stringToPrivKey(privKeyString);

        String encText = encrypt(text, pubKey);
        System.out.println("encrypted: " + encText);

        String decText = decrypt(encText, privateKey);

        if (decText.equals(text)) {
            System.out.println("CryptoAsym seems to work as intended");
        } else {
            System.out.println("CryptoAsym seems to be broken: " + text + " isn't equal to: " + decText);
        }
    }

}
