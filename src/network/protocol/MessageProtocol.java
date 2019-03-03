package network.protocol;

import database.ProtocolQueue;
import database.ServerDatabaseConnection;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.Message;
import message.ProtocolMessage;
import network.ConnectionHandler;
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
    public static final String ACTION_RESPONSE = "RESPONSE";
    
    public static final int RESPONSE_READ = 3;
    public static boolean sendMessage(Message message)
    {   
        ClientConnectionHandler conn = ClientNetworkManager.getConnection();
        ProtocolParameters pp = new ProtocolParameters();
        pp.add(PROTOCOL_ACTION, ACTION_SEND);
        pp.add("Id", String.valueOf(message.getId()));
        pp.add("Sender", message.getSenderName());
        pp.add("Receiver", message.getReceiverName());
        pp.add("Text", message.getText());
        pp.add("DateSent", message.getDateSentString()); 
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
    
    public static boolean processInput(ProtocolParameters pp, ConnectionHandler conn)
    {
        switch (pp.getParameter(Protocol.PROTOCOL_ACTION))
        {
            case ACTION_SEND:
                return processServerInput(pp, (ServerConnectionHandler) conn);
            case ACTION_RECEIVE:
                return processClientInput(pp);
            case ACTION_RESPONSE:
                return processResponse(pp);
            default:
                break;
        }
        
        return false;
    }
    
    public static boolean processClientInput(ProtocolParameters pp)
    {
        Message message = new Message(pp);
        ChatManager.receiveMessage(message);
        return true;
    }
                    
    
    public static boolean processServerInput(ProtocolParameters pp, ServerConnectionHandler conn)
    {
        try 
        {
            int responseCode = forwardMessage(HEAD, pp);
            sendResponse(responseCode, pp, conn);
            return true;
        } 
        catch (IOException ex)
        {
            Logger.getLogger(MessageProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return false; 
    }
    
    public static void sendResponse(int responseCode, ProtocolParameters pp, ServerConnectionHandler conn) throws IOException
    {
        ProtocolParameters rPP = new ProtocolParameters();
            rPP.add(PROTOCOL_ACTION, ACTION_RESPONSE);
            rPP.add("MessageId", pp.getParameter("Id"));
            rPP.add("Receiver", pp.getParameter("Receiver"));
            rPP.add("ResponseCode", String.valueOf(responseCode));
            send(HEAD, rPP, conn);
    }
    
    private static int forwardMessage(String head, ProtocolParameters pp) throws IOException
    {
        pp.replace("Action", ACTION_RECEIVE);
        String output = buildProtocolString(head, pp);
        ProtocolMessage message = new ProtocolMessage(pp);
        message.setText(output);
        return ConnectionSwitch.switchProtocol(message);
    }
    
    public static void retrieveStoredMessages(String username, ServerConnectionHandler conn) throws SQLException, IOException 
    {          
        ResultSet rs = ProtocolQueue.loadUnsentProtocols(username);
        while(rs.next())
        {
            ProtocolMessage message = new ProtocolMessage(rs);
            if(conn.send(message) == ServerConnectionHandler.MESSAGE_DELIVERED)
            {
                ProtocolParameters pp = new ProtocolParameters(new Scanner(message.getText()));
                sendResponse(ServerConnectionHandler.MESSAGE_DELIVERED, pp, conn);
                rs.deleteRow();
            }             
        }   
        
        ServerDatabaseConnection.closeQuery(rs);    
    } 

    private static boolean processResponse(ProtocolParameters pp)
    {
        int messageId = Integer.parseInt(pp.getParameter("MessageId"));
        int responseCode = Integer.parseInt(pp.getParameter("ResponseCode"));
        String chatName = pp.getParameter("Receiver");
        ChatManager.receiveResponse(chatName, messageId, responseCode);
        return true;
    }
  
}
