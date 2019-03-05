package network.client;

//Imports
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.ConnectionHandler;
import network.protocol.LoginProtocol;

/**
 * ClientConnectionHandler class is a connection handler for the client.
 *
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class ClientConnectionHandler extends ConnectionHandler {

    private String password;

    /**
     * Parameterized Constructor for ClientConnectionHandler.
     *
     * @param socket
     * @param username
     * @param password
     * @throws IOException
     */
    public ClientConnectionHandler(Socket socket, String username, String password) throws IOException {
        super(socket);
        setUsername(username);
        this.password = password;
    }

    /**
     * Parameterized Constructor for ClientConnectionHandler.
     *
     * @param socket
     * @throws IOException
     */
    public ClientConnectionHandler(Socket socket) throws IOException {
        super(socket);
    }

    /**
     * Create listener thread for the socket. Once connection established sends the login details. 
     */
    @Override
    public void run() {
        try {
            LoginProtocol.loginRequest(getUsername(), password, this);
            if (ClientProtocolProcessor.processLogin(this)) {
                ClientNetworkManager.loginSuccesful();
                while (isConnected()) {
                    ClientProtocolProcessor.processInputStream(this);
                }
            }
            ClientNetworkManager.connectionFailed();
        } catch (IOException ex) {
            Logger.getLogger(ClientConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
