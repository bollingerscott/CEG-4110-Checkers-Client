package game;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JButton;

import table.Table;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import lobby.lobbyWindow;
import RMIConnection.Interfaces.RMIServerInterface;
import Chat.ChatBar;

/*
 * The frame for the game
 * includes game instance and chat
 * Interfaces with the lobby
 * 
 * @author Scott Bollinger
 */
@SuppressWarnings("serial")
public class GameWindow extends JFrame {

	//private JFrame frmCheckers;
	private static RMIServerInterface server;
	private boolean observer;
	private Integer oppMoves = 0;
	private String myColor;
	private Game game;
	private Stats stats;
	private lobbyWindow myLobby;
	private Table myTable;


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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//frmCheckers = new JFrame();
		setTitle("Checkers");
		setResizable(false);
		setVisible(true);
		getContentPane().setBackground(Color.DARK_GRAY);
		setBounds(100, 100, 762, 637);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				//dispose();
				getMyLobby().setVisible(true);
			}
		});
		
		stats = new Stats();
		stats.setBackground(Color.WHITE);
		stats.setBounds(526, 6, 220, 553);
		getContentPane().add(stats);
		
		game = new Game(stats, observer, server, myTable, myColor);
		game.setForeground(Color.ORANGE);
		game.setBackground(new Color(139, 69, 19));
		game.setBounds(6, 6, 521, 424);
		getContentPane().add(game);
		
		JButton buttonHint = new JButton("Hint");
		buttonHint.setBounds(656, 570, 89, 23);
		getContentPane().add(buttonHint);
		
		ChatBar chatBar = new ChatBar(server);
		chatBar.setBounds(6, 436, 521, 161);
		getContentPane().add(chatBar);
		

		//repaint();
		//TODO hint ai algorithm stretch goal
		

	}

	public Integer getOppMoves() {
		return oppMoves;
	}

	public void setOppMoves(Integer oppMoves) {
		this.oppMoves = oppMoves;
	}
	
	public Game getGame() {
		return game;
	}
	
	public lobbyWindow getMyLobby(){
		return myLobby;
	}
}
