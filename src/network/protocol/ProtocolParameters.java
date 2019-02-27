
package network.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ProtocolParameters 
{
    private static final String PARAMETER_DELIMITER = "#END#";
    private ArrayList<String> parameterNames;
    private HashMap<String, String> parameters;
    
    public ProtocolParameters()
    {
        parameterNames = new ArrayList<>();
        parameters = new HashMap<>();
    }

    public ProtocolParameters(Scanner reader)
    {
        parameterNames = new ArrayList<>();
        parameters = new HashMap<>();
        String in = "";
        while(reader.hasNextLine())
        {
            in += reader.nextLine() + "\n";         
        }
        
        String[] pairs = in.split(PARAMETER_DELIMITER);
                
        for(String pair: pairs)
        {     
            String cleanPair = pair.trim();
            if(!cleanPair.contains(":"))
                break;
            
            String name = cleanPair.substring(0, cleanPair.indexOf(":"));
            String data = cleanPair.substring(cleanPair.indexOf(":") + 1);
            parameterNames.add(name);
            parameters.put(name, data);
        }
    }
    
    
    
    
    public void add(String parameterName, String parameterValue)
    {
        parameterNames.add(parameterName);
        parameters.put(parameterName, parameterValue);
    }

    public String getParameter(String name) 
    {
        return parameters.get(name);
    }
    
    public String toString()
    {
        String output = "";
        for(String name: parameterNames)
        {
            output += name + ":" + parameters.get(name) + PARAMETER_DELIMITER + "\n";
        }
        return output;             
    }
    
    
}
