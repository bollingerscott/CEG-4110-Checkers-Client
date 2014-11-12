package game;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;

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

	private JTextField player1name;
	private JTextField player2name;
	private JTextField moves1;
	private JTextField piecesLeft1;
	private JTextField piecesTaken1;
	private JTextField moves2;
	private JTextField piecesLeft2;
	private JTextField piecesTaken2;
	private static RMIServerInterface server;
	private boolean observer;
	private Integer oppMoves = 0;
	private Integer moves = 0;//TODO fix updating stats by moving stats into game class =(
	private Game game;


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
		
		game = new Game(this, observer, server);
		game.setForeground(Color.ORANGE);
		game.setBackground(new Color(139, 69, 19));
		game.setBounds(6, 6, 521, 424);
		frmCheckers.getContentPane().add(game);
		
		player1name = new JTextField();
		player1name.setEditable(false);
		player1name.setBackground(Color.LIGHT_GRAY);
		player1name.setFont(new Font("Tahoma", Font.PLAIN, 24));
		player1name.setBounds(537, 46, 208, 37);
		frmCheckers.getContentPane().add(player1name);
		player1name.setColumns(10);
		player1name.setText(game.getUser());
		
		player2name = new JTextField();
		player2name.setFont(new Font("Tahoma", Font.PLAIN, 24));
		player2name.setBackground(Color.LIGHT_GRAY);
		player2name.setEditable(false);
		player2name.setColumns(10);
		player2name.setBounds(537, 326, 208, 37);
		frmCheckers.getContentPane().add(player2name);
		player2name.setText(game.getOpponent());
		//TODO get opponent name somehow
		
		JLabel lblOfMoves1 = new JLabel("# of Moves");
		lblOfMoves1.setForeground(Color.LIGHT_GRAY);
		lblOfMoves1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblOfMoves1.setBounds(537, 94, 132, 35);
		frmCheckers.getContentPane().add(lblOfMoves1);
		
		moves1 = new JTextField();
		lblOfMoves1.setLabelFor(moves1);
		moves1.setBackground(Color.LIGHT_GRAY);
		moves1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		moves1.setEditable(false);
		moves1.setBounds(674, 94, 71, 35);
		frmCheckers.getContentPane().add(moves1);
		moves1.setColumns(10);
		moves1.setText(moves.toString());
		
		JLabel lblPiecesLeft1 = new JLabel("Pieces Left");
		lblPiecesLeft1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPiecesLeft1.setForeground(Color.LIGHT_GRAY);
		lblPiecesLeft1.setBounds(537, 140, 127, 37);
		frmCheckers.getContentPane().add(lblPiecesLeft1);
		
		piecesLeft1 = new JTextField();
		piecesLeft1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		piecesLeft1.setEditable(false);
		lblPiecesLeft1.setLabelFor(piecesLeft1);
		piecesLeft1.setBackground(Color.LIGHT_GRAY);
		piecesLeft1.setBounds(674, 140, 71, 37);
		frmCheckers.getContentPane().add(piecesLeft1);
		piecesLeft1.setColumns(10);
		piecesLeft1.setText(game.getLeft().toString());
		
		JLabel lblPiecesTaken1 = new JLabel("Pieces Taken");
		lblPiecesTaken1.setForeground(Color.LIGHT_GRAY);
		lblPiecesTaken1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPiecesTaken1.setBounds(537, 187, 132, 37);
		frmCheckers.getContentPane().add(lblPiecesTaken1);
		
		piecesTaken1 = new JTextField();
		piecesTaken1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		piecesTaken1.setEditable(false);
		lblPiecesTaken1.setLabelFor(piecesTaken1);
		piecesTaken1.setBackground(Color.LIGHT_GRAY);
		piecesTaken1.setBounds(674, 188, 71, 37);
		frmCheckers.getContentPane().add(piecesTaken1);
		piecesTaken1.setColumns(10);
		piecesTaken1.setText(game.getTaken().toString());
		
		JLabel lblPlayer2 = new JLabel("Player 2");
		lblPlayer2.setLabelFor(player2name);
		lblPlayer2.setForeground(Color.LIGHT_GRAY);
		lblPlayer2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlayer2.setBounds(537, 290, 86, 29);
		frmCheckers.getContentPane().add(lblPlayer2);
		
		JLabel lblOfMoves2 = new JLabel("# of Moves");
		lblOfMoves2.setForeground(Color.LIGHT_GRAY);
		lblOfMoves2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblOfMoves2.setBounds(537, 374, 132, 35);
		frmCheckers.getContentPane().add(lblOfMoves2);
		
		JLabel lblPiecesLeft2 = new JLabel("Pieces Left");
		lblPiecesLeft2.setForeground(Color.LIGHT_GRAY);
		lblPiecesLeft2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPiecesLeft2.setBounds(537, 414, 127, 37);
		frmCheckers.getContentPane().add(lblPiecesLeft2);
		
		JLabel lblPiecesTaken2 = new JLabel("Pieces Taken");
		lblPiecesTaken2.setForeground(Color.LIGHT_GRAY);
		lblPiecesTaken2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPiecesTaken2.setBounds(537, 457, 132, 37);
		frmCheckers.getContentPane().add(lblPiecesTaken2);
		
		moves2 = new JTextField();
		lblOfMoves2.setLabelFor(moves2);
		moves2.setBackground(Color.LIGHT_GRAY);
		moves2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		moves2.setEditable(false);
		moves2.setColumns(10);
		moves2.setBounds(674, 374, 71, 35);
		frmCheckers.getContentPane().add(moves2);
		moves2.setText(oppMoves.toString());
		
		piecesLeft2 = new JTextField();
		lblPiecesLeft2.setLabelFor(piecesLeft2);
		piecesLeft2.setBackground(Color.LIGHT_GRAY);
		piecesLeft2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		piecesLeft2.setEditable(false);
		piecesLeft2.setColumns(10);
		piecesLeft2.setBounds(674, 415, 71, 35);
		frmCheckers.getContentPane().add(piecesLeft2);
		piecesLeft2.setText(game.getOpponentLeft().toString());
		
		piecesTaken2 = new JTextField();
		lblPiecesTaken2.setLabelFor(piecesTaken2);
		piecesTaken2.setBackground(Color.LIGHT_GRAY);
		piecesTaken2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		piecesTaken2.setEditable(false);
		piecesTaken2.setColumns(10);
		piecesTaken2.setBounds(674, 458, 71, 35);
		frmCheckers.getContentPane().add(piecesTaken2);
		piecesTaken2.setText(game.getOpponentTaken().toString());
		
		JLabel lblVS = new JLabel("VS");
		lblVS.setForeground(Color.RED);
		lblVS.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblVS.setBounds(631, 253, 38, 24);
		frmCheckers.getContentPane().add(lblVS);
		
		JLabel lblPlayer1 = new JLabel("Player 1");
		lblPlayer1.setLabelFor(player1name);
		lblPlayer1.setForeground(Color.LIGHT_GRAY);
		lblPlayer1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlayer1.setBounds(537, 6, 86, 29);
		frmCheckers.getContentPane().add(lblPlayer1);
		
		JButton buttonHint = new JButton("Hint");
		buttonHint.setBounds(656, 570, 89, 23);
		frmCheckers.getContentPane().add(buttonHint);
		
		ChatBar chatBar = new ChatBar(server);
		chatBar.setBounds(6, 436, 521, 161);
		frmCheckers.getContentPane().add(chatBar);
		frmCheckers.repaint();
		//TODO hint ai algorithm stretch goal
		

	}

	public Integer getOppMoves() {
		return oppMoves;
	}

	public void setOppMoves(Integer oppMoves) {
		this.oppMoves = oppMoves;
	}
	
	public JTextField getMoves1() {
		return moves1;
	}

	public void setMoves1(JTextField moves1) {
		this.moves1 = moves1;
	}

	public JTextField getPiecesLeft1() {
		return piecesLeft1;
	}

	public void setPiecesLeft1(JTextField piecesLeft1) {
		this.piecesLeft1 = piecesLeft1;
	}

	public JTextField getPiecesTaken1() {
		return piecesTaken1;
	}

	public void setPiecesTaken1(JTextField piecesTaken1) {
		this.piecesTaken1 = piecesTaken1;
	}

	public JTextField getMoves2() {
		return moves2;
	}

	public void setMoves2(JTextField moves2) {
		this.moves2 = moves2;
	}

	public JTextField getPiecesLeft2() {
		return piecesLeft2;
	}

	public void setPiecesLeft2(JTextField piecesLeft2) {
		this.piecesLeft2 = piecesLeft2;
	}

	public JTextField getPiecesTaken2() {
		return piecesTaken2;
	}

	public void setPiecesTaken2(JTextField piecesTaken2) {
		this.piecesTaken2 = piecesTaken2;
	}

	public Integer getMoves() {
		return moves;
	}

	public void setMoves(Integer moves) {
		this.moves = moves;
	}
	
	public Game getGame() {
		return game;
	}

}
