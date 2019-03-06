package ui.mainmenu;

// Imports
import file.FileMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.UIManager;
import message.Message;

/**
 * SideBarChat class is used to create and display chats on the MainMenu so that
 * a user may see past chats and select which one to display.
 *
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class SideBarChat extends javax.swing.JPanel
{

    private MainMenu mainMenu; // Declare an instance of MainMenu.
    public ChatPanel chat; // Declare an instance of GenericChat.

    /**
     * Parameterized Constructor for SideBarChat class, creates a chat on the side of the Main Menu form with no content.
     * @param chatName
     * @param mm
     */
    public SideBarChat(String chatName, MainMenu mm) 
    {
        initComponents(); // Initialise GUI components.
        try 
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Adapt and optimize look and feel depending on OS.
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
        lblChatName.setText(chatName);
        lblLastMessage.setText("No Messages");
        lblLastMessageTime.setText("");
        mainMenu = mm;
        this.chat = new ChatPanel(chatName, this);
    }

    /**
     * Parameterized Constructor for SideBarChat class, creates a chat on the side of the Main Menu form with content of the last message.
     */
    public SideBarChat(String chatName, Message lastMessage, MainMenu mm)
    {
        initComponents(); // Initialise GUI components.
        try 
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Adapt and optimize look and feel depending on OS.
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        lblChatName.setText(chatName); // Set chat name label to chat name parameter.
        lblLastMessage.setText(lastMessage.getText()); // Set last message label to the body of text of the messsage's text.
        lblLastMessageTime.setText("Date"); // Set the message time label.
        mainMenu = mm; // Set MainMenu to MainMenu parameter.
        this.chat = new ChatPanel(chatName, this); // Instantiate GenericChat with the passed in chat name.
    }

    /**
     * Returns the chat name of the side bar chat selected.
     *
     * @return
     */
    public String getChatName() 
    {
        return chat.getChatName(); // Get the chat name of chat and return it.
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        lblLastMessageTime = new javax.swing.JLabel();
        lblLastMessage = new javax.swing.JLabel();
        lblChatName = new javax.swing.JLabel();

        setBackground(new java.awt.Color(244, 244, 244));
        setAlignmentX(0.0F);
        setAlignmentY(0.0F);
        setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        setMaximumSize(new java.awt.Dimension(275, 90));
        setPreferredSize(new java.awt.Dimension(275, 90));
        addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                formMouseClicked(evt);
            }
        });

        lblLastMessageTime.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        lblLastMessageTime.setText("Last Message Time");

        lblLastMessage.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        lblLastMessage.setText("Last Message");

        lblChatName.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        lblChatName.setText("Chat Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblLastMessageTime))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblChatName)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblLastMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblChatName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLastMessage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblLastMessageTime)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Sets the MainMenu display to chat selected.
     *
     * @param evt
     */
    private void formMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_formMouseClicked
    {//GEN-HEADEREND:event_formMouseClicked
        mainMenu.setChat(chat);
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblChatName;
    private javax.swing.JLabel lblLastMessage;
    private javax.swing.JLabel lblLastMessageTime;
    // End of variables declaration//GEN-END:variables

    /**
     * Adds received message to chat and sets as last message.
     *
     * @param message
     */
    public void receiveMessage(Message message) 
    {
        setLastMessage(message); // Sets last message of chat to message.
        chat.receiveMessage(message); // Adds message to chat.
    }

    public void setLastMessage(Message message) 
    {
        lblLastMessage.setText(message.getText());
        if (message.getSent() != null) 
        {
            Date date = message.getSent();
            Date now = new Date();
            if(now.getDate() == date.getDate() && now.getMonth() == date.getMonth())
                lblLastMessageTime.setText(new SimpleDateFormat("HH:mm").format(date));
            else
                lblLastMessageTime.setText(new SimpleDateFormat("dd/MM").format(date));
        } 
        else 
        {
            lblLastMessage.setText("");
        }
    }

    /**
     * Get response from chat with the given message ID and response code.
     *
     * @param messageId
     * @param responseCode
     */
    public void receiveResponse(int messageId, int responseCode) 
    {
        chat.receiveResponse(messageId, responseCode);
    }

    public void receiveFile(FileMessage message)
    {
        setLastMessage(message);
        lblLastMessage.setText("File: " + message.getFileName());
        chat.receiveFile(message);
    }

    public void receiveSavedMessage(Message message)
    {
        setLastMessage(message);
        lblLastMessage.setText(message.getText());
        chat.receiveSavedMessage(message);
    }
    
    public void receiveSavedFile(FileMessage message)
    {
        setLastMessage(message);
        lblLastMessage.setText("File: " + message.getFileName());
        chat.receiveSavedFile(message);
    }
}
