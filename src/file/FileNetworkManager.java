package file;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.NetworkMessage;
import network.ConnectionHandler;
import network.client.ClientNetworkManager;
import network.protocol.MessageNetworkManager;
import network.protocol.NetworkMessageHandler;
import network.protocol.ProtocolParameters;
import network.server.ConnectionSwitch;
import network.server.ServerConnectionHandler;
import ui.ChatManager;
import ui.mainmenu.FileMessagePanel;

/**
 *	Sorry this file sending is so ugly this was done in a rush
 * @author Tristan
 */
public class FileNetworkManager extends NetworkMessageHandler
{
    public static final String HEAD = "FILE";
    public static final String ACTION_START_UPLOAD = "START_UPLOAD";
    public static final String ACTION_START_DOWNLOAD = "START_DOWNLOAD";
    public static final String ACTION_DOWNLOAD_PENDING = "DOWNLOAD_PENDING";
    public static final String ACTION_CLOSE = "CLOSE";
    
    public static final int FILE_TRANSER_PORT = 4444;
    
    public static boolean processInput(ProtocolParameters pp, ConnectionHandler conn)
    {
        String action = pp.getParameter(NetworkMessageHandler.PROTOCOL_ACTION);
        if(action.equals(ACTION_START_UPLOAD))
            startServerFileTranser(pp, (ServerConnectionHandler) conn, false);
        else if(action.equals(ACTION_START_DOWNLOAD))
            startServerFileTranser(pp, (ServerConnectionHandler) conn, true);
        else if(action.equals(ACTION_DOWNLOAD_PENDING))
            clientProcessPending(pp);
        else if(action.equals(ACTION_CLOSE))
            processClose(pp, (ServerConnectionHandler)conn);
        else if(action.equals(MessageNetworkManager.ACTION_RESPONSE))
            MessageNetworkManager.processResponse(pp);
                    
        return true;
    }
    
    public static void uploadFile(File file, FileMessage message, FileMessagePanel fp)
    {
        ProtocolParameters pp = new ProtocolParameters(HEAD, ACTION_START_UPLOAD);
        pp.add("Id", String.valueOf(message.getId()));
        pp.add("ChatName", message.getChatName());
        pp.add("Sender", message.getSenderName());
        pp.add("Receiver", message.getReceiverName());
        pp.add("Text", "");
        pp.add("DateSent", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z").format(new Date()));
        pp.add("FileName", file.getName());
        pp.add("FileSize", String.valueOf(message.getFileSize()));
        send(pp, ClientNetworkManager.getConnection());
        
        startFileTranser(file, true, fp);
    }
    
    public static void downloadFile(File file, int fileId, FileMessagePanel fp)
    {
        ProtocolParameters pp = new ProtocolParameters(HEAD, ACTION_START_DOWNLOAD);
        pp.add("FileId", String.valueOf(fileId));
        send(pp, ClientNetworkManager.getConnection());       
        startFileTranser(file, false, fp);
    }
        
    /**
     * Client start transfer
     * @param file
     * @param sending 
     * @param fp 
     */
    public static void startFileTranser(File file, boolean sending, FileMessagePanel fp)
    {
        
        ServerSocket myService; // Declare Server's Main socket
        
        System.out.println("Waiting for connection");
        try
        {
            myService = new ServerSocket(FILE_TRANSER_PORT); // Port number must be > 1023
            ClientFileTransferConnectionHandler tCon = new ClientFileTransferConnectionHandler(myService, file, sending, fp);
            new Thread(tCon).start();
            myService.close();
        } 
        catch (IOException ex)
        {
            Logger.getLogger(FileNetworkManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(FileNetworkManager.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }
    
    /**
     * 
     * @param pp
     * @param conn
     * @param sending 
     */
    private static void startServerFileTranser(ProtocolParameters pp, ServerConnectionHandler conn, boolean sending)
    {
        FileMessage message = new FileMessage(pp);
        try
        {
            String ipAddress = conn.getSocket().getRemoteSocketAddress().toString();
            Socket fileTransferSocket = new Socket(ipAddress.substring(1,ipAddress.indexOf(":")), FILE_TRANSER_PORT);
            if(sending)
                new Thread(new ServerFileTransferConnectionHandler(fileTransferSocket, message.getFileId(), conn)).start();
            else
                new Thread(new ServerFileTransferConnectionHandler(fileTransferSocket, message, false, conn)).start();
        } 
        catch (IOException ex)
        {
            Logger.getLogger(FileNetworkManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 
     * @param conn 
     */
    public static void sendClose(ConnectionHandler conn)
    {
        ProtocolParameters pp = new ProtocolParameters(HEAD, ACTION_CLOSE);
        send(pp, conn);
    }
    
    /**
     * 
     * @param conn 
     */
    public static void processClose(ProtocolParameters pp, ServerConnectionHandler conn)
    {
        int id = Integer.parseInt(pp.getParameter("ConnectionId"));
        conn.removeSubConnection(id);
    }
    
    /**
     * 
     * @param pp 
     */
    public static void clientProcessPending(ProtocolParameters pp)
    {       
        ChatManager.receiveFile(new FileMessage(pp));
    }
    
    /**
     * 
     * @param message
     * @param conn 
     */
    public static void sendResponseAndPendingFile(FileMessage message, ServerConnectionHandler conn)
    {
        ProtocolParameters pp = new ProtocolParameters(HEAD, ACTION_DOWNLOAD_PENDING);
        pp.add("Id", String.valueOf(message.getId()));
        pp.add("ChatName", message.getChatName());
        pp.add("Sender", message.getSenderName());
        pp.add("Receiver", message.getReceiverName());
        pp.add("Text", "");
        pp.add("DateSent", message.getDateSentString());
        pp.add("FileName", message.getFileName());
        pp.add("FileId", String.valueOf(message.getFileId()));
        pp.add("FilePath", message.getFilePath());
        pp.add("FileSize", String.valueOf(message.getFileSize()));
        NetworkMessage nM = new NetworkMessage(pp);
        int responseCode = ConnectionSwitch.switchProtocol(nM);
        
        try
        {
            MessageNetworkManager.sendResponse(responseCode, pp, conn);
        } 
        catch (IOException ex)
        {
            Logger.getLogger(FileNetworkManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
