/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

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
    
    public FileMessage(int messageId, String chatName, String senderName, 
            String receiverName, String text, int state, Date received, 
            Date sent, int fileId, String fileName, String filePath)
    {
        super(messageId, chatName, senderName, receiverName, text, state, received, sent);
        this.fileId = fileId;
        this.fileName = fileName;
        this.filePath = filePath;
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
            this.fileId = Integer.parseInt(pp.getParameter("FileId"));
        this.fileName = pp.getParameter("FileName");
        this.filePath = pp.getParameter("FilePath");
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
    
    
}
