package test;

import database.PasswordHelper;
import static java.lang.Math.random;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import message.Message;
import network.client.ClientNetworkManager;
import network.client.protocol.ClientMessageProtocol;

/**
 *
 * @author Tristan
 */
public class TestMessage extends javax.swing.JFrame {

    /**
     * Creates new form TestMessage
     */
    public TestMessage() throws NoSuchAlgorithmException, InvalidKeySpecException 
    {
        initComponents();
        ClientNetworkManager.openSocketClient(9999);
//        byte[] salt = new byte[16];
//        new Random().nextBytes(salt);
//        KeySpec spec = new PBEKeySpec("211298".toCharArray(), salt, 65536, 128);
//        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//        byte[] hash = f.generateSecret(spec).getEncoded();
//        Base64.Encoder enc = Base64.getEncoder();
//        System.out.printf("salt: %s%n", enc.encodeToString(salt));
//        System.out.printf("hash: %s%n", enc.encodeToString(hash));

    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txaText = new javax.swing.JTextArea();
        send = new javax.swing.JButton();
        txtTo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txaText.setColumns(20);
        txaText.setRows(5);
        jScrollPane1.setViewportView(txaText);

        send.setText("Send");
        send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendActionPerformed(evt);
            }
        });

        jLabel1.setText("To");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtTo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(send)
                        .addGap(241, 241, 241))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(73, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(send)
                    .addComponent(txtTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(154, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendActionPerformed
        Message message = new Message(ClientNetworkManager.getUsername(), txtTo.getText(), txaText.getText());
        ClientMessageProtocol.sendMessage(message, ClientNetworkManager.getConnection());
    }//GEN-LAST:event_sendActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TestMessage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TestMessage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TestMessage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TestMessage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try
                {
                    new TestMessage().setVisible(true);
                } 
                catch (NoSuchAlgorithmException ex)
                {
                    Logger.getLogger(TestMessage.class.getName()).log(Level.SEVERE, null, ex);
                } 
                catch (InvalidKeySpecException ex)
                {
                    Logger.getLogger(TestMessage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton send;
    private javax.swing.JTextArea txaText;
    private javax.swing.JTextField txtTo;
    // End of variables declaration//GEN-END:variables
}
