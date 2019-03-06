package test;

import javax.swing.JOptionPane;
import network.client.ClientNetworkManager;
import ui.mainmenu.MainMenu;

/**
 *
 * @author Tristan
 */
public class Test2
{
    public static void openMessage()
    {
        if(ClientNetworkManager.login("admin", "admin"))
        {
            MainMenu mm = new MainMenu();
            mm.start();
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
