
package message;

import java.sql.Date;
import network.protocol.ProtocolParameters;

public class Message 
{
    private int id;
    private String senderName;
    private String receiverName;
    private String text;
    private Date received;
    private Date sent;

    public Message(String senderName, String receiverName, String text) 
    {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.text = text;
        received = null;
    }

    public Message(ProtocolParameters pp)
    {
        this.id = Integer.parseInt(pp.getParameter("Id"));
        this.senderName = pp.getParameter("Sender");
        this.receiverName = pp.getParameter("Receiver");
        this.text = pp.getParameter("Text");
    }

    public Message(int id, String senderName, String receiverName, String text)
    {
        this.id = id;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.text = text;
        received = null;
    }  

    public int getId()
    {
        return id;
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
    
    public void setText(String text)
    {
        this.text = text;
    }

    public Date getReceived()
    {
        return received;
    }

    public void setReceived(Date received)
    {
        this.received = received;
    }

    public Date getSent()
    {
        return sent;
    }

    public void setSent(Date sent)
    {
        this.sent = sent;
    }  
}
