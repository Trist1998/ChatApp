package network;

// Imports
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ConnectionHandler used to manage input and output streams of sockets as well
 * as write from output streams.
 *
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public abstract class ConnectionHandler implements Runnable 
{

    // Variables
    private Socket socket;
    private BufferedReader inputStream;
    private BufferedWriter outputStream;
    private boolean closed;
    private String username;

    /**
     * Parameterized Constructor for ProtocolMessage class, creates input and output stream for a socket.
     *
     * @param socket
     * @throws IOException
     */
    public ConnectionHandler(Socket socket) throws IOException 
    {
        this.socket = socket;
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        closed = false;
    }

    /**
     * Write to outputStream.
     *
     * @param protocol
     * @return
     */
    public boolean send(String protocol) 
    {
        synchronized (outputStream) 
        {
            try 
            {
                outputStream.write(protocol);//Remember to put + ProtocolProcessor.PROTOCOL_END + "\n"; where the String is built
                outputStream.flush(); 
                return true;
            } 
            catch (IOException ex)
            {
                Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
    }

    /**
     * Close the in and out streams and the socket.
     *
     * @throws IOException
     */
    public synchronized void close() throws IOException 
    {
        inputStream.close();
        outputStream.close();
        socket.close();
        closed = true;
    }

    /**
     * Get the input stream.
     *
     * @return
     */
    public BufferedReader getInputStream() 
    {
        return inputStream;
    }

    /**
     * Get the socket.
     *
     * @return
     */
    public Socket getSocket() 
    {
        return socket;
    }

    /**
     * Check if connection is not closed.
     *
     * @return
     */
    public synchronized boolean isConnected() 
    {
        return !closed;
    }

    /**
     * Get output stream.
     *
     * @return
     */
    protected BufferedWriter getOutputStream()
    {
        return outputStream;
    }

    /**
     * Set username.
     *
     * @param username
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * Get username.
     *
     * @return
     */
    public String getUsername() 
    {
        return username;
    }
}
