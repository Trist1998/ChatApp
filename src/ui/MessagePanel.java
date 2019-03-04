package ui;

import java.awt.Color;
import javax.swing.UIManager;
import message.Message;
import network.client.ClientNetworkManager;
import network.server.ServerConnectionHandler;

/**
 *
 * @author Tristan
 */
public class MessagePanel extends javax.swing.JPanel {

    private Message message;
    private int state;
    
    
    public MessagePanel(Message message, JPanel parent)
    {
        this.message = message;
        state = -1;
        
        initComponents();
        txaMessage.setSize(pnlContainer.getWidth(), getContentHeight(message.getText()));
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        txaMessage.setText(message.getText());
        lblDateTime.setText(message.getDateSentString());
        setMaximumSize(new Dimension(parent.getWidth(), pnlContainer.getPreferredSize().height));
        pnlContainer.setMaximumSize(new Dimension(getWidth() - 100, getPreferredSize().height));
        
        setAlignmentX(Component.LEFT_ALIGNMENT);
        if(message.getSenderName().equals(ClientNetworkManager.getUsername()))
        {
            setAlignmentX(Component.RIGHT_ALIGNMENT);
            Color colour = new Color(153,204,255);
            pnlContainer.setBackground(colour);
            txaMessage.setBackground(colour);
        }
            
        if (message.getSenderName().equals(ClientNetworkManager.getUsername())) {
            this.setBackground(new Color(153, 204, 255));
        }
        setStateLabel();
    }

    private void setStateLabel() {
        System.out.println("Hello");
        switch (message.getState()) {
            case ServerConnectionHandler.MESSAGE_DELIVERED:
                lblState.setText("Delivered");
                break;
            case ServerConnectionHandler.MESSAGE_SAVED:
                lblState.setText("Saved");
                break;
            case ServerConnectionHandler.MESSAGE_LOST:
                lblState.setText("Lost");
                break;
            default:
                lblState.setText("");
                break;
        }
    }
    public int getContentHeight(String content)
    {
        JEditorPane dummyEditorPane=new JEditorPane();
        dummyEditorPane.setSize(txaMessage.getWidth(),Short.MAX_VALUE);
        dummyEditorPane.setText(content);
        
        return dummyEditorPane.getPreferredSize().height;
    }
    
    public void receiveResponse(int responseCode)
    {
        message.setState(responseCode);
        setStateLabel();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlContainer = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaMessage = new javax.swing.JTextArea();
        lblDateTime = new javax.swing.JLabel();
        lblState = new javax.swing.JLabel();

        setBackground(new java.awt.Color(244, 244, 244));
        setForeground(new java.awt.Color(153, 255, 153));
        setToolTipText("");
        setAlignmentX(0.0F);
        setAlignmentY(0.0F);

        lblDateTime.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        lblDateTime.setText("lblDateTime");

        lblState.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        lblState.setText("lblState");
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txaMessage.setEditable(false);
        txaMessage.setColumns(20);
        txaMessage.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        txaMessage.setLineWrap(true);
        txaMessage.setRows(1);
        txaMessage.setBorder(null);
        txaMessage.setMaximumSize(new java.awt.Dimension(500, 500));
        jScrollPane1.setViewportView(txaMessage);

        lblDateTime.setText("lblDateTime");

        lblState.setText("lblState");

        javax.swing.GroupLayout pnlContainerLayout = new javax.swing.GroupLayout(pnlContainer);
        pnlContainer.setLayout(pnlContainerLayout);
        pnlContainerLayout.setHorizontalGroup(
            pnlContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
            .addGroup(pnlContainerLayout.createSequentialGroup()
                .addComponent(lblDateTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblState))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDateTime)
                    .addComponent(lblState)))
        );

        add(pnlContainer, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 2, -1, -1));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDateTime;
    private javax.swing.JLabel lblState;
    private javax.swing.JPanel pnlContainer;
    private javax.swing.JTextArea txaMessage;
    // End of variables declaration//GEN-END:variables

}
