package network.protocol;

import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author Tristan
 */
public class ProtocolProcessor 
{
    public static String parseInputStream(BufferedReader reader) throws IOException
    {
        String output = "";
        for(;;)
        {
                    
            String line =  reader.readLine() + "\n";
            if(line.trim().equals(Protocol.PROTOCOL_END))
                break;
            else
                output += line;
        }
        return output;
    }
}
