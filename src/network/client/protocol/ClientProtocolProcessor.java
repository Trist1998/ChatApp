
package network.client.protocol;

import network.protocol.ProtocolProcessor;
import java.io.IOException;
import java.util.Scanner;
import network.client.ClientConnectionHandler;
import network.protocol.ProtocolParameters;
import network.server.protocol.ServerLoginProtocol;

public class ClientProtocolProcessor extends ProtocolProcessor
{

    public static void processInputStream(ClientConnectionHandler conn) throws IOException 
    {
        String protocol = parseInputStream(conn);
        Scanner reader = new Scanner(protocol);
        ProtocolParameters pp = new ProtocolParameters(reader);
        reader.close();   
        String head = reader.nextLine();
        
        if(head.trim().equals(ClientMessageProtocol.HEAD_IDENTIFIER))
            runMessageInputProcess(pp);    
    }
    
    private static void runMessageInputProcess(ProtocolParameters pp)
    {
            new Thread(new Runnable()
            { 
                public void run()
                {
                    ClientMessageProtocol.processInput(pp);
                }
            }
            ).start();   
    }
  
    public static boolean processLogin(ClientConnectionHandler conn) throws IOException 
    {
        String protocol = parseInputStream(conn);
        Scanner reader = new Scanner(protocol);
        ProtocolParameters pp = new ProtocolParameters(reader);
        reader.close();
        
        String head = pp.getHead();
        if(head.equals(ServerLoginProtocol.HEAD_LOGIN_RESPONSE))
        {
            ClientLoginProtocol.processInput(pp);
            return true;
        }
        return false;
    }
}
