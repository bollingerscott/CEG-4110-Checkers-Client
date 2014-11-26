package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;

import lobby.lobbyWindow;
import table.Table;
import Chat.ChatBar;
import RMIConnection.Interfaces.RMIServerInterface;

import javax.swing.JRadioButton;

import java.awt.Font;

/*
 * The frame for the game
 * includes game instance and chat
 * Interfaces with the lobby
 * 
 * @author Scott Bollinger
 */
@SuppressWarnings("serial")
public class GameWindow extends JFrame {

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
	private ChatBar chatBar;
	private String opponentName;
	private JRadioButton rdbtnRedBlack;
	private JRadioButton rdbtnBrownWhite;
	

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
		setBounds(100, 100, 762, 645);
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
		stats.setBounds(526, 6, 220, 533);
		getContentPane().add(stats);
		stats.repaint();

		game = new Game(stats, observer, server, myTable, myColor);
		game.setForeground(Color.ORANGE);
		game.setBackground(new Color(139, 69, 19));
		game.setBounds(6, 6, 521, 424);
		getContentPane().add(game);
		game.repaint();

		chatBar = new ChatBar(server);
		chatBar.setBounds(6, 436, 521, 175);
		getContentPane().add(chatBar);
		chatBar.setCurState("inGame");
		chatBar.setOpponent(opponentName);
		chatBar.setUserName(user);
		chatBar.setObserver(observer);
		chatBar.repaint();

		forfeitButton = new ForfeitButton(server, user, game.getOpponent(), this);
		forfeitButton.setRolloverEnabled(true);
		forfeitButton.setDefaultCapable(false);
		forfeitButton.setBounds(537, 576, 96, 35);
		getContentPane().add(forfeitButton);
		forfeitButton.setVisible(true);
		forfeitButton.repaint();

		hntbtnHint = new HintButton(myColor, game);
		hntbtnHint.setBounds(650, 576, 96, 35);
		getContentPane().add(hntbtnHint);
		hntbtnHint.setVisible(true);
		hntbtnHint.repaint();
		
		rdbtnRedBlack = new JRadioButton("Red Black");
		rdbtnRedBlack.setBounds(536, 546, 96, 23);
		getContentPane().add(rdbtnRedBlack);
		rdbtnRedBlack.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnRedBlack.setSelected(true);
		rdbtnRedBlack.setOpaque(false);
		rdbtnRedBlack.repaint();
		rdbtnRedBlack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.changeColor("black", "red");
				
			}
		});
		
		
		rdbtnBrownWhite = new JRadioButton("Brown White");
		rdbtnBrownWhite.setBounds(634, 546, 115, 23);
		getContentPane().add(rdbtnBrownWhite);
		rdbtnBrownWhite.setOpaque(false);
		rdbtnBrownWhite.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnBrownWhite.repaint();
		rdbtnBrownWhite.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.changeColor("brown", "white");
				
			}
		});
		


		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnBrownWhite);
		group.add(rdbtnRedBlack);
		
		if (observer){
			hntbtnHint.setEnabled(false);
			forfeitButton.setEnabled(false);
		}
	}

	public void sendMsg(String s){
		chatBar.addMessage(user, s);
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
		chatBar.setUserName(user);
	}
}
