package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tristan
 */
public class UserTable extends DatabaseTable
{
    public static final String TABLE_NAME = "users";
    public static final String[] FIELDS = new String[]
            {
                "username",
                "password"
            };


    @Override
    public String[] getFields()
    {
       return FIELDS;
    }

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }
    
    public boolean authenticateLogin(String username, String password) throws SQLException
    {
        String sql = buildLoadAllWhereSQLString("username", username);
        ResultSet rs = getObjectResultSet(sql);
        rs.first();
        String passwordToken = rs.getString("password");
        System.out.println(passwordToken + " token");
        
        return new PasswordHelper().authenticate(password, passwordToken);
    }
    
    public boolean createUser(String username, String password)
    {
        //TODO SQL injection check, better error reporting.
        try
        {
            sendTableUpdate("INSERT INTO " + TABLE_NAME + " VALUES('"+ username +"', '" + new PasswordHelper().hash(password) + "')");
            return true;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(UserTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
                   
    
    
}