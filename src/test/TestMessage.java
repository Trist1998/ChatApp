package test;

import javax.swing.JOptionPane;
import network.client.ClientNetworkManager;
import ui.ChatManager;
import ui.MainMenu;

/**
 *
 * @author Tristan
 */
public class TestMessage
{
    public static void openMessage()
    {
        if(ClientNetworkManager.login("TestUser", "123456"))
        {
            MainMenu mm = new MainMenu();
            ChatManager.buildChatViews(mm);
            mm.setVisible(true);         
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Login Failed");
        }    
    }
    
    public static void main(String args[]) 
    {
       openMessage();
    }
}
