package ui;

// Imports
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import network.client.ClientNetworkManager;

/**
 * MainMenu class allows a user to send and receive messages to other users of the ChatApp.
 * @author @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class MainMenu extends javax.swing.JFrame 
{

    private ChatManager manager; // Declare new instance of ChatManager.
    private GenericChat currentChat; // Declare new instance of GenericChat.
    
    /**
     * Non-Parameterized Constructor for MainMenu Class create the Main Menu form.
     */
    public MainMenu() 
    {
        initComponents(); // Initialise GUI components.
        this.setLocationRelativeTo(null); // Set frame to center of screen.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Adapt and optimize look and feel depending on OS.
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        lblUsername.setText(ClientNetworkManager.getUsername()); // Set label to username.

        BoxLayout layout = new BoxLayout(pnlScrollChats, BoxLayout.Y_AXIS); // Declare and instantiate new BoxLayout.
        pnlScrollChats.setLayout(layout); // Set chat scroll panel layout.
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        pnlChatWindow = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        lblUsername = new javax.swing.JLabel();
        pnlChats = new javax.swing.JScrollPane();
        pnlScrollChats = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ChatApp Main Menu");
        setBackground(new java.awt.Color(229, 229, 229));
        setBounds(new java.awt.Rectangle(0, 0, 1000, 600));
        setMaximumSize(new java.awt.Dimension(1000, 600));
        setMinimumSize(new java.awt.Dimension(1000, 600));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(244, 244, 244));
        jPanel1.setAlignmentX(0.0F);
        jPanel1.setAlignmentY(0.0F);
        jPanel1.setBounds(new java.awt.Rectangle(0, 0, 1000, 600));
        jPanel1.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        jPanel1.setMaximumSize(new java.awt.Dimension(1000, 600));
        jPanel1.setPreferredSize(new java.awt.Dimension(1200, 600));

        pnlChatWindow.setBackground(new java.awt.Color(224, 239, 255));
        pnlChatWindow.setAlignmentX(0.0F);
        pnlChatWindow.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        pnlChatWindow.setLayout(new javax.swing.BoxLayout(pnlChatWindow, javax.swing.BoxLayout.LINE_AXIS));

        jButton1.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        jButton1.setText("Add Chat");
        jButton1.setAlignmentY(0.0F);
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lblUsername.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        lblUsername.setText("Username");

        pnlChats.setBorder(null);
        pnlChats.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pnlChats.setAlignmentX(0.0F);
        pnlChats.setAlignmentY(0.0F);

        pnlScrollChats.setBackground(new java.awt.Color(198, 240, 255));
        pnlScrollChats.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        pnlScrollChats.setLayout(new java.awt.BorderLayout());
        pnlChats.setViewportView(pnlScrollChats);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsername)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(27, 27, 27))))
                    .addComponent(pnlChats, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlChatWindow, javax.swing.GroupLayout.DEFAULT_SIZE, 709, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlChatWindow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(pnlChats, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, 1000, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * If the Add Chat button is clicked, display a form to user to enter who to send message to.
     * @param evt 
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        String chatName = JOptionPane.showInputDialog("Enter user name:"); // Get receiver name.
        ChatManager.createChat(chatName); // Create a chat and display it on MainMenu form.
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * Adds chat and display it on MainMenu form.
     * @param sideBarChat 
     */
    public synchronized void addChat(SideBarChat sideBarChat) {
        System.out.println("Added chat on event dispatch thread? " + javax.swing.SwingUtilities.isEventDispatchThread());

        sideBarChat.setPreferredSize(new Dimension(pnlScrollChats.getWidth(), sideBarChat.getPreferredSize().height)); // Set dimensions of SideBarChat.
        sideBarChat.setVisible(true); // Make the SideBarChat visible.
        pnlScrollChats.add(sideBarChat); // Display SideBarChat on MainMenu.
        pnlScrollChats.revalidate(); // Layout the container,
        pnlScrollChats.repaint(); // Refresh GUI componenet.
    }

    /**
     * When a user selects a chat, it will be displayed on the MainMenu form.
     * @param chat 
     */
    public synchronized void setChat(GenericChat chat) {
        //System.out.println("added chat on event dispatch thread? " + javax.swing.SwingUtilities.isEventDispatchThread());     
        if (currentChat != chat) // If the current displayed chat is not the chat is seelcted.
        {
            if (currentChat != null) {
                pnlChatWindow.remove(currentChat); // Remove from the display.
            }
            
            chat.setSize(pnlChatWindow.getWidth(), pnlChatWindow.getHeight()); // Set chat dimensions.
            chat.setVisible(true); // Make the chat visible.
            pnlChatWindow.add(chat); // Display chat on MainMenu.
            pnlChatWindow.revalidate(); // Layout the container.
            pnlChatWindow.repaint(); // Refresh GUI component.
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel pnlChatWindow;
    private javax.swing.JScrollPane pnlChats;
    private javax.swing.JPanel pnlScrollChats;
    // End of variables declaration//GEN-END:variables
}
