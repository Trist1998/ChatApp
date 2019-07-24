package network;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tristan
 */
public abstract class SubConnectionHandler extends ConnectionHandler
{
    private ConnectionHandler parent;
    private int connectionId;
    
    public SubConnectionHandler(ConnectionHandler parent, Socket socket) throws IOException
    {
        super(socket);
        this.parent = parent;
        this.connectionId = parent.getNextConnectionId();
    }

    public int getConnectionid()
    {
        return connectionId;
    }
    
    public ConnectionHandler getParentConnection()
    {
        return parent;
    }
    
    @Override
    public void close()
    {
        parent.removeSubConnection(this);
        try
        {
            super.close();
        } 
        catch (IOException ex)
        {
            Logger.getLogger(SubConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
