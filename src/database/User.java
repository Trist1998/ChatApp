package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tristan
 */
public class User extends DatabaseTable
{
    public static final String TABLE_NAME = "users";
    public static final String[] FIELDS = new String[]
            {
                "username",
                "password"
            };
    
    private String username;
    private String password;//hashed


    public User(String username, String password)
    {
        this.username = username;
        this.password = password;      
    }
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
    
    public boolean authenticateLogin()//Used to check login details
    {
        String sql = buildLoadAllWhereSQLString("username", username);
        try
        {
            ResultSet rs = getObjectResultSet(sql);
            if(!rs.next())
                return false;
            String passwordToken = rs.getString("password");
            ServerDatabaseConnection.closeQuery(rs);
            return new PasswordHelper().authenticate(password, passwordToken);
        }
        catch(SQLException e)
        {
            System.out.println(e.toString());
            return false;
        }
        
    }
    
    public boolean createUser()
    {
        return createUser(username, new PasswordHelper().hash(password));
    }
    
    private boolean createUser(String username, String password)
    {
        //TODO SQL injection check, better error reporting.
        try
        {
            sendTableUpdate("INSERT INTO " + TABLE_NAME + " VALUES('"+ username +"', '" + password + "')");
            return true;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public String getUsername()
    {
        return username;
    }

    private void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    private void setPassword(String password)
    {
        this.password = password;
    }
                   
    
    
}
