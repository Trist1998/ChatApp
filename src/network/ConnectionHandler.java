package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.client.ClientConnectionHandler;

/**
 *
 * @author Tristan
 */
public abstract class ConnectionHandler implements Runnable
{
    private Socket socket;
    private BufferedReader inputStream;
    private BufferedWriter outputStream;
    private boolean closed;
    
    
    public ConnectionHandler(Socket socket) throws IOException
    {
        this.socket = socket;
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        closed = false;
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
    
    public synchronized void close() throws IOException
    {
        inputStream.close();
        outputStream.close();
        socket.close();
        closed = true;
    }
    
    public BufferedReader getInputStream()
    {
        return inputStream;
    }
    
    public Socket getSocket()
    {
        return socket;
    }
    
    public synchronized boolean isConnected()
    {
        return !closed;
    }
}
