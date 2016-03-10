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
 * @author Maxi: connect()
 */
public class Connection {
    
    private InetAddress destIPAddress;
    private int destPort;
    private Socket socket;
    private boolean doRead;

    public Connection(InetAddress destIPAddress, int destPort) {
        this.destIPAddress = destIPAddress;
        this.destPort = destPort;
    }
    
    
    
    public boolean connect() {
        try{
            socket = new Socket(destIPAddress,destPort,Config.getMyIP(),Config.getMyPort());
            return true; // return true if connection established successfully
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public void send(String message) throws IOException{
        if (socket != null){
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.print(message);
        }
        else {
            this.connect();
            this.send(message);
        }
    }
    
    public void receive() throws IOException{
        if (socket != null){
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            doRead = true;
            while (doRead){
                System.out.println(in.readLine());
            }
        }
        else {
            this.connect();
            this.receive();
        }
    }

    public boolean doesRead() {
        return doRead;
    }
    
    public void setRead(boolean flag){
        doRead = flag;
    }
    
}
