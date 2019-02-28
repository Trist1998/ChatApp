
package message;

import java.sql.Date;
import network.protocol.ProtocolParameters;

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

    public Message(ProtocolParameters pp)
    {
        this.senderName = pp.getParameter("Sender");
        this.receiverName = pp.getParameter("Receiver");
        this.text = pp.getParameter("Text");
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
