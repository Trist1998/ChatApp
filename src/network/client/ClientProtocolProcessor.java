package network.client;

// Imports
import file.FileNetworkManager;
import network.protocol.NetworkMessageListener;
import java.io.IOException;
import network.ConnectionHandler;
import network.protocol.LoginNetworkManager;
import network.protocol.MessageNetworkManager;
import network.protocol.UserCreationNetworkManager;
import network.protocol.ProtocolParameters;

/**
 * 
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class ClientProtocolProcessor extends NetworkMessageListener
{

    public static void processInputStream(ClientConnectionHandler conn) throws IOException 
    {
        ProtocolParameters pp  = parseInputStream(conn);  
        String head = pp.getHead();
        if(head.equals(MessageNetworkManager.HEAD))
            runMessageInputProcess(pp, conn);
        else if(head.equals(FileNetworkManager.HEAD))
                runFileInputProcess(pp, conn);
    }
    
    private static void runMessageInputProcess(ProtocolParameters pp, ConnectionHandler conn)
    {
            new Thread(new Runnable()
            { 
                public void run()
                {
                    MessageNetworkManager.processInput(pp, conn);
                }
            }
            ).start();   
    }
    
    private static void runFileInputProcess(ProtocolParameters pp, ConnectionHandler conn)
    {
            new Thread(new Runnable()
            { 
                public void run()
                {
                    FileNetworkManager.processInput(pp, conn);
                }
            }
            ).start();   
    }
  
    public static boolean processLogin(ClientConnectionHandler conn) throws IOException 
    {
        ProtocolParameters pp = parseInputStream(conn);
      
        String head = pp.getHead();
        if(head.equals(LoginNetworkManager.HEAD))
        {           
            return LoginNetworkManager.processInput(pp, conn, false);
        }
        return false;
    }
    
    public static boolean processNewUser(ClientConnectionHandler conn) throws IOException
    {
        ProtocolParameters pp = parseInputStream(conn);
      
        String head = pp.getHead();
        if(head.equals(UserCreationNetworkManager.HEAD))
        {
            return UserCreationNetworkManager.processInput(pp);
        }
        return false;
    }
                   
}
