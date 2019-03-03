
package network.client;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.ConnectionHandler;
import network.client.protocol.ClientLoginProtocol;
import network.client.protocol.ClientProtocolProcessor;

public class ClientConnectionHandler extends ConnectionHandler
{
    private String username;
    private String password;
    
    public ClientConnectionHandler(Socket socket,String username, String password) throws IOException
    {
        super(socket);
        this.username = username;
        this.password = password;
    }
    
    public ClientConnectionHandler(Socket socket) throws IOException
    {
        super(socket);
    }
    
    public String getUsername()
    {
        return username;
    }

    @Override
    public void run() 
    {
        try 
        {      
            ClientLoginProtocol.loginRequest(username, password, this);
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
