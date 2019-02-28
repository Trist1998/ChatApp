package network.server;

import database.ProtocolQueue;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public static void switchProtocol(String senderName, String receiverName, String text)
    {
        ServerConnectionHandler connection = activeConnections.get(receiverName);//add to protocol queue if not connected
        if(connection != null)
        {
            connection.send(text);
        }
        else
        {
            ProtocolQueue pq = new ProtocolQueue(senderName, receiverName, text);
            try
            {
                pq.addToQueue();//TODO If successful send delivered to server notification to sender
            } 
            catch (SQLException ex)
            {
                Logger.getLogger(ConnectionSwitch.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void removeConnection(ServerConnectionHandler conn)
    {
        activeConnections.remove(conn.getUsername());
    }
    
}
