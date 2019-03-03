
package network.server.protocol;

import database.ProtocolQueue;
import database.ServerDatabaseConnection;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.ProtocolMessage;
import network.server.ConnectionSwitch;
import network.protocol.Protocol;
import network.protocol.ProtocolParameters;
import static network.protocol.Protocol.buildProtocolString;
import network.server.ServerConnectionHandler;

public class ServerMessageProtocol extends Protocol
{
    public static final String HEAD_IDENTIFIER = "MSG";
    
    public static boolean processInput(ProtocolParameters pp)
    {
        try 
        {
            forwardMessage(HEAD_IDENTIFIER, pp);
            return true;
        } 
        catch (IOException ex)
        {
            Logger.getLogger(ServerMessageProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return false; 
    }
    
    private static boolean forwardMessage(String head, ProtocolParameters pp) throws IOException
    {
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
            System.out.println("Got Mail");
            ProtocolMessage message = new ProtocolMessage(rs);
            if(conn.send(message))
                rs.deleteRow();
        }   
        
        ServerDatabaseConnection.closeQuery(rs);    
    }
    
}
