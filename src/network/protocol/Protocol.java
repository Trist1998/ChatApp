
package network.protocol;

import java.io.IOException;
import network.client.ClientConnectionHandler;



public class Protocol 
{
    public static final String PROTOCOL_END = "END_PROTOCOL";
    
    public static String buildProtocolString(String head, ProtocolParameters parameters)
    {
        parameters.setHead(head);
        String output = parameters.toString() + "\n";
        output += PROTOCOL_END + "\n";
        
        return output;
    }
    
    public static boolean send(String head, ProtocolParameters parameters, ClientConnectionHandler conn) throws IOException
    {
        String output = buildProtocolString(head, parameters);
        conn.send(output);
        return true;
    }
    
    public static boolean processInput(ProtocolParameters pp)//reader.nextLine() should return the first parameter
    {
        System.out.println(pp.toString());  
        return true;
    }
    
}
