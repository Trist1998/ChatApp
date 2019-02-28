package network.server;

import java.util.HashMap;

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
    
    public static void switchProtocol(String receiverName, String text)
    {
        ServerConnectionHandler connection = activeConnections.get(receiverName);//add to protocol queue if not connected
        if(connection != null)
        {
            connection.send(text);
        }
        else
            System.out.println(receiverName + " is not connected");
    }

    public static void removeConnection(ServerConnectionHandler conn)
    {
        activeConnections.remove(conn.getUsername());
    }
    
}
