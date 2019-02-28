package database;

import java.sql.SQLException;

/**
 *
 * @author Tristan
 */
public class ProtocolQueue extends DatabaseTable
{
    public static final String TABLE_NAME = "message_queue";
    public static final String[] FIELDS = new String[]{"senderName", "receiverName", "text", "sentTimestamp"};
    
    private String senderName;
    private String receiverName;
    private String text;

    public ProtocolQueue(String senderName, String receiverName, String text)
    {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.text = text;
    }
    
    public void addToQueue() throws SQLException
    {
        addToQueue(senderName, receiverName, text);
    }
            
    public void addToQueue(String senderName, String receiverName, String text) throws SQLException
    {
        String[] fields = new String[]{senderName, receiverName, text};
        String[] nonStringFields = new String[]{"CURRENT_TIMESTAMP"};
        insert(FIELDS, fields, nonStringFields);
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
    
}
