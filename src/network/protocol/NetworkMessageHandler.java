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
     * Sends Protocol Parameters string to server.
     * @param parameters
     * @param conn
     * @return
     * @throws IOException 
     */  
    public static boolean send(ProtocolParameters parameters, ConnectionHandler conn)
    {
        conn.send(parameters.buildProtocolString());
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
