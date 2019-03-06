package message;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import network.ConnectionHandler;
import network.client.ClientNetworkManager;
import network.server.ServerConnectionHandler;

/**
 *
 * @author Tristan
 */
public class ServerFileTransferConnectionHandler extends ConnectionHandler
{
    private int id;
    private FileMessage message;
    private boolean sending;//If false client is receiving file 
    private ServerConnectionHandler parent;

    public ServerFileTransferConnectionHandler(Socket socket, FileMessage message, boolean sending, ServerConnectionHandler parent) throws IOException
    {
        super(socket);
        this.id = message.getFileId();
        this.message = message;
        this.sending = sending;
        this.parent = parent;
    }
    
    public ServerFileTransferConnectionHandler(Socket socket, int id, ServerConnectionHandler parent) throws IOException
    {
        super(socket);
        this.id = id;
        sending = true;
        this.parent = parent;
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
        System.out.println("Senfining file");
        InputStream in = null;
        OutputStream out = null;
        try 
        {
            String fileName = "("+ id +")" + new FileTable().loadFileName(id);
            // Get the size of the file
            byte[] bytes = new byte[1024];
            in = new FileInputStream(fileName);
            out = getSocket().getOutputStream();
            int count;
            while ((count = in.read(bytes)) > 0)
            {
                out.write(bytes, 0, count);
            }   
            out.close();
            in.close();
            getSocket().close();
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
            id = new FileTable().loadFileIdBySenderAndMessageId(message);
            String fileName = getDirectory() + "("+ id +")" + new FileTable().loadFileName(id);
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
            message.setFileId(id);
            FileNetworkManager.sendResponseAndPendingFile(message, parent);
            System.out.println("Done");
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
        catch (SQLException ex) 
        {
            Logger.getLogger(ServerFileTransferConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        FileNetworkManager.sendClose(parent);
    }
    
    private static String getDirectory()
    {
        File directory = new File(System.getProperty("user.dir") +"/serverfiles/");
        if(!directory.exists())
            directory.mkdir();
        return System.getProperty("user.dir") +"/serverfiles/";
    }
    
}
