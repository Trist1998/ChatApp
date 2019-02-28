
package network.client;

import database.PasswordHelper;
import java.io.IOException;
import java.net.Socket;


public class ClientNetworkManager 
{
    public static final int SERVER_PORT = 9999;
    public static final String HOST_NAME = "localhost";
    
    private static ClientConnectionHandler connection;
    private static String username;
    private static String hashedPassword;
    private static boolean loggedIn;

    public static void openSocketClient(int portNumber, String username, String password) 
    {
        ClientNetworkManager.username = username;
        hashedPassword = new PasswordHelper().clientPasswordHash(password);
        Socket myClient; // Declare Client's socket
        try 
        {
            myClient = new Socket(HOST_NAME, portNumber); // Port number must be >1023
            new Thread(new ClientConnectionHandler(myClient, username, hashedPassword)).start();    
        } 
        catch (IOException e) 
        {
            System.out.println(e); // Error message.
        }
    }
    
    public static ClientConnectionHandler getNewConnection(int portNumber)
    {
        try 
        {
            Socket myClient = new Socket(HOST_NAME, portNumber); // Port number must be >1023
            return new ClientConnectionHandler(myClient, username, hashedPassword);    
        } 
        catch (IOException e) 
        {
            System.out.println(e); // Error message.
        }
        return null;
    }

    public static ClientConnectionHandler getConnection()
    {
        if(connection != null)
            openSocketClient(ClientNetworkManager.SERVER_PORT, username, hashedPassword);
            
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
    
    public static void login(String username, String password)
    {
        loggedIn = false;
        openSocketClient(SERVER_PORT, username, password);
    }
    
    public static void loginSuccesful()
    {
        loggedIn = true;
    }
    
    public static boolean isLoggedIn() 
    {
        return loggedIn;
    }
    
    public static void connectionFailed()
    {
        connection = null;
    }
    
    public static boolean hasConnectionFailed()
    {
        return connection == null;
    }
      
}
