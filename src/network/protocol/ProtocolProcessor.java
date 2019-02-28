package network.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import network.ConnectionHandler;
import static network.server.protocol.ServerProtocolProcessor.PROTOCOL_END;

/**
 *
 * @author Tristan
 */
public class ProtocolProcessor 
{
    public static final String PROTOCOL_END = "END_PROTOCOL";
    
    public static String parseInputStream(ConnectionHandler conn) throws IOException
    {
        BufferedReader reader = conn.getInputStream();
        String output = "";
        for(;;)
        {
            String line;
            try
            {
                line = reader.readLine() + "\n";
            } 
            catch (IOException ex)
            {
                System.out.println("Connection Closed");
                conn.close();
                return "FAILED";
            }
            
            if(line.trim().equals(PROTOCOL_END))
                break;
            else
                output += line;
        }
        return output;
    }
}
