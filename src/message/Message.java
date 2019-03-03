
package message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.protocol.ProtocolParameters;

public class Message 
{
    private int id;
    private String senderName;
    private String receiverName;
    private String text;
    private int state;
    private Date received;
    private Date sent;

    public Message(String senderName, String receiverName, String text) 
    {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.text = text;
        received = null;
        state = -1;
    }

    public Message(ProtocolParameters pp)
    {
        this.id = Integer.parseInt(pp.getParameter("Id"));
        this.senderName = pp.getParameter("Sender");
        this.receiverName = pp.getParameter("Receiver");
        this.text = pp.getParameter("Text");
        try
        {
            this.sent = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z").parse(pp.getParameter("DateSent"));
        } 
        catch (ParseException ex)
        {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        state = -1;
    }

    public Message(int id, String senderName, String receiverName, String text)
    {
        this.id = id;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.text = text;
        received = null;
        state = -1;
    }  
    
    public synchronized void setState(int responseCode)
    {
        state = Math.max(state, responseCode);
    }
    
    public synchronized int getState()
    {
        return state;
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
    
    public String getDateSentString()
    {
        return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z").format(sent);
    }
                    

    public void setSent(Date sent)
    {
        this.sent = sent;
    }  
}
