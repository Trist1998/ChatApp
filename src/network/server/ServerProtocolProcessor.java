
package network.server;

import java.io.IOException;
import network.ConnectionHandler;
import network.protocol.LoginProtocol;
import network.protocol.MessageProtocol;
import network.protocol.ProtocolParameters;
import network.protocol.ProtocolProcessor;
import network.protocol.UserCreationProtocol;

public class ServerProtocolProcessor extends ProtocolProcessor
{
    
    public static void processServerInputStream(ConnectionHandler conn) throws IOException 
    {
        ProtocolParameters pp = parseInputStream(conn);
        if(pp != null)
        {
            String head = pp.getHead();
            System.out.println("We got data with the header of " + head);
            if(head.trim().equals(MessageProtocol.HEAD))
                runServerMessageInputProcess(pp, conn);
        }
    }
    
    
    private static void runServerMessageInputProcess(ProtocolParameters pp, ConnectionHandler conn)
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
    
    public static boolean processInitialConnection(ServerConnectionHandler conn) throws IOException 
    {
        ProtocolParameters pp = parseInputStream(conn);
        if(pp != null)
        {
            String head = pp.getHead();
            
            if(head.equals(LoginProtocol.HEAD))
            {
                return LoginProtocol.processInput(pp, conn, true);    
            }
            else if(head.equals(UserCreationProtocol.HEAD))
            {
                UserCreationProtocol.processInput(pp, conn);
            }           
        }
        return false;
    }
}
