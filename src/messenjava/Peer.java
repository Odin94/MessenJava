/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenjava;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;

/**
 *
 * @author Odin: dummy, 
 * @author Maxi: receive
 */
public class Peer {

    private int ID;
    private String name;
    private Connection connection;
    private RSAPublicKey pubKey;
    private CryptoSym cryptoSym;
    private ArrayList<Message> oldMessages;
    private MessenJava view;
    
    public Peer(String name, String ipAddress, int port, String pubKey, String privKey, MessenJava view) {
        this.name = name;
        this.connection = new Connection(Util.StringToInetAddress(ipAddress), port, this);
        this.view = view;
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
    
    public void sendWithoutEncryption(String message) throws IOException{
        this.connection.send(message);
    }
    
    public void receive(String message) {
        //writeMessageToTxt
        //frontend.newMessage(this,message)
        if (message != null){
            System.out.println(message);
            view.display(message, false);
        }
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

    public void setView(MessenJava view) {
        this.view = view;
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
