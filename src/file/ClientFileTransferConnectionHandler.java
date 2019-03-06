package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.ConnectionHandler;
import network.client.ClientNetworkManager;

/**
 *
 * @author Tristan
 */
public class ClientFileTransferConnectionHandler extends ConnectionHandler
{
    private File file;   
    private boolean sending;//If false client is receiving file 
    
    public ClientFileTransferConnectionHandler(ServerSocket ss, File file, boolean sending) throws IOException, SQLException
    {
        super(ss.accept());
        this.sending = sending;
        this.file = file;
    }

    @Override
    public void run()
    {
        
        if(sending)
            sendFile(file);
        else
            receiveFile(file);
        while(isConnected())
        {}
        try
        {
            close();
        } 
        catch (IOException ex)
        {
            Logger.getLogger(ClientFileTransferConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendFile(File file)
    {
        System.out.println("Sending");
        InputStream in = null;
        OutputStream out = null;
        try 
        {
            // Get the size of the file
            long length = file.length();
            byte[] bytes = new byte[1024];
            in = new FileInputStream(file);
            out = getSocket().getOutputStream();
            int count;
            while ((count = in.read(bytes)) > 0)
            {
                out.write(bytes, 0, count);
            }   
            out.close();
            in.close();
            getSocket().close();
            System.out.println("Done");
        } 
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(ClientFileTransferConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex)
        {
            Logger.getLogger(ClientFileTransferConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    
    public void receiveFile(File file)
    {
        System.out.println("Receiving");
        InputStream in = null;
        OutputStream out = null;
        try
        {
            file.createNewFile();
            // Get the size of the file
            long length = file.length();
            byte[] bytes = new byte[1024];
            in = getSocket().getInputStream();
            out = new FileOutputStream(file);
            int count;
            while ((count = in.read(bytes)) > 0) 
            {
                out.write(bytes, 0, count);
            }
            out.close();
            in.close();
            getSocket().close();
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(ClientFileTransferConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(ClientFileTransferConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        } 
        FileNetworkManager.sendClose(ClientNetworkManager.getConnection());
    }
    
}
