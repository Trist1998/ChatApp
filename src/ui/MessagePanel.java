package ui;

import java.awt.Color;
import message.Message;
import network.client.ClientNetworkManager;

/**
 *
 * @author Tristan
 */
public class MessagePanel extends javax.swing.JPanel
{
    private Message message;
    
    public MessagePanel(Message message)
    {
        this.message = message;
        initComponents();
        txaMessage.setText(message.getText());
        if(message.getSenderName().equals(ClientNetworkManager.getUsername()))
            this.setBackground(new Color(153,204,255));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        lblDateTime = new javax.swing.JLabel();
        lblRead = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaMessage = new javax.swing.JTextArea();

        setForeground(new java.awt.Color(153, 255, 153));
        setToolTipText("");

        lblDateTime.setText("lblDateTime");

        lblRead.setText("lblRead");

        txaMessage.setEditable(false);
        txaMessage.setColumns(20);
        txaMessage.setLineWrap(true);
        txaMessage.setRows(1);
        jScrollPane1.setViewportView(txaMessage);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblDateTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblRead))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDateTime)
                    .addComponent(lblRead)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDateTime;
    private javax.swing.JLabel lblRead;
    private javax.swing.JTextArea txaMessage;
    // End of variables declaration//GEN-END:variables
}
