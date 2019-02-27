package network.client.protocol;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.client.ClientConnectionHandler;
import network.protocol.Protocol;
import network.protocol.ProtocolParameters;

public class ClientLoginProtocol extends Protocol
{

    public static final String HEAD_LOGIN_REQUEST = "LOGIN_REQUEST";

    public static void loginRequest(String username, String password, ClientConnectionHandler conn) //TODO make boolean for validation
    {
        ProtocolParameters pp = new ProtocolParameters();
            pp.add("Username", username);
            pp.add("Password", password);
        try 
        {                       
            send(HEAD_LOGIN_REQUEST, pp, conn);
        }
        catch (IOException ex) 
        {
            Logger.getLogger(ClientLoginProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ClientLoginProtocol() 
    {
        super();
    }
    
    public static boolean processInput(Scanner reader)
    {
        ProtocolParameters pp = new ProtocolParameters(reader);
        String confirmation = pp.getParameter("Confirmation");
        return confirmation.equals("Accepted");
    }

    
}
