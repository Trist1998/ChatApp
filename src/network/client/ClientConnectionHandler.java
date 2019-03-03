
package network.client;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.ConnectionHandler;
import network.protocol.LoginProtocol;

public class ClientConnectionHandler extends ConnectionHandler
{
    private String password;
    
    public ClientConnectionHandler(Socket socket,String username, String password) throws IOException
    {
        super(socket);
        setUsername(username);
        this.password = password;
    }
    
    public ClientConnectionHandler(Socket socket) throws IOException
    {
        super(socket);
    }

    @Override
    public void run() 
    {
        try 
        {      
            LoginProtocol.loginRequest(getUsername(), password, this);
            if(ClientProtocolProcessor.processLogin(this))
            {
                ClientNetworkManager.loginSuccesful();
                while(isConnected())
                {
                    ClientProtocolProcessor.processInputStream(this);              
                }
            }
            ClientNetworkManager.connectionFailed();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ClientConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
