package network.protocol;

// Imports
import java.io.BufferedReader;
import java.io.IOException;
import network.ConnectionHandler;

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
     */
    public static ProtocolParameters parseInputStream(ConnectionHandler conn) throws IOException
    {
        BufferedReader reader = conn.getInputStream();
        ProtocolParameters pp = new ProtocolParameters();
        String parameter = "";
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
                return null;
            }                         
            if(line.trim().equals(PROTOCOL_END))
                break;
            else
                parameter += line;
            if(line.contains(ProtocolParameters.PARAMETER_DELIMITER))
            {
                String p = parameter.replaceAll(ProtocolParameters.PARAMETER_DELIMITER, "");
                pp.add(p.trim());
                parameter = "";            
            }
        }
        return pp;
    }
}
