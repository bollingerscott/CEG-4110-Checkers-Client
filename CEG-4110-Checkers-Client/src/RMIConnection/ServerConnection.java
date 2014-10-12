package RMIConnection;

import java.io.*;

import RMIConnection.Interfaces.TCPMsg;
import RMIConnection.Interfaces.TCPServerInterface;

/**
 * This class defines the connection from the RSI, to the server. Method
 * calls from the client are translated in the ClientConnection object and 
 * are passed off to this class to communicate with the server via TCP. 
 * @author derek
 * 8.15.08
 */
public class ServerConnection implements TCPServerInterface{

	private DataOutputStream streamToServer;
	private ClientConnection rmiInt;
	
	public ServerConnection(ClientConnection clientCon, DataOutputStream toServer){
		streamToServer = toServer;
		rmiInt = clientCon;
	}
	
	public void disconnect(String user){
		try{
			rmiInt.outputToConsole("Disconnecting...");
			streamToServer.write((Integer.toString(TCPMsg.QUIT)+" "+user+TCPMsg.endOfMsg).getBytes());
			//first we need to join the listener thread
			rmiInt.killListenThread();			
			streamToServer.close();
		}catch(IOException ex){
			rmiInt.outputToConsole("IOE Disconnecting: "+ex.getMessage());
		}
	}

	public void joinTable(String user, int tid) {
		try{
			streamToServer.write((Integer.toString(TCPMsg.JOIN_TBL)+" "+user+" "+Integer.toString(tid)+TCPMsg.endOfMsg).getBytes());
		}catch(IOException ex){
			rmiInt.outputToConsole(ex.getMessage());
		}
	}

	public void leaveTable(String user) {
		try{
			streamToServer.write((Integer.toString(TCPMsg.LEAVE_TBL)+" "+user+TCPMsg.endOfMsg).getBytes());
		}catch(IOException ex){
			rmiInt.outputToConsole(ex.getMessage());
		}
	}

	public void makeTable(String user) {
		try{
			streamToServer.write((Integer.toString(TCPMsg.MAKE_TBL)+" "+user+TCPMsg.endOfMsg).getBytes());
		}catch(IOException ex){
			rmiInt.outputToConsole(ex.getMessage());
		}	
	}

	public void move(String user, int fr, int fc, int tr, int tc) {
		String moveF = Integer.toString(fr)+","+ Integer.toString(fc);
		String moveT = Integer.toString(tr)+","+Integer.toString(tc);
		try{
			streamToServer.write((Integer.toString(TCPMsg.MOVE)+" "+user+" "+moveF+" "+moveT+TCPMsg.endOfMsg).getBytes());
		}catch(IOException ex){
			rmiInt.outputToConsole(ex.getMessage());
		}
		
	}

	public void msgAll(String user, String msg) {
		try{
			streamToServer.write((Integer.toString(TCPMsg.MSG_ALL)+" "+user+" "+msg+TCPMsg.endOfMsg).getBytes());
		}catch(IOException ex){
			rmiInt.outputToConsole(ex.getMessage());
		}
		
	}

	public void msgPlayer(String from, String to, String msg) {
		try{
			streamToServer.write((Integer.toString(TCPMsg.MSG_C)+" "+from+" "+to+" "+msg+TCPMsg.endOfMsg).getBytes());
		}catch(IOException ex){
			rmiInt.outputToConsole(ex.getMessage());
		}
		
	}

	public void playerReady(String user) {
		try{
			streamToServer.write((Integer.toString(TCPMsg.READY)+" "+user+TCPMsg.endOfMsg).getBytes());
		}catch(IOException ex){
			rmiInt.outputToConsole(ex.getMessage());
		}	
	}

	//@Override
	public void getTblStatus(String user, int tid) {
		try{
			streamToServer.write((Integer.toString(TCPMsg.ASK_TBL_STATUS)+" "+user+" "+Integer.toString(tid)+TCPMsg.endOfMsg).getBytes());
		}catch(IOException ex){
			rmiInt.outputToConsole(ex.getMessage());
		}	
	}

	@Override
	public void getProfile(String user, String profileFor) {
		try{
			streamToServer.write((Integer.toString(TCPMsg.GET_PROFILE)+" "+user+" "+profileFor+TCPMsg.endOfMsg).getBytes());
		}catch(IOException ex){
			rmiInt.outputToConsole(ex.getMessage());
		}	
	}

	@Override
	public void login(String user, String pwd) {
		try{
			streamToServer.write((Integer.toString(TCPMsg.LOGIN)+" "+user+" "+pwd+TCPMsg.endOfMsg).getBytes());
		}catch(IOException ex){
			rmiInt.outputToConsole(ex.getMessage());
		}	
		
	}

	//@Override
	public void observeTable(String user, int tid) {
		try{
			streamToServer.write((Integer.toString(TCPMsg.OBSERVE_TBL)+" "+user+" "+Integer.toString(tid)+TCPMsg.endOfMsg).getBytes());
		}catch(IOException ex){
			rmiInt.outputToConsole(ex.getMessage());
		}	
		
	}

	@Override
	public void register(String user, String pwd) {
		try{
			streamToServer.write((Integer.toString(TCPMsg.REGISTER)+" "+user+" "+pwd+TCPMsg.endOfMsg).getBytes());
		}catch(IOException ex){
			rmiInt.outputToConsole(ex.getMessage());
		}	
		
	}

	//@Override
	public void stopObserving(String user, int tid) {
		try{
			streamToServer.write((Integer.toString(TCPMsg.STOP_OBSERVING)+" "+user+" "+Integer.toString(tid)+TCPMsg.endOfMsg).getBytes());
		}catch(IOException ex){
			rmiInt.outputToConsole(ex.getMessage());
		}	
		
	}

	@Override
	public void updateProfile(String user, String desc) {
		try{
			streamToServer.write((Integer.toString(TCPMsg.UPDATE_PROFILE)+" "+user+" "+desc+TCPMsg.endOfMsg).getBytes());
		}catch(IOException ex){
			rmiInt.outputToConsole(ex.getMessage());
		}	
		
	}

	//@Override
	public void goMakeTable(String user) {
		try{
			streamToServer.write((Integer.toString(TCPMsg.GO_MAKE_TBL)+" "+user+TCPMsg.endOfMsg).getBytes());
		}catch(IOException ex){
			rmiInt.outputToConsole(ex.getMessage());
		}			
	}	
	
	//@Override
	public void goMove(String user, int tr, int tc) {
		String moveT = Integer.toString(tr)+","+Integer.toString(tc);
		try{
			streamToServer.write((Integer.toString(TCPMsg.GO_MOVE)+" "+user+" "+moveT+TCPMsg.endOfMsg).getBytes());
		}catch(IOException ex){
			rmiInt.outputToConsole(ex.getMessage());
		}			
	}


}
