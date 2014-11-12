package game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import javax.swing.border.BevelBorder;

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
	private String user = "Scott";
	private String opponent = "Opponent";
	private Integer moves = 0;
	private String color = "red";
	private static RMIServerInterface server;
	private int tid;
	private String gameStatus = null;
	private boolean turn;
	private Image table;
	private boolean observer;
	private Integer left, taken, opponentLeft, opponentTaken;
	private GameWindow window;
	
	/**
	 * Create the panel.
	 */
	@SuppressWarnings("static-access")
	public Game(GameWindow window, boolean observer, RMIServerInterface server) {
		this.server = server;
		this.setObserver(observer);
		this.window = window;

		setBorder(new BevelBorder(BevelBorder.RAISED, new Color(139, 69, 19), null, null, null));
		setLayout(null);
		
		board = new Board();
		board.setBorder(new BevelBorder(BevelBorder.RAISED, Color.LIGHT_GRAY, new Color(128, 128, 128), null, null));
		board.setBounds(54, 11, 402, 402);
		add(board);
		board.setLayout(null);
		
		table = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/table.jpg"));
		setStats();
		addMouseListener(this);
		
		
	}
	
	protected void paintComponent(Graphics g){
		board.paintComponent(g);
		g.drawImage(table, 0, 0, null);
		repaint();
	}
	
	public void draw(){
		repaint();
	}
	
	private void move(String user, int fr, int fc, int tr, int tc){
		try {
			server.move(user, fr, fc, tr, tc);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		setStats();
		moves += 1;
		window.setMoves(moves);
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
		if (color.equals("black")){
			board.setFlip(true);
			board.setOppositeColor("red");
		}
		else {
			board.setOppositeColor("black");
		}
		board.setColor(color);
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

	@Override//TODO sound fx
	public void mouseClicked(MouseEvent e) {
		if (!isObserver()){
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
	}

	public Integer getMoves() {
		return moves;
	}

	public void setMoves(Integer moves) {
		this.moves = moves;
	}
}
