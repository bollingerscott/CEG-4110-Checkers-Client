package game;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import lobby.lobbyWindow;
import table.Table;
import Chat.ChatBar;
import RMIConnection.Interfaces.RMIServerInterface;

/*
 * The frame for the game
 * includes game instance and chat
 * Interfaces with the lobby
 * 
 * @author Scott Bollinger
 */
@SuppressWarnings("serial")
public class GameWindow extends JFrame {

	/**
	 * Launch the application. for testing
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					@SuppressWarnings("unused")
					GameWindow window = new GameWindow(false, null, null, new Table(1, "Bob", "Scott"), "red");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	private static RMIServerInterface server;
	private boolean observer;
	private Integer oppMoves = 0;
	private String status;
	private String user;
	private boolean turn;
	private String myColor;
	private Game game;
	private Stats stats;
	private lobbyWindow myLobby;
	private Table myTable;
	private ForfeitButton forfeitButton;
	private HintButton hntbtnHint;

	/**
	 * Create the application.
	 */

	public GameWindow(boolean observer, RMIServerInterface server, lobbyWindow myLobby, Table myTable, String myColor) {
		super();
		GameWindow.server = server;
		this.observer = observer;
		this.myLobby = myLobby;
		this.myTable = myTable;
		this.myColor = myColor;
		initialize();	
	}

	public lobbyWindow getMyLobby(){
		return myLobby;
	}

	public Integer getOppMoves() {
		return oppMoves;
	}

	public String getStatus() {
		return status;
	}

	public String getUser() {
		return user;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Checkers Table: " + myTable.getTid());
		setResizable(false);
		setVisible(true);
		getContentPane().setBackground(Color.DARK_GRAY);
		setBounds(100, 100, 762, 637);
		getContentPane().setLayout(null);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				dispose();
				if (!observer){
					try {
						server.leaveTable(user);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					//getMyLobby().setVisible(true);
				}
				else {
					try {
						server.stopObserving(user, myTable.getTid());
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		stats = new Stats();
		stats.setBackground(Color.WHITE);
		stats.setBounds(526, 6, 220, 553);
		getContentPane().add(stats);
		stats.repaint();

		game = new Game(stats, observer, server, myTable, myColor);
		game.setForeground(Color.ORANGE);
		game.setBackground(new Color(139, 69, 19));
		game.setBounds(6, 6, 521, 424);
		getContentPane().add(game);
		game.repaint();

		ChatBar chatBar = new ChatBar(server);
		chatBar.setBounds(6, 436, 521, 161);
		getContentPane().add(chatBar);
		chatBar.repaint();
		
		forfeitButton = new ForfeitButton(server, user, game.getOpponent());
		forfeitButton.setBounds(536, 558, 96, 35);
		getContentPane().add(forfeitButton);
		
		hntbtnHint = new HintButton(myColor, game);
		hntbtnHint.setBounds(650, 558, 96, 32);
		getContentPane().add(hntbtnHint);

		if (observer){
			hntbtnHint.setEnabled(false);
			forfeitButton.setEnabled(false);
		}
		
		//TODO hint ai algorithm stretch goal		
	}

	public boolean isTurn() {
		return turn;
	}

	public void setOppMoves(Integer oppMoves) {
		this.oppMoves = oppMoves;
		game.setOpponentMoves(oppMoves);
	}

	public void setStatus(String status) {
		this.status = status;
		game.setGameStatus(status);
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
		game.setTurn(turn);
	}

	public void setUser(String user) {
		this.user = user;
		game.setUser(user);
	}
}
