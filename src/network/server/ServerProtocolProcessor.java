package network.server;

// Imports
import file.FileNetworkManager;
import java.io.IOException;
import network.ConnectionHandler;
import network.protocol.LoginNetworkManager;
import network.protocol.MessageNetworkManager;
import network.protocol.NetworkMessageListener;
import network.protocol.ProtocolParameters;
import network.protocol.UserCreationNetworkManager;


/**
 * ServerProtocolProcessor class processes protocols on server side.
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class ServerProtocolProcessor extends NetworkMessageListener 
{

    /**
     * Listens for incoming protocol messages from client and delegates to corresponding class.
     * @param conn
     * @throws IOException
     */
    public static void processServerInputStream(ConnectionHandler conn) throws IOException 
    {
        ProtocolParameters pp = parseInputStream(conn);
        
        if (pp != null) 
        {
            String head = pp.getHead().trim();
            System.out.println("We got data with the header of " + head);
            if (head.equals(MessageNetworkManager.HEAD))
            {
                runServerMessageInputProcess(pp, conn);
            }
            else if(head.equals(FileNetworkManager.HEAD))
                runServerFileInputProcess(pp, conn);
        }
    }

    /**
     * Creates new threads to processes protocol.
     * @param pp
     * @param conn
     */
    private static void runServerMessageInputProcess(ProtocolParameters pp, ConnectionHandler conn) 
    {
        new Thread(new Runnable() 
        {
            public void run() 
            {
                MessageNetworkManager.processInput(pp, conn);
            }
        }).start();
    }
    
    private static void runServerFileInputProcess(ProtocolParameters pp, ConnectionHandler conn) 
    {
        new Thread(new Runnable() 
        {
            public void run() 
            {
                FileNetworkManager.processInput(pp, conn);
            }
        }).start();
    }

    /**
     * Used for login and create protocols where connection is terminated if incorrect.
     * @param conn
     * @return
     * @throws IOException
     */
    public static boolean processInitialConnection(ServerConnectionHandler conn) throws IOException 
    {
        ProtocolParameters pp = parseInputStream(conn);
        
        if (pp != null) 
        {
            String head = pp.getHead();

            if (head.equals(LoginNetworkManager.HEAD)) 
            {
                return LoginNetworkManager.processInput(pp, conn, true);
            } 
            else if (head.equals(UserCreationNetworkManager.HEAD)) 
            {
                UserCreationNetworkManager.processInput(pp, conn);
            }
        }
        return false;
    }
}
