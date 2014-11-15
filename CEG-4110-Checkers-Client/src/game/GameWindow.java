package game;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import RMIConnection.Interfaces.RMIServerInterface;
import Chat.ChatBar;

/*
 * The frame for the game
 * includes game instance and chat
 * Interfaces with the lobby
 * 
 * @author Scott Bollinger
 */
public class GameWindow {

	private JFrame frmCheckers;
	private static RMIServerInterface server;
	private boolean observer;
	private Integer oppMoves = 0;
	private Game game;
	private Stats stats;


	/**
	 * Launch the application. for testing
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					GameWindow window = new GameWindow(false, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	/**
	 * Create the application.
	 */
	@SuppressWarnings("static-access")
	public GameWindow(boolean observer, RMIServerInterface server) {
		this.server = server;
		this.observer = observer;
		initialize();
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCheckers = new JFrame();
		frmCheckers.setTitle("Checkers");
		frmCheckers.setResizable(false);
		frmCheckers.setVisible(true);
		frmCheckers.getContentPane().setBackground(Color.DARK_GRAY);
		frmCheckers.setBounds(100, 100, 762, 637);
		frmCheckers.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCheckers.getContentPane().setLayout(null);
		frmCheckers.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				frmCheckers.setVisible(false);
				frmCheckers.dispose();
			}
		});
		
		stats = new Stats();
		stats.setBackground(Color.WHITE);
		stats.setBounds(526, 6, 220, 553);
		frmCheckers.getContentPane().add(stats);
		
		game = new Game(stats, observer, server);
		game.setForeground(Color.ORANGE);
		game.setBackground(new Color(139, 69, 19));
		game.setBounds(6, 6, 521, 424);
		frmCheckers.getContentPane().add(game);
		
		JButton buttonHint = new JButton("Hint");
		buttonHint.setBounds(656, 570, 89, 23);
		frmCheckers.getContentPane().add(buttonHint);
		
		ChatBar chatBar = new ChatBar(server);
		chatBar.setBounds(6, 436, 521, 161);
		frmCheckers.getContentPane().add(chatBar);
		

		//frmCheckers.repaint();
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
}
