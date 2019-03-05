package database;

// Imports
import java.sql.ResultSet;
import java.sql.SQLException;

import message.ProtocolMessage;

/**
 * ProtocolQueue class create a table of protocols that were added to a queue.  
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class ProtocolQueue extends DatabaseTable {

    public static final String TABLE_NAME = "protocol_queue"; // Set table name.
    public static final String[] FIELDS = new String[]{"senderName", "receiverName", "text", "sentTimestamp"}; // Set fields of table.

    private ProtocolMessage message; // Declare instance of ProtocolMessage.

    /**
     * Parameterized Constructor for ProtocolQueue class, sets current protocol message to passed in parameter.
     * @param message 
     */
    public ProtocolQueue(ProtocolMessage message) {
        this.message = message;
    }

    /**
     * Add to queue.
     *
     * @throws SQLException
     */
    public void addToQueue() throws SQLException {
        addToQueue(message);
    }

    /**
     * Add message to queue.
     * @param message
     * @throws SQLException 
     */
    public void addToQueue(ProtocolMessage message) throws SQLException {
        String[] fields = new String[]{message.getSenderName(), message.getReceiverName(), message.getText()};
        String[] nonStringFields = new String[]{"CURRENT_TIMESTAMP"};
        insert(FIELDS, fields, nonStringFields);
    }

    /**
     * Get the fields.
     * @return 
     */
    @Override
    public String[] getFields() {
        return FIELDS;
    }

    /**
     * get the table name.
     * @return 
     */
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    /**
     * Returns the unset protocols of a receiver.
     * @param receiverName
     * @return
     * @throws SQLException 
     */
    public static ResultSet loadUnsentProtocols(String receiverName) throws SQLException {
        return getObjectResultSet("SELECT * FROM " + TABLE_NAME + " WHERE receiverName = '" + receiverName + "'");
    }

}
