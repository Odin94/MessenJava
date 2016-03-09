/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package messenjava;

/**
 *
 * @author Odin
 */
public class Connection {
    
    private String ipAddress;
    private int port;
    
    public Connection(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }
    
    public boolean connect() {
        System.out.println("Connection.connect not yet implemented");
        return true; // return true if connection established successfully
    }
    
    public void send(String message) {
        
    }
    
    public void receive() {
        
    }
    
}
