package messenjava;

/**
 *
 * @author Maxi
 */
public class Message {

    protected String sender;
    protected String receiver;
    protected boolean isCommand; 
    protected String timestamp;

    public static Message parseStringToMessage(String msg){
        if (msg.startsWith("Command")){
            return CommandMessage.parseStringToCommandMessage(msg);
        }
        else {
            return DocumentMessage.parseStringToDocumentMessage(msg);
        }
    }
    
    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getTimestamp() {
        return timestamp;
    }
    
    public boolean isCommand(){
        return isCommand;
    }
           
}
