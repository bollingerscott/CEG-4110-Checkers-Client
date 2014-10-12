package RMIConnection.Interfaces;

public interface TCPServerInterface {
	/*
	 * These will be messages sent from the client to the server. This interface
	 * defines the module that translates and then sends messages to the game 
	 * server via tcp.
	 */
    public void msgAll(String user, String msg);
    public void msgPlayer(String from, String to, String msg);
    public void makeTable(String user);
    public void joinTable(String user, int tid);
    public void playerReady(String user);
    public void move(String user, int fr, int fc, int tr, int tc);
    public void leaveTable(String user);
    public void disconnect(String user);
    public void getTblStatus(String user, int tid);
    public void observeTable(String user, int tid);
    public void stopObserving(String user, int tid);
    public void register(String user, String pwd);
    public void login(String user, String pwd);
    public void updateProfile(String user, String desc);
    public void getProfile(String user, String profileFor);
    public void goMakeTable(String user);
    public void goMove(String user, int tr, int tc); 

}
