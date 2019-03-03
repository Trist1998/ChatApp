
package network.server;

import database.ProtocolQueue;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.ProtocolMessage;
import network.ConnectionHandler;
import network.client.ClientConnectionHandler;
import network.protocol.MessageProtocol;

public class ServerConnectionHandler extends ConnectionHandler
{
    
    public ServerConnectionHandler(ServerSocket ss) throws IOException, SQLException
    {
        super(ss.accept());
    }

    @Override
    public void run() 
    {
        try 
        {
            if(ServerProtocolProcessor.processInitialConnection(this))
            {
                MessageProtocol.retrieveStoredMessages(getUsername(), this);
                System.out.println("Waiting for data from " + getUsername());
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
        } catch (SQLException ex)
        {
            Logger.getLogger(ServerConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public boolean send(ProtocolMessage protocol) throws SQLException 
    {
        BufferedWriter outputStream = getOutputStream();
        synchronized(outputStream)
        {        
            try 
            {
                outputStream.write(protocol.getText());//Remember to put + ProtocolProcessor.PROTOCOL_END + "\n"; where the String is built
                outputStream.flush();
                return true;
            } 
            catch (IOException ex) 
            {
                if(!protocol.isAlreadySaved())
                    new ProtocolQueue(protocol).addToQueue();
                Logger.getLogger(ClientConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }      
    }
    
}
