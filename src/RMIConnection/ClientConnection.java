package RMIConnection;
import java.io.*;
import java.net.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import RMIConnection.Interfaces.CheckersClient;
import RMIConnection.Interfaces.RMIServerInterface;

/**
 * The Client Interface is the piece of the bridge facing the checkers client.
 * This class facilitates communication from the checkers client to the server
 * using RMI. 
 * 
 * Client messages are received here and then passed to the ServerInterface 
 * module where the message is translated into a TCP message the server can 
 * understand. That message is then sent across the network.
 * 
 * @author derek 8.15.08
 */
public class ClientConnection implements RMIServerInterface {
		
	private Socket socket = null;
    
    private DataInputStream streamFromServer;
	private DataOutputStream streamToServer;
	
	private String userName;	
	
	private CheckersClient client;
	private ServerConnection serverCon;
	
	private TCPListenerThread listener;
    
	public ClientConnection(){
		socket = null;
		streamFromServer = null;
		streamToServer = null;
		userName = "";
		client = null;
		serverCon = null;
		listener = null;
	}
		
	public boolean registerPlayer(String registryName, String host) throws RemoteException {
		/* Need to get the player from the RMI registry
		 */
		if (System.getSecurityManager() == null) {
		    System.setSecurityManager(new SecurityManager());
		}
		try {
		    Registry registry = LocateRegistry.getRegistry(host);
		    client = (CheckersClient) registry.lookup(registryName);
		    if(client != null){
		    	lnOut("Client registered with RMI Server Connection!");
		    	return true;
		    }
		    else {
		    	lnOut("RMI Server Connection could not find client in registry.");
		    	return false;
		    }
		}
		catch(Exception ex){
			lnOut("Exception occured retrieving player from registry. Exception:");
			lnOut(ex.getMessage());
			return false;
		}
	}

	public boolean connectToServer(String ip, String userName) {
		if(client == null){
			lnOut("Client must be registered before connecting to the server.");
			return false;
		}
		else{
		  try {
	            socket = new Socket(ip, 45322);
	            lnOut("Connected to server!");;
	            streamFromServer = new DataInputStream( socket.getInputStream());
				streamToServer =new DataOutputStream( socket.getOutputStream());

				//With the connection established, create the server connection module.
				serverCon = new ServerConnection(this, streamToServer);				
				//next start a thread that listens to incoming 
				//messages from the game server via TCP.
				listener = new TCPListenerThread(this, client, streamFromServer);
				
				//finally send the requested user name to the server.
				streamToServer.write(userName.getBytes());
				return true;
				
	        } catch (UnknownHostException e) {
	            System.out.println("Don't know about host: "+ip);
	            System.out.println(e.getMessage());
	            return false;
	        } catch (IOException e) {
	            System.out.println("Couldn't get I/O for the connection to: "+ip);
	            System.out.println(e.getMessage());
	            return false;
	        } catch (Exception e) {
	        	System.out.println(e.getMessage());
	        	return false;
	        }
		}		
	}
	
	public void killListenThread() throws IOException{
		if(listener != null){
			listener.active = false;
			listener.interrupt();
			listener = null;
			socket.close();
		}
	}

	public void disconnect(boolean endProcess) {
		serverCon.disconnect(userName);	
		if(endProcess){
			System.exit(1);
		}
	}

	//@Override
	public void joinTable(int tid) {
		serverCon.joinTable(userName, tid);		
	}

	//@Override
	public void leaveTable() {
		serverCon.leaveTable(userName);		
	}

	//@Override
	public void makeTable() {
		serverCon.makeTable(userName);
	}

	//@Override
	public void move(int fr, int fc, int tr, int tc) {
		serverCon.move(userName, fr, fc, tr, tc);
	}

	//@Override
	public void ready() {
		serverCon.playerReady(userName);		
	}

	public void sendMsg(String to, String msg) {
		serverCon.msgPlayer(userName, to, msg);
	}

	public void sendMsg_All(String msg) {
		serverCon.msgAll(userName, msg);		
	}
	
	/** Game playing methods **/
	public void getTblStatus(String user, int tid) throws RemoteException {
		serverCon.getTblStatus(user, tid);		
	}

	public void joinTable(String user, int tid) throws RemoteException {
		serverCon.joinTable(user, tid);	
	}

	public void leaveTable(String user) throws RemoteException {
		serverCon.leaveTable(user);
	}

	public void makeTable(String user) throws RemoteException {
		serverCon.makeTable(user);
	}

	public void move(String user, int fr, int fc, int tr, int tc)
			throws RemoteException {
		serverCon.move(user, fr, fc, tr, tc);		
	}

	public void playerReady(String user) throws RemoteException {
		serverCon.playerReady(user);
	}
	
	public void observeTable(String user, int tid) throws RemoteException {
		serverCon.observeTable(user, tid);		
	}

	public void stopObserving(String user, int tid) throws RemoteException {
		serverCon.stopObserving(user, tid);
		
	}
	public void goMakeTable(String user) throws RemoteException {
		serverCon.goMakeTable(user);
	}

	public void goMove(String user, int tr, int tc) throws RemoteException {
		serverCon.goMove(user, tr, tc);	
	}
	
	/**
	 * Console helper methods
	 */
	private void lnOut(String s){System.out.println(s);}			

	public void outputToConsole(String s){
		lnOut(s);
	}

	/** Disabled messages.
	/*
	@Override
	public void getProfile(String user, String profileFor)
			throws RemoteException {
		serverCon.getProfile(user, profileFor);		
	}

	@Override
	public void login(String user, String pwd) throws RemoteException {
		serverCon.login(user, pwd);
		
	}

	@Override
	public void register(String user, String pwd) throws RemoteException {
		serverCon.register(user, pwd);
		
	}

	@Override
	public void updateProfile(String user, String desc) throws RemoteException {
		serverCon.updateProfile(user, desc);		
	}
	*/
	
}
