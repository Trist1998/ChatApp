package message;

import java.util.HashMap;
import ui.GenericChat;
import ui.MainMenu;

/**
 * @author Tristan
 */
public class ChatManager
{
    private static HashMap<String, GenericChat> chats = new HashMap<>();  
    private static MainMenu mainMenu;

    public static void buildChatViews(MainMenu mm)
    {
        mainMenu = mm;
        //TODO load locally saved chats
    }
    
    public static void receiveMessage(Message message)
    {
        
        GenericChat gC = chats.get(message.getSenderName());
        if(gC == null)           
            gC = createNewChatAndAdd(message.getSenderName());
        gC.receiveMessage(message);
    }
    
    private static GenericChat createNewChatAndAdd(String senderName)
    {
        GenericChat gC = new GenericChat(senderName);
        chats.put(senderName, gC);
        mainMenu.getChatWindow().add(gC);
        return gC;
    }
}
