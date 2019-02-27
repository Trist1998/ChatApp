
package network.server.protocol;


import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.client.protocol.ClientLoginProtocol;
import network.server.ServerConnectionHandler;
import network.protocol.Protocol;
import static network.protocol.Protocol.buildProtocolString;
import network.protocol.ProtocolParameters;

/**
 * @author Tristan
 * 
 * The message processed is different to the message received due to client server relationship
 */
public class ServerLoginProtocol extends Protocol
{
    public static final String[] PARAMETER_NAMES_SEND = new String[]{"Confirmation", "Message"};
    enum PARAMETER_NAMES{Confirmation, Message}
    public static final String[] PARAMETER_NAMES_RECEIVE = new String[]{"Username", "Password"};
    public static final String HEAD_LOGIN_RESPONSE = "LOGIN_RESPONSE";

    public ServerLoginProtocol() 
    {
        super();
    }
    
    public static boolean processInput(Scanner reader, ServerConnectionHandler conn)
    {
        try 
        {
            ProtocolParameters pp = new ProtocolParameters(reader);
            String username = pp.getParameter("Username");
            conn.setUsername(username);
            ProtocolParameters npp = new ProtocolParameters();
            if(true) //TODO check username and password
            {
                
                npp.add("Confirmation", "Accepted");
                npp.add("Message", "Login Accepted");
                
                send(HEAD_LOGIN_RESPONSE, npp, conn);
                return true;
            }
            npp.add("Confirmation", "Declined");
            npp.add("Message", "Login details incorrect");
            
            send(HEAD_LOGIN_RESPONSE, npp, conn);
            
        }
        catch (IOException ex)
        {
            Logger.getLogger(ServerLoginProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static boolean send(String head, ProtocolParameters parameters, ServerConnectionHandler conn) throws IOException
    {
        String output = buildProtocolString(head, parameters);
        conn.send(output);
        return true;
    }
    
}
