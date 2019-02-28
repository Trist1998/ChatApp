
package network.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ProtocolParameters 
{
    private static final String HEAD_PARAMETER_NAME = "HEAD";
    public static final String PARAMETER_DELIMITER = "#END#";
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
            add(cleanPair);           
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
    
    @Override
    public String toString()
    {
        String output = HEAD_PARAMETER_NAME + ":" + getHead() + PARAMETER_DELIMITER + "\n";
        for(String name: parameterNames)
        {
            output += name + ":" + parameters.get(name) + PARAMETER_DELIMITER + "\n";
        }
        return output;             
    }

    public void setHead(String head)
    {
        parameters.put(HEAD_PARAMETER_NAME, head);
    }
    
    public String getHead()
    {
        return parameters.get(HEAD_PARAMETER_NAME);
    }

    public void add(String line)
    {
        if(!line.equals(""))
        {
            String name = line.substring(0, line.indexOf(":"));
            String data = line.substring(line.indexOf(":") + 1);
            parameterNames.add(name);
            parameters.put(name, data.trim());
        }
    }
    
    
}
