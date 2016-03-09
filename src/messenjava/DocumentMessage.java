package messenjava;

import java.util.Date;

/**
 *
 * @author Maxi
 */
public class DocumentMessage extends Message{

    private String payload;
    private String OTS;

    public DocumentMessage(String sender, String receiver, String payload, String timestamp, String OTS) {
        this.sender = sender;
        this.receiver = receiver;
        this.payload = payload;
        this.timestamp = timestamp;
        this.OTS = OTS;
    }

    public DocumentMessage(String sender, String receiver, String payload, String OTS) {
        this.sender = sender;
        this.receiver = receiver;
        this.payload = payload;
        this.timestamp = new Date().toString();
        this.OTS = OTS;
    }
    
    
    public static DocumentMessage parseStringToDocumentMessage(String msg){
        String[] msgParts = msg.split("-");
        if (checkMsgFormat(msgParts)){
            return new DocumentMessage(msgParts[1].split(":")[1], msgParts[2].split(":")[1], 
                   msgParts[3].split(":")[1],msgParts[4].split(":")[1],msgParts[5].split(":")[1]);
        }
        else{
            System.err.println("Invalid Message Format in parseStringToDocumentMessage");
            return null;
        }
    }

    public static boolean checkMsgFormat(String[] msgParts){
        try{
            return msgParts[0].startsWith("Document") && msgParts[1].startsWith("Sender") 
                   && msgParts[2].startsWith("Receiver") && msgParts[3].startsWith("Timestamp") 
                   && msgParts[4].startsWith("OTS") && msgParts[5].startsWith("Payload");
        }
        catch (Exception e){
            return false;
        }
    }
    
    @Override
    public String toString(){
        return String.format("Document-Sender:%s-Receiver:%s-Timestamp:%s-OTS:%s-Payload:%s", 
                sender,receiver,timestamp,OTS,payload);
    }
    
    public String getPayload() {
        return payload;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getOTS() {
        return OTS;
    }
    
    
    
    
}
