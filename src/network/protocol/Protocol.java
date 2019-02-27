
package network.protocol;

import java.io.IOException;
import java.util.Scanner;
import network.client.ClientConnectionHandler;



public class Protocol 
{
    public static final String PROTOCOL_END = "END_PROTOCOL";
    
    public static String buildProtocolString(String head, ProtocolParameters parameters)
    {
        String output = head + "\n";
        output += parameters.toString() + "\n";
        output += PROTOCOL_END + "\n";
        
        return output;
    }
    
    public static boolean send(String head, ProtocolParameters parameters, ClientConnectionHandler conn) throws IOException
    {
        String output = buildProtocolString(head, parameters);
        conn.send(output);
        return true;
    }
    
    public static boolean processInput(Scanner reader)//reader.nextLine() should return the first parameter
    {
        while(reader.hasNextLine())
        {
            System.out.println(reader.nextLine());
        }
        
        return true;
    }
    
}
