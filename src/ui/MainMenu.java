package ui;

import java.awt.Dimension;
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
        jButton1 = new javax.swing.JButton();
        lblUsername = new javax.swing.JLabel();
        pnlChats = new javax.swing.JScrollPane();
        pnlScrollChats = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlChatWindow.setBackground(new java.awt.Color(204, 204, 255));
        pnlChatWindow.setLayout(new javax.swing.BoxLayout(pnlChatWindow, javax.swing.BoxLayout.LINE_AXIS));

        jButton1.setText("Add chat");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });

        lblUsername.setText("Username");

        pnlChats.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pnlScrollChats.setBackground(new java.awt.Color(204, 255, 255));
        pnlScrollChats.setLayout(new java.awt.BorderLayout());
        pnlChats.setViewportView(pnlScrollChats);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(23, 23, 23)
                            .addComponent(lblUsername))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGap(121, 121, 121)
                            .addComponent(jButton1)))
                    .addComponent(pnlChats, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlChatWindow, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlChatWindow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblUsername)
                .addGap(3, 3, 3)
                .addComponent(jButton1)
                .addGap(18, 18, Short.MAX_VALUE)
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        String chatName = JOptionPane.showInputDialog("Enter user name:");
        ChatManager.createChat(chatName);
    }//GEN-LAST:event_jButton1ActionPerformed

    
    public synchronized void addChat(SideBarChat sideBarChat)
    {
            System.out.println("added chat on event dispatch thread? " + javax.swing.SwingUtilities.isEventDispatchThread());
            
            sideBarChat.setPreferredSize(new Dimension(pnlScrollChats.getWidth(), sideBarChat.getPreferredSize().height));
            sideBarChat.setVisible(true);
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
