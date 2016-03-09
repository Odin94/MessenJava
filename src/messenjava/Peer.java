/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenjava;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;

/**
 *
 * @author Odin
 */
public class Peer {

    private String name;
    private Connection connection;
    private RSAPublicKey pubKey;
    private RSAPrivateKey privKey;
    private CryptoSym cryptoSym;
    private ArrayList<String> messages;

    public Peer(String name, String ipAddress, int port, String pubKey, String privKey) {
        this.name = name;
        this.connection = new Connection(ipAddress, port);
        //this.pubKey = getPubKeyFromString(pubKey);
        //this.privKey = getPrivKeyFromString(privKey);
    }

    public void send(String message) {
        //encrypt message; throw exception if CryptoSym not established?
        
        connection.send(message);
    }
    
    public void receive() {
        //append recvd message to messages
    }
    
   
    private void createCryptoSym() {
        cryptoSym = new CryptoSym(establishSharedSecret());
    }
    
    private String establishSharedSecret() {
        System.out.println("Peer.establishSharedSecret not yet implemented!!");
        return "h¾R¦î1‹˜»êÁ²‘îéÉyò…þ%v"; //actual AES key I think
    }

}