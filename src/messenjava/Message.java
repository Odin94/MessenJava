package messenjava;

/**
 *
 * @author Maxi
 */
public class Message {

    protected String sender;
    protected String receiver;
    protected boolean isCommand; 

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }
    
    public boolean isCommand(){
        return isCommand;
    }
    
    public static Message parseStringToMessage(String msg){
        if (msg.startsWith("Command")){
            return CommandMessage.parseStringToCommandMessage(msg);
        }
        else {
            return DocumentMessage.parseStringToDocumentMessage(msg);
        }
    }
    
}
