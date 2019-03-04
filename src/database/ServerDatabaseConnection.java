package database;

// Imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * ServerDatabaseConnection class used to connect to database and perform SQL
 * queries.
 *
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class ServerDatabaseConnection {

    public static final String host = "jdbc:derby://localhost:1527/ChatApp"; // Set host.
    public static final String uName = "root"; // Set username.
    public static final String uPass = "zxcvbnm"; // Set password.
    public static Connection connection;
    //public String driver = "org.apache.derby.jdbc.EmbeddedDriver";

    /**
     * Tries to connect to host and execute an update.
     *
     * @param SQL
     */
    public static void update(String SQL) {
        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();

            stmt.executeUpdate(SQL);
            con.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ServerDatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Sum Ting Wong");
        }
    }

    /**
     * Returns a result from an SQL query.
     *
     * @param SQL
     * @return
     */
    public static ResultSet query(String SQL) {
        Connection con;
        Statement stmt;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(host, uName, uPass);
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(SQL);
        } catch (SQLException ex) {
            Logger.getLogger(ServerDatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Sum Ting Wong");
        }
        return rs;
    }

    /**
     * Close connection, statments and result set.
     *
     * @param rs
     * @throws SQLException
     */
    public static void closeQuery(ResultSet rs) throws SQLException {
        Connection con = rs.getStatement().getConnection();
        Statement statement = rs.getStatement();

        con.close();
        statement.close();
        rs.close();
    }
}
