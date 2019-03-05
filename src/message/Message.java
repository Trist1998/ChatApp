package message;

// Imports
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.protocol.ProtocolParameters;

/**
 * Message class is used to build messages.
 *
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class Message {

    // Variables
    private int id;
    private String senderName;
    private String receiverName;
    private String text;
    private int state;
    private Date received;
    private Date sent;

    /**
     * Parameterized Constructor for Message class, builds message with passed
     * in parameters.
     *
     * @param senderName
     * @param receiverName
     * @param text
     */
    public Message(String senderName, String receiverName, String text) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.text = text;
        received = null;
        state = -1;
    }

    /**
     * Parameterized Constructor for Message class, takes in Protocol Parameters
     * to build message.
     *
     * @param pp
     */
    public Message(ProtocolParameters pp) {
        this.id = Integer.parseInt(pp.getParameter("Id"));
        this.senderName = pp.getParameter("Sender");
        this.receiverName = pp.getParameter("Receiver");
        this.text = pp.getParameter("Text");
        try {
            this.sent = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z").parse(pp.getParameter("DateSent"));
        } catch (ParseException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        state = -1;
    }

    /**
     * Parameterized Constructor for Message class, takes passed in parameters
     * to build message.
     *
     * @param id
     * @param senderName
     * @param receiverName
     * @param text
     */
    public Message(int id, String senderName, String receiverName, String text) {
        this.id = id;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.text = text;
        received = null;
        state = -1;
    }

    /**
     * Sets state of message based on response code.
     *
     * @param responseCode
     */
    public synchronized void setState(int responseCode) {
        state = Math.max(state, responseCode);
    }

    /**
     * Get state of message.
     *
     * @return
     */
    public synchronized int getState() {
        return state;
    }

    /**
     * Get ID of message.
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Get sender of message name.
     *
     * @return
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * Get receiver of message name.
     *
     * @return
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * get text of message.
     *
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * Check if message is received.
     *
     * @return
     */
    public boolean isReceived() {
        return received != null;
    }

    /**
     * Set text of message.
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Get the date and time of message when received.
     *
     * @return
     */
    public Date getReceived() {
        return received;
    }

    /**
     * Set the date and time of message when received.
     *
     * @param received
     */
    public void setReceived(Date received) {
        this.received = received;
    }

    /**
     * Get the date and time of message when sent.
     *
     * @return
     */
    public Date getSent() {
        return sent;
    }

    /**
     * Get the date and time of message when sent in a string format.
     *
     * @return
     */
    public String getDateSentString() {
        return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z").format(sent);
    }

    /**
     * Set the date and time of message when sent.
     *
     * @param sent
     */
    public void setSent(Date sent) {
        this.sent = sent;
    }
}
