
import java.net.InetAddress;
import java.util.Date;
import messenjava.CommandMessage;

/**
 *
 * @author Maxi
 */
public class test {
    public static void main (String[] args){
        try{
            byte[] IP = new byte[4];
            IP[0] = (byte) 192;
            IP[1] = (byte) 168;
            IP[2] = (byte) 1;
            IP[3] = (byte) 33;
            System.out.println(InetAddress.getByAddress(IP).getHostAddress());
            
        }
        catch (Exception e){
            System.err.println("This should never happen. "
                    + "UnkownHostError in parseStringToCommandMessage. "
                    + "IpAddress of invalid length.");
        }
    }
    
}
