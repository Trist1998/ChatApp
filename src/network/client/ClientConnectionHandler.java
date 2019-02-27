
package network.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.client.protocol.ClientLoginProtocol;
import network.client.protocol.ClientProtocolProcessor;

public class ClientConnectionHandler implements Runnable
{
    private Socket socket;
    private BufferedReader inputStream;
    private BufferedWriter outputStream;
    private String username;
    private String password;
    
    public ClientConnectionHandler(Socket socket,String username, String password) throws IOException
    {
        this.socket = socket;
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.username = username;
        this.password = password;
    }
    
    public ClientConnectionHandler(Socket socket) throws IOException
    {
        this.socket = socket;
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
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
            if(ClientProtocolProcessor.processLogin(inputStream, this))
            {
                ClientNetworkManager.loginSuccesful();
                while(socket.isConnected())
                {
                    ClientProtocolProcessor.processInputStream(inputStream);              
                }
            }           
            close();
            ClientNetworkManager.connectionFailed();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ClientConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ClientConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void close() throws IOException
    {
        inputStream.close();
        outputStream.close();
        socket.close();
    }
}
