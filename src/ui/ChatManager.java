package ui;

import java.util.concurrent.ConcurrentHashMap;
import javax.swing.SwingUtilities;
import message.Message;

/**
 * @author Tristan
 */
public class ChatManager
{
    private static ConcurrentHashMap<String, SideBarChat> chats = new ConcurrentHashMap<>();  
    private static MainMenu mainMenu;
    

    public static void buildChatViews(MainMenu mm)
    {
        mainMenu = mm;
        //TODO load locally saved chats
    }
    
    public static void createChat(String chatName)
    {
        SideBarChat newSideBarComp = new SideBarChat(chatName, mainMenu);
        chats.put(newSideBarComp.getChatName(), newSideBarComp);
        mainMenu.addChat(newSideBarComp);
    }
    
    private static void createChat(Message message)
    {
        SideBarChat newSideBarComp = new SideBarChat(message.getSenderName(), mainMenu);      
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
            SwingUtilities.invokeLater(new Runnable() 
            {
                public void run() 
                {
                    chat.receiveResponse(messageId, responseCode);
                }
            });         
    }
}
