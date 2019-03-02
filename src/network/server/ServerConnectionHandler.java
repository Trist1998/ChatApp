
package network.server;

import database.ServerDatabaseConnection;
import java.io.IOException;
import java.net.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.ConnectionHandler;
import network.server.protocol.ServerProtocolProcessor;

public class ServerConnectionHandler extends ConnectionHandler
{
    private String username;
    
    public ServerConnectionHandler(ServerSocket ss) throws IOException, SQLException
    {
        super(ss.accept());
        username = "";
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
            if(ServerProtocolProcessor.processInitialConnection(this))
            {
                System.out.println("Waiting for data from " + username);
                while(isConnected())
                {
                    ServerProtocolProcessor.processServerInputStream(this);
                }
            }
            ConnectionSwitch.removeConnection(this);
            close();          
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ServerConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setUsername(String username) 
    {
        this.username = username;
    }
    
}
