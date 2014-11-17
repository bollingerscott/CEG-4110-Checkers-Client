package Client;

import game.GameWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import lobby.lobbyWindow;
import table.Table;
import table.TableScreen;
import RMIConnection.Interfaces.CheckersClient;
import RMIConnection.Interfaces.RMIServerInterface;

/**
 * Modified dereks code. Starts with a window for inputting server name and user
 * name. If that connects correctly the lobby window opens. This is where the
 * bulk of the work will be done. (Adding tables, joining tables, observing
 * tables, chat private message etc.)
 */

public class CheckersLobby implements CheckersClient {

	public enum State {
		notConnected, connected, inLobby, onTable, inGame
	}

	// Brad local server = ::1,
	// derekServer 137.99.11.115	130.108.28.165 
	//fe80::250c:975d:58e0:52f4%19
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				myLobby = new lobbyWindow();
				myLobby.addWindowListener(new WindowAdapter(){
					@Override
					public void windowClosing(WindowEvent e) {
						try {
							if (curState != State.notConnected){
								serverConnection.disconnect(true);
							}
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						System.exit(1);
					}
				});
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
							.exportObject(tester, 0);
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
	};

	{
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager
					.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private ArrayList<String> lobbyUserList; // string lists of users for output
	private static RMIServerInterface serverConnection;
	private static State curState;
	private String myName = "";

	private String myColor;
	private boolean isCheckers;
	private byte[][] curBoardState;
	private boolean debug = false; // set true for debug mode, which prints more
	private static lobbyWindow myLobby;
	private JFrame frame;
	private JTextField serverTextField;
	private JTextField Username;

	private JButton btnStartClient;
	private GameWindow game;
	private Integer myTid;
	private Map<Integer, GameWindow> observeGames = new HashMap<>();
	private Map<Integer, Table> tables = new HashMap<>();

	private TableScreen myTable;
	//130.108.203.235:45322
	private final String DEFAULT_SERVER_IP = "130.108.28.165"; // Usefor For debugging-

	public CheckersLobby() {
		super();
		lobbyUserList = new ArrayList<String>();
		curState = State.notConnected;
		myLobby.syncState(curState);
		initialize();
	}

	// alert that you have left your table.
	@Override
	public void alertLeftTable() {
		curState = State.connected;
		myLobby.syncState(curState);

		//myTable.closeWindow(); //TODO add closing window here
		debugOutput(">> You have left the table");
		// TODO Table related logic
	}

	// called if the client sends an ill-formated TCP message
	@Override
	public void badMessage() {
		// TODO Lobby logic?
		debugOutput(">> badMessage()");
	}

	// alert that your color is Black, for the game.
	@Override
	public void colorBlack() {
		this.myColor = "black";
		//game.getGame().setColor("black");
	}

	// ///////////////////////////////////////////////////////////////////////////
	// //////////Utility functions to help deal with server interaction with GUI
	// ///////////////////////////////////////////////////////////////////////////

	// alert that your color is Red, for the game.
	@Override
	public void colorRed() {
		this.myColor = "red";
		//game.getGame().setColor("red");
	}

	// ///////////////////////////////////////////////////////////////////////////
	// //////////////Server interaction functions, needed for satisfying
	// interface
	// ///////////////////////////////////////////////////////////////////////////
	@Override
	public void connectionOK() {
		debugOutput("Server says connection OK!");
		curState = State.connected;
		myLobby.syncState(curState);
	}

	// server has updated the board state
	/*
	 * TODO Need to move board state to table and get board state from table in game.
	 */
	@Override
	public void curBoardState(int t, byte[][] boardState) {
		Table table = tables.get(t);
		table.setBoardState(boardState);
		//game.getGame().setBoardState(boardState);
	}

	// Only works if debug is true, prints to console
	private void debugOutput(String s) {
		if (debug)
			System.out.println(">Debug message " + s);
	}

	// you cannot perform the requested operation because you are in the lobby.
	@Override
	public void errorInLobby() {
		// TODO Lobby logic
		output(">> You cannot perform that action from within the lobby.");
	}

	// called if you say you are ready on a table with no current game.
	@Override
	public void gameNotCreatedYet() {
		// TODO TABLE LOGIC/GAME LOGIC?
		output(">> Please wait for an opponent before starting the game.");
	}

	// alert that at the table you are sitting at, a game is starting.
	@Override
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
		game = new GameWindow(false, serverConnection, myLobby, tables.get(myTid), myColor);
		game.setUser(myName);
		myTable = null;
		myLobby.setVisible(false);
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

	// the requested move is illegal.
	@Override
	public void illegalMove() {
		// TODO GAMELOGIC?
		output(">> That move is illegal!");
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
			@Override
			public void actionPerformed(ActionEvent arg0) {
				inputSubmit();
			}
		});
	}

	// Event for when user presses connect button on setup window.
	private void inputSubmit() {
		try {
			if (curState.equals(State.notConnected)) {
				this.myName = Username.getText(); //setting class variable
				Username.setText("");
				String ip = serverTextField.getText();
				serverTextField.setText("");
				if (!serverConnection.connectToServer(ip, this.myName)) {
					System.out
					.println("Connection failed. Check console output of RMI process for information.");
				} else {
					System.out.println("Connection success");
					curState = State.connected;
					myLobby.syncState(curState);
					frame.setVisible(false);
					myLobby.startWindow(serverConnection, this.myName, curState);
				}
			}

		} catch (RemoteException e) {
			System.out.println("A remote exception occured: ");
			System.out.println(e.getMessage());
		} finally {
		}
	}

	// alert that you have joined the table with id tid.
	@Override
	public void joinedTable(int tid) {
		curState = State.onTable;
		myLobby.syncState(curState);
		debugOutput(">> You have joined table " + Integer.toString(tid));

		//TODO: grab table and make a TableScreen
		Table table = tables.get(tid);
		table.setRedseat(myName);
		table.setPlayer1(false);
		this.myTid = tid;
		if (myTable == null) {
			myTable = new TableScreen(serverConnection, myName, tid, table.getBlackseat(), myName);
		}
		else {
			myTable.update(table.getBlackseat(), table.getRedseat());
		}
	}

	@Override
	public void nameIllegal() throws RemoteException {
		JOptionPane.showMessageDialog(null,
				"The name requested is in illegal. Please choose another.",
				"Error", JOptionPane.ERROR_MESSAGE);
		curState = State.notConnected;
		myLobby.syncState(curState);

		Username.setText("");
	}

	@Override
	public void nameInUseError() {
		Username.setText("");
		JOptionPane.showMessageDialog(null,
				"The name requested is in use. Please choose another.",
				"Error", JOptionPane.ERROR_MESSAGE);
		curState = State.notConnected;
		myLobby.syncState(curState);

		serverTextField.setText(DEFAULT_SERVER_IP);
	}

	// ///////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////Error messages
	// ///////////////////////////////////////////////////////////////
	@Override
	public void networkException(String msg) {
		JOptionPane.showMessageDialog(null,
				"A network exception has occured. Connection lost.", "Error",
				JOptionPane.ERROR_MESSAGE);
		curState = State.notConnected;
		myLobby.syncState(curState);

	}

	// Got message from server, set at private message or global message and
	// update.
	@Override
	public void newMsg(String user, String msg, boolean pm) {
		if (pm) {
			output("[PM] " + user + ": " + msg);
		} else
			output(user + ": " + msg);
	}

	@Override
	public void newTable(int t) {
		int[] myIntArray = { t };
		myLobby.addTables(myIntArray);
		Table table = new Table(t, myName, "-1");
		tables.put(t, table);
		this.myTid = t;
		table.setPlayer1(true);
	}

	// you cannot perform the requested op because you are not in the lobby.
	@Override
	public void notInLobby() {
		// TODO lobby logic
		output(">> You cannot perform that action from outside of the lobby.");
	}

	// called if you send a stop observing command but you are not observing a
	// table.
	@Override
	public void notObserving() {
		// TODO OBserver logic
		debugOutput(">> notObserving()");
	}

	// called if it is not your turn but you make a move.
	@Override
	public void notYourTurn() {
		// TODO GAME LOGIc
		output(">> It is not your turn!");
	}

	@Override
	public void nowJoinedLobby(String user) {
		if (user.equals(this.myName)) {
			output(">> You have  joined the lobby.");
		}
		lobbyUserList.add(user);
		updateUserList();
	}

	// alert that a user has left the lobby
	@Override
	public void nowLeftLobby(String user) {
		lobbyUserList.remove(user);
		updateUserList();
	}

	// you are now observing table tid.
	@Override
	public void nowObserving(int tid) {
		debugOutput(">> nowObserving(" + tid + ")");
		observeGames.put(tid, new GameWindow(true, serverConnection, myLobby, tables.get(tid), ""));
	}

	// an alert saying that a table state has changed.
	// this is received whenever anyone joins or leaves a table,
	// or if table state is queried by calling getTblStatus()
	@Override
	public void onTable(int tid, String blackSeat, String redSeat) {
		Table table = tables.get(tid);
		table.setBlackseat(blackSeat);
		table.setRedseat(redSeat);
		table.setTid(tid);
		//TODO: have a list of tables. to check against. This currently assumes one (myTable)
		/*System.out.println("Should have created table");
		if (myTable == null) {
			myTable = new TableScreen(serverConnection,myName,tid,blackSeat,redSeat);
		}
		else {
			myTable.update(blackSeat, redSeat);
		}*/

		// TODO Table related logic

	}

	// called when your opponent leaves the table
	@Override
	public void oppLeftTable() {
		if (myTable != null) {
			myTable.oppLeft();
		}
		CheckersLobby.curState = State.inLobby;
		// TODO Table logic
		debugOutput(">> oppLeftTable()");
	}

	// notice that your opponent has moved from position (fr,fc) to (tr,tc)
	@Override
	public void oppMove(int fr, int fc, int tr, int tc) {
		debugOutput(">> oppMove(" + fr + "," + fc + "," + tr + "," + tc + ")");
		game.setOppMoves((game.getOppMoves())+1);
	}

	// called if you send a game command but your opponent is not ready
	@Override
	public void oppNotReady() {
		// TODO Table logic/Game logic?
		output(">> Please wait for your opponent to start the game.");
	}

	// Outputs to main window in lobby window.
	private void output(String s) {
		myLobby.addTextMainLobbyWindow(s);
	}

	// you stopped observing table tid.
	@Override
	public void stoppedObserving(int tid) {
		debugOutput(">> stoppedObserving(" + tid + ")");
		observeGames.remove(tid);
	}

	// the table your trying to join is full.
	@Override
	public void tableFull() {
		// TODO TABLE LOGIC
		output(">> The table you are trying to join is full. Please choose another one.");
	}

	// same preconditions as onTable()
	// called immediately after onTable()
	public void tableGame(int tid) throws RemoteException {
		System.out.println("outputmethod tablegame");
		// TODO Table related logic
		output(">> tableGame called");
		newTable(tid);
	}

	// initial listing of tables
	@Override
	public void tableList(int[] tids) {
		myLobby.addInitialTables(tids);
		for (int tid : tids){
			tables.put(tid, new Table(tid, "-1", "-1"));
			try {
				serverConnection.getTblStatus(myName, tid);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	// the table queried does not exist.
	@Override
	public void tblNotExists() {
		// TODO TABLE LOGIC
		debugOutput(">> tblNotExists()");
	}

	// Updates user list, common funciton used by add/remove user as well as
	// intial user list
	private void updateUserList() {
		myLobby.setUsers(lobbyUserList);
		myLobby.updateUsers();
	}

	// updated listing of users in lobby
	@Override
	public void usersInLobby(String[] users) {
		lobbyUserList.clear();
		for (String s : users)
			lobbyUserList.add(s);
		updateUserList();
	}

	// alert that you have joined the lobby
	@Override
	public void youInLobby() {
		curState = State.inLobby;
		myLobby.syncState(curState);
		output(">> Welcome to the game lobby.");
	}

	// alert that you have left the lobby
	@Override
	public void youLeftLobby() {
		curState = State.connected;
		myLobby.syncState(curState);
		output(">> You have left the game lobby.");
	}

	// notice that for the game you are playing, you lost.
	@Override
	public void youLose() {
		debugOutput(">> youLose()");
		game.setStatus("lose");
	}

	// its your turn.
	@Override
	public void yourTurn() {
		debugOutput(">> yourTurn()");
		game.setTurn(true);
	}

	// notice that for the game you are playing, you win!
	@Override
	public void youWin() {
		game.setStatus("win");
	}
}
