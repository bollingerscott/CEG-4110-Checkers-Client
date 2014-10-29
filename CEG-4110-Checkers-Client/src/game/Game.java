package game;

import javax.swing.*;

import java.awt.*;

import javax.swing.border.BevelBorder;

import RMIConnection.Interfaces.RMIServerInterface;

public class Game extends JPanel {

	private Board board;
	private String user;
	private String color;
	private RMIServerInterface server;
	private int tid;
	private String gameStatus = null;
	private boolean turn;
	private Image table;
	
	/**
	 * Create the panel.
	 */
	public Game() {
		setBorder(new BevelBorder(BevelBorder.RAISED, new Color(139, 69, 19), null, null, null));
		setLayout(null);
		
		board = new Board();
		board.setBorder(new BevelBorder(BevelBorder.RAISED, Color.LIGHT_GRAY, new Color(128, 128, 128), null, null));
		board.setBounds(54, 11, 402, 402);
		add(board);
		board.setLayout(null);
		
		table = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/table.jpg"));
	}
	
	protected void paintComponent(Graphics g){
		board.paintComponent(g);
		//g.setColor(new Color(139, 69, 19));
		//g.fillRect(0, 0, 521, 424);
		g.drawImage(table, 0, 0, null);
	}
	
	public byte[][] getBoardState() {
		return board.getBoard_state();
	}

	public void setBoardState(byte[][] boardState) {
		board.setBoard_state(boardState);
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}
}
