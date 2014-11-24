package game;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import lobby.lobbyWindow;
import table.Table;
import Chat.ChatBar;
import Client.CheckersLobby.State;
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
	//private ChatBar chatBar;
	private JTextField chatInputField;
	private JTextArea chatTextArea;
	private JScrollPane chatScrollPane;
	private String opponentName;

	/**
	 * Create the application.
	 */
	public GameWindow(boolean observer, RMIServerInterface server, lobbyWindow myLobby, Table myTable, String myColor) {
		super();
		getContentPane().setIgnoreRepaint(true);
		GameWindow.server = server;
		this.observer = observer;
		this.myLobby = myLobby;
		this.myTable = myTable;
		this.myColor = myColor;
		myTable.setPlayer1(false);
		if (myColor.equalsIgnoreCase("red")){
			opponentName = myTable.getBlackseat();
		}
		else {
			opponentName = myTable.getRedseat();
		}
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
		stats.setBackground(Color.DARK_GRAY);
		stats.setBounds(526, 6, 220, 546);
		getContentPane().add(stats);
		stats.repaint();

		game = new Game(stats, observer, server, myTable, myColor);
		game.setForeground(Color.ORANGE);
		game.setBackground(new Color(139, 69, 19));
		game.setBounds(6, 6, 521, 424);
		getContentPane().add(game);
		game.repaint();

		/*chatBar = new ChatBar(server);
		chatBar.setBounds(6, 436, 521, 161);
		getContentPane().add(chatBar);
		chatBar.repaint();*/

		forfeitButton = new ForfeitButton(server, user, game.getOpponent(), this);
		forfeitButton.setRolloverEnabled(true);
		forfeitButton.setDefaultCapable(false);
		forfeitButton.setBounds(536, 558, 96, 35);
		getContentPane().add(forfeitButton);
		forfeitButton.setVisible(true);
		forfeitButton.repaint();

		hntbtnHint = new HintButton(myColor, game);
		hntbtnHint.setBounds(650, 558, 96, 35);
		getContentPane().add(hntbtnHint);
		hntbtnHint.setVisible(true);
		hntbtnHint.repaint();

		if (observer){
			hntbtnHint.setEnabled(false);
			forfeitButton.setEnabled(false);
		}

		JPanel chatPlaceHolderPanel = new JPanel();
		chatPlaceHolderPanel.setBackground(Color.DARK_GRAY);
		chatPlaceHolderPanel.setBounds(10, 437, 517, 171);
		getContentPane().add(chatPlaceHolderPanel);
		chatPlaceHolderPanel.setLayout(null);

		chatInputField = new JTextField();
		chatInputField.setBounds(1, 127, 421, 33);
		chatPlaceHolderPanel.add(chatInputField);
		chatInputField.setColumns(10);

		JButton chatSendButton = new JButton("Send");
		chatSendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (chatInputField.getText().length() > 0) {
					try {
						String input = chatInputField.getText();
						System.out.println(opponentName);
							server.sendMsg(opponentName, input);
					} catch (RemoteException e) {
						e.printStackTrace();
						System.out.println("Caught error sending message?");

					}
				}
				chatInputField.setText("");
			}
		});
		chatSendButton.setBounds(432, 127, 85, 33);
		chatPlaceHolderPanel.add(chatSendButton);

		chatScrollPane = new JScrollPane();
		chatScrollPane
		.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		chatScrollPane.setBounds(0, 1, 517, 115);
		chatPlaceHolderPanel.add(chatScrollPane);

		chatTextArea = new JTextArea();
		chatScrollPane.setViewportView(chatTextArea);
		chatTextArea.setEditable(false);

		chatPlaceHolderPanel.add(chatScrollPane);
		chatPlaceHolderPanel.repaint();
	}

	// Adds to main table screen
	public void addTextGameWindow(String string) {
		if (chatTextArea != null) {
			if (chatTextArea.getText().length() == 0) {
				chatTextArea.setText(string);
			} else
				chatTextArea.setText(chatTextArea.getText() + "\n" + string);
		}
		chatTextArea.select(Integer.MAX_VALUE, 0);
	}

	public boolean isTurn() {
		return turn;
	}

	public void setOppMoves(Integer oppMoves) {
		this.oppMoves = oppMoves;
		if (myColor.equalsIgnoreCase("red")) {
			game.setBlackSeatMoves(oppMoves);
		}
		else {
			game.setRedSeatMoves(oppMoves);
		}
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
