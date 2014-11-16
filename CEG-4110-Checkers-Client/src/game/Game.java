package game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import javax.swing.border.BevelBorder;

import table.Table;
import RMIConnection.Interfaces.RMIServerInterface;

/*
 * The panel for the game
 * Sets the background image and draws the board
 * Sends user interaction to the board
 * Interfaces with the server for move operation with coordinates from board
 * 
 * @author Scott Bollinger
 */
@SuppressWarnings("serial")
public class Game extends JPanel implements MouseListener {

	private Board board;
	private String user;
	private String opponent;
	private Integer moves = 0;
	private Integer opponentMoves = 0;
	private String color;
	private static RMIServerInterface server;
	private Table myTable;
	private String gameStatus;
	private boolean turn = true;
	private Image wood;
	private boolean observer;
	private Integer left, taken, opponentLeft, opponentTaken;
	private Stats stats;
	private ImageIcon myIcon;
	private ImageIcon opponentsIcon;
	private boolean flip = false;
	
	/**
	 * Create the panel.
	 */

	public Game(Stats stats, boolean observer, RMIServerInterface server, Table myTable, String color) {
		Game.server = server;
		this.setObserver(observer);
		this.stats = stats;
		this.myTable = myTable;
		this.color = color;
		if (color.equalsIgnoreCase("black")){
			flip = true;
		}

		setBorder(new BevelBorder(BevelBorder.RAISED, new Color(139, 69, 19), null, null, null));
		setLayout(null);
		
		board = new Board(flip);
		board.setBorder(new BevelBorder(BevelBorder.RAISED, Color.LIGHT_GRAY, new Color(128, 128, 128), null, null));
		board.setBounds(54, 11, 402, 402);
		add(board);
		board.setLayout(null);
		
		wood = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/table.jpg"));
		setStats();
		setColor(color);
		
		if (myTable.isPlayer1()){
			user = myTable.getBlackseat();
			opponent = myTable.getRedseat();
			stats.getName1().setText(user);
			stats.getName2().setText(opponent);
		}
		else {
			user = myTable.getRedseat();
			opponent = myTable.getBlackseat();
			stats.getName2().setText(user);
			stats.getName1().setText(opponent);
		}

		addMouseListener(this);

	}
	
	@Override
	protected void paintComponent(Graphics g){
		if (myTable.isChanged()){
			setBoardState(myTable.getBoardState());
			myTable.setChanged(false);
		}
		board.paintComponent(g);
		g.drawImage(wood, 0, 0, null);
		setStats();
		if (isTurn()){
			stats.getColor1().setIcon(myIcon);
			stats.getColor2().setIcon(null);
		}
		else {
			stats.getColor1().setIcon(null);
			stats.getColor2().setIcon(opponentsIcon);
		}
		repaint();
	}
	
	private void move(String user, int fr, int fc, int tr, int tc){
		setStats();
		moves += 1;
		try {
			server.move(user, fr, fc, tr, tc);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}
	
	public Integer getLeft() {
		return left;
	}

	public void setLeft(Integer left) {
		this.left = left;
	}

	public Integer getTaken() {
		return taken;
	}

	public void setTaken(Integer taken) {
		this.taken = taken;
	}

	public Integer getOpponentLeft() {
		return opponentLeft;
	}

	public void setOpponentLeft(Integer opponentLeft) {
		this.opponentLeft = opponentLeft;
	}

	public Integer getOpponentTaken() {
		return opponentTaken;
	}

	public void setOpponentTaken(Integer opponentTaken) {
		this.opponentTaken = opponentTaken;
	}

	public void setStats(){
		if (color.equals("red")){
			setTaken(board.getBlackTaken());
			setLeft(board.getRedLeft());
			setOpponentTaken(board.getRedTaken());
			setOpponentLeft(board.getBlackLeft());
		}
		else {
			setTaken(board.getRedTaken());
			setLeft(board.getBlackLeft());
			setOpponentTaken(board.getBlackTaken());
			setOpponentLeft(board.getRedLeft());
		}
		if (myTable.isPlayer1()){
			stats.getMoves1().setText(moves.toString());
			stats.getMoves2().setText(opponentMoves.toString());
			stats.getLeft1().setText(left.toString());
			stats.getLeft2().setText(opponentLeft.toString());
			stats.getTaken1().setText(taken.toString());
			stats.getTaken2().setText(opponentTaken.toString());
		}
		else {
			stats.getMoves2().setText(moves.toString());
			stats.getMoves1().setText(opponentMoves.toString());
			stats.getLeft2().setText(left.toString());
			stats.getLeft1().setText(opponentLeft.toString());
			stats.getTaken2().setText(taken.toString());
			stats.getTaken1().setText(opponentTaken.toString());
		}
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
		stats.getName1().setText(user);
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
		if (color.equals("black")){
			board.setBoard_state(myTable.getBoardState());
			board.setOppositeColor("red");
			this.myIcon = (new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/black_piece.png"))));
			this.opponentsIcon = (new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/red_checker.png"))));
		}
		else {
			board.setOppositeColor("black");
			this.myIcon = (new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/red_checker.png"))));
			this.opponentsIcon = (new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/black_piece.png"))));
		}
		board.setColor(color);
	}

	public String getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
		//TODO result screen
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	@Override//TODO sound fx
	public void mouseClicked(MouseEvent e) {
		if (!isObserver() && isTurn()){
			board.mouseClicked(e);
			if (board.isMoving()){
				move(user, board.getFr(), board.getFc(), board.getTr(), board.getTc());
				board.setMoving(false);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	public boolean isObserver() {
		return observer;
	}

	public void setObserver(boolean observer) {
		this.observer = observer;
	}

	public String getOpponent() {
		return opponent;
	}

	public void setOpponent(String opponent) {
		this.opponent = opponent;
		stats.getName2().setText(opponent);
	}

	public Integer getMoves() {
		return moves;
	}

	public void setMoves(Integer moves) {
		this.moves = moves;
	}

	public Integer getOpponentMoves() {
		return opponentMoves;
	}

	public void setOpponentMoves(Integer opponentMoves) {
		this.opponentMoves = opponentMoves;
	}
}
