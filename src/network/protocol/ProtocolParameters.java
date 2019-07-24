package network.protocol;

// Imports
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * ProtocolParameters class is a container of protocol parameters.
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class ProtocolParameters 
{

    // Variables
    private JSONObject theObject;

    /**
     * Non-Parameterized Constructor for ProtocolParameters class, creates empty.
     */
    public ProtocolParameters() 
    {
        theObject = new JSONObject();
    }
    
    /**
     * 
     * @param head
     * @param action 
     */
    public ProtocolParameters(String head, String action) 
    {
        theObject = new JSONObject();
        setHead(head);
        add(NetworkMessageHandler.PROTOCOL_ACTION, action);
    }

    /**
     * Parameterized Constructor for ProtocolParameters class, used to build ProtocolParameters.
     * @param in
     */
    public ProtocolParameters(String in) throws ParseException 
    {
        in.replaceAll(NetworkMessageListener.PROTOCOL_END, "");
        in.replaceAll("END__", "END_");
        theObject = (JSONObject) new JSONParser().parse(in);
    }

    /**
     * Adds a parameter to the object.
     * @param parameterName
     * @param parameterValue
     */
    public void add(String parameterName, String parameterValue) 
    {
        theObject.put(parameterName, parameterValue);
    }

    /**
     * Gets parameter value from name.
     * @param name
     * @return
     */
    public String getParameter(String name) 
    {
        if(theObject.get(name) == null) return null;
        return String.valueOf(theObject.get(name));
    }

    /**
     * Returns protocol string.
     * @return
     */
    @Override
    public String toString() 
    {
        return theObject.toJSONString();
    }

    /**
     * Sets head of protocol. 
     * @param head
     */
    public void setHead(String head) 
    {
        theObject.put(NetworkMessageHandler.PROTOCOL_HEAD, head);
    }

    /**
     * Gets head of protocol. 
     * @return
     */
    public String getHead() 
    {
        return String.valueOf(theObject.get(NetworkMessageHandler.PROTOCOL_HEAD));
    }

    /**
     * Updates parameter value.
     * @param parameterName
     * @param value
     */
    public void replace(String parameterName, String value) 
    {
        theObject.replace(parameterName, value);
    }
    
    /**
     * Converts Protocol Parameters to a single string.
     * @return 
     */
    public String buildProtocolString() 
    {
        String output = toString().replaceAll("END_", "END__") + "\n";
        output += NetworkMessageHandler.PROTOCOL_END + "\n";
        return output;
    }

}
