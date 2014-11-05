package game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageProducer;
import java.rmi.RemoteException;

import javax.swing.border.BevelBorder;

import RMIConnection.Interfaces.RMIServerInterface;

public class Game extends JPanel implements MouseListener {

	private Board board;
	private String user;
	private String color;
	private RMIServerInterface server;
	private int tid;
	private String gameStatus = null;
	private boolean turn;
	private Image table;
	private boolean observer;
	
	/**
	 * Create the panel.
	 */
	public Game(boolean observer/*, RMIServerInterface server*/) {
		//this.server = server; //commented for testing purposes
		this.setObserver(observer);

		setBorder(new BevelBorder(BevelBorder.RAISED, new Color(139, 69, 19), null, null, null));
		setLayout(null);
		
		board = new Board();
		board.setBorder(new BevelBorder(BevelBorder.RAISED, Color.LIGHT_GRAY, new Color(128, 128, 128), null, null));
		board.setBounds(54, 11, 402, 402);
		add(board);
		board.setLayout(null);
		
		table = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/table.jpg"));
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	@Override
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public boolean isObserver() {
		return observer;
	}

	public void setObserver(boolean observer) {
		this.observer = observer;
	}
}
