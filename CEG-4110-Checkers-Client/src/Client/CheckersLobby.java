package Client;

import game.GameWindow;

import java.util.*;

import RMIConnection.Interfaces.CheckersClient;
import RMIConnection.Interfaces.RMIServerInterface;

import java.awt.event.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.*;

import table.Table;
import lobby.lobbyWindow;

/**
 * Modified dereks code. Starts with a window for inputting server name and user
 * name. If that connects correctly the lobby window opens. This is where the
 * bulk of the work will be done. (Adding tables, joining tables, observing
 * tables, chat private message etc.)
 */
@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
public class CheckersLobby extends javax.swing.JFrame implements CheckersClient {

	{
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager
					.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public enum State {
		notConnected, connected, inLobby, onTable, inGame
	};

	private ArrayList<String> lobbyUserList; // string lists of users for output
	private static RMIServerInterface serverConnection;
	private static State curState;
	private String myName = "";

	private boolean isCheckers;
	private byte[][] curBoardState;
	private boolean debug = true; // set true for debug mode, which prints more
	private static lobbyWindow myLobby;
	private JFrame frame;
	private JTextField serverTextField;
	private JTextField Username;
	private JButton btnStartClient;

	private GameWindow game;
	private Table myTable;
	
	
	private final String DEFAULT_SERVER_IP = "::1"; // Usefor For debugging-
													// Brad local server = ::1,
													// derekServer 137.99.11.115

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				myLobby = new lobbyWindow();
				CheckersLobby tester = new CheckersLobby();

				System.setProperty("java.security.policy",
						"file:./src/Client/client.policy");
				System.setProperty("java.rmi.server.codebase", "file:./bin/");

				// now establish a presence in the RMI registry and try to get
				// the checkers server connector.
				try {
					// generate a random registry id for this player
					String name = "CheckersClient"
							+ (int) (Math.random() * 10000);
					// export the player to the registry. Stub is a reference to
					// the object in the reg.
					CheckersClient stub = (CheckersClient) UnicastRemoteObject
							.exportObject((CheckersClient) tester, 0);
					// get the registry
					Registry registry = LocateRegistry.getRegistry();
					// bind the object in registry to the unique registry id we
					// generated
					registry.rebind(name, stub);
					System.out.println("TestClient bound to registry!");
					// connect to the RMI server connection on this pc
					// (localhost) and give it the id of this client.
					tester.getServerConnection("localhost", name);

					// add a hook to disconnect for when the user force quits /
					// alt+f4 / cmd+q's
					Runtime.getRuntime().addShutdownHook(new Thread() {
						@Override
						public void run() {
							try {
								serverConnection.disconnect(false);
							} catch (RemoteException e) {
								/**
								 * Now cracks a noble heart. Good-night, sweet
								 * prince; And flights of angels sing thee to
								 * thy rest.
								 **/
							}
						}
					});

				} catch (RemoteException e) {
					System.out.println("Error binding client to registry.");
					System.out.println(e.getMessage());
				}
			}
		});
	}

	private void getServerConnection(String host, String clientID) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			String name = "CheckersServerInterface";
			Registry registry = LocateRegistry.getRegistry(host);
			serverConnection = (RMIServerInterface) registry.lookup(name);
			if (serverConnection != null) {
				System.out.println("Server connection found in registry!");
				serverConnection.registerPlayer(clientID, host);
			} else {
				System.out.println("Could not register with the server");
				System.exit(0);
			}
		} catch (Exception e) {
			System.err.println("TestClient Exception:");
			e.printStackTrace();
		}
	}

	public CheckersLobby() {
		super();
		lobbyUserList = new ArrayList<String>();
		curState = State.notConnected;
		myLobby.syncState(curState);
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().setLayout(null);

		btnStartClient = new JButton("Start Client!");
		btnStartClient.setBounds(299, 104, 125, 23);
		frame.getContentPane().add(btnStartClient);

		serverTextField = new JTextField(DEFAULT_SERVER_IP); // My default
																// server ip,
																// set
		// for easier testing. Feel
		// free to change
		serverTextField.setBounds(135, 81, 146, 23);
		frame.getContentPane().add(serverTextField);
		serverTextField.setColumns(10);

		Username = new JTextField("DefaultUserName");
		Username.setBounds(135, 138, 146, 23);
		frame.getContentPane().add(Username);
		Username.setColumns(10);

		JLabel lblNewLabel = new JLabel("Server IP Address");
		lblNewLabel.setBounds(29, 81, 110, 23);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(29, 138, 110, 23);
		frame.getContentPane().add(lblUsername);
		frame.setVisible(true);
		btnStartClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inputSubmit();
			}
		});
	}

	// Event for when user presses connect button on setup window.
	private void inputSubmit() {
		try {
			if (curState.equals(State.notConnected)) {
				String name = Username.getText();
				Username.setText("");
				String ip = serverTextField.getText();
				serverTextField.setText("");
				if (!serverConnection.connectToServer(ip, name)) {
					System.out
							.println("Connection failed. Check console output of RMI process for information.");
				} else {
					System.out.println("Connection success");
					curState = State.connected;
					myLobby.syncState(curState);
					frame.setVisible(false);
					myLobby.startWindow(serverConnection, name, curState);
				}
			}

		} catch (RemoteException e) {
			System.out.println("A remote exception occured: ");
			System.out.println(e.getMessage());
		} finally {
		}
	}

	// ///////////////////////////////////////////////////////////////////////////
	// //////////Utility functions to help deal with server interaction with GUI
	// ///////////////////////////////////////////////////////////////////////////

	// Outputs to main window in lobby window.
	private void output(String s) {
		myLobby.addTextMainLobbyWindow(s);
	}

	// Only works if debug is true, prints to console
	private void debugOutput(String s) {
		if (debug)
			System.out.println(">Debug message " + s);
	}

	// Updates user list, common funciton used by add/remove user as well as
	// intial user list
	private void updateUserList() {
		myLobby.setUsers(lobbyUserList);
		myLobby.updateUsers();
	}

	// ///////////////////////////////////////////////////////////////////////////
	// //////////////Server interaction functions, needed for satisfying
	// interface
	// ///////////////////////////////////////////////////////////////////////////
	public void connectionOK() {
		debugOutput("Server says connection OK!");
		curState = State.connected;
		myLobby.syncState(curState);
	}

	public void nowJoinedLobby(String user) {
		if (user.equals(this.myName)) {
			output(">> You have  joined the lobby.");
		}
		lobbyUserList.add(user);
		updateUserList();
	}

	// Got message from server, set at private message or global message and
	// update.
	public void newMsg(String user, String msg, boolean pm) {
		if (pm) {
			output("[PM] " + user + ": " + msg);
		} else
			output(user + ": " + msg);
	}

	// alert that a user has left the lobby
	public void nowLeftLobby(String user) {
		lobbyUserList.remove(user);
		updateUserList();
	}

	// updated listing of users in lobby
	public void usersInLobby(String[] users) {
		lobbyUserList.clear();
		for (String s : users)
			lobbyUserList.add(s);
		updateUserList();
	}

	// alert that you have joined the lobby
	public void youInLobby() {
		curState = State.inLobby;
		myLobby.syncState(curState);
		output(">> Welcome to the game lobby.");
	}

	// alert that you have left the lobby
	public void youLeftLobby() {
		curState = State.connected;
		myLobby.syncState(curState);
		output(">> You have left the game lobby.");
	}

	// initial listing of tables
	public void tableList(int[] tids) {
		myLobby.addInitialTables(tids);
	}

	// an alert saying that a table state has changed.
	// this is received whenever anyone joins or leaves a table,
	// or if table state is queried by calling getTblStatus()
	public void onTable(int tid, String blackSeat, String redSeat) {
	
		System.out.println("Should have created table");
		myTable = new Table(serverConnection,myName,tid,blackSeat,redSeat);

		//Add setting black and red seat names, aswell as TID somewhere.
		
		// TODO Table related logic

	}

	// same preconditions as onTable()
	// called immediately after onTable()
	public void tableGame(int tid) throws RemoteException {
		System.out.println("outputmethod tablegame");
		// TODO Table related logic
	}

	public void newTable(int t) {
		int[] myIntArray = { t };
		myLobby.addTables(myIntArray);

	}

	// alert that you have joined the table with id tid.
	public void joinedTable(int tid) {
		curState = State.onTable;
		myLobby.syncState(curState);
		debugOutput(">> You have joined table " + Integer.toString(tid));
	}

	// alert that you have left your table.
	public void alertLeftTable() {
		curState = State.connected;
		myLobby.syncState(curState);
		
		//myTable.closeWindow(); //TODO add closing window here
		debugOutput(">> You have left the table");
		// TODO Table related logic
	}

	// alert that at the table you are sitting at, a game is starting.
	public void gameStart() {
		curState = State.inGame;
		myLobby.syncState(curState);

		if (isCheckers) {
			curBoardState = new byte[8][8];
			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					// if both row & column are even (or ordd)
					if ((x % 2 == 0 && y % 2 == 0)
							|| (x % 2 != 0 && y % 2 != 0))
						curBoardState[y][x] = 0;
					else if (y < 3) // top three rows
						curBoardState[y][x] = 1;
					else if (y > 4) // bottom three rows
						curBoardState[y][x] = 2;
				}
			}
		} else {
			curBoardState = new byte[19][19];
			for (int y = 0; y < 19; y++)
				for (int x = 0; x < 19; x++)
					curBoardState[y][x] = 0;
		}
		game = new GameWindow(false, serverConnection);
		game.getGame().setUser(myName);
	}

	// alert that your color is Black, for the game.
	public void colorBlack() {
		game.getGame().setColor("black");
	}

	// alert that your color is Red, for the game.
	public void colorRed() {
		game.getGame().setColor("red");
	}

	// notice that your opponent has moved from position (fr,fc) to (tr,tc)
	public void oppMove(int fr, int fc, int tr, int tc) {
		debugOutput(">> oppMove(" + fr + "," + fc + "," + tr + "," + tc + ")");
	}

	// server has updated the board state
	public void curBoardState(int t, byte[][] boardState) {
		game.getGame().setBoardState(boardState);
	}

	// notice that for the game you are playing, you win!
	public void youWin() {
		game.getGame().setGameStatus("win");
	}

	// notice that for the game you are playing, you lost.
	public void youLose() {
		debugOutput(">> youLose()");
		game.getGame().setGameStatus("lose");
	}

	// its your turn.
	public void yourTurn() {
		debugOutput(">> yourTurn()");
		game.getGame().setTurn(true);
	}

	// you are now observing table tid.
	public void nowObserving(int tid) {
		debugOutput(">> nowObserving(" + tid + ")");
		game.getGame().setObserver(true);
	}

	// you stopped observing table tid.
	public void stoppedObserving(int tid) {
		debugOutput(">> stoppedObserving(" + tid + ")");
		game.getGame().setObserver(false);
	}

	// ///////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////Error messages
	// ///////////////////////////////////////////////////////////////
	public void networkException(String msg) {
		JOptionPane.showMessageDialog(null,
				"A network exception has occured. Connection lost.", "Error",
				JOptionPane.ERROR_MESSAGE);
		curState = State.notConnected;
		myLobby.syncState(curState);

	}

	public void nameInUseError() {
		Username.setText("");
		JOptionPane.showMessageDialog(null,
				"The name requested is in use. Please choose another.",
				"Error", JOptionPane.ERROR_MESSAGE);
		curState = State.notConnected;
		myLobby.syncState(curState);

		serverTextField.setText(DEFAULT_SERVER_IP);
	}

	public void nameIllegal() throws RemoteException {
		JOptionPane.showMessageDialog(null,
				"The name requested is in illegal. Please choose another.",
				"Error", JOptionPane.ERROR_MESSAGE);
		curState = State.notConnected;
		myLobby.syncState(curState);

		Username.setText("");
	}

	// the requested move is illegal.
	public void illegalMove() {
		// TODO GAMELOGIC?
		output(">> That move is illegal!");
	}

	// the table your trying to join is full.
	public void tableFull() {
		// TODO TABLE LOGIC
		output(">> The table you are trying to join is full. Please choose another one.");
	}

	// the table queried does not exist.
	public void tblNotExists() {
		// TODO TABLE LOGIC
		debugOutput(">> tblNotExists()");
	}

	// called if you say you are ready on a table with no current game.
	public void gameNotCreatedYet() {
		// TODO TABLE LOGIC/GAME LOGIC?
		output(">> Please wait for an opponent before starting the game.");
	}

	// called if it is not your turn but you make a move.
	public void notYourTurn() {
		// TODO GAME LOGIc
		output(">> It is not your turn!");
	}

	// called if you send a stop observing command but you are not observing a
	// table.
	public void notObserving() {
		// TODO OBserver logic
		debugOutput(">> notObserving()");
	}

	// called if you send a game command but your opponent is not ready
	public void oppNotReady() {
		// TODO Table logic/Game logic?
		output(">> Please wait for your opponent to start the game.");
	}

	// you cannot perform the requested operation because you are in the lobby.
	public void errorInLobby() {
		// TODO Lobby logic
		output(">> You cannot perform that action from within the lobby.");
	}

	// called if the client sends an ill-formated TCP message
	public void badMessage() {
		// TODO Lobby logic?
		debugOutput(">> badMessage()");
	}

	// called when your opponent leaves the table
	public void oppLeftTable() {
		// TODO Table logic
		debugOutput(">> oppLeftTable()");
	}

	// you cannot perform the requested op because you are not in the lobby.
	public void notInLobby() {
		// TODO lobby logic
		output(">> You cannot perform that action from outside of the lobby.");
	}
}
