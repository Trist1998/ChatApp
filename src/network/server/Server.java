
package network.server;
import java.io.IOException;
import java.net.*;
import network.client.ClientNetworkManager;
public class Server 
{
    public static void main(String[] args) 
    {
        openSocketServer(ClientNetworkManager.SERVER_PORT);
    }
    
    public static void openSocketServer(int portNumber) 
    {     
        while(true)
        try 
        {
            ServerSocket myService; // Declare Server's Main socket
            myService = new ServerSocket(portNumber); // Port number must be > 1023
            System.out.println("Waiting for connection");
            new Thread(new ServerConnectionHandler(myService)).start();    
            System.out.println("Connection made");  
            myService.close();
        } 
        catch (IOException e)
        {
            System.out.println(e); // Error message.
        }
    }
}