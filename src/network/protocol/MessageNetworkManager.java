package network.protocol;

// Imports 
import database.NetworkMessageQueue;
import database.ServerDatabaseConnection;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.Message;
import message.NetworkMessage;
import network.ConnectionHandler;
import network.client.ClientConnectionHandler;
import network.client.ClientNetworkManager;
import network.server.ConnectionSwitch;
import network.server.ServerConnectionHandler;
import org.json.simple.parser.ParseException;
import ui.ChatManager;

/**
 * MessageNetworkManager class is used to send and process messages.
 *
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class MessageNetworkManager extends NetworkMessageHandler 
{

    // Variables 
    public static final String HEAD = "MSG";
    public static final String ACTION_SEND = "SEND";
    public static final String ACTION_RECEIVE = "RECEIVE";
    public static final String ACTION_RESPONSE = "RESPONSE";
    public static final String ACTION_REQUEST = "REQUEST";
    public static final int RESPONSE_READ = 3;

    /**
     * Attempts to send message, if successful returns true.
     *
     * @param message
     * @return
     */
    public static boolean sendMessage(Message message) 
    {
        ClientConnectionHandler conn = ClientNetworkManager.getConnection();
        ProtocolParameters pp = new ProtocolParameters(HEAD, ACTION_SEND);
        
        pp.add("Id", String.valueOf(message.getId()));
        pp.add("ChatName", message.getChatName());
        pp.add("Sender", message.getSenderName());
        pp.add("Receiver", message.getReceiverName());
        pp.add("Text", message.getText());
        pp.add("DateSent", message.getDateSentString());
        
        return send(pp, conn);
    }

    /**
     * Processes the protocol parameters.
     *
     * @param pp
     * @param conn
     * @return
     */
    public static boolean processInput(ProtocolParameters pp, ConnectionHandler conn) 
    {
        switch (pp.getParameter(NetworkMessageHandler.PROTOCOL_ACTION)) 
        {
            case ACTION_SEND:
                return processServerInput(pp, (ServerConnectionHandler) conn);
            case ACTION_RECEIVE:
                return processClientInput(pp);
            case ACTION_REQUEST:
                return retrieveStoredMessages(conn.getUsername(), (ServerConnectionHandler) conn);
            case ACTION_RESPONSE:
                return processResponse(pp);
            default:
                break;
        }

        return false;
    }

    /**
     * Client side processes received message.
     *
     * @param pp
     * @return
     */
    public static boolean processClientInput(ProtocolParameters pp) 
    {
        Message message = new Message(pp);
        ChatManager.receiveMessage(message);
        return true;
    }

    /**
     * Processes message sent from client.
     *
     * @param pp
     * @param conn
     * @return
     */
    public static boolean processServerInput(ProtocolParameters pp, ServerConnectionHandler conn) 
    {
        try 
        {
            int responseCode = forwardMessage(pp);
            sendResponse(responseCode, pp, conn);
            return true;
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(MessageNetworkManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    /**
     * Sends response to sender of message depending on response code.
     *
     * @param responseCode
     * @param pp
     * @param conn
     * @throws IOException
     */
    public static void sendResponse(int responseCode, ProtocolParameters pp, ServerConnectionHandler conn) throws IOException 
    {
        ProtocolParameters rPP = new ProtocolParameters(HEAD, ACTION_RESPONSE);
        rPP.add("MessageId", pp.getParameter("Id"));
        rPP.add("Receiver", pp.getParameter("Receiver"));
        rPP.add("ResponseCode", String.valueOf(responseCode));
        send(rPP, conn);
    }

    /**
     * Sends message from server to receiver client.
     *
     * @param head
     * @param pp
     * @return
     * @throws IOException
     */
    private static int forwardMessage(ProtocolParameters pp) throws IOException 
    {
        pp.replace(NetworkMessageHandler.PROTOCOL_ACTION, ACTION_RECEIVE);
        NetworkMessage message = new NetworkMessage(pp);
        message.setText(pp.buildProtocolString());
        return ConnectionSwitch.switchProtocol(message);
    }

    /**
     * Retrieve stored messages from database and send to client.
     * @param username
     * @param conn
     */
    public static boolean retrieveStoredMessages(String username, ServerConnectionHandler conn)
    {
        try 
        {
            ResultSet rs = NetworkMessageQueue.loadUnsentProtocols(username);
            while (rs.next()) 
            {
                NetworkMessage message = new NetworkMessage(rs);
                if (conn.send(message) == ServerConnectionHandler.MESSAGE_DELIVERED)
                {
                    ProtocolParameters pp = new ProtocolParameters(message.getText());
                    sendResponse(ServerConnectionHandler.MESSAGE_DELIVERED, pp, conn);
                    rs.deleteRow();
                }
            }
            
            ServerDatabaseConnection.closeQuery(rs);
            return true;
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(MessageNetworkManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex)
        {
            Logger.getLogger(MessageNetworkManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ParseException ex)
        {
            Logger.getLogger(MessageNetworkManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Client processes response. 
     * @param pp
     * @return
     */
    public static boolean processResponse(ProtocolParameters pp) 
    {
        int messageId = Integer.parseInt(pp.getParameter("MessageId"));
        int responseCode = Integer.parseInt(pp.getParameter("ResponseCode"));
        String chatName = pp.getParameter("Receiver");
        ChatManager.receiveResponse(chatName, messageId, responseCode);
        return true;
    }

    public static void requestStoredMessages()
    {
        ProtocolParameters pp = new ProtocolParameters(HEAD, ACTION_REQUEST);
        send(pp, ClientNetworkManager.getConnection());
    }

}
