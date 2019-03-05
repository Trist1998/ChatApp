package network.server;

// Imports
import java.io.IOException;
import network.ConnectionHandler;
import network.protocol.LoginProtocol;
import network.protocol.MessageProtocol;
import network.protocol.ProtocolParameters;
import network.protocol.ProtocolProcessor;
import network.protocol.UserCreationProtocol;

/**
 * ServerProtocolProcessor class processes protocols on server side.
 * @author Tristan Wood, Alex Priscu, Zubair Wiener
 */
public class ServerProtocolProcessor extends ProtocolProcessor {

    /**
     * Listens for incoming protocol messages from client and delegates to corresponding class.
     * @param conn
     * @throws IOException
     */
    public static void processServerInputStream(ConnectionHandler conn) throws IOException {
        ProtocolParameters pp = parseInputStream(conn);
        if (pp != null) {
            String head = pp.getHead();
            System.out.println("We got data with the header of " + head);
            if (head.trim().equals(MessageProtocol.HEAD)) {
                runServerMessageInputProcess(pp, conn);
            }
            //Add response protocol
        }
    }

    /**
     * Creates new threads to processes protocol.
     * @param pp
     * @param conn
     */
    private static void runServerMessageInputProcess(ProtocolParameters pp, ConnectionHandler conn) {
        new Thread(new Runnable() {
            public void run() {
                MessageProtocol.processInput(pp, conn);
            }
        }
        ).start();
    }

    /**
     * Used for login and create protocols where connection is terminated if incorrect.
     * @param conn
     * @return
     * @throws IOException
     */
    public static boolean processInitialConnection(ServerConnectionHandler conn) throws IOException {
        ProtocolParameters pp = parseInputStream(conn);
        if (pp != null) {
            String head = pp.getHead();

            if (head.equals(LoginProtocol.HEAD)) {
                return LoginProtocol.processInput(pp, conn, true);
            } else if (head.equals(UserCreationProtocol.HEAD)) {
                UserCreationProtocol.processInput(pp, conn);
            }
        }
        return false;
    }
}
