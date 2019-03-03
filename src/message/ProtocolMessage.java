package message;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import network.protocol.ProtocolParameters;

/**
 *
 * @author Tristan
 */
public class ProtocolMessage
{
    private String senderName;
    private String receiverName;
    private String text;
    private Date sent;
    private boolean alreadySaved;

    public ProtocolMessage(String senderName, String receiverName, String text) 
    {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.text = text;
    }
    
    public ProtocolMessage(ResultSet rs) throws SQLException
    {
        this.senderName = rs.getString("senderName");
        this.receiverName = rs.getString("receiverName");
        this.text = rs.getString("text");
        sent = rs.getDate("sentTimestamp");
        alreadySaved = true;
    }
    
    public ProtocolMessage(ProtocolParameters pp)
    {
        this.senderName = pp.getParameter("Sender");
        this.receiverName = pp.getParameter("Receiver");
        this.text = pp.toString();
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

    public boolean isAlreadySaved() 
    {
        return alreadySaved;
    }
    
    public void setText(String text)
    {
        this.text = text;
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
