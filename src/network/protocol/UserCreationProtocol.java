package network.protocol;

import database.PasswordHelper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.client.ClientNetworkManager;

/**
 *
 * @author Tristan
 */
public class UserCreationProtocol extends Protocol
{
    public static final String HEAD = "CREATE_USER";
    public boolean createUser(String username, String password)
    {
        ProtocolParameters pp = new ProtocolParameters();
        pp.add("username", username);
        pp.add("password", new PasswordHelper().hash(password));
        
        try
        {
            send(HEAD, pp, ClientNetworkManager.getConnection());
            return true;
        } 
        catch (IOException ex)
        {
            Logger.getLogger(UserCreationProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
