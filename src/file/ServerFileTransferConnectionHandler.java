package file;

import file.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.SubConnectionHandler;
import network.server.ServerConnectionHandler;

/**
 *
 * @author Tristan
 */
public class ServerFileTransferConnectionHandler extends SubConnectionHandler
{
    private int fileId;
    private FileMessage message;
    private boolean sending;//If false client is receiving file 

    public ServerFileTransferConnectionHandler(Socket socket, FileMessage message, boolean sending, ServerConnectionHandler parent) throws IOException
    {
        super(parent, socket);
        this.fileId = message.getFileId();
        this.message = message;
        this.sending = sending;
    }
    
    public ServerFileTransferConnectionHandler(Socket socket, int fileId, ServerConnectionHandler parent) throws IOException
    {
        super(parent, socket);
        this.fileId = fileId;
        sending = true;
    }

    @Override
    public void run()
    {
        if(sending)
            sendFile();
        else
            receiveFile();
    }
    
    private void sendFile()
    {
        System.out.println("Sending file");
        InputStream in = null;
        OutputStream out = null;
        try 
        {
            String fileName = getDirectory()+"("+ fileId +")" + new FileTable().loadFileName(fileId);
            // Get the size of the file
            
            byte[] bytes = new byte[1024];
            in = new FileInputStream(new File(fileName));
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
        catch (IOException ex)
        {
            Logger.getLogger(ServerFileTransferConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void receiveFile()
    {
       
        System.out.println("Receiving");
        InputStream in = null;
        OutputStream out = null;
        try
        {
            new FileTable().saveFile(message);
            fileId = new FileTable().loadFileIdBySenderAndMessageId(message);
            String fileName = getDirectory() + "("+ fileId +")" + new FileTable().loadFileName(fileId);
            File file = new File(fileName);
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
            message.setFileId(fileId);
            FileNetworkManager.sendResponseAndPendingFile(message, (ServerConnectionHandler)getParentConnection());
            System.out.println("Done");
            out.close();
            in.close();
            FileNetworkManager.sendClose((ServerConnectionHandler)getParentConnection());
            TimeUnit.SECONDS.sleep(10);
            close();
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(ClientFileTransferConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(ClientFileTransferConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(ServerFileTransferConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(ServerFileTransferConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private static String getDirectory()
    {
        File directory = new File(System.getProperty("user.dir") +"/serverfiles/");
        if(!directory.exists())
            directory.mkdir();
        return System.getProperty("user.dir") +"/serverfiles/";
    }
    
}
