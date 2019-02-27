
package message;

import java.sql.Date;

public class Message 
{
    private String senderName;
    private String receiverName;
    private String text;
    private Date received;

    public Message(String senderName, String receiverName, String text) 
    {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.text = text;
        received = null;
    }

    
    public String getSenderName() 
    {
        return senderName;
    }

    public String getReceiverName() 
    {
        return receiverName;
    }

    public String getText() 
    {
        return text;
    }

    public boolean isReceived() 
    {
        return received != null;
    }
    
    
}
