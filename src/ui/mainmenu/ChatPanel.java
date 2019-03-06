package ui.mainmenu;

// imports
import file.FileMessage;
import file.FileNetworkManager;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.UIManager;
import message.LocalChatStorage;
import message.Message;
import network.client.ClientNetworkManager;
import network.protocol.MessageNetworkManager;

/**
 * ChatPanel class displays an exchange of text messages between two users on a GUI form.
 * It allows the user to type a message and send it in the chat.
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class ChatPanel extends javax.swing.JPanel 
{

    private static final AtomicInteger idCounter = new AtomicInteger(); // Declare and instantiate new Atomic Integer for the ID counter.
    private String chatName; // // Declare string that stores chat name.
    private final HashMap<Integer, ResponseReceiver> waitingForResponse; // Declare a HashMap called waitingForResponse.
    private SideBarChat sidebar; // Declare a SideBarChat object
    private ArrayList<Message> messageQueue;
    public boolean hasBeenOpened;

    /**
     * Parameterized Constructor for ChatPanel class, creates a chat form to be display on the Main Menu form.
     * @param chatName
     * @param sidebar 
     */
    public ChatPanel(String chatName, SideBarChat sidebar) 
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
        messageQueue = new ArrayList<>();
        lblChatName.setText(chatName); // Set chat name to chat name parameter. 
        pnlMessages.setLayout(new BoxLayout(pnlMessages, BoxLayout.Y_AXIS)); // Set message panel layout.
        waitingForResponse = new HashMap<>(); // Initialise HashMap.
        hasBeenOpened = false;
    }
    
    public void setUpButton()
    {
        if(messageQueue.isEmpty())
            btnLoadChat.setVisible(false);
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
        LocalChatStorage.saveMessage(message);
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
        
        if (message.getSenderName().equals(ClientNetworkManager.getUsername())) 
        {
            
            if (message.getState() < 2) 
            {
                waitingForResponse.put(message.getId(), newMessage);
            }
            if (message.getId() > idCounter.get()) 
            {
                idCounter.set(message.getId() + 1);
            }
        }
        
        JScrollBar sb = pnlScrollMessages.getVerticalScrollBar();
        sb.setValue(sb.getMaximum());
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
        btnSendFile = new javax.swing.JButton();
        btnSend = new javax.swing.JButton();
        btnLoadChat = new javax.swing.JButton();

        setBackground(new java.awt.Color(244, 244, 244));
        setMaximumSize(new java.awt.Dimension(704, 599));

        jPanel1.setBackground(new java.awt.Color(222, 222, 222));

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

        btnSendFile.setBackground(new java.awt.Color(204, 204, 204));
        btnSendFile.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        btnSendFile.setText("Send File");
        btnSendFile.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnSendFileActionPerformed(evt);
            }
        });

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

        btnLoadChat.setText("Load Chat");
        btnLoadChat.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnLoadChatActionPerformed(evt);
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
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblChatName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLoadChat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSendFile)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblChatName))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnSendFile))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnLoadChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1, 1, 1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlScrollMessages, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(btnSend, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        processQueue();
        btnLoadChat.setVisible(false);
        if(!txaMessage.getText().equals(""))
        {
            Message message = new Message(getNextMessageId(), chatName, ClientNetworkManager.getUsername(), chatName, txaMessage.getText());
            message.setSent(new Date());
            MessageNetworkManager.sendMessage(message);
            saveMessage(message);
            addMessage(message);
            txaMessage.setText("");
        }
    }//GEN-LAST:event_btnSendActionPerformed

    private void btnSendFileActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSendFileActionPerformed
    {//GEN-HEADEREND:event_btnSendFileActionPerformed
        processQueue();
        JFileChooser fc = new JFileChooser(getDirectory());
        fc.showOpenDialog(jPanel1);
        fc.setVisible(true);
        File file = fc.getSelectedFile();
        
        if(file.exists())
        {
            FileMessage fileMessage = new FileMessage(getNextMessageId(), chatName, ClientNetworkManager.getUsername(), chatName, file); 
            FileMessagePanel mP = addFileMessage(fileMessage);
            new Thread(new Runnable()
            {
                public void run()
                {
                    FileNetworkManager.uploadFile(file, fileMessage, mP);
                }
            }).start();
            LocalChatStorage.saveFileMessage(fileMessage);
        }
        
        
            
    }//GEN-LAST:event_btnSendFileActionPerformed

    private void btnLoadChatActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnLoadChatActionPerformed
    {//GEN-HEADEREND:event_btnLoadChatActionPerformed
        processQueue();
        btnLoadChat.setVisible(false);
    }//GEN-LAST:event_btnLoadChatActionPerformed
    
    private static String getDirectory()
    {
        return System.getProperty("user.dir") +"/downloads/";
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoadChat;
    private javax.swing.JButton btnSend;
    private javax.swing.JButton btnSendFile;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblChatName;
    private javax.swing.JPanel pnlMessages;
    private javax.swing.JScrollPane pnlScrollMessages;
    private javax.swing.JTextArea txaMessage;
    // End of variables declaration//GEN-END:variables

    

    public FileMessagePanel addFileMessage(FileMessage message)
    {
        FileMessagePanel fp = new FileMessagePanel(message);
        
        JPanel pnl = new JPanel();
        pnl.setMaximumSize(new Dimension(pnlMessages.getSize().width, fp.getPreferredSize().height));
        fp.setSize(fp.getPreferredSize());
        fp.setVisible(true);
        pnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        if(message.isUserAlsoSender())
            pnl.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        pnl.add(fp);
       
        pnlMessages.add(Box.createRigidArea(new Dimension(0,5)));
        pnlMessages.add(pnl);
        pnl.setVisible(true);
        pnlMessages.revalidate();
        pnlMessages.repaint();
        
        if (message.getId() > idCounter.get()) 
        {
            idCounter.set(message.getId() + 1);
        }
        if (message.getSenderName().equals(ClientNetworkManager.getUsername())) 
        {
            waitingForResponse.put(message.getId(), fp);
        }
        
        JScrollBar sb = pnlScrollMessages.getVerticalScrollBar();
        sb.setValue( sb.getMaximum());
        return fp;
    }

    private void addToQueue(Message message)
    {
        messageQueue.add(message);
    }
    
    public void processQueue()
    {
        if(!hasBeenOpened)
        {
            btnLoadChat.setVisible(false);
            hasBeenOpened = true;
            for (Message message : messageQueue)
            {
                if(message.getText().equals("_FILE_"))
                    addFileMessage((FileMessage)message);
                else
                    addMessage(message);
            }
        }  
    }
    
    /**\
     * 
     * @param message 
     */
    public void receiveSavedMessage(Message message)
    {
        addToQueue(message);
    }
    
    /**
     *
     * @param message
     */
    public void receiveSavedFile(FileMessage message)
    {
        message.setText("_FILE_");
        addToQueue(message);      
    }
    
    public void receiveFile(FileMessage message)
    {
        addFileMessage(message);
        LocalChatStorage.saveFileMessage(message);
    }
    
    /**
     * Get response from chat with the given message ID and response code.
     *
     * @param messageId
     * @param responseCode
     */
    public void receiveResponse(int messageId, int responseCode) 
    {
        ResponseReceiver m = waitingForResponse.get(messageId);
        if (m != null) 
        {
            m.receiveResponse(responseCode);
            if (responseCode == MessageNetworkManager.RESPONSE_READ) 
            {
                waitingForResponse.remove(messageId);
            }
        }
    }
    
    /**
     * Adds received message to chat.
     *
     * @param message
     */
    public synchronized void receiveMessage(Message message) 
    {
        if(hasBeenOpened)
            addMessage(message);
        else
            addToQueue(message);
        saveMessage(message);
    }
}
