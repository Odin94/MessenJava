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
        String[] msgParts = msg.split("-");
        if (checkMsgFormat(msgParts)){
            boolean flag;
            if (msgParts[1].equals("Generate")){
                flag = true;
            }
            else{
                flag = false;
            }
                
            return new CommandMessage (msgParts[2].split(":")[1], msgParts[3].split(":")[1], 
               msgParts[4].split(":")[1], flag, msgParts[6].split(":")[1],
               Integer.parseInt(msgParts[7].split(":")[1]), Util.StringToInetAddress(msgParts[8].split(":")[1]),
               Integer.parseInt(msgParts[5].split(":")[1]));
           
        }
        else{
            System.err.println("Invalid Message Format in parseStringToCommandMessage");
            return null;
        }
        
    }
    
    public static boolean checkMsgFormat(String[] msgParts){
        try{
            return msgParts[0].startsWith("Command") && (msgParts[1].equals("Generate") || msgParts[1].equals("Delete"))
                   && msgParts[2].startsWith("Sender") && msgParts[3].startsWith("Receiver") 
                   && msgParts[4].startsWith("Timestamp") && msgParts[5].startsWith("ID") 
                   && msgParts[6].startsWith("Name") && msgParts[7].startsWith("Port") 
                   && msgParts[8].startsWith("IP");
        }
        catch (Exception e){
            return false;
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

    public boolean isGenerate() {
        return isGenerate;
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    public InetAddress getIPAddress() {
        return IPAddress;
    }

    public int getID() {
        return ID;
    }
    
}
