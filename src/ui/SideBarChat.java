package ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import message.Message;

/**
 *
 * @author Tristan
 */
public class SideBarChat extends javax.swing.JPanel
{

    /**
     * Creates new form SideBarChat
     * @param chatName
     * @param lastMessage
     */
    private MainMenu mainMenu;
    private GenericChat chat;
    
    public SideBarChat(String chatName, MainMenu mm)
    {
        initComponents();
        lblChatName.setText(chatName);
        mainMenu = mm;
        this.chat = new GenericChat(chatName);
    }
    
    public SideBarChat(String chatName, Message lastMessage, MainMenu mm)
    {
        initComponents();
        lblChatName.setText(chatName);
        lblLastMessage.setText(lastMessage.getText());
        lblLastMessageTime.setText("Date");
        mainMenu = mm;
        this.chat = new GenericChat(chatName);
    }
    
    public String getChatName()
    {
        return chat.getChatName();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        lblLastMessageTime = new javax.swing.JLabel();
        lblLastMessage = new javax.swing.JLabel();
        lblChatName = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                formMouseClicked(evt);
            }
        });

        lblLastMessageTime.setText("Last Message Time");

        lblLastMessage.setText("Last Message");

        lblChatName.setText("Chat Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 207, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(lblLastMessageTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLastMessage)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_formMouseClicked
    {//GEN-HEADEREND:event_formMouseClicked
        System.out.println("Clicked This panel");
        mainMenu.setChat(chat);
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblChatName;
    private javax.swing.JLabel lblLastMessage;
    private javax.swing.JLabel lblLastMessageTime;
    // End of variables declaration//GEN-END:variables
//    @Override
//    public Dimension getPreferredSize()
//    {
//        return new Dimension(getWidth(),getHeight());
//    }
    public void receiveMessage(Message message)
    {
        lblLastMessage.setText(message.getText());
        lblLastMessageTime.setText(new Date().toString());
        chat.receiveMessage(message);
    }
}
