package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.SwingUtilities;
import message.Message;

/**
 * @author Tristan
 */
public class ChatManager
{
    private static HashMap<String, SideBarChat> chats = new HashMap<>();  
    private static MainMenu mainMenu;
    

    public static void buildChatViews(MainMenu mm) throws ParseException
    {
        mainMenu = mm;
        
        File file = new File("ChatAppMessages");
        String dlim = "---DELIMITER---";

        try
        {
            Scanner csv = new Scanner(file);

            while(csv.hasNext())
            {
                String s = csv.nextLine();
                String[] line = s.split(dlim);
                
                int id = Integer.parseInt(line[0]);
                String senderName = line[1];
                String receiverName = line[2];
                String text = line[3];
                int state = Integer.parseInt(line[4]);
                Date received =  new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z").parse(line[5]);
                Date sent = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z").parse(line[6]);
                
                Message message = new Message(id, senderName, receiverName, text, state, received, sent);
                receiveMessage(message);
                
                
            }
            csv.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
        
        
        
    }
    
   
    
    public synchronized static void createChat(String chatName)
    {
        SideBarChat newSideBarComp = new SideBarChat(chatName, mainMenu);
        chats.put(newSideBarComp.getChatName(), newSideBarComp);
        mainMenu.addChat(newSideBarComp);
    }
    
    public synchronized static void createChat(Message message)
    {
        SideBarChat newSideBarComp = new SideBarChat(message.getSenderName(), message, mainMenu);
        chats.put(newSideBarComp.getChatName(), newSideBarComp);
        mainMenu.addChat(newSideBarComp);
        newSideBarComp.receiveMessage(message);
    }
    
    public synchronized static void receiveMessage(Message message)
    {    
        SideBarChat sideBarComp = chats.get(message.getSenderName());
        SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                if(sideBarComp == null)
                    ChatManager.createChat(message);
                else
                    sideBarComp.receiveMessage(message);
            }
        });     
    }

    public static void receiveResponse(String chatName, int messageId, int responseCode)
    {
        SideBarChat chat = chats.get(chatName);
        if(chat != null)
            chat.receiveResponse(messageId, responseCode);
    }
}
