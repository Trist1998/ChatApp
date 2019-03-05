package ui;

// Imports
import javax.swing.UIManager;
import message.Message;

/**
 * SideBarChat class is used to create and display chats on the MainMenu so that
 * a user may see past chats and select which one to display.
 *
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class SideBarChat extends javax.swing.JPanel {

    private MainMenu mainMenu; // Declare an instance of MainMenu.
    private GenericChat chat; // Declare an instance of GenericChat.

    /**
     * Parameterized Constructor for SideBarChat class, creates a chat on the side of the Main Menu form with no content.
     */
    public SideBarChat(String chatName, MainMenu mm) {
        initComponents(); // Initialise GUI components.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Adapt and optimize look and feel depending on OS.
        } catch (Exception e) {
            e.printStackTrace();
        }
        lblChatName.setText(chatName); // Set chat name lable to chat name parameter.
        mainMenu = mm; // Set MainMenu to MainMenu parameter.
        this.chat = new GenericChat(chatName, this); // Instantiate GenericChat with the passed in chat name.
    }

    /**
     * Parameterized Constructor for SideBarChat class, creates a chat on the side of the Main Menu form with content of the last message.
     */
    public SideBarChat(String chatName, Message lastMessage, MainMenu mm) {
        initComponents(); // Initialise GUI components.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Adapt and optimize look and feel depending on OS.
        } catch (Exception e) {
            e.printStackTrace();
        }
        lblChatName.setText(chatName); // Set chat name label to chat name parameter.
        lblLastMessage.setText(lastMessage.getText()); // Set last message label to the body of text of the messsage's text.
        lblLastMessageTime.setText("Date"); // Set the message time label.
        mainMenu = mm; // Set MainMenu to MainMenu parameter.
        this.chat = new GenericChat(chatName, this); // Instantiate GenericChat with the passed in chat name.
    }

    /**
     * Returns the chat name of the side bar chat selected.
     *
     * @return
     */
    public String getChatName() {
        return chat.getChatName(); // Get the chat name of chat and return it.
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblLastMessageTime = new javax.swing.JLabel();
        lblLastMessage = new javax.swing.JLabel();
        lblChatName = new javax.swing.JLabel();

        setBackground(new java.awt.Color(244, 244, 244));
        setAlignmentX(0.0F);
        setAlignmentY(0.0F);
        setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 215, Short.MAX_VALUE)
                        .addComponent(lblLastMessageTime))
                    .addComponent(lblLastMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblChatName)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblChatName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(lblLastMessageTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLastMessage)
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
        System.out.println("Clicked This panel");
        mainMenu.setChat(chat); // / Display selected chat on MainMenu.
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
    public void receiveMessage(Message message) {
        setLastMessage(message); // Sets last message of chat to message.
        chat.receiveMessage(message); // Adds message to chat.
    }

    /**
     * Set last message label to message passed in.
     *
     * @param message
     */
    public void setLastMessage(Message message) {
        lblLastMessage.setText(message.getText()); // Set last message label to the body of text of the messsage's text.
        if (message.getSent() != null) {
            lblLastMessageTime.setText(message.getDateSentString()); // Set last message time label to the time it was sent.
        } else {
            lblLastMessage.setText("");
        }
    }

    /**
     * Get response from chat with the given message ID and response code.
     *
     * @param messageId
     * @param responseCode
     */
    void receiveResponse(int messageId, int responseCode) {
        chat.receiveResponse(messageId, responseCode);
    }
}
