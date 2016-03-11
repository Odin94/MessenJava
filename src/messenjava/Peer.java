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

    private int ID;
    private String name;
    private Connection connection;
    private RSAPublicKey pubKey;
    private CryptoSym cryptoSym;
    private ArrayList<Message> oldMessages;
    
    public Peer(String name, String ipAddress, int port, String pubKey, String privKey) {
        this.name = name;
        this.connection = new Connection(Util.StringToInetAddress(ipAddress), port, this);
        //this.pubKey = getPubKeyFromString(pubKey);
        //this.privKey = getPrivKeyFromString(privKey);
    }

    public void prepareSecretAndSend(String message) {
        /** generate message header, message object
        * call Message.toString()
        * encrypt the returned String -MW */
        
        //encrypt message; throw exception if CryptoSym not established?
        
        //connection.send(message);
    }
    
    public void receive(String message) {
        //writeMessageToTxt
        //frontend.newMessage(this,message)
        System.out.println(message);
        
    }
    
   
    private void createCryptoSym() {
        cryptoSym = new CryptoSym(establishSharedSecret());
    }
    
    private String establishSharedSecret() {
        System.out.println("Peer.establishSharedSecret not yet implemented!!");
        return "h¾R¦î1‹˜»êÁ²‘îéÉyò…þ%v"; //actual AES key I think
    }
 
    public void setConnectionAndConnect(Connection connection){
        this.connection = connection;
        this.connection.connect(); //also starts a listener
    }
    
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
        
    public String getName(){
        return name;
    }

    public int getID() {
        return ID;
    }

    public Connection getConnection() {
        return connection;
    }

    public RSAPublicKey getPubKey() {
        return pubKey;
    }

    public CryptoSym getCryptoSym() {
        return cryptoSym;
    }

    public ArrayList<Message> getOldMessages() {
        return oldMessages;
    }
    
    
}
