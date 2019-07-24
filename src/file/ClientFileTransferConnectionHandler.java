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
import javax.swing.SwingUtilities;
import network.ConnectionHandler;
import network.SubConnectionHandler;
import network.client.ClientNetworkManager;
import ui.mainmenu.FileMessagePanel;

/**
 *
 * @author Tristan
 */
public class ClientFileTransferConnectionHandler extends SubConnectionHandler
{
    private File file;   
    private boolean sending;//If false client is receiving file 
    FileMessagePanel panel;
    
    public ClientFileTransferConnectionHandler(ServerSocket ss, File file, boolean sending , FileMessagePanel panel, ConnectionHandler parent) throws IOException, SQLException
    {
        super(parent, ss.accept());
        this.sending = sending;
        this.file = file;
        this.panel = panel;
    }

    @Override
    public void run()
    {
        System.out.println("Strated transer");
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
        catch (Exception ex)
        {
            Logger.getLogger(ClientFileTransferConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void updateProgressBar(int value)
    {
        SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                panel.getProgressBar().setValue(value);
            }
        });
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
            long total = 0;
            int count;
            while ((count = in.read(bytes)) > 0)
            {
                out.write(bytes, 0, count);
                total += 1024;
                
                float percent = (total*100.0f)/length;
                updateProgressBar((int) percent);
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
            long length = panel.getFileMessage().getFileSize();
            byte[] bytes = new byte[1024];
            in = getSocket().getInputStream();
            out = new FileOutputStream(file);
            int total = 0;
            int count;
            while ((count = in.read(bytes)) > 0) 
            {
                out.write(bytes, 0, count);
                total += 1024;             
                float percent = (total*100.0f)/length;
                updateProgressBar((int) percent);
            }
            System.out.println("Done");
            out.close();
            in.close();
            getSocket().close();
            confirmComplete();
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

    private void confirmComplete()
    {
        SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                panel.setButtonText();
            }
        });
    }
    
}
