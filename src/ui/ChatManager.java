package ui;

import java.util.HashMap;
import javax.swing.SwingUtilities;
import message.Message;

/**
 * @author Tristan
 */
public class ChatManager
{
    private static HashMap<String, SideBarChat> chats = new HashMap<>();  
    private static MainMenu mainMenu;
    

    public static void buildChatViews(MainMenu mm)
    {
        mainMenu = mm;
        //TODO load locally saved chats
    }
    
    public synchronized static void createChat(String chatName)
    {
        SideBarChat newSideBarComp = new SideBarChat(chatName, mainMenu);
        chats.put(newSideBarComp.getName(), newSideBarComp);
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
}
