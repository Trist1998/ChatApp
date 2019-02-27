
package network.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import network.client.protocol.ClientLoginProtocol;
import network.client.protocol.ClientProtocolProcessor;

public class ClientConnectionHandler implements Runnable
{
    private Socket socket;
    private BufferedReader inputStream;
    private BufferedWriter outputStream;
    private String username;
    private boolean loggedIn;
    
    public ClientConnectionHandler(Socket socket) throws IOException
    {
        this.socket = socket;
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        username = JOptionPane.showInputDialog("Username");
        loggedIn = false;                              
        ClientNetworkManager.setUsername(username);
        ClientNetworkManager.setConnection(this);
//        ClientStateManager.setSocket(socket);
//        ClientStateManager.setStreamWriter(outputStream);
//        ClientStateManager.setStreamReader(inputStream);
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
            ClientLoginProtocol.loginRequest(username, "password", this);
            if(ClientProtocolProcessor.processLogin(inputStream, this))
            {
                loggedIn = true;
                while(socket.isConnected())
                {
                    ClientProtocolProcessor.processInputStream(inputStream);              
                }
            } 
            socket.close();          
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

    public void setUsername(String username) 
    {
        this.username = username;
    }

    public synchronized boolean isLoggedIn() 
    {
        return loggedIn;
    }
}
