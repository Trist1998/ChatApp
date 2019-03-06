package ui;

// Imports
import ui.mainmenu.MainMenu;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import network.client.ClientNetworkManager;

/**
 * Login Class class is used to display a GUI that allows the user to
 * login or sign up toChatApp. If a user successfully enters their details and
 * clicks the login button, the MainMenu form will display. Alternatively the
 * user may click the sign up button to register.
 *
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class Login extends javax.swing.JFrame 
{
    /**
     * Non-Parameterized Constructor for Login Class, creates the Login Form.
     */
    public Login() 
    {
        initComponents(); // Initialise GUI components.
        this.setLocationRelativeTo(null); // Set frame to center of screen.
        jPanel1.getRootPane().setDefaultButton(btnLogin); // Set default button to login, allows user to hit enter.
        try 
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Adapt and optimize look and feel depending on OS.
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        txtUsername = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ChatApp Login");
        setBackground(new java.awt.Color(229, 229, 229));
        setMaximumSize(new java.awt.Dimension(1000, 600));
        setSize(new java.awt.Dimension(1000, 600));

        jPanel1.setBackground(new java.awt.Color(244, 244, 244));
        jPanel1.setAlignmentX(0.0F);
        jPanel1.setAlignmentY(0.0F);
        jPanel1.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        jPanel1.setMaximumSize(new java.awt.Dimension(1000, 600));
        jPanel1.setMinimumSize(new java.awt.Dimension(1000, 600));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 600));

        txtUsername.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        jLabel2.setText("Password");

        txtPassword.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N

        jButton1.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        jButton1.setText("Sign Up");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        jLabel1.setText("Username");

        btnLogin.setFont(new java.awt.Font("Heiti SC", 0, 13)); // NOI18N
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnLoginActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/resources/Screenshot 2019-03-04 at 14.19.35.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(326, 326, 326)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnLogin)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addComponent(jLabel3)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(334, 334, 334)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPassword)
                            .addComponent(txtUsername))))
                .addContainerGap(330, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btnLogin))
                .addGap(98, 98, 98))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

/**
 * If the Login Button is clicked checks if able to login and establish connection, then displays the main menu with chats.
 * @param evt 
 */
    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        if (attemptLogin()) // Check to see if possible to login and connect.
        {
            System.out.println("Opening main menu"); // Successful login.
            
            //Open Chat
            MainMenu mm = new MainMenu(); // Declare and instantiate new instance of MainMenu.
            mm.start();
            this.dispose(); // Close this form.
        } 
        else 
        {
            JOptionPane.showMessageDialog(null, "Login Failed"); // Error message.
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    /**
     * If the Sign Up button is clicked, display the Create User form where a user can sign up.
     * @param evt 
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        CreateUser newAccount = new CreateUser(); // Declare and instantiate new instance of CreateUser.
        newAccount.setVisible(true); // Display CreateUser form.
        this.dispose(); // Close this form.
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * Main method, runs the the Login thread.
     * @param args 
     */
    public static void main(String args[]) 
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run() 
            {
                new Login().setVisible(true); // Display Login form.
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables

    /**
     * Checks to see if able to login and connect.
     * @return 
     */
    private boolean attemptLogin() 
    {
        return ClientNetworkManager.login(txtUsername.getText(), txtPassword.getText());
    }
}
