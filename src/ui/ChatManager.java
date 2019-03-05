package ui;

// Imports
import java.util.HashMap;
import javax.swing.SwingUtilities;
import message.Message;

/**
 *
 * @author @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class ChatManager {

    private static HashMap<String, SideBarChat> chats = new HashMap<>(); // Declare a HashMap called chats.  
    private static MainMenu mainMenu; // Declare an instance of MainMenu.

    /**
     *
     * @param mm
     */
    public static void buildChatViews(MainMenu mm) {
        mainMenu = mm;
        //TODO load locally saved chats
    }

    /**
     * Creates a new side bar chat and adds it to the Main Menu form.
     *
     * @param chatName
     */
    public synchronized static void createChat(String chatName) {
        SideBarChat newSideBarComp = new SideBarChat(chatName, mainMenu);
        chats.put(newSideBarComp.getChatName(), newSideBarComp);
        mainMenu.addChat(newSideBarComp); // Add sidebar chat component to Main Menu form.
    }

    /**
     * Creates a new side bar chat and adds it to the Main Menu form.
     *
     * @param message
     */
    public synchronized static void createChat(Message message) {
        SideBarChat newSideBarComp = new SideBarChat(message.getSenderName(), message, mainMenu);
        chats.put(newSideBarComp.getChatName(), newSideBarComp);
        mainMenu.addChat(newSideBarComp);
        newSideBarComp.receiveMessage(message);
    }

    /**
     * Adds message to chat.
     * @param message 
     */
    public synchronized static void receiveMessage(Message message) {
        SideBarChat sideBarComp = chats.get(message.getSenderName());
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (sideBarComp == null) {
                    ChatManager.createChat(message);
                } else {
                    sideBarComp.receiveMessage(message);
                }
            }
        });

    }

    /**
     * Receives response of message, to update message state.
     * @param chatName
     * @param messageId
     * @param responseCode 
     */
    public static void receiveResponse(String chatName, int messageId, int responseCode) {
        SideBarChat chat = chats.get(chatName);
        if (chat != null) {
            chat.receiveResponse(messageId, responseCode);
        }
    }
}
