package network.protocol;

// Imports
import java.io.IOException;
import network.ConnectionHandler;

/**
 * Protocol class is a parent class to all protocols. Defines all protocol structures.
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class NetworkMessageHandler
{

    // Variables
    public static final String PROTOCOL_HEAD = "Head";//During processing on the receivers Determines which class processes the protocol
    public static final String PROTOCOL_ACTION = "Action";//Determines what action inside the processing class
    public static final String PROTOCOL_END = "END_PROTOCOL";

    /**
     * Converts Protocol Parameters to a single string.
     * @param head
     * @param parameters
     * @return 
     */
    public static String buildProtocolString(String head, ProtocolParameters parameters) 
    {
        parameters.setHead(head);
        String output = parameters.toString() + "\n";
        output += PROTOCOL_END + "\n";

        return output;
    }

    /**
     * Sends Protocol Parameters string to server.
     * @param head
     * @param parameters
     * @param conn
     * @return
     * @throws IOException 
     */
    public static boolean send(String head, ProtocolParameters parameters, ConnectionHandler conn) throws IOException 
    {
        String output = buildProtocolString(head, parameters);
        conn.send(output);
        return true;
    }

    /**
     * Process Input of Protocol Parameters.
     * @param pp
     * @return 
     */
    public static boolean processInput(ProtocolParameters pp)//reader.nextLine() should return the first parameter
    {
        System.out.print("THIS PROCESS INPUT NEEDS TO BE IMPLEMENTED");
        System.out.println(pp.toString());
        return true;
    }

}
