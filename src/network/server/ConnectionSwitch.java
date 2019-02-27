package network.server;

import java.util.HashMap;

/**
 *
 * @author Tristan
 */
public class ConnectionSwitch
{
    private static HashMap<String, ServerConnectionHandler> activeConnections = new HashMap<>();
    
    public synchronized static void addConnection(ServerConnectionHandler connection)
    {
        if(activeConnections.get(connection.getUsername()) == null)
            activeConnections.put(connection.getUsername(), connection);
        else 
            System.out.println("Already connected pls log out");
    }
    
    public static void switchProtocol(String receiverName, String text)
    {
        ServerConnectionHandler connection = activeConnections.get(receiverName);
        if(connection != null)
        {
            connection.send(text);
        }
        else
            System.out.println(receiverName + " is not connected");
    }
    
}
