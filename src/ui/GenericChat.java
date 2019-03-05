package ui;

// imports
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.BoxLayout;
import javax.swing.UIManager;
import message.Message;
import network.client.ClientNetworkManager;
import network.protocol.MessageProtocol;

/**
 * GenericChat class displays an exchange of text messages between two users on a GUI form.
 * It allows the user to type a message and send it in the chat.
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class GenericChat extends javax.swing.JPanel 
{

    private static AtomicInteger idCounter = new AtomicInteger(); // Declare and instantiate new Atomic Integer for the ID counter.
    private String chatName; // // Declare string that stores chat name.
    private HashMap<Integer, MessagePanel> waitingForResponse; // Declare a HashMap called waitingForResponse.
    private SideBarChat sidebar; // Declare a SideBarChat object

    /**
     * Parameterized Constructor for GenericChat class, creates a chat form to be display on the Main Menu form.
     * @param chatName
     * @param sidebar 
     */
    public GenericChat(String chatName, SideBarChat sidebar) {
        this.chatName = chatName; // Set the chat name to chat name parameter.
        this.sidebar = sidebar; // Set sidebar chat to passed in sidebar parameter.
        initComponents(); // Initialise GUI components.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Adapt and optimize look and feel depending on OS.
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        lblChatName.setText(chatName); // Set chat name to chat name parameter. 
        pnlMessages.setLayout(new BoxLayout(pnlMessages, BoxLayout.Y_AXIS)); // Set message panel layout.
        waitingForResponse = new HashMap<>(); // Initialise HashMap.
    }

    /**
     * Get the ID of the next message.
     * @return 
     */
    public int getNextMessageId() {
        return idCounter.getAndIncrement(); // Return message ID counter and increment it.
    }

    /**
     * Saves messages in chat.
     * @param message 
     */
    private void saveMessage(Message message) {
        //TODO save message somewhere 
    }

    /**
     * Return the name of the chat.
     * @return 
     */
    public String getChatName() {
        return chatName; // Return chat name.
    }

    /**
     * Add message to chat, used when loading the chat.
     * @param message 
     */
    public void addMessage(Message message)
    {
        MessagePanel newMessage = new MessagePanel(message); // Declare and instantiate new message panel.
        pnlMessages.add(newMessage); // Add the message to the panel.
        newMessage.setSize(newMessage.getPreferredSize()); // Set the panel dimensions.
        newMessage.setVisible(true); 
        pnlMessages.revalidate(); // Layout the container.
        pnlMessages.repaint(); // Refresh GUI component.
        sidebar.setLastMessage(message); // Set the last message of the side abr to passed in message.
        if (message.getId() > idCounter.get()) 
        {
            idCounter.set(message.getId() + 1);
        }
        if (message.getSenderName().equals(ClientNetworkManager.getUsername()))
        {
            waitingForResponse.put(message.getId(), newMessage);
        }
    }

    /**
     * Adds received message to chat.
     *
     * @param message
     */
    public synchronized void receiveMessage(Message message) 
    {
        addMessage(message);
        saveMessage(message);
    }

     /**
     * Get response from chat with the given message ID and response code.
     *
     * @param messageId
     * @param responseCode
     */
    void receiveResponse(int messageId, int responseCode) {
        MessagePanel m = waitingForResponse.get(messageId);
        if (m != null) {
            m.receiveResponse(responseCode);
            if (responseCode == MessageProtocol.RESPONSE_READ) {
                waitingForResponse.remove(messageId);
            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblChatName = new javax.swing.JLabel();
        pnlScrollMessages = new javax.swing.JScrollPane();
        pnlMessages = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaMessage = new javax.swing.JTextArea();
        btnSend = new javax.swing.JButton();

        setBackground(new java.awt.Color(244, 244, 244));

        jPanel1.setBackground(new java.awt.Color(229, 229, 229));

        lblChatName.setFont(new java.awt.Font("Heiti SC", 1, 24)); // NOI18N
        lblChatName.setText("Chat Name");

        pnlMessages.setBackground(new java.awt.Color(204, 204, 204));
        pnlMessages.setOpaque(false);
        pnlMessages.setLayout(new java.awt.BorderLayout());
        pnlScrollMessages.setViewportView(pnlMessages);

        txaMessage.setColumns(20);
        txaMessage.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        txaMessage.setRows(4);
        jScrollPane2.setViewportView(txaMessage);

        btnSend.setBackground(new java.awt.Color(204, 204, 204));
        btnSend.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlScrollMessages, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblChatName)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lblChatName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlScrollMessages, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * If send button clicked, gets text entered, saves as a message and sends it.
     * @param evt 
     */
    private void btnSendActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSendActionPerformed
    {//GEN-HEADEREND:event_btnSendActionPerformed
        Message message = new Message(getNextMessageId(), ClientNetworkManager.getUsername(), chatName, txaMessage.getText());
        message.setSent(new Date());
        MessageProtocol.sendMessage(message);
        saveMessage(message);
        addMessage(message);
    }//GEN-LAST:event_btnSendActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSend;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblChatName;
    private javax.swing.JPanel pnlMessages;
    private javax.swing.JScrollPane pnlScrollMessages;
    private javax.swing.JTextArea txaMessage;
    // End of variables declaration//GEN-END:variables
}
