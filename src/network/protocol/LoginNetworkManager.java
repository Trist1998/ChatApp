package network.protocol;

// Imports
import database.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.ConnectionHandler;
import network.client.ClientConnectionHandler;
import network.server.ConnectionSwitch;
import network.server.ServerConnectionHandler;

/**
 * LoginProtocol class handles login protocols.
 *
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class LoginNetworkManager extends NetworkMessageHandler 
{

    // Variables
    public static final String HEAD = "LOGIN";
    public static final String ACTION_REQUEST = "REQUEST";
    public static final String ACTION_RESPONSE = "RESPONSE";
    public static final String LOGIN_ACCEPTED = "ACCEPTED";
    public static final String[] PARAMETER_NAMES_REQUEST = new String[]{"Username", "Password"};

    /**
     * Parameterized Constructor for LoginProtocol class. Creates new protocol.
     */
    public LoginNetworkManager() 
    {
        super();
    }

    /**
     * Takes in protocol parameters, determines whether server or client and
     * processes them.
     *
     * @param pp
     * @param conn
     * @param server
     * @return
     */
    public static boolean processInput(ProtocolParameters pp, ConnectionHandler conn, boolean server) 
    {
        if (pp.getParameter(PROTOCOL_ACTION).equals(ACTION_REQUEST) && server)
        {
            return processServerLogin(pp, conn);
        } 
        else if (pp.getParameter(PROTOCOL_ACTION).equals(ACTION_RESPONSE) && !server) 
        {
            return processClientLoginResponse(pp);
        }
        return false;
    }

    /**
     * Builds a login request and sends it to server.
     *
     * @param username
     * @param password
     * @param conn
     */
    public static void loginRequest(String username, String password, ClientConnectionHandler conn) //TODO make boolean for validation
    {
        ProtocolParameters pp = new ProtocolParameters(HEAD, ACTION_REQUEST);
        pp.add("Username", username);
        pp.add("Password", password);
        send(pp, conn);
    }

    /**
     * Receives and processes login request.
     *
     * @param pp
     * @param conn
     * @return
     */
    private static boolean processServerLogin(ProtocolParameters pp, ConnectionHandler conn) 
    {
        String username = pp.getParameter("Username"); 
        String password = pp.getParameter("Password");
        conn.setUsername(username);
        User user = new User(username, password);
        ProtocolParameters responsePp = new ProtocolParameters(HEAD, ACTION_RESPONSE);
        if (user.authenticateLogin() && ConnectionSwitch.addConnection((ServerConnectionHandler) conn)) //Checks username and password and if the user is currently logged in
        {
            conn.setUsername(username);
            responsePp.add("Confirmation", LOGIN_ACCEPTED);
            responsePp.add("Message", "Login Accepted");
            
            send(responsePp, conn);
            return true;
        }
        responsePp.add("Confirmation", "Declined");
        responsePp.add("Message", "Login details incorrect");
        send(responsePp, conn);
        return false;
    }

    /**
     * Confirms that user is logged in successfully.
     *
     * @param pp
     * @return
     */
    private static boolean processClientLoginResponse(ProtocolParameters pp) 
    {
        String confirmation = pp.getParameter("Confirmation");
        return confirmation.equals(LOGIN_ACCEPTED);
    }

}
