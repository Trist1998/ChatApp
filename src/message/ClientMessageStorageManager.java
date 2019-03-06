package message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.client.ClientNetworkManager;
import ui.ChatManager;

/**
 *
 * @author Tristan
 */
public class ClientMessageStorageManager
{
    public static final String PARAMETER_DELIMITER = "!P!";
    public static final String MESSAGE_DELIMITER = "!M!";
    
    public static void saveMessage(Message message)
    {
        int id = message.getId();
        String chatName = message.getChatName();
        String senderName = message.getSenderName();
        String receiverName = message.getReceiverName();
        String text = message.getText();
        int state = message.getState();
        Date received = message.getReceived();
        Date sent = message.getSent();
             
        String messageString = "";
        messageString += id + PARAMETER_DELIMITER;
        messageString += chatName + PARAMETER_DELIMITER;
        messageString += senderName + PARAMETER_DELIMITER;
        messageString += receiverName + PARAMETER_DELIMITER;
        messageString += text + PARAMETER_DELIMITER;
        messageString += state + PARAMETER_DELIMITER;
        messageString += received + PARAMETER_DELIMITER;
        messageString += sent + PARAMETER_DELIMITER;
        messageString += "\n" + MESSAGE_DELIMITER;
        
        try
        {    
            PrintWriter writer = new PrintWriter(new FileWriter (getSaveFilePath(), true));
            writer.append(messageString);
            writer.close();
        }
        catch(FileNotFoundException ex)
        {
            System.out.println("oops");
        }
        catch(IOException ex)
        {
            System.out.println("Error writing to file");
        }
    }
    
    public static void loadMessages() throws IOException
    {
        
        File file = new File(getSaveFilePath());

        try
        {
            Scanner reader = new Scanner(file);
            
            while(reader.hasNextLine())
            {
                String messageString = extractMessageString(reader);   
                if(messageString != null)
                {
                    String[] line = messageString.split(PARAMETER_DELIMITER);

                    int id = Integer.parseInt(line[0]);
                    String chatName = line[1];
                    String senderName = line[2];
                    String receiverName = line[3];
                    String text = line[4];
                    
                    int state = 0;
                    Date received = null;
                    Date sent = null;
                    try
                    {
                        state = Integer.parseInt(line[5]);
                        received = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z").parse(line[6]);
                        sent = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z").parse(line[7]);
                    } 
                    catch (ParseException ex)
                    {
                        
                        Logger.getLogger(ClientMessageStorageManager.class.getName()).log(Level.FINE, null, ex);
                        System.exit(-1);
                    }


                    Message message = new Message(id, chatName, senderName, receiverName, text, state, received, sent);
                    System.out.println("Message sent to receiving");
                    ChatManager.receiveMessage(message);
                }
                else
                    break;
            }
            reader.close();
        }
        catch (FileNotFoundException e)
        {
            createSaveFile();
        } 
        
    }
    
    private static String extractMessageString(Scanner reader)
    {
        String messageString = "";
        boolean end = false;
        while(reader.hasNextLine())
        {
            String line;
            line = reader.nextLine() + "\n";

                         
            if(line.trim().equals(MESSAGE_DELIMITER))
            {
                end = true;
                break;
            }
                
            else
                messageString += line;
        }
        if(end)
            return messageString;
        else
            return null;
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
            Logger.getLogger(ClientMessageStorageManager.class.getName()).log(Level.SEVERE, null, ex);
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
}
