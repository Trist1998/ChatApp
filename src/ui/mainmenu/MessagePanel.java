package ui.mainmenu;

// Imports
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import message.Message;
import network.server.ServerConnectionHandler;

/**
 * MessagePanel class create a panel form to display an actual message in the
 * chat.
 *
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class MessagePanel extends javax.swing.JPanel implements ResponseReceiver
{

    private Message message;
    private int state;
    
    
    public MessagePanel(Message message, JPanel parent)
    {
        this.message = message;
        state = -1;
        
        initComponents();
        
        try 
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        txaMessage.setSize(pnlContainer.getWidth(), getContentHeight(message.getText()));
        txaMessage.setText(message.getText());
        lblDateTime.setText(message.getDisplayDateSentString());
        setMaximumSize(new Dimension(parent.getWidth(), pnlContainer.getPreferredSize().height));
        pnlContainer.setMaximumSize(new Dimension(getWidth() - 100, getPreferredSize().height));
        
        
        if(message.isUserAlsoSender())
        {
            Color colour = new Color(153,204,255);
            //pnlContainer.setBackground(colour);
            txaMessage.setBackground(colour);
        }
        else
        {
            txaMessage.setBackground(new Color(150,255,200));
        }
        setStateLabel();
    }

    /**
     * Set the state label based on message state. (Delivered, saved, lost).
     */
    private void setStateLabel()
    {
        switch (message.getState()) 
        {
            case ServerConnectionHandler.MESSAGE_DELIVERED:
                lblState.setText("Delivered");
                break;
            case ServerConnectionHandler.MESSAGE_SAVED:
                lblState.setText("...");
                break;
            case ServerConnectionHandler.MESSAGE_LOST:
                lblState.setText("Lost");
                break;
            default:
                lblState.setText("");
                break;
        }
    }

    /**
     * Sets the message state based on the response code.
     *
     * @param responseCode
     */

    public int getContentHeight(String content)
    {
        JEditorPane dummyEditorPane = new JEditorPane();
        dummyEditorPane.setSize(txaMessage.getWidth(),Short.MAX_VALUE);
        dummyEditorPane.setText(content);
        
        return dummyEditorPane.getPreferredSize().height;
    }
    
    @Override
    public void receiveResponse(int responseCode)
    {
        message.setState(responseCode);
        setStateLabel();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        pnlContainer = new javax.swing.JPanel();
        lblState = new javax.swing.JLabel();
        lblDateTime = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaMessage = new javax.swing.JTextArea();

        setBackground(new java.awt.Color(244, 244, 244));
        setForeground(new java.awt.Color(244, 244, 244));
        setToolTipText("");
        setAlignmentX(0.0F);
        setAlignmentY(0.0F);
        setMinimumSize(new java.awt.Dimension(162, 64));
        setName(""); // NOI18N

        pnlContainer.setBackground(new java.awt.Color(222, 222, 222));
        pnlContainer.setMinimumSize(new java.awt.Dimension(170, 70));
        pnlContainer.setPreferredSize(new java.awt.Dimension(170, 70));

        lblState.setFont(new java.awt.Font("Heiti SC", 0, 10)); // NOI18N
        lblState.setText("lblState");

        lblDateTime.setFont(new java.awt.Font("Heiti SC", 0, 10)); // NOI18N
        lblDateTime.setText("lblDateTime");

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setViewportBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txaMessage.setEditable(false);
        txaMessage.setColumns(20);
        txaMessage.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        txaMessage.setLineWrap(true);
        txaMessage.setRows(1);
        jScrollPane1.setViewportView(txaMessage);

        javax.swing.GroupLayout pnlContainerLayout = new javax.swing.GroupLayout(pnlContainer);
        pnlContainer.setLayout(pnlContainerLayout);
        pnlContainerLayout.setHorizontalGroup(
            pnlContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContainerLayout.createSequentialGroup()
                .addComponent(lblState)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(lblDateTime)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        pnlContainerLayout.setVerticalGroup(
            pnlContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContainerLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblState)
                    .addComponent(lblDateTime))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlContainer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDateTime;
    private javax.swing.JLabel lblState;
    private javax.swing.JPanel pnlContainer;
    private javax.swing.JTextArea txaMessage;
    // End of variables declaration//GEN-END:variables

}
