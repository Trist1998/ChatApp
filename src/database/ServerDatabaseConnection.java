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
    public static Connection connection;
    //public String driver = "org.apache.derby.jdbc.EmbeddedDriver";

    public static void update(String SQL) 
    {
        try 
        {
            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();

            stmt.executeUpdate(SQL);
            con.close();
            stmt.close();
            System.out.println("Sent");          
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(ServerDatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Sum Ting Wong");
        }
    }

    public static ResultSet query(String SQL) 
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
    
    public static void closeQuery(ResultSet rs) throws SQLException
    {
        Connection con = rs.getStatement().getConnection();
        Statement statement = rs.getStatement();
        
        con.close();
        statement.close();
        rs.close();
    }
}
