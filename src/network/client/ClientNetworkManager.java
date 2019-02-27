
package network.client;

import java.io.IOException;
import java.net.Socket;


public class ClientNetworkManager 
{
    private static ClientConnectionHandler connection;
    private static String username;
    public static final int SERVER_PORT = 9999;
    public static final String host = "localhost";

    public static void openSocketClient(int PortNumber) 
    {
        Socket myClient; // Declare Client's socket
        try 
        {
            myClient = new Socket(host, PortNumber); // Port number must be >1023
            new Thread(new ClientConnectionHandler(myClient)).start();    
        } 
        catch (IOException e) 
        {
            System.out.println(e); // Error message.
        }
    }

    public static ClientConnectionHandler getConnection()
    {
        return connection;
    }

    public static void setConnection(ClientConnectionHandler connection)
    {
        ClientNetworkManager.connection = connection;
    }

    public static String getUsername() 
    {
       return username;
    }
    
    public static void setUsername(String username)
    {
        ClientNetworkManager.username = username;
    }

    public static boolean isLoggedIn() 
    {
        return connection.isLoggedIn();
    }
    
    
}
