
package network.protocol;

import java.io.IOException;
import network.ConnectionHandler;



public class Protocol 
{
    public static final String PROTOCOL_HEAD = "Head";//Determines which class processes the protocol
    public static final String PROTOCOL_ACTION = "Action";//Determines what action inside the processing class
    public static final String PROTOCOL_END = "END_PROTOCOL";
    
    public static String buildProtocolString(String head, ProtocolParameters parameters)
    {
        parameters.setHead(head);
        String output = parameters.toString() + "\n";
        output += PROTOCOL_END + "\n";
        
        return output;
    }
    
    public static boolean send(String head, ProtocolParameters parameters, ConnectionHandler conn) throws IOException
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
