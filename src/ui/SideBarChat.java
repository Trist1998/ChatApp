package ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.UIManager;
import message.Message;

/**
 *
 * @author Tristan
 */
public class SideBarChat extends javax.swing.JPanel
{

    /**
     * Creates new form SideBarChat
     *
     * @param chatName
     * @param lastMessage
     */
    private MainMenu mainMenu;
    private GenericChat chat;

    public SideBarChat(String chatName, MainMenu mm) 
    {
        initComponents();
        try 
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        lblChatName.setText(chatName);
        lblLastMessage.setText("No Messages");
        lblLastMessageTime.setText("");
        mainMenu = mm;
        this.chat = new GenericChat(chatName, this);
    }

    public SideBarChat(String chatName, Message lastMessage, MainMenu mm) 
    {
        initComponents();
        try 
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        lblChatName.setText(chatName);
        lblLastMessage.setText(lastMessage.getText());
        lblLastMessageTime.setText(lastMessage.getDateSentString());
        mainMenu = mm;
        this.chat = new GenericChat(chatName, this);
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

        setBackground(new java.awt.Color(244, 244, 244));
        setAlignmentX(0.0F);
        setAlignmentY(0.0F);
        setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        setMaximumSize(new java.awt.Dimension(275, 90));
        setMinimumSize(null);
        setPreferredSize(new java.awt.Dimension(275, 90));
        addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
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
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblLastMessageTime))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblChatName)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblLastMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblChatName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLastMessage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblLastMessageTime)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_formMouseClicked
    {//GEN-HEADEREND:event_formMouseClicked
        mainMenu.setChat(chat);
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblChatName;
    private javax.swing.JLabel lblLastMessage;
    private javax.swing.JLabel lblLastMessageTime;
    // End of variables declaration//GEN-END:variables

    public void receiveMessage(Message message) 
    {
        setLastMessage(message);
        chat.receiveMessage(message);
    }

    public void setLastMessage(Message message) 
    {
        lblLastMessage.setText(message.getText());
        if (message.getSent() != null) 
        {
            Date date = message.getSent();
            Date now = new Date();
            if(now.getDate() == date.getDate() && now.getMonth() == date.getMonth())
                lblLastMessageTime.setText(new SimpleDateFormat("HH:mm").format(date));
            else
                lblLastMessageTime.setText(new SimpleDateFormat("dd/MM").format(date));
        } 
        else 
        {
            lblLastMessage.setText("");
        }
    }

    public void receiveResponse(int messageId, int responseCode) 
    {
        chat.receiveResponse(messageId, responseCode);
    }
}
