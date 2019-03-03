
package network.server.protocol;


import database.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.server.ServerConnectionHandler;
import network.protocol.Protocol;
import static network.protocol.Protocol.buildProtocolString;
import network.protocol.ProtocolParameters;
import network.server.ConnectionSwitch;

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
    
    public static boolean processInput(ProtocolParameters pp, ServerConnectionHandler conn)
    {
        try 
        {
            String username = pp.getParameter("Username");
            String password = pp.getParameter("Password");
            conn.setUsername(username);
            ProtocolParameters responsePp = new ProtocolParameters();
            User user = new User(username, password);
            
            if(user.authenticateLogin() && ConnectionSwitch.addConnection(conn)) //Checks username and password and if the user is currently logged in
            {
                conn.setUsername(username);
                responsePp.add("Confirmation", "Accepted");
                responsePp.add("Message", "Login Accepted");
                
                send(HEAD_LOGIN_RESPONSE, responsePp, conn);
                return true;
            }
            responsePp.add("Confirmation", "Declined");
            responsePp.add("Message", "Login details incorrect");
            
            send(HEAD_LOGIN_RESPONSE, responsePp, conn);
            
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
