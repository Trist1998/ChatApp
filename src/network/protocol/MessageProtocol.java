package network.protocol;

import database.ProtocolQueue;
import database.ServerDatabaseConnection;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.Message;
import message.ProtocolMessage;
import network.client.ClientConnectionHandler;
import network.client.ClientNetworkManager;
import static network.protocol.Protocol.buildProtocolString;
import static network.protocol.Protocol.send;
import network.server.ConnectionSwitch;
import network.server.ServerConnectionHandler;
import ui.ChatManager;

/**
 *
 * @author Tristan
 */
public class MessageProtocol extends Protocol
{
    public static final String HEAD = "MSG";
    public static final String ACTION_SEND = "SEND";
    public static final String ACTION_RECEIVE = "RECEIVE";
    
    public static boolean sendMessage(Message message)
    {   
        ClientConnectionHandler conn = ClientNetworkManager.getConnection();
        ProtocolParameters pp = new ProtocolParameters();
        pp.add("Action", ACTION_SEND);
        pp.add("Id", String.valueOf(message.getId()));
        pp.add("Sender", message.getSenderName());
        pp.add("Receiver", message.getReceiverName());
        pp.add("Text", message.getText());       
        try 
        {
           send(HEAD, pp, conn);
           return true;
        } 
        catch (IOException ex) 
        {
           Logger.getLogger(MessageProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static boolean processInput(ProtocolParameters pp)
    {
        if(pp.getParameter(Protocol.PROTOCOL_ACTION).equals(ACTION_SEND))
            return processServerInput(pp);
        else if(pp.getParameter(Protocol.PROTOCOL_ACTION).equals(ACTION_RECEIVE))
            return processClientInput(pp);
        
        return false;
    }
    
    public static boolean processClientInput(ProtocolParameters pp)
    {
        Message message = new Message(pp);
        ChatManager.receiveMessage(message);
        return true;
    }
                    
    
    public static boolean processServerInput(ProtocolParameters pp)
    {
        try 
        {
            forwardMessage(HEAD, pp);
            return true;
        } 
        catch (IOException ex)
        {
            Logger.getLogger(MessageProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return false; 
    }
    
    private static boolean forwardMessage(String head, ProtocolParameters pp) throws IOException
    {
        pp.add("Action", ACTION_RECEIVE);
        String output = buildProtocolString(head, pp);
        ProtocolMessage message = new ProtocolMessage(pp);
        message.setText(output);
        ConnectionSwitch.switchProtocol(message);
        return true;
    }
    
    public static void retrieveStoredMessages(String username, ServerConnectionHandler conn) throws SQLException 
    {          
        ResultSet rs = ProtocolQueue.loadUnsentProtocols(username);
        while(rs.next())
        {
            ProtocolMessage message = new ProtocolMessage(rs);
            if(conn.send(message))
                rs.deleteRow();
        }   
        
        ServerDatabaseConnection.closeQuery(rs);    
    } 
  
}
