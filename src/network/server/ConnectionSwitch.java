package network.server;

// Imports
import database.NetworkMessageQueue;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.NetworkMessage;

/**
 * ConnectionSwitch class is a server side object that is used to send messages to receiver's connection handler.
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class ConnectionSwitch
{
    private static HashMap<String, ServerConnectionHandler> activeConnections = new HashMap<>();
    
    /**
     * Adds connection to activeConnections.
     * @param connection
     * @return 
     */
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
    
    /**
     * Pushes messages to receiver's connection.
     * @param message
     * @return 
     */
    public static int switchProtocol(NetworkMessage message)
    {
        ServerConnectionHandler connection = activeConnections.get(message.getReceiverName());//add to protocol queue if not connected
        if(connection != null)
        {
            return connection.send(message);//In this context the text of the message should be the entire protocol
        }
        else
        {
            NetworkMessageQueue pq = new NetworkMessageQueue(message);
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

    /**
     * Removes closed connection from activeConnections.
     * @param conn 
     */
    public static void removeConnection(ServerConnectionHandler conn)
    {
        activeConnections.remove(conn.getUsername());
    }
    
}
