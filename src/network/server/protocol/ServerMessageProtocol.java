
package network.server.protocol;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.server.ConnectionSwitch;
import network.protocol.Protocol;
import network.protocol.ProtocolParameters;
import static network.protocol.Protocol.buildProtocolString;

public class ServerMessageProtocol extends Protocol
{
    public static final String HEAD_IDENTIFIER = "MSG";
    
    public static boolean processInput(ProtocolParameters pp)
    {
        try 
        {
            forwardMessage(HEAD_IDENTIFIER, pp);
            return true;
        } 
        catch (IOException ex)
        {
            Logger.getLogger(ServerMessageProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return false; 
    }
    
    private static boolean forwardMessage(String head, ProtocolParameters pp) throws IOException
    {
        String output = buildProtocolString(head, pp);
        ConnectionSwitch.switchProtocol(pp.getParameter("Receiver"), output);
        return true;
    }
}
