package messenjava;

/**
 *
 * @author Odin: Dummy
 * @author Maxi: connect()
 */
public class Connection {
    
    private String ipAddress;
    private int port;
    
    public Connection(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }
    
    public boolean connect() {
        
        return true; // return true if connection established successfully
    }
    
    public void send(String message) {
        
    }
    
    public void receive() {
        
    }
    
}
