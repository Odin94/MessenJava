package messenjava;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.collections.ObservableList;

/**
 *
 * @author Maxi
 */
public class ConnectionListenerThread extends Thread{

    ServerSocket ssocket;
    
    public ConnectionListenerThread() throws Exception{
        ssocket = new ServerSocket(Config.getMyPort());
    }
        
    @Override
    public void run(){
        while(true){
            try{
                System.out.println("A thread is now listening for connections");
                Socket socketToOtherGuy = ssocket.accept();
                InetAddress remoteIP = socketToOtherGuy.getInetAddress();
                int remotePort = socketToOtherGuy.getPort();
                Peer otherGuy = findPeerFromIPandPort(remoteIP, remotePort);
                otherGuy.setConnectionAndConnect(new Connection(socketToOtherGuy, otherGuy));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public Peer findPeerFromIPandPort(InetAddress IP, int port){
        //uses MessenJava.otherPeers
        return null;
    }

    
    
    
}
