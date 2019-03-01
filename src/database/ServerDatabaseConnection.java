package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Tristan
 */
public class ServerDatabaseConnection 
{
    public static final String host = "jdbc:derby://localhost:1527/ChatApp";
    public static final String uName = "root";
    public static final String uPass = "zxcvbnm";
    public Connection connection;
    //public String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    public ServerDatabaseConnection() throws SQLException
    {
        connection = DriverManager.getConnection(host, uName, uPass);
    }
            
    public void update(String SQL) 
    {
        try 
        {
            Statement stmt = connection.createStatement();

            stmt.executeUpdate(SQL);
            System.out.println("Sent");          
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(ServerDatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Sum Ting Wong");
        }
    }

    public ResultSet query(String SQL) 
    {
        Connection con;
        Statement stmt;
        ResultSet rs = null;
        
        try 
        {
            con = DriverManager.getConnection(host, uName, uPass);

            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            rs = stmt.executeQuery(SQL);            

            System.out.println("Query Done");

        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(ServerDatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Sum Ting Wong");
        }
        return rs;
    }
}
