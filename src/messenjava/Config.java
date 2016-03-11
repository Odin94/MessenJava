package messenjava;

import com.offbynull.portmapper.PortMapperFactory;
import com.offbynull.portmapper.gateway.Bus;
import com.offbynull.portmapper.gateway.Gateway;
import com.offbynull.portmapper.gateways.network.NetworkGateway;
import com.offbynull.portmapper.gateways.process.ProcessGateway;
import com.offbynull.portmapper.mapper.MappedPort;
import com.offbynull.portmapper.mapper.PortMapper;
import com.offbynull.portmapper.mapper.PortType;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.interfaces.RSAPrivateKey;
import java.util.List;

/**
 *
 * @author Maxi
 */
public class Config {

    private static int myID;
    private static String myName;
    private static int myPort;
    /**
     * To get my IP, I need to use something like STUN
     * Maybe a port mapper and STUN will solve the problem...
     */
    private static InetAddress myIP; 
    private static RSAPrivateKey privKey;

    public static void initializeConfig() throws Exception{
        //TODO!
        //Read config, use port mapping
        myID = 1;
        myName = "Maxi";
        myIP = Util.StringToInetAddress("192.168.179.20");
        myPort = 50002;
        privKey = null;
        
    }
    
    public static void forwardPort(int port) throws InterruptedException{
        // Start gateways
        Gateway network = NetworkGateway.create();
        Gateway process = ProcessGateway.create();
        Bus networkBus = network.getBus();
        Bus processBus = process.getBus();

        // Discover port forwarding devices and take the first one found
        List<PortMapper> mappers = PortMapperFactory.discover(networkBus, processBus);
        PortMapper mapper = mappers.get(0);

        // Map internal port 12345 to some external port (55555 preferred)
        //
        // IMPORTANT NOTE: Many devices prevent you from mapping ports that are <= 1024
        // (both internal and external ports). Be mindful of this when choosing which
        // ports you want to map.
        MappedPort mappedPort = mapper.mapPort(PortType.TCP, port, port, 60);
        System.out.println("Port mapping added: " + mappedPort);

        // Refresh mapping half-way through the lifetime of the mapping (for example,
        // if the mapping is available for 40 seconds, refresh it every 20 seconds)
        while(true) {
            mappedPort = mapper.refreshPort(mappedPort, mappedPort.getLifetime() / 2L);
            System.out.println("Port mapping refreshed: " + mappedPort);
            Thread.sleep(mappedPort.getLifetime() * 1000L);
        }

    }
    
    public static int getMyID() {
        return myID;
    }

    public static String getMyName() {
        return myName;
    }

    public static int getMyPort() {
        return myPort;
    }

    public static InetAddress getMyIP() {
        return myIP;
    }

    public static RSAPrivateKey getPrivKey() {
        return privKey;
    }
    
    
    
}
