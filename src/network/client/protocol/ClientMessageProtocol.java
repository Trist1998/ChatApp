
package network.client.protocol;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.Message;
import network.client.ClientConnectionHandler;
import network.protocol.Protocol;
import network.protocol.ProtocolParameters;
import network.server.protocol.ServerMessageProtocol;


public class ClientMessageProtocol extends Protocol
{
   public static final String HEAD_IDENTIFIER = "MSG";
    public ClientMessageProtocol() 
    {
        super();
    }

    public static boolean sendMessage(Message message, ClientConnectionHandler conn)
    {   
        ProtocolParameters pp = new ProtocolParameters();
        pp.add("Sender", message.getSenderName());
        pp.add("Receiver", message.getReceiverName());
        pp.add("text", message.getText());
        try 
        {
           send(HEAD_IDENTIFIER, pp, conn);
           return true;
        } 
        catch (IOException ex) 
        {
           Logger.getLogger(ServerMessageProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static boolean processInput(Scanner reader)
    {
        ProtocolParameters pp = new ProtocolParameters(reader);
        System.out.println(pp.getParameter("Sender"));
        System.out.println(pp.getParameter("text"));
        return true;
    }
}