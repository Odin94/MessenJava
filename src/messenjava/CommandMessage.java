package messenjava;

import java.net.InetAddress;
import java.util.Date;

/**
 *
 * @author Maxi
 */
public class CommandMessage extends Message {

    private boolean isGenerate; //1 for generate, 0 for delete
    private String name;
    private int port;
    private InetAddress IPAddress;
    private int ID;    

    public CommandMessage(String sender, String receiver, String timestamp, boolean isGenerate, 
            String name, int port, InetAddress IPAddress, int ID) {
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
        this.isGenerate = isGenerate;
        this.name = name;
        this.port = port;
        this.IPAddress = IPAddress;
        this.ID = ID;
    }
    
    public CommandMessage(String sender, String receiver, boolean isGenerate, 
            String name, int port, InetAddress IPAddress, int ID) {
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = new Date().toString();
        this.isGenerate = isGenerate;
        this.name = name;
        this.port = port;
        this.IPAddress = IPAddress;
        this.ID = ID;
    }
          
    public static CommandMessage parseStringToCommandMessage(String msg){
        try{
            byte[] IP = new byte[4];
            IP[0] = (byte) 192;
            IP[1] = (byte) 168;
            IP[2] = (byte) 1;
            IP[3] = (byte) 33;
            
            return new CommandMessage ("Sender", "Receiver", new Date().toString(), true,"Peter",42000,
                    InetAddress.getByAddress(IP),13);
        }
        catch (Exception e){
            System.err.println("This should never happen. "
                    + "UnkownHostError in parseStringToCommandMessage. "
                    + "IpAddress of invalid length.");
            return null; 
        }
    }
    
    @Override
    public String toString(){
        String flag;
        if (isGenerate){
            flag = "Generate";
        }
        else{
            flag = "Delete";
        }
        return String.format("Command-%s-Sender:%s-Receiver:%s-Timestamp:%s-ID:%s-Name:%s-Port:%s-IP:%s", 
                flag,sender,receiver,timestamp,ID,name,port,IPAddress.toString());
    }
    
}
