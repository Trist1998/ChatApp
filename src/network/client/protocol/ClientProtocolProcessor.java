
package network.client.protocol;

import network.protocol.ProtocolProcessor;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import network.client.ClientConnectionHandler;
import network.server.protocol.ServerLoginProtocol;

public class ClientProtocolProcessor extends ProtocolProcessor
{

    public static void processInputStream(BufferedReader br) throws IOException 
    {
        String protocol = parseInputStream(br);
        Scanner reader = new Scanner(protocol);        
        String head = reader.nextLine();
        
        if(head.trim().equals(ClientMessageProtocol.HEAD_IDENTIFIER))
            runMessageInputProcess(reader);    
    }
    
    private static void runMessageInputProcess(Scanner reader)
    {
            new Thread(new Runnable()
            { 
                public void run()
                {
                    ClientMessageProtocol.processInput(reader);
                    reader.close();
                }
            }
            ).start();   
    }
  
    public static boolean processLogin(BufferedReader br, ClientConnectionHandler conn) throws IOException 
    {
        String protocol = parseInputStream(br);
        Scanner reader = new Scanner(protocol);
        String head = reader.nextLine();
        if(head.equals(ServerLoginProtocol.HEAD_LOGIN_RESPONSE))
        {
            ClientLoginProtocol.processInput(reader);
            return true;
        }
        return false;
    }
}
