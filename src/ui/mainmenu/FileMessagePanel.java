package ui.mainmenu;

import file.FileMessage;
import file.FileNetworkManager;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JProgressBar;
import network.server.ServerConnectionHandler;

/**
 *
 * @author Tristan
 */
public class FileMessagePanel extends javax.swing.JPanel implements ResponseReceiver
{

    /**
     * Creates new form FileMessagePanel
     */
    private FileMessage message;
    
    public FileMessagePanel(FileMessage message)
    {
        initComponents();
        this.message = message;
        lblFileName.setText(message.getFileName());
        lblDateTime.setText(message.getDisplayDateSentString());
        if(message.getState() == 1)
            pbDownload.setValue(100);
        setStateLabel();
        setButtonText();
        if(message.isUserAlsoSender())
        {
            Color colour = new Color(153,204,255);
            //pnlContainer.setBackground(colour);
            pnlContainer.setBackground(colour);
        }
        else
        {
            pnlContainer.setBackground(new Color(150,255,200));
        }
    }
    
    public JProgressBar getProgressBar()
    {
        return pbDownload;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        pnlContainer = new javax.swing.JPanel();
        btnAction = new javax.swing.JButton();
        pbDownload = new javax.swing.JProgressBar();
        lblFileName = new javax.swing.JLabel();
        lblState = new javax.swing.JLabel();
        lblDateTime = new javax.swing.JLabel();

        setBackground(new java.awt.Color(222, 222, 222));
        setMinimumSize(new java.awt.Dimension(250, 80));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(250, 80));

        pnlContainer.setBackground(new java.awt.Color(204, 255, 255));
        pnlContainer.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlContainer.setMinimumSize(new java.awt.Dimension(200, 70));
        pnlContainer.setName(""); // NOI18N
        pnlContainer.setPreferredSize(new java.awt.Dimension(70, 100));

        btnAction.setBackground(new java.awt.Color(204, 255, 255));
        btnAction.setFont(btnAction.getFont());
        btnAction.setText("Download");
        btnAction.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnActionActionPerformed(evt);
            }
        });

        pbDownload.setBackground(new java.awt.Color(204, 255, 255));
        pbDownload.setForeground(new java.awt.Color(153, 255, 255));

        lblFileName.setFont(lblFileName.getFont());
        lblFileName.setText("fileName");

        javax.swing.GroupLayout pnlContainerLayout = new javax.swing.GroupLayout(pnlContainer);
        pnlContainer.setLayout(pnlContainerLayout);
        pnlContainerLayout.setHorizontalGroup(
            pnlContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContainerLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(btnAction)
                .addGap(6, 6, 6)
                .addComponent(lblFileName)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(pbDownload, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
        );
        pnlContainerLayout.setVerticalGroup(
            pnlContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContainerLayout.createSequentialGroup()
                .addComponent(pbDownload, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAction)
                    .addComponent(lblFileName)))
        );

        lblState.setText("lblState");

        lblDateTime.setText("lblDateTime");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblState)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblDateTime)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblState)
                    .addComponent(lblDateTime))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnActionActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnActionActionPerformed
    {//GEN-HEADEREND:event_btnActionActionPerformed
        if(isDownloaded())
            openFile();
        else
            requestDownload();
    }//GEN-LAST:event_btnActionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAction;
    private javax.swing.JLabel lblDateTime;
    private javax.swing.JLabel lblFileName;
    private javax.swing.JLabel lblState;
    private javax.swing.JProgressBar pbDownload;
    private javax.swing.JPanel pnlContainer;
    // End of variables declaration//GEN-END:variables

    @Override
    public void receiveResponse(int responseCode)
    {
        message.setState(responseCode);
        setStateLabel();      
    }
    
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

    private void openFile()
    {
        try
        {
            Desktop.getDesktop().open(new File(message.getFilePath()));
        } 
        catch (IOException ex)
        {
            Logger.getLogger(FileMessagePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean isDownloaded()
    {
        if(message.getFilePath() == null || message.getFilePath().equals(""))
            return false;
        return new File(message.getFilePath()).exists();
    }

    private void requestDownload()
    {
        JFileChooser fc = new JFileChooser(getDirectory());
        fc.setSelectedFile(new File(message.getFileName()));
        fc.showSaveDialog(pnlContainer);
        fc.setVisible(true);
        FileMessagePanel thisPanel = this;
        
        if(fc.getSelectedFile() != null)
        {
            message.setFilePath(fc.getSelectedFile().getAbsoluteFile().toString());
            new Thread(new Runnable()
            {
                public void run()
                {
                    FileNetworkManager.downloadFile(fc.getSelectedFile(), message.getFileId(), thisPanel);
                }
            }).start();
        }
        
    }

    public void setButtonText()
    {
        if(isDownloaded())
            btnAction.setText("Open");
        else
            btnAction.setText("Download");
    }
    
    private static String getDirectory()
    {
        return System.getProperty("user.dir") +"/downloads/";
    }

    public FileMessage getFileMessage()
    {
        return message;
    }
    
}
