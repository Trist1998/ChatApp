package file;

import database.DatabaseTable;
import database.ServerDatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tristan
 */
public class FileTable extends DatabaseTable
{
    public static String TABLE_NAME = "files";
    public static final String[] FIELDS = new String[]{"senderName", "receiverName", "fileName", "messageId"};
    
    public void saveFile(FileMessage message) throws SQLException
    {
        String[] insertData = new String[]{message.getSenderName(), message.getReceiverName(), message.getFileName()};
        insert(FIELDS, insertData, new String[]{String.valueOf(message.getId())});
    }
    
    public int loadFileIdBySenderAndMessageId(FileMessage message)
    {
        try
        {
            ResultSet rs = getObjectResultSet(getLoadAllSQL() + " WHERE messageId = " + message.getId() + " AND senderName = '" + message.getSenderName() + "'");
            rs.first();
            int fileId = rs.getInt("id");
            ServerDatabaseConnection.closeQuery(rs);
            return fileId;
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(FileTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
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

    public String loadFileName(int id)
    {
        try
        {
            ResultSet rs = queryById(id);
            rs.first();
            String fileData = rs.getString("fileName");
            ServerDatabaseConnection.closeQuery(rs);
            return fileData;
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(FileTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
  
}
