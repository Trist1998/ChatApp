package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import message.ProtocolMessage;

/**
 *
 * @author Tristan
 */
public class ProtocolQueue extends DatabaseTable
{
    public static final String TABLE_NAME = "protocol_queue";
    public static final String[] FIELDS = new String[]{"senderName", "receiverName", "text", "sentTimestamp"};
    
    private ProtocolMessage message;

    public ProtocolQueue(ProtocolMessage message)
    {
        this.message = message;
    }
    
    public void addToQueue() throws SQLException
    {
        addToQueue(message);
    }
            
    public void addToQueue(ProtocolMessage message) throws SQLException
    {
        String[] fields = new String[]{message.getSenderName(), message.getReceiverName(), message.getText()};
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
    
    public static ResultSet loadUnsentProtocols(String receiverName) throws SQLException
    {
        return getObjectResultSet("SELECT * FROM " + TABLE_NAME + " WHERE receiverName = '" + receiverName + "'");
    }
    
}
