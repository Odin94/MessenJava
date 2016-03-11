package messenjava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Odin: Dummy
 * @author Maxi: connect(), and a lot of other stuff
 */
public class Connection {
    
    private InetAddress destIPAddress;
    private int destPort;
    private Socket socket;
    private Peer peer;
    private MessageListenerThread mlt = null;

    public Connection(InetAddress destIPAddress, int destPort, Peer peer) {
        this.destIPAddress = destIPAddress;
        this.destPort = destPort;
        this.peer = peer;
    }
    
    public Connection(Socket socket, Peer peer){
        this.socket = socket;
        this.destPort = socket.getPort();
        this.destIPAddress = socket.getInetAddress();
        this.peer = peer;
    }
    
    /**
     * Connects and starts a thread which listens on this connection. 
     * If something is received, the thread will then call Peer.receive(), 
     * which will print to txt and notify frontend.
     * @return true if connection established successfully
     */
    public boolean connect() {
        if (!isConnected()){
            System.out.println("Connecting");
            try{
                socket = new Socket(destIPAddress,destPort,Config.getMyIP(),Config.getMyPort());
                System.out.println("Connected");
                mlt = new MessageListenerThread(peer);
                mlt.start();
                return true; // return true if connection established successfully
            }
            catch (IOException e){
                e.printStackTrace();
                return false;
            }
        }
        else{
            System.err.println("Connect was called, although the connection is already established. "
                    + "\nNothing was done.");
            return true; //true, because there is a valid connection
        }
    }
    
    public void send(String message) throws IOException{
        System.out.println("Sending");
        if (isConnected()){
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.print(message);
            System.out.println("Has sent");
        }
        else {
            System.out.println("Need to connect first");
            this.connect();
            this.send(message);
        }
    }
    
    public String receiveOneLine() throws IOException{
        if (isConnected()){
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String res = in.readLine();
            System.out.println(res);
            return res;
        }
        else {
            this.connect();
            return this.receiveOneLine();
        }
    }

    public boolean isConnected(){
        return socket != null;
    }
    
    public void stopListening(){
        if (mlt != null){
            mlt.setListen(false);
        }
    }
    
}
