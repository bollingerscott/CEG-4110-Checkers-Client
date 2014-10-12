package RMIConnection.Interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote {
	/**
	 * This interface defines an RMI server connection.
	 * The RMI server connection provides a mechanism for 
	 * communicating with the game server over TCP, but using
	 * RMI to deliver the messages. So all possible messages 
	 * from the client to the server via RMI are defined here.
	 * @author derk 8.15.08 
	 * 
	 */
	 
	 /* 
	  * This message alerts the server that your client is registered in the RMI 
	  * registry and should be looked up.
	  * 
	  * To connect to the checkers server RMI interface, use the parameters
	  * registryName = RMIServerConnection
	  * host = localhost 
	  * to access the registry. See sample code in handout and testClient.java
	  * 
	  * returns true if the server found your client and retrieved a reference to you,
	  * else false.
	  * */
	
	 /**
	  * Phase 1 messages.
	  */
	 public boolean registerPlayer(String registryName, String host) throws RemoteException;	 
	 //connect to the checkers server at address <ip> using username <userName>.
	 public boolean connectToServer(String ip, String userName) throws RemoteException;
	 //send the message <msg> to everyone in the lobby.
	 public void sendMsg_All(String msg) throws RemoteException;
	 //send the message <msg> o user <user>.
	 public void sendMsg(String to, String msg) throws RemoteException;	 
	 //tell the server you are disconnecting.
	 public void disconnect(boolean endProcess) throws RemoteException;
	 
	 //tell the server you want to make a table.
	 public void makeTable(String user) throws RemoteException;
	 //tell the server you are joining table with id tid.
	 public void joinTable(String user, int tid) throws RemoteException;
	 //tell the server you are ready to play a game, after sitting on a table.
	 public void playerReady(String user) throws RemoteException;
	 //tell the server you are moving from (fr,fc) to (tr,tc). See CheckersClient interface.
	 public void move(String user, int fr, int fc, int tr, int tc) throws RemoteException;
	 //tell the server you are leaving the table you have joined.
	 public void leaveTable(String user) throws RemoteException;
	 //ask the server what the status of table with id tid is.
	 public void getTblStatus(String user, int tid) throws RemoteException;
	 //start observing the table with id tid.
	 public void observeTable(String user, int tid) throws RemoteException;
	 //stop observing the table with id tid.
	 public void stopObserving(String user, int tid) throws RemoteException;
	 //make a table that is playing Go
	 public void goMakeTable(String user) throws RemoteException;
	 //make a move on a Go game instance
	 public void goMove(String user, int tr, int tc) throws RemoteException;
	 
	 /*** This functionality is disabled.
	 //register pwd with your username.
	 public void register(String user, String pwd) throws RemoteException;
	 //log into the server with pwd.
	 public void login(String user, String pwd) throws RemoteException;
	 //update your user profile with the following desc
	 public void updateProfile(String user, String desc) throws RemoteException;
	 //get the profile of user profileFor
	 public void getProfile(String user, String profileFor) throws RemoteException;
	 */
}
