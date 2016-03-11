package messenjava;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Maxi
 */
public class Util {

    public static InetAddress StringToInetAddress(String ip){
        String[] parts = ip.split("\\.");
        try{
                byte[] IP = new byte[4];
                IP[0] = (byte) Integer.parseInt(parts[0]);
                IP[1] = (byte) Integer.parseInt(parts[1]);
                IP[2] = (byte) Integer.parseInt(parts[2]);
                IP[3] = (byte) Integer.parseInt(parts[3]);
                return InetAddress.getByAddress(IP);
        }
        catch (UnknownHostException e){
            e.printStackTrace();
            System.err.println(String.format("Converting String %s to IP failed!",ip));
            return null;
        }
    }
    
}
