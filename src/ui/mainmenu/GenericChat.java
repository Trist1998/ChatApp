package ui.mainmenu;

// imports
import ui.mainmenu.SideBarChat;
import ui.mainmenu.MessagePanel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.UIManager;
import message.Message;
import network.client.ClientNetworkManager;
import network.protocol.MessageNetworkManager;

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
    public GenericChat(String chatName, SideBarChat sidebar) 
    {
        this.chatName = chatName; // Set the chat name to chat name parameter.
        this.sidebar = sidebar; // Set sidebar chat to passed in sidebar parameter.
        initComponents(); // Initialise GUI components.
        try 
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Adapt and optimize look and feel depending on OS.
        } 
        catch (Exception e)
        {
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
    public int getNextMessageId() 
    {
        return idCounter.getAndIncrement(); // Return message ID counter and increment it.
    }

    /**
     * Saves messages in chat.
     * @param message 
     */
    private void saveMessage(Message message) 
    {
        //TODO save message somewhere 
    }

    /**
     * Return the name of the chat.
     * @return 
     */
    public String getChatName() 
    {
        return chatName; // Return chat name.
    }
    
    public void addMessage(Message message)//Use this method when loading the chat 
    { 
        MessagePanel newMessage = new MessagePanel(message, pnlMessages);
        JPanel pnl = new JPanel();
        pnl.setMaximumSize(new Dimension(pnlMessages.getSize().width, newMessage.getPreferredSize().height));
        newMessage.setSize(newMessage.getPreferredSize());
        newMessage.setVisible(true);
        pnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        if(message.isUserAlsoSender())
            pnl.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        pnl.add(newMessage);
       
        pnlMessages.add(Box.createRigidArea(new Dimension(0,5)));
        pnlMessages.add(pnl);
        pnl.setVisible(true);
        pnlMessages.revalidate();
        pnlMessages.repaint();
        
        sidebar.setLastMessage(message);
        if (message.getId() > idCounter.get()) 
        {
            idCounter.set(message.getId() + 1);
        }
        if (message.getSenderName().equals(ClientNetworkManager.getUsername())) 
        {
            waitingForResponse.put(message.getId(), newMessage);
        }
        
        JScrollBar sb = pnlScrollMessages.getVerticalScrollBar();
        sb.setValue( sb.getMaximum());
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
    public void receiveResponse(int messageId, int responseCode) 
    {
        MessagePanel m = waitingForResponse.get(messageId);
        if (m != null) 
        {
            m.receiveResponse(responseCode);
            if (responseCode == MessageNetworkManager.RESPONSE_READ) 
            {
                waitingForResponse.remove(messageId);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        lblChatName = new javax.swing.JLabel();
        pnlScrollMessages = new javax.swing.JScrollPane();
        pnlMessages = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaMessage = new javax.swing.JTextArea();
        btnSend = new javax.swing.JButton();

        setBackground(new java.awt.Color(244, 244, 244));
        setMaximumSize(new java.awt.Dimension(704, 599));

        jPanel1.setBackground(new java.awt.Color(229, 229, 229));

        lblChatName.setFont(new java.awt.Font("Heiti SC", 1, 24)); // NOI18N
        lblChatName.setText("Chat Name");

        pnlScrollMessages.setBackground(new java.awt.Color(153, 0, 0));

        pnlMessages.setBackground(new java.awt.Color(255, 153, 255));
        pnlMessages.setForeground(new java.awt.Color(51, 51, 255));
        pnlMessages.setAlignmentX(0.0F);
        pnlMessages.setAlignmentY(0.0F);
        pnlMessages.setOpaque(false);
        pnlMessages.setLayout(new javax.swing.BoxLayout(pnlMessages, javax.swing.BoxLayout.LINE_AXIS));
        pnlScrollMessages.setViewportView(pnlMessages);

        txaMessage.setColumns(20);
        txaMessage.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        txaMessage.setLineWrap(true);
        txaMessage.setRows(4);
        jScrollPane2.setViewportView(txaMessage);

        btnSend.setBackground(new java.awt.Color(204, 204, 204));
        btnSend.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
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
                    .addComponent(pnlScrollMessages)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblChatName)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSend)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lblChatName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlScrollMessages, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        MessageNetworkManager.sendMessage(message);
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
