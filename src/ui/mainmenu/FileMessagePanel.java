package ui.mainmenu;

import file.FileMessage;
import file.FileNetworkManager;
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
    }
    
    public JProgressBar getProgressBar()
    {
        return pbDownload;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        btnAction = new javax.swing.JButton();
        pbDownload = new javax.swing.JProgressBar();
        lblState = new javax.swing.JLabel();
        lblDateTime = new javax.swing.JLabel();
        lblFileName = new javax.swing.JLabel();

        jPanel1.setMinimumSize(new java.awt.Dimension(200, 70));
        jPanel1.setName(""); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(70, 100));

        btnAction.setText("Download");
        btnAction.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnActionActionPerformed(evt);
            }
        });

        pbDownload.setBackground(new java.awt.Color(204, 204, 204));
        pbDownload.setForeground(new java.awt.Color(153, 255, 153));

        lblState.setText("lblState");

        lblDateTime.setText("lblDateTime");

        lblFileName.setText("fileName");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pbDownload, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblFileName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                        .addComponent(btnAction))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(lblState)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDateTime)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pbDownload, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblState)
                    .addComponent(lblDateTime))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAction)
                    .addComponent(lblFileName))
                .addGap(0, 4, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblDateTime;
    private javax.swing.JLabel lblFileName;
    private javax.swing.JLabel lblState;
    private javax.swing.JProgressBar pbDownload;
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
        fc.showSaveDialog(jPanel1);
        fc.setVisible(true);
        if(fc.getSelectedFile() != null)
        new Thread(new Runnable()
        {
            public void run()
            {
                FileNetworkManager.downloadFile(fc.getSelectedFile(), message.getFileId(), getProgressBar());
            }
        }).start();
        
    }

    private void setButtonText()
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
    
}
