package RMIConnection;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import RMIConnection.Interfaces.RMIServerInterface;

/**
 * The RMI Server Connection is a module that essentially wraps TCP requests 
 * to the game server as RMI messages. Clients interact with this process, 
 * which translates the RMI messages received into messages for the server
 * translated and sent through TCP. The purpose of this piece is to provide
 * network connectivity and communication without the need to use TCP 
 * or handle network communication. 
 * 
 * The process consists of 3 main classes: 
 * ClientInterface (implements RMIServerInerface)
 * ServerInterface 
 * TCPListenerThread
 * 
 * ClientInterface is the RMI client-facing module of the server connection process.
 * In this class RMI messages to the server are handled. Checkers clients will
 * need to retrieve a reference to this module from the RMI registry. 
 * 
 * The ServerInterface is server-facing module of the server connection process. 
 * When the ClientInterface retrieves a message via RMI, the message is passed off to the
 * ServerInterface. The ServerInterface translates the message and then sends it across the 
 * network to the game server via TCP. 
 * 
 * The TCPListenerThread is a thread that listens for messages from the server. When a message
 * is retrieved, the message is translated and then sent to the client via RMI.
 * 
 * @author derk 8.15.08
 *
 */
/**
 * This class just contains the main method to fire up the RMI Server Connection module.
 * 
 * Remember: ONE INSTANCE WITH A UNIQUE NAME PER CLIENT PER PC. 
 */
public class RMIConnectionEngine {
	public static void main(String args[]){		
		//next establish a presence in the RMI registry.
		System.setProperty("java.security.policy","file:./src/RMIConnection/server.policy");
		System.setProperty("java.rmi.server.codebase", RMIServerInterface.class
				.getProtectionDomain().getCodeSource().getLocation().toString());
		
		//System.setProperty("java.rmi.server.codebase", "file:./bin/");
		 if (System.getSecurityManager() == null) {
	            System.setSecurityManager(new SecurityManager());
	        }
	        try {
	            String name = "CheckersServerInterface";
	            RMIServerInterface rmiServer = new ClientConnection();
	            RMIServerInterface stub =
	                (RMIServerInterface) UnicastRemoteObject.exportObject(rmiServer, 0);
	            Registry registry = LocateRegistry.getRegistry();
	            registry.rebind(name, stub);
	            System.out.println("This is the console for the RMI Server Interface.");
	            System.out.println("RMI Server Connection process bound, available for ONE connection.");
	        } catch (Exception e) {
	            System.err.println("Exception binding to registry:");
	            e.printStackTrace();
	        }
	}
}
