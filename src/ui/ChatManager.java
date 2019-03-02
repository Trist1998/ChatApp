package ui;

import java.util.HashMap;
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
    
    
    
    public synchronized static void receiveMessage(Message message)
    {
        
        SideBarChat sideBarComp = chats.get(message.getSenderName());
        if(sideBarComp == null)
        {
            sideBarComp = new SideBarChat(message.getSenderName(), message, mainMenu);
            mainMenu.addChat(sideBarComp);
            sideBarComp.setVisible(true);
        }

        sideBarComp.receiveMessage(message);
        
    }
}
