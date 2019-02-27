
package network.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.server.protocol.ServerProtocolProcessor;

public class ServerConnectionHandler implements Runnable
{
    private Socket socket;
    private BufferedReader inputStream;
    private BufferedWriter outputStream;
    private String username;
    
    public ServerConnectionHandler(ServerSocket ss) throws IOException
    {
        socket = ss.accept();  
        outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
            if(ServerProtocolProcessor.processInitialConnection(inputStream, this))
            {
                ConnectionSwitch.addConnection(this); 
                System.out.println("Waiting for data from " + username);
                while(socket.isConnected())
                {
                    ServerProtocolProcessor.processServerInputStream(inputStream);              
                }
            }              
            close();          
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ServerConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void send(String protocol) 
    {
        synchronized(outputStream)
        {        
            try 
            {
                outputStream.write(protocol);//Remember to put + ProtocolProcessor.PROTOCOL_END + "\n"; where the String is built
                outputStream.flush();
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(ServerConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setUsername(String username) 
    {
        this.username = username;
    }
    
    public void close() throws IOException
    {
        inputStream.close();
        outputStream.close();
        socket.close();
    }
}
