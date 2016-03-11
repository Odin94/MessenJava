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
        //ssocket = new ServerSocket(Config.getMyPort());
        ssocket = new ServerSocket(50004);
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
                otherGuy.setConnection(new Connection(socketToOtherGuy, otherGuy));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public Peer findPeerFromIPandPort(InetAddress IP, int port){
        for (int i = 0; i<MessenJava.otherPeers.size(); i++){
            if(MessenJava.otherPeers.get(i).getConnection().getDestIPAddress()==IP &&
                    MessenJava.otherPeers.get(i).getConnection().getDestPort()==port){
                return MessenJava.otherPeers.get(i);
            }
        }
        return null;
    }

    
    
    
}
