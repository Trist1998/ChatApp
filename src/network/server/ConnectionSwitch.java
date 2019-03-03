package network.server;

import database.ProtocolQueue;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.ProtocolMessage;

/**
 *
 * @author Tristan
 */
public class ConnectionSwitch
{
    private static HashMap<String, ServerConnectionHandler> activeConnections = new HashMap<>();
    
    public synchronized static boolean addConnection(ServerConnectionHandler connection)
    {
        if(activeConnections.get(connection.getUsername()) == null)
        {
            activeConnections.put(connection.getUsername(), connection);
            return true;
        }
        System.out.println("Already connected pls log out");
        return false;
    }
    
    public static int switchProtocol(ProtocolMessage message)
    {
        ServerConnectionHandler connection = activeConnections.get(message.getReceiverName());//add to protocol queue if not connected
        if(connection != null)
        {
            return connection.send(message);//In this context the text of the message should be the entire protocol
        }
        else
        {
            ProtocolQueue pq = new ProtocolQueue(message);
            try
            {
                pq.addToQueue();//TODO If successful send delivered to server notification to sender
                return ServerConnectionHandler.MESSAGE_SAVED;//Message saved to database
            } 
            catch (SQLException ex)
            {
                Logger.getLogger(ConnectionSwitch.class.getName()).log(Level.SEVERE, null, ex);
                return ServerConnectionHandler.MESSAGE_LOST;//Means message hasn't been saved to database
            }
        }
    }

    public static void removeConnection(ServerConnectionHandler conn)
    {
        activeConnections.remove(conn.getUsername());
    }
    
}
