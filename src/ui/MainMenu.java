package ui;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import network.client.ClientNetworkManager;

/**
 *
 * @author Tristan
 */
public class MainMenu extends javax.swing.JFrame
{
    private ChatManager manager;
    private GenericChat currentChat;
    
    public MainMenu() 
    {
        initComponents();
        lblUsername.setText(ClientNetworkManager.getUsername());
        
        BoxLayout layout = new BoxLayout(pnlScrollChats, BoxLayout.Y_AXIS);
        pnlScrollChats.setLayout(layout);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        pnlChatWindow = new javax.swing.JPanel();
        btnAddChat = new javax.swing.JButton();
        lblUsername = new javax.swing.JLabel();
        pnlChats = new javax.swing.JScrollPane();
        pnlScrollChats = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlChatWindow.setBackground(new java.awt.Color(204, 204, 255));
        pnlChatWindow.setLayout(new javax.swing.BoxLayout(pnlChatWindow, javax.swing.BoxLayout.LINE_AXIS));

        btnAddChat.setText("Add chat");
        btnAddChat.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnAddChatActionPerformed(evt);
            }
        });

        lblUsername.setText("Username");

        pnlChats.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pnlChats.setAutoscrolls(true);
        pnlChats.setMaximumSize(new java.awt.Dimension(360, 32767));
        pnlChats.setMinimumSize(new java.awt.Dimension(360, 7));

        pnlScrollChats.setBackground(new java.awt.Color(204, 255, 255));
        pnlScrollChats.setAlignmentY(0.5F);
        pnlScrollChats.setMinimumSize(new java.awt.Dimension(360, 0));
        pnlScrollChats.setLayout(new javax.swing.BoxLayout(pnlScrollChats, javax.swing.BoxLayout.LINE_AXIS));
        pnlChats.setViewportView(pnlScrollChats);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(lblUsername))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(207, 207, 207)
                        .addComponent(btnAddChat))
                    .addComponent(pnlChats, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlChatWindow, javax.swing.GroupLayout.DEFAULT_SIZE, 841, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlChatWindow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(btnAddChat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddChatActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnAddChatActionPerformed
    {//GEN-HEADEREND:event_btnAddChatActionPerformed
        String chatName = JOptionPane.showInputDialog("Enter user name:");
        ChatManager.createChat(chatName);
    }//GEN-LAST:event_btnAddChatActionPerformed

    
    public synchronized void addChat(SideBarChat sideBarChat)
    {
            sideBarChat.setSize(new Dimension(pnlScrollChats.getWidth(), sideBarChat.getPreferredSize().height));
            sideBarChat.setVisible(true);
            pnlScrollChats.add(Box.createRigidArea(new Dimension(0,5)));
            pnlScrollChats.add(sideBarChat);
            pnlScrollChats.revalidate();
            pnlScrollChats.repaint();
    }
    
    public synchronized void setChat(GenericChat chat)
    {
        //System.out.println("added chat on event dispatch thread? " + javax.swing.SwingUtilities.isEventDispatchThread());     
        if(currentChat != chat)
        {
            if(currentChat != null)
                pnlChatWindow.remove(currentChat);
            chat.setSize(pnlChatWindow.getWidth(), pnlChatWindow.getHeight());
            chat.setVisible(true);
            pnlChatWindow.setLayout(new BoxLayout(pnlChatWindow,0));
            pnlChatWindow.add(chat);
            pnlChatWindow.revalidate();
            pnlChatWindow.repaint();
        }
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddChat;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel pnlChatWindow;
    private javax.swing.JScrollPane pnlChats;
    private javax.swing.JPanel pnlScrollChats;
    // End of variables declaration//GEN-END:variables
}
