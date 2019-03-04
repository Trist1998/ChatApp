package ui;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import network.client.ClientNetworkManager;

/**
 *
 * @author Tristan
 */
public class MainMenu extends javax.swing.JFrame {

    private ChatManager manager;
    private GenericChat currentChat;

    public MainMenu() {
        initComponents();
        this.setLocationRelativeTo(null);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        lblUsername.setText(ClientNetworkManager.getUsername());

        BoxLayout layout = new BoxLayout(pnlScrollChats, BoxLayout.Y_AXIS);
        pnlScrollChats.setLayout(layout);
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
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setPreferredSize(new java.awt.Dimension(1200, 600));

        jPanel1.setBackground(new java.awt.Color(244, 244, 244));
        jPanel1.setAlignmentX(0.0F);
        jPanel1.setAlignmentY(0.0F);
        jPanel1.setMaximumSize(new java.awt.Dimension(1200, 600));
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
                .addComponent(pnlChatWindow, javax.swing.GroupLayout.DEFAULT_SIZE, 909, Short.MAX_VALUE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 645, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        String chatName = JOptionPane.showInputDialog("Enter user name:");
        ChatManager.createChat(chatName);
    }//GEN-LAST:event_jButton1ActionPerformed

    public synchronized void addChat(SideBarChat sideBarChat) {
        System.out.println("added chat on event dispatch thread? " + javax.swing.SwingUtilities.isEventDispatchThread());

        sideBarChat.setPreferredSize(new Dimension(pnlScrollChats.getWidth(), sideBarChat.getPreferredSize().height));
        sideBarChat.setVisible(true);
        pnlScrollChats.add(sideBarChat);
        pnlScrollChats.revalidate();
        pnlScrollChats.repaint();
    }

    public synchronized void setChat(GenericChat chat) {
        //System.out.println("added chat on event dispatch thread? " + javax.swing.SwingUtilities.isEventDispatchThread());     
        if (currentChat != chat) {
            if (currentChat != null) {
                pnlChatWindow.remove(currentChat);
            }
            chat.setSize(pnlChatWindow.getWidth(), pnlChatWindow.getHeight());
            chat.setVisible(true);
            pnlChatWindow.add(chat);
            pnlChatWindow.revalidate();
            pnlChatWindow.repaint();
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
