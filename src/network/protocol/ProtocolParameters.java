package network.protocol;

// Imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * ProtocolParameters class is a container of protocol parameters.
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class ProtocolParameters 
{

    // Variables
    public static final String PARAMETER_DELIMITER = "#END#";
    private ArrayList<String> parameterNames;
    private HashMap<String, String> parameters;

    /**
     * Non-Parameterized Constructor for ProtocolParameters class, creates empty.
     */
    public ProtocolParameters() 
    {
        parameterNames = new ArrayList<>();
        parameters = new HashMap<>();
    }

    /**
     * Parameterized Constructor for ProtocolParameters class, used to build ProtocolParameters.
     * @param reader
     */
    public ProtocolParameters(Scanner reader) 
    {
        parameterNames = new ArrayList<>();
        parameters = new HashMap<>();
        String in = "";
        while (reader.hasNextLine()) 
        {
            in += reader.nextLine() + "\n";
        }

        String[] pairs = in.split(PARAMETER_DELIMITER);

        for (String pair : pairs) 
        {
            String cleanPair = pair.trim();
            if (!cleanPair.contains(":")) 
            {
                break;
            }
            add(cleanPair);
        }
    }

    /**
     * Adds a parameter to the object.
     * @param parameterName
     * @param parameterValue
     */
    public void add(String parameterName, String parameterValue) 
    {
        parameterNames.add(parameterName);
        parameters.put(parameterName, parameterValue);
    }

    /**
     * Gets parameter value from name.
     * @param name
     * @return
     */
    public String getParameter(String name) 
    {
        return parameters.get(name);
    }

    /**
     * Returns protocol string.
     * @return
     */
    @Override
    public String toString() 
    {
        String output = NetworkMessageHandler.PROTOCOL_HEAD + ":" + getHead() + PARAMETER_DELIMITER + "\n";
        for (String name : parameterNames) 
        {
            output += name + ":" + parameters.get(name) + PARAMETER_DELIMITER + "\n";
        }
        return output;
    }

    /**
     * Sets head of protocol. 
     * @param head
     */
    public void setHead(String head) 
    {
        parameters.put(NetworkMessageHandler.PROTOCOL_HEAD, head);
    }

    /**
     * Gets head of protocol. 
     * @return
     */
    public String getHead() 
    {
        return parameters.get(NetworkMessageHandler.PROTOCOL_HEAD);
    }

    /**
     * Converts line into protocol parameters. 
     * @param line
     */
    public void add(String line) 
    {
        if (!line.equals("")) 
        {
            String name = line.substring(0, line.indexOf(":"));
            String data = line.substring(line.indexOf(":") + 1);
            if (!name.equals(NetworkMessageHandler.PROTOCOL_HEAD)) 
            {
                parameterNames.add(name);
            }
            parameters.put(name, data.trim());
        }
    }

    /**
     * Updates parameter value.
     * @param parameterName
     * @param value
     */
    public void replace(String parameterName, String value) 
    {
        parameters.replace(parameterName, value);
    }

}
