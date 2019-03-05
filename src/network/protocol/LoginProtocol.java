package network.protocol;

import database.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.ConnectionHandler;
import network.client.ClientConnectionHandler;
import static network.protocol.Protocol.send;
import network.server.ConnectionSwitch;
import network.server.ServerConnectionHandler;

/**
 *
 * @author Tristan
 */
public class LoginProtocol extends Protocol
{
    public static final String HEAD = "LOGIN";
    public static final String ACTION_REQUEST = "REQUEST";
    public static final String ACTION_RESPONSE = "RESPONSE";
    public static final String[] PARAMETER_NAMES_RESPONSE = new String[]{"Confirmation", "Message"};
    public static final String[] PARAMETER_NAMES_REQUEST = new String[]{"Username", "Password"};
    

    public LoginProtocol() 
    {
        super();
    }
    
    public static boolean processInput(ProtocolParameters pp, ConnectionHandler conn, boolean server)
    {
        if(pp.getParameter(PROTOCOL_ACTION).equals(ACTION_REQUEST) && server)
            return processServerLogin(pp, conn);
        else if(pp.getParameter(PROTOCOL_ACTION).equals(ACTION_RESPONSE) && !server)
            return processClientLoginResponse(pp);
        return false;
    }
    
    public static void loginRequest(String username, String password, ClientConnectionHandler conn) //TODO make boolean for validation
    {
        ProtocolParameters pp = new ProtocolParameters();
        pp.add(PROTOCOL_ACTION, ACTION_REQUEST);
        pp.add("Username", username);
        pp.add("Password", password);
        try 
        {                       
            send(HEAD, pp, conn);
        }
        catch (IOException ex) 
        {
            Logger.getLogger(LoginProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static boolean processServerLogin(ProtocolParameters pp, ConnectionHandler conn)
    {
        try 
        {         
            String username = pp.getParameter("Username");
            String password = pp.getParameter("Password");
            conn.setUsername(username);
            User user = new User(username, password);
            
            ProtocolParameters responsePp = new ProtocolParameters();           
            responsePp.add(PROTOCOL_ACTION, ACTION_RESPONSE);
            if(user.authenticateLogin() && ConnectionSwitch.addConnection((ServerConnectionHandler) conn)) //Checks username and password and if the user is currently logged in
            {
                conn.setUsername(username);
                responsePp.add("Confirmation", "Accepted");
                responsePp.add("Message", "Login Accepted");
                
                send(HEAD, responsePp, conn);
                return true;
            }
            responsePp.add("Confirmation", "Declined");
            responsePp.add("Message", "Login details incorrect");
            
            send(HEAD, responsePp, conn);
            
        }
        catch (IOException ex)
        {
            Logger.getLogger(LoginProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private static boolean processClientLoginResponse(ProtocolParameters pp)
    {
        String confirmation = pp.getParameter("Confirmation");
        return confirmation.equals("Accepted");
    }
    
    
     
}
