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
        return new DocumentMessage("Some Sender", "Some Receiver","Some Payload","Some Timestamp","Maybe some OTS");
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
