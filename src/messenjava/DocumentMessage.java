package messenjava;

import java.util.Date;

/**
 *
 * @author Maxi
 */
public class DocumentMessage extends Message{

    private String Payload;
    private String timestamp;
    private String OTS;

    public DocumentMessage(String sender, String receiver, String Payload, String timestamp, String OTS) {
        this.sender = sender;
        this.receiver = receiver;
        this.Payload = Payload;
        this.timestamp = timestamp;
        this.OTS = OTS;
    }

    public DocumentMessage(String sender, String receiver, String Payload, String OTS) {
        this.sender = sender;
        this.receiver = receiver;
        this.Payload = Payload;
        this.timestamp = new Date().toString();
        this.OTS = OTS;
    }
    
    
    public static DocumentMessage parseStringToDocumentMessage(String msg){
        return new DocumentMessage("Some Sender", "Some Receiver","Some Payload","Some Timestamp","Maybe some OTS");
    }

    public String getPayload() {
        return Payload;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getOTS() {
        return OTS;
    }
    
    
    
    
}
