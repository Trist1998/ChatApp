package ui;

import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.BoxLayout;
import message.Message;
import network.client.ClientNetworkManager;
import network.protocol.MessageProtocol;

/**
 * @author Tristan
 */
public class GenericChat extends javax.swing.JPanel 
{
    private static AtomicInteger idCounter = new AtomicInteger();
    private String chatName;
    
    public GenericChat(String chatName) 
    {
        this.chatName = chatName;
        initComponents();
        lblChatName.setText(chatName);
        pnlMessages.setLayout(new BoxLayout(pnlMessages, BoxLayout.Y_AXIS));
    }
    public int getNextMessageId()
    {
        return idCounter.getAndIncrement();
    }
    
    private void saveMessage(Message message)
    {
        //TODO save message somewhere 
    }
    
    public String getChatName()
    {
      return chatName;   
    }
    
    public void addMessage(Message message)//Use this method when loading the chat 
    { 
        MessagePanel newMessage = new MessagePanel(message);
        pnlMessages.add(newMessage);
        newMessage.setSize(newMessage.getPreferredSize());
        newMessage.setVisible(true);
        pnlMessages.revalidate();
        pnlMessages.repaint();
    }
    
    public synchronized void receiveMessage(Message message)
    {      
        addMessage(message);
        saveMessage(message);
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

        setBackground(new java.awt.Color(229, 229, 229));

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

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSendActionPerformed
    {//GEN-HEADEREND:event_btnSendActionPerformed
        Message message = new Message(getNextMessageId(), ClientNetworkManager.getUsername(), chatName, txaMessage.getText());
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
