package network.protocol;

// Imports
import java.io.BufferedReader;
import java.io.IOException;
import network.ConnectionHandler;
import org.json.simple.parser.ParseException;

/**
 * NetworkMessageListener class used to listen to Network messages.
 * @author Tristan Wood, Alex Priscu, Zubair Wieners
 */
public class NetworkMessageListener 
{
    public static final String PROTOCOL_END = "END_PROTOCOL";
    
    /**
     * Listens for incoming protocol and converts to protocol parameters. 
     * @param conn
     * @return
     * @throws IOException 
     * @throws org.json.simple.parser.ParseException 
     */
    public static ProtocolParameters parseInputStream(ConnectionHandler conn) throws IOException, ParseException
    {
        BufferedReader reader = conn.getInputStream();
        return parseInputStream(reader);
    }
    
    public static ProtocolParameters parseInputStream(BufferedReader reader) throws IOException, ParseException
    {
        String parameter = "";
        for(;;)
        {
            String line;
            try
            {
                line = reader.readLine();
                if(line != null)
                    line += "\n";
                else
                    return null;
            } 
            catch (IOException ex)
            {
                System.out.println("File Closed");
                reader.close();
                return null;
            }                         
            if(line.trim().equals(PROTOCOL_END))
                break;
            else
                parameter += line;
        }
        return new ProtocolParameters(parameter);
    }
}
