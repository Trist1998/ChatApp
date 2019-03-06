package ui.mainmenu;

// Imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import network.client.ClientNetworkManager;
import network.protocol.MessageNetworkManager;
import ui.ChatManager;

/**
 * MainMenu class allows a user to send and receive messages to other users of the ChatApp.
 * @author @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class MainMenu extends javax.swing.JFrame 
{

    private ChatPanel currentChat; // Stores reference to GenericChat open in the window
    
    /**
     * Non-Parameterized Constructor for MainMenu Class create the Main Menu form.
     */
    public MainMenu() 
    {
        initComponents(); // Initialise GUI components.
        this.setLocationRelativeTo(null); // Set frame to center of screen.
        try 
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Adapt and optimize look and feel depending on OS.
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        lblUsername.setText(ClientNetworkManager.getUsername()); // Set label to username.

        BoxLayout layout = new BoxLayout(pnlChats, BoxLayout.Y_AXIS);
        pnlChats.setLayout(layout);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        pnlChatWindow = new javax.swing.JPanel();
        btnAddChat = new javax.swing.JButton();
        pnlScrollChats = new javax.swing.JScrollPane();
        pnlChats = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblUsername = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ChatApp Main Menu");
        setBackground(new java.awt.Color(229, 229, 229));
        setBounds(new java.awt.Rectangle(0, 0, 1000, 600));
        setMinimumSize(new java.awt.Dimension(1000, 600));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(244, 244, 244));
        jPanel1.setAlignmentX(0.0F);
        jPanel1.setAlignmentY(0.0F);
        jPanel1.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        jPanel1.setMaximumSize(new java.awt.Dimension(1000, 600));
        jPanel1.setName(""); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 600));

        pnlChatWindow.setBackground(new java.awt.Color(224, 239, 255));
        pnlChatWindow.setAlignmentX(0.0F);
        pnlChatWindow.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        pnlChatWindow.setMaximumSize(new java.awt.Dimension(704, 599));
        pnlChatWindow.setLayout(new javax.swing.BoxLayout(pnlChatWindow, javax.swing.BoxLayout.LINE_AXIS));

        btnAddChat.setBackground(new java.awt.Color(255, 255, 255));
        btnAddChat.setFont(new java.awt.Font("Heiti SC", 1, 10)); // NOI18N
        btnAddChat.setText("Add Chat");
        btnAddChat.setAlignmentY(0.0F);
        btnAddChat.setBorder(null);
        btnAddChat.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnAddChatActionPerformed(evt);
            }
        });

        pnlScrollChats.setBorder(null);
        pnlScrollChats.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pnlScrollChats.setAlignmentX(0.0F);
        pnlScrollChats.setAlignmentY(0.0F);

        pnlChats.setBackground(new java.awt.Color(198, 240, 255));
        pnlChats.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        pnlChats.setLayout(new java.awt.BorderLayout());
        pnlScrollChats.setViewportView(pnlChats);

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));

        lblUsername.setFont(new java.awt.Font("Heiti SC", 1, 13)); // NOI18N
        lblUsername.setText("Username");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUsername)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUsername)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAddChat)
                            .addComponent(pnlScrollChats, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlChatWindow, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddChat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlScrollChats))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlChatWindow, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddChatActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnAddChatActionPerformed
    {//GEN-HEADEREND:event_btnAddChatActionPerformed
        String chatName = JOptionPane.showInputDialog("Enter user name:");
        if(chatName != null && !chatName.equals("") && !chatName.equals(ClientNetworkManager.getUsername()))//TODO check that user exists
            ChatManager.createChat(chatName);
    }//GEN-LAST:event_btnAddChatActionPerformed

    
    public synchronized void addChat(SideBarChat sideBarChat)
    {
        SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                JPanel pnl = new JPanel();
                pnl.setLayout(new FlowLayout(FlowLayout.LEFT));
                pnl.setMaximumSize(new Dimension(pnlChats.getSize().width, sideBarChat.getPreferredSize().height + 10));
                pnl.setBackground(new Color(95,200,255));
                    sideBarChat.setSize(new Dimension(pnlChats.getWidth(), sideBarChat.getPreferredSize().height));
                    sideBarChat.setVisible(true);
                    pnl.add(sideBarChat);
                    pnlChats.add(Box.createRigidArea(new Dimension(0,5)));
                    pnlChats.add(pnl);
                    pnlChats.revalidate();
                    pnlChats.repaint();
                
            }
        });
    }

    /**
     * When a user selects a chat, it will be displayed on the MainMenu form.
     * @param chat 
     */
    public synchronized void setChat(ChatPanel chat) 
    {
        //System.out.println("added chat on event dispatch thread? " + javax.swing.SwingUtilities.isEventDispatchThread());     
        if (currentChat != chat) 
        {
            if (currentChat != null) 
            {
                pnlChatWindow.remove(currentChat);                              
            }
            currentChat = chat;
            chat.setMaximumSize(pnlChatWindow.getSize());
            chat.setSize(pnlChatWindow.getWidth(), pnlChatWindow.getHeight());
            chat.setUpButton();
            chat.setVisible(true);
            pnlChatWindow.setLayout(new BoxLayout(pnlChatWindow, 0));
            pnlChatWindow.add(chat);
            pnlChatWindow.revalidate();
            pnlChatWindow.repaint();
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddChat;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel pnlChatWindow;
    private javax.swing.JPanel pnlChats;
    private javax.swing.JScrollPane pnlScrollChats;
    // End of variables declaration//GEN-END:variables

    public void start()
    {
        try
        {
            ChatManager.buildChatViews(this);                   
        } 
        catch (IOException ex)
        {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        MessageNetworkManager.requestStoredMessages();
        this.setVisible(true);
    }
}
