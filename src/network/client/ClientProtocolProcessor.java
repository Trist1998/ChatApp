package network.client;

// Imports
import network.protocol.ProtocolProcessor;
import java.io.IOException;
import network.ConnectionHandler;
import network.protocol.LoginProtocol;
import network.protocol.MessageProtocol;
import network.protocol.ProtocolParameters;
import network.protocol.UserCreationProtocol;

/**
 * 
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class ClientProtocolProcessor extends ProtocolProcessor
{

    public static void processInputStream(ClientConnectionHandler conn) throws IOException 
    {
        ProtocolParameters pp  = parseInputStream(conn);  
        String head = pp.getHead();
        if(head.equals(MessageProtocol.HEAD))
            runMessageInputProcess(pp, conn);    
    }
    
    private static void runMessageInputProcess(ProtocolParameters pp, ConnectionHandler conn)
    {
            new Thread(new Runnable()
            { 
                public void run()
                {
                    MessageProtocol.processInput(pp, conn);
                }
            }
            ).start();   
    }
  
    public static boolean processLogin(ClientConnectionHandler conn) throws IOException 
    {
        ProtocolParameters pp = parseInputStream(conn);
      
        String head = pp.getHead();
        if(head.equals(LoginProtocol.HEAD))
        {           
            return LoginProtocol.processInput(pp, conn, false);
        }
        return false;
    }
    
    public static boolean processNewUser(ClientConnectionHandler conn) throws IOException
    {
        ProtocolParameters pp = parseInputStream(conn);
      
        String head = pp.getHead();
        if(head.equals(UserCreationProtocol.HEAD))
        {
            return UserCreationProtocol.processInput(pp);
        }
        return false;
    }
                   
}
