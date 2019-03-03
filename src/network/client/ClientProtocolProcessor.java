
package network.client;

import network.protocol.ProtocolProcessor;
import java.io.IOException;
import network.protocol.LoginProtocol;
import network.protocol.MessageProtocol;
import network.protocol.ProtocolParameters;

public class ClientProtocolProcessor extends ProtocolProcessor
{

    public static void processInputStream(ClientConnectionHandler conn) throws IOException 
    {
        ProtocolParameters pp  = parseInputStream(conn);  
        String head = pp.getHead();
        if(head.equals(MessageProtocol.HEAD))
            runMessageInputProcess(pp);    
    }
    
    private static void runMessageInputProcess(ProtocolParameters pp)
    {
            new Thread(new Runnable()
            { 
                public void run()
                {
                    MessageProtocol.processInput(pp);
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
}
