package message;

import file.FileMessage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.client.ClientNetworkManager;
import network.protocol.NetworkMessageListener;

import network.protocol.ProtocolParameters;
import org.json.simple.parser.ParseException;
import ui.ChatManager;

/**
 *
 * @author Tristan
 */
public class LocalChatStorage
{
    public static void saveMessage(Message message)
    {
        try
        {
            ProtocolParameters pp = new ProtocolParameters("MESSAGE", "SAVED");       
            pp.add("Id", String.valueOf(message.getId()));
            pp.add("ChatName", message.getChatName());
            pp.add("Sender", message.getSenderName());
            pp.add("Receiver", message.getReceiverName());
            pp.add("Text", message.getText());
            pp.add("DateSent", message.getDateSentString());
            pp.add("State", String.valueOf(message.getState()));
            
            BufferedWriter bw = new BufferedWriter(new FileWriter (getSaveFilePath(), true));
            bw.write(pp.buildProtocolString());
            bw.close();
        } 
        catch (IOException ex)
        {
            Logger.getLogger(LocalChatStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void saveFileMessage(FileMessage message)
    {
        try
        {
            ProtocolParameters pp = new ProtocolParameters("FILEMESSAGE", "SAVED");       
            pp.add("Id", String.valueOf(message.getId()));
            pp.add("ChatName", message.getChatName());
            pp.add("Sender", message.getSenderName());
            pp.add("Receiver", message.getReceiverName());
            pp.add("Text", message.getText());
            pp.add("DateSent", message.getDateSentString());
            pp.add("State", String.valueOf(message.getState()));
            pp.add("FileName", message.getFileName());
            pp.add("FileId", String.valueOf(message.getFileId()));
            pp.add("FilePath", message.getFilePath());
            pp.add("FileSize", String.valueOf(message.getFileSize()));
            
            BufferedWriter bw = new BufferedWriter(new FileWriter (getSaveFilePath(), true));
            bw.write(pp.buildProtocolString());
            bw.close();
        } 
        catch (IOException ex)
        {
            Logger.getLogger(LocalChatStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void loadMessages() throws ParseException
    {
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new FileReader(new File(getSaveFilePath())));
            String line;
            while(true)
            {
                try
                {
                    ProtocolParameters pp = NetworkMessageListener.parseInputStream(reader);
                    if(pp == null)
                        break;
                    if(pp.getHead().equals("MESSAGE"))
                        ChatManager.addLocalMessage(new Message(pp));
                    else
                        ChatManager.addLocalFileMessage(new FileMessage(pp));
                }
                catch (IOException ex)
                {
                    Logger.getLogger(LocalChatStorage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } 
        catch (FileNotFoundException ex)
        {
            createSaveFile();
        }
    }
    
    private static String getSaveFilePath()
    {
        return getDirectory() + ClientNetworkManager.getUsername() + ".msgs";
    }
    
    private static String getDirectory()
    {
        return System.getProperty("user.dir") +"/users/";
    }
    
    private static void createSaveFile()
    {
        File newFile = new File(getSaveFilePath());
        File directory = new File(getDirectory());
        try
        {
            if(!directory.exists())
                directory.mkdir();
            newFile.createNewFile();
        } 
        catch (IOException ex)
        {
            Logger.getLogger(LocalChatStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
