package messenjava;

import java.net.InetAddress;
import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;

/**
 *
 * @author Maxi
 */
public class Config {

    private static int myID;
    private static String myName;
    private static int myPort;
    /**
     * To get my IP, I need to use something like STUN
     * There are open STUN servers, and probably some implementations, but I did not find anything directly useful.
     * Still, we want P2P, so keep on searching...
     */
    private static InetAddress myIP; 
    private static RSAPrivateKey privKey;

    public void initializeConfig(){
        //TODO!
        //Read txt, using the still missing txt-reader.
        //Then put it in the static values
        //Then party hard
        //REAL HARD!!!11
        myID = 1;
        myName = "Peter";
        myPort = 1337;
    }
    
    public static int getMyID() {
        return myID;
    }

    public static String getMyName() {
        return myName;
    }

    public static int getMyPort() {
        return myPort;
    }

    public static InetAddress getMyIP() {
        return myIP;
    }

    public static RSAPrivateKey getPrivKey() {
        return privKey;
    }
    
    
    
}
