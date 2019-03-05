package network.protocol;

// Imports
import database.PasswordHelper;
import database.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.client.ClientConnectionHandler;
import network.client.ClientNetworkManager;
import network.server.ServerConnectionHandler;

/**
 * UserCreationProtocol class is used to in creation of users.
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class UserCreationNetworkManager extends NetworkMessageHandler
{
    // Variables
    public static final String HEAD = "CREATE_USER";
    public static final String ACTION_REQUEST = "REQUEST";
    public static final String ACTION_RESPONSE = "RESPONSE";
    
    /**
     * Attempts to create user.
     * @param username
     * @param password
     * @return 
     */
    public static boolean createUser(String username, String password)
    {
        ProtocolParameters pp = new ProtocolParameters();
        pp.add(NetworkMessageHandler.PROTOCOL_ACTION, ACTION_REQUEST);
        pp.add("username", username);
        pp.add("password", new PasswordHelper().clientPasswordHash(password));
        
        try
        {
            ClientConnectionHandler conn = ClientNetworkManager.getNewConnection(ClientNetworkManager.SERVER_PORT);
            if(conn != null)
            {
                send(HEAD, pp, conn);            
                return true;//ClientProtocolProcessor.processNewUser(conn);
            }
        } 
        catch (IOException ex)
        {
            Logger.getLogger(UserCreationNetworkManager.class.getName()).log(Level.SEVERE, null, ex);             
        }
        return false; 
    }
    
    /**
     * Processes protocol parameters.
     * @param pp
     * @param conn 
     */
    public static void processInput(ProtocolParameters pp, ServerConnectionHandler conn)
    {
        User user = new User(pp.getParameter("username"), pp.getParameter("password"));
        ProtocolParameters resPP = new ProtocolParameters();
        resPP.add(NetworkMessageHandler.PROTOCOL_HEAD, HEAD);
        resPP.add(NetworkMessageHandler.PROTOCOL_ACTION, ACTION_RESPONSE);
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
    
    /**
     * 
     * @param pp
     * @return 
     */
    public static boolean processInput(ProtocolParameters pp)
    {
        return pp.getParameter("Confirmation").equals("Accepted");
    }
}
