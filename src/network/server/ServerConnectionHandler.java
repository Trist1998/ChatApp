package network.server;

// Imports
import database.NetworkMessageQueue;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.NetworkMessage;
import network.ConnectionHandler;

/**
 * ServerConnectionHandler class is the connection Handler for server connections.
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class ServerConnectionHandler extends ConnectionHandler 
{
    private AtomicInteger idCounter;
    private HashMap<Integer,ConnectionHandler> activeSubConnections;
    /**
     * Constructor.
     * @param ss
     * @throws IOException
     * @throws SQLException
     */

    public ServerConnectionHandler(ServerSocket ss) throws IOException, SQLException 
    {
        super(ss.accept());
        idCounter = new AtomicInteger(0);
        activeSubConnections = new HashMap<>();
    }

    /**
     * Listens to inputStream while connection is open.
     */
    @Override
    public void run() 
    {
        try 
        {
            if (ServerProtocolProcessor.processInitialConnection(this)) 
            {
                System.out.println("Waiting for data from " + getUsername());
                while (isConnected()) 
                {
                    ServerProtocolProcessor.processServerInputStream(this);
                }
            }
            ConnectionSwitch.removeConnection(this);
            close();
        } 
        catch (Exception ex)
        {
            Logger.getLogger(ServerConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    public static final int MESSAGE_DELIVERED = 2;
    public static final int MESSAGE_SAVED = 1;
    public static final int MESSAGE_LOST = 0;

    /**
     * Sends protocol string inside protocol to client.
     * @param protocol
     * @return
     */
    public int send(NetworkMessage protocol) 
    {
        BufferedWriter outputStream = getOutputStream();
        synchronized (outputStream) 
        {
            try 
            {
                outputStream.write(protocol.getText());//Remember to put + ProtocolProcessor.PROTOCOL_END + "\n"; where the String is built
                outputStream.flush();
                return MESSAGE_DELIVERED;
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
                if(!protocol.isAlreadySaved())
                    try 
                    {
                        new NetworkMessageQueue(protocol).addToQueue();
                        return MESSAGE_SAVED;
                    } 
                    catch (SQLException ex1)
                    {
                        Logger.getLogger(ServerConnectionHandler.class.getName()).log(Level.SEVERE, null, ex1);
                        return MESSAGE_LOST;
                    }              
            }
            return MESSAGE_LOST;
        }      
    }
    public void addSubConnection(int id, ConnectionHandler conn)
    {
        activeSubConnections.put(id, conn);
    }
    
    
    public void removeSubConnection(int id)
    {
        activeSubConnections.remove(id);
    }
    
    public int getNextSubId()
    {
        return idCounter.getAndIncrement();             
    }
}
