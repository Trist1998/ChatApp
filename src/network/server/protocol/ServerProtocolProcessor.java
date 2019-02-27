
package network.server.protocol;

import network.server.protocol.ServerMessageProtocol;
import network.server.protocol.ServerLoginProtocol;
import network.client.protocol.ClientMessageProtocol;
import network.client.protocol.ClientLoginProtocol;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import network.server.ServerConnectionHandler;

public class ServerProtocolProcessor 
{

    public static final String PROTOCOL_END = "END_PROTOCOL";
    
    public static String parseInputStream(BufferedReader reader) throws IOException
    {
        String output = "";
        for(;;)
        {
                    
            String line =  reader.readLine() + "\n";
            if(line.trim().equals(PROTOCOL_END))
                break;
            else
                output += line;
        }
        return output;
    }

    public static void processServerInputStream(BufferedReader br) throws IOException 
    {
        String protocol = parseInputStream(br);
        Scanner reader = new Scanner(protocol);        
        String head = reader.nextLine();
        System.out.println("We got data with the header of " + head);
        if(head.trim().equals(ServerMessageProtocol.HEAD_IDENTIFIER))
            runServerMessageInputProcess(reader);
    }
    
    public static void processClientInputStream(BufferedReader br) throws IOException 
    {
        String protocol = parseInputStream(br);
        Scanner reader = new Scanner(protocol);        
        String head = reader.nextLine();
        
        if(head.trim().equals(ClientMessageProtocol.HEAD_IDENTIFIER))
            runClientMessageInputProcess(reader);    
    }
    
    private static void runClientMessageInputProcess(Scanner reader)
    {
            new Thread(new Runnable()
            { 
                public void run()
                {
                    ServerMessageProtocol.processInput(reader);
                    reader.close();
                }
            }
            ).start();   
    }
    private static void runServerMessageInputProcess(Scanner reader)
    {
            new Thread(new Runnable()
            { 
                public void run()
                {
                    ServerMessageProtocol.processInput(reader);
                    reader.close();
                }
            }
            ).start();   
    }
    
    public static boolean processLogin(BufferedReader br, ServerConnectionHandler conn) throws IOException 
    {
        String protocol = parseInputStream(br);
        System.out.println(protocol);
        Scanner reader = new Scanner(protocol);
        
        String head = reader.nextLine();
        if(head.trim().equals(ClientLoginProtocol.HEAD_LOGIN_REQUEST))
        {
            if(ServerLoginProtocol.processInput(reader, conn))
            {
                return true;
            }          
        }
        return false;
    }
}
