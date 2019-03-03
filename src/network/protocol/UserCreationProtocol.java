package network.protocol;

import database.PasswordHelper;
import database.ServerDatabaseConnection;
import database.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.client.ClientConnectionHandler;
import network.client.ClientNetworkManager;
import network.server.ServerConnectionHandler;

/**
 *
 * @author Tristan
 */
public class UserCreationProtocol extends Protocol
{
    public static final String HEAD = "CREATE_USER";
    public static boolean createUser(String username, String password)
    {
        ProtocolParameters pp = new ProtocolParameters();
        pp.add("username", username);
        pp.add("password", new PasswordHelper().clientPasswordHash(password));
        
        try
        {
            ClientConnectionHandler conn = ClientNetworkManager.getNewConnection(ClientNetworkManager.SERVER_PORT);
            send(HEAD, pp, conn);
            return true;
        } 
        catch (IOException ex)
        {
            Logger.getLogger(UserCreationProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static void processInput(ProtocolParameters pp, ServerConnectionHandler conn)
    {
        User user = new User(pp.getParameter("username"), pp.getParameter("password"));
        ProtocolParameters resPP = new ProtocolParameters();
        resPP.add("HEAD", "RESPONSE");
        resPP.add("Action", "CREATE_USER");
        if(user.createUser())
        {          
            resPP.add("Confirmation", "Accepted");
        }
        else
        {
            resPP.add("Confirmation", "Declined");
            resPP.add("Message", "Something wrong XD");
        }
        conn.send(resPP.toString());           
    }
}
