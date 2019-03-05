package network.client;

// Imports
import database.PasswordHelper;
import java.io.IOException;
import java.net.Socket;

/**
 * ClientNetworkManager class is used to establish a socket and connection. 
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class ClientNetworkManager 
{
    public static final int SERVER_PORT = 9999;
    public static final String HOST_NAME = "localhost";
    
    private static ClientConnectionHandler connection;
    private static String username;
    private static String hashedPassword;
    private static boolean loggedIn;

    /**
     * Creates a socket on port number, sends it to connection handler.
     * @param portNumber
     * @param username
     * @param password 
     */
    public static void openSocketClient(int portNumber, String username, String password) 
    {      
        try 
        {
            Socket myClient = new Socket(HOST_NAME, portNumber);
            connection = new ClientConnectionHandler(myClient, username, hashedPassword);
            new Thread(connection).start();
        } 
        catch (IOException e) 
        {
            System.out.println(e);
        }
    }
    
    //Used mainly for user creation protocol
    public static ClientConnectionHandler getNewConnection(int portNumber)
    {
        try 
        {
            Socket myClient = new Socket(HOST_NAME, portNumber);
            return new ClientConnectionHandler(myClient, username, hashedPassword);    
        } 
        catch (IOException e) 
        {
            System.out.println(e);
        }
        return null;
    }

    public static ClientConnectionHandler getConnection()
    {
        if(connection == null)
        {
            openSocketAndWaitForConfirmation();
        }
            
            
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

    public static boolean login(String username, String password)
    {      
        ClientNetworkManager.username = username;
        hashedPassword = new PasswordHelper().clientPasswordHash(password);
        return openSocketAndWaitForConfirmation();
    }
    
    private static boolean openSocketAndWaitForConfirmation()
    {
        loggedIn = false;
        openSocketClient(SERVER_PORT, username, hashedPassword);      
        while(!isLoggedIn())
        {
            if(hasConnectionFailed())
            {
                return false;
            }               
        }
        return true;   
    }
    
    public synchronized static void loginSuccesful()
    {
        System.out.println("Login successful");
        loggedIn = true;
    }
    
    public synchronized static boolean isLoggedIn() 
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
