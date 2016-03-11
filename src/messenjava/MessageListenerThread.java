package messenjava;

import java.net.Socket;

/**
 *
 * @author Maxi
 */
public class MessageListenerThread extends Thread {

    private Peer peerToListen;
    private boolean doListen;

    public MessageListenerThread(Peer peerToListen) {
        this.peerToListen = peerToListen;
        doListen = true;
    }
    
    @Override
    public void run(){
        while (doListen){
            try{
                String res = peerToListen.getConnection().receiveOneLine();
                peerToListen.receive(res);
                
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public boolean doesListen(){
        return doListen;
    }
    
    public void setListen(boolean flag){
        doListen = flag;
    }
    
}
