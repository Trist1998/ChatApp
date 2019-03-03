
package network.server.protocol;

import network.client.protocol.ClientLoginProtocol;
import java.io.IOException;
import network.ConnectionHandler;
import network.protocol.ProtocolParameters;
import network.protocol.ProtocolProcessor;
import network.protocol.UserCreationProtocol;
import network.server.ServerConnectionHandler;

public class ServerProtocolProcessor extends ProtocolProcessor
{
    
    public static void processServerInputStream(ConnectionHandler conn) throws IOException 
    {
        ProtocolParameters pp = parseInputStream(conn);
        if(pp != null)
        {
            String head = pp.getHead();
            System.out.println("We got data with the header of " + head);
            if(head.trim().equals(ServerMessageProtocol.HEAD_IDENTIFIER))
                runServerMessageInputProcess(pp);
            //Add response protocol
        }
    }
    
    
    private static void runServerMessageInputProcess(ProtocolParameters pp)
    {
            new Thread(new Runnable()
            { 
                public void run()
                {
                    ServerMessageProtocol.processInput(pp);
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
            
            if(head.equals(ClientLoginProtocol.HEAD_LOGIN_REQUEST))
            {
                return ServerLoginProtocol.processInput(pp, conn);    
            }
            else if(head.equals(UserCreationProtocol.HEAD))
            {
                UserCreationProtocol.processInput(pp,conn);
            }           
        }
        return false;
    }
}
