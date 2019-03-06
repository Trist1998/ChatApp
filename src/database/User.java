package database;

// Imports
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User class creates a table of users with usernames and passwords and
 * authenticates their details.
 *
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class User extends DatabaseTable 
{

    public static final String TABLE_NAME = "users"; // Set table name.
    public static final String[] FIELDS = new String[] // Set fields of table.
    {
        "username",
        "password"
    };

    private String username;
    private String password;//hashed

    /**
     * Take in username and password and set them to fields.
     *
     * @param username
     * @param password
     */
    public User(String username, String password) 
    {
        this.username = username;
        this.password = password;
    }

    /**
     * Return field of table.
     *
     * @return
     */
    @Override
    public String[] getFields() 
    {
        return FIELDS;
    }

    /**
     * Return name of table.
     *
     * @return
     */
    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }

    /**
     * Authenticate login details.
     *
     * @return
     */
    public boolean authenticateLogin()
    {
        String sql = buildLoadAllWhereSQLString("username", username);
        try 
        {
            ResultSet rs = getObjectResultSet(sql);
            if (!rs.next())
            {
                return false;
            }
            String passwordToken = rs.getString("password");
            ServerDatabaseConnection.closeQuery(rs);
            return new PasswordHelper().authenticate(password, passwordToken);
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }

    }

    /**
     * Create a new user.
     *
     * @return
     */
    public boolean createUser()
    {
        return createUser(username, new PasswordHelper().hash(password));
    }

    /**
     * Create a new user with username and password passed in.
     *
     * @param username
     * @param password
     * @return
     */
    private boolean createUser(String username, String password) 
    {
        //TODO SQL injection check, better error reporting.
        try {
            sendTableUpdate("INSERT INTO " + TABLE_NAME + " VALUES('" + username + "', '" + password + "')");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Get username.
     *
     * @return
     */
    public String getUsername() 
    {
        return username;
    }

    /**
     * Set username to username passed in.
     *
     * @param username
     */
    private void setUsername(String username) 
    {
        this.username = username;
    }

    /**
     * Get password.
     *
     * @return
     */
    public String getPassword() 
    {
        return password;
    }

    /**
     * Set password that is passed in.
     *
     * @param password
     */
    private void setPassword(String password)
    {
        this.password = password;
    }

}
