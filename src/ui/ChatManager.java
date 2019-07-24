package ui;

import file.FileMessage;
import java.io.IOException;
import ui.mainmenu.SideBarChat;
import ui.mainmenu.MainMenu;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.SwingUtilities;
import message.LocalChatStorage;
import message.Message;

/**
 *
 * @author @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class ChatManager
{
    private static ConcurrentHashMap<String, SideBarChat> chats = new ConcurrentHashMap<>(); // Declare a HashMap called chats.  
    private static MainMenu mainMenu; // Declare an instance of MainMenu.
    

    /**
     * Loads locally saved messages to the ui
     * @param mm
     */
    public static void buildChatViews(MainMenu mm) throws IOException 
    {
        mainMenu = mm;
        LocalChatStorage.loadMessages();
    }

    /**
     * Creates a new side bar chat and adds it to the Main Menu form.
     *
     * @param chatName
     */
    public static SideBarChat createChat(String chatName) 
    {
        SideBarChat newSideBarChat = new SideBarChat(chatName, mainMenu);
        chats.put(chatName, newSideBarChat);
        mainMenu.addChat(newSideBarChat); // Add sidebar chat component to Main Menu form.
        return  newSideBarChat;
    }
    
    private static SideBarChat createChat(Message message)
    {
        SideBarChat newSideBarChat = new SideBarChat(message.getChatName(), mainMenu);      
        chats.put(newSideBarChat.getChatName(), newSideBarChat);      
        mainMenu.addChat(newSideBarChat);
        return  newSideBarChat;
    }

    /**
     * Adds message to chat.
     * @param message 
     */
    public synchronized static void receiveMessage(Message message) 
    {
        message.setChatName(message.getSenderName());
        SideBarChat sideBarChat = chats.get(message.getSenderName());
        SideBarChat toReceive;
        if(sideBarChat == null)
            toReceive = createChat(message);
        else
            toReceive = sideBarChat;

        SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                toReceive.receiveMessage(message);
            }
        });
    }
    
    public synchronized static void addLocalMessage(Message message) 
    {
        SideBarChat sideBarComp = chats.get(message.getChatName());
        forwardSavedToChat(sideBarComp, message);                
    }
    
    private static void forwardSavedToChat(SideBarChat sideBarChat, Message message)
    {
        SideBarChat toReceive;
        if(sideBarChat == null)
            toReceive = createChat(message);
        else
            toReceive = sideBarChat;

        SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                toReceive.receiveSavedMessage(message);
            }
        });
    }
    /**
     * Receives response of message, to update message state.
     * @param chatName
     * @param messageId
     * @param responseCode 
     */
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

    public static void receiveFile(FileMessage message)
    {
        message.setChatName(message.getSenderName());
        SideBarChat chat = chats.get(message.getChatName());
        SwingUtilities.invokeLater(new Runnable() 
        {
                public void run() 
                {
                    SideBarChat toReceive;
                    if(chat == null)
                        toReceive = createChat(message);
                    else
                        toReceive = chat;
                    toReceive.receiveFile(message);

                }
        });  
    }

    public synchronized static void addLocalFileMessage(FileMessage message) 
    {
        SideBarChat sideBarComp = chats.get(message.getChatName());
        forwardSavedFileToChat(sideBarComp, message);                
    }
    
    private static void forwardSavedFileToChat(SideBarChat sideBarChat, FileMessage message)
    {
        SideBarChat toReceive;
        if(sideBarChat == null)
            toReceive = createChat(message);
        else
            toReceive = sideBarChat;

        SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                toReceive.receiveSavedFile(message);
            }
        });
    }

}
