/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

import java.io.File;
import java.util.Date;
import message.Message;
import network.protocol.ProtocolParameters;

/**
 *
 * @author Tristan
 */
public class FileMessage extends Message
{
    private int fileId;//In server table
    private String fileName;
    private String filePath;
    private long fileSize;
    
    public FileMessage(int messageId, String chatName, String senderName, 
            String receiverName, String text, int state, Date received, 
            Date sent, int fileId, String fileName, String filePath, long fileSize)
    {
        super(messageId, chatName, senderName, receiverName, text, state, received, sent);
        this.fileId = fileId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
    
    public FileMessage(int id, String chatName, String senderName, String receiverName, String fileName, String filePath) 
    {
        super(id,chatName, senderName, receiverName, "");
        this.fileName = fileName;
        this.filePath = filePath;
    }
    
    public FileMessage(ProtocolParameters pp)
    {
        super(pp);
        String idString = pp.getParameter("FileId");
        if(idString != null && !idString.equals(""))
            this.fileId = Integer.parseInt(idString);
        this.fileName = pp.getParameter("FileName");
        this.filePath = pp.getParameter("FilePath");
        String sizeString = pp.getParameter("FileSize");
        if(sizeString != null && !sizeString.equals(""))
            this.fileSize = Long.parseLong(sizeString);
    }

    public FileMessage(int messageId, String chatName, String senderName, String receiverName,  File file)
    {
        super(messageId, chatName, senderName, receiverName, "");
        this.fileName = file.getName();
        this.filePath = file.getAbsoluteFile().toString();
        this.fileSize = file.length();

    }

    public FileMessage(Message message, String fileName, String filePath, int fileId, long fileSize)
    {
        super(message);
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileId = fileId;
        this.fileSize = fileSize;
        
    }

    public int getFileId()
    {
        return fileId;
    }

    public void setFileId(int fileId)
    {
        this.fileId = fileId;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public long getFileSize()
    {
        return fileSize;
    }
    
}
