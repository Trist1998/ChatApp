package message;

// Imports
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import network.protocol.ProtocolParameters;

/**
 * ProtocolMessage class is used to build protocol messages.
 *
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class ProtocolMessage {

    // Variables
    private String senderName;
    private String receiverName;
    private String text;
    private Date sent;
    private boolean alreadySaved;

    /**
     * Parameterized Constructor for ProtocolMessage class, builds a protocol
     * message with parameters passed in.
     *
     * @param senderName
     * @param receiverName
     * @param text
     */
    public ProtocolMessage(String senderName, String receiverName, String text) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.text = text;
    }

    /**
     * Parameterized Constructor for ProtocolMessage class, builds a protocol
     * message with result set.
     *
     * @param rs
     * @throws SQLException
     */
    public ProtocolMessage(ResultSet rs) throws SQLException {
        this.senderName = rs.getString("senderName");
        this.receiverName = rs.getString("receiverName");
        this.text = rs.getString("text");
        sent = rs.getDate("sentTimestamp");
        alreadySaved = true;
    }

    /**
     * Parameterized Constructor for ProtocolMessage class, builds a protocol
     * message with protocol parameters.
     *
     * @param pp
     */
    public ProtocolMessage(ProtocolParameters pp) {
        this.senderName = pp.getParameter("Sender");
        this.receiverName = pp.getParameter("Receiver");
        this.text = pp.toString();
    }

    /**
     * Get sender name.
     *
     * @return
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * Get receiver name.
     *
     * @return
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * Get text of protocol message.
     *
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * Check if it is already saved.
     *
     * @return
     */
    public boolean isAlreadySaved() {
        return alreadySaved;
    }

    /**
     * Set text of protocol massage.
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Get date and time of when protocol message sent.
     *
     * @return
     */
    public Date getSent() {
        return sent;
    }

    /**
     * Set date and time of when protocol message sent.
     *
     * @param sent
     */
    public void setSent(Date sent) {
        this.sent = sent;
    }
}
