package messenjava;

/**
 *
 * @author Maxi
 */
public class CommandMessage extends Message {

    private boolean isGenerate; //1 for generate, 0 for delete
    private String name;
    private String port;
    private int ID;    

    public CommandMessage(String sender, String receiver, boolean isGenerate, String name, String port, int ID) {
        this.sender = sender;
        this.receiver = receiver;
        this.isGenerate = isGenerate;
        this.name = name;
        this.port = port;
        this.ID = ID;
    }
          
    public static CommandMessage parseStringToCommandMessage(String msg){
        return new CommandMessage ("Sender", "Receiver", true,"Peter","42000",13);
    }
    
}
