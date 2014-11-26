package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import table.Table;
import replay.ReplayFile;
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
	private Integer blackSeatMoves = 0;
	private Integer redSeatMoves = 0;
	private String color;
	private static RMIServerInterface server;
	private Table myTable;
	private String gameStatus = "tie";
	private boolean turn;
	private Image wood;
	private boolean observer;
	private Integer blackSeatTaken, redSeatTaken, blackSeatLeft, redSeatLeft;
	private Stats stats;
	private boolean flip = false;
	private boolean start = true;
	private Clip moveChecker;
	private ReplayFile replayFile;
	private List<byte[][]> states;
	private Tile hintedTile;
	private boolean changed = false;

	/**
	 * Create the panel.
	 */

	public Game(Stats stats, boolean observer, RMIServerInterface server, Table myTable, String color) {
		Game.server = server;
		this.setObserver(observer);
		this.stats = stats;
		this.myTable = myTable;
		this.color = color;
		this.states = new ArrayList<byte[][]>();
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

		stats.getBlackSeatName().setText(myTable.getBlackseat());
		stats.getRedSeatName().setText(myTable.getRedseat());

		addMouseListener(this);

		
	}

	private void playMoveSound(){
		try {
			moveChecker = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/move_checker.wav"));
			moveChecker.open(inputStream);
			moveChecker.start();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public byte[][] getBoardState() {
		return board.getBoard_state();
	}

	public String getColor() {
		return color;
	}

	public String getGameStatus() {
		return gameStatus;
	}

	public Integer getBlackSeatTaken() {
		return blackSeatTaken;
	}

	public Integer getBlackSeatMoves() {
		return blackSeatMoves;
	}

	public String getOpponent() {
		return opponent;
	}

	public Integer getBlackSeatLeft() {
		return blackSeatLeft;
	}

	public Integer getRedSeatMoves() {
		return redSeatMoves;
	}

	public Integer getRedSeatLeft() {
		return redSeatLeft;
	}

	public Integer getRedSeatTaken() {
		return redSeatTaken;
	}

	public String getUser() {
		return user;
	}

	public boolean isObserver() {
		return observer;
	}

	public boolean isTurn() {
		return turn;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!isObserver() && isTurn() && !((gameStatus.equalsIgnoreCase("win")) || (gameStatus.equalsIgnoreCase("lose")))){
			if (hintedTile != null){
				hintedTile.reset();
				board.enable(hintedTile, false);
				hintedTile = null;
			}
			board.mouseClicked(e);
			if (board.isMoving()){
				move(user, board.getFr(), board.getFc(), board.getTr(), board.getTc());
				board.setMoving(false);
				playMoveSound();
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

	private void move(String user, int fr, int fc, int tr, int tc){
		setStats();
		if (myTable.getBlackseat().equals(this.user)){
			blackSeatMoves += 1;
		}
		else {
			redSeatMoves += 1;
		}
		try {
			server.move(user, fr, fc, tr, tc);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		turn = false;
	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		if (myTable.isChanged() || start){
			if (!start) {}
			setBoardState(myTable.getBoardState());
			myTable.setChanged(false);
			start = false;
		}
		board.paintComponent(g);
		g.drawImage(wood, 0, 0, null);
		setStats();
		if (isTurn()){
			if (color.equals("black")){
				stats.setBlackSeatIconVisible(true);
				stats.setRedSeatIconVisible(false);
			}
			else {
				stats.setBlackSeatIconVisible(false);
				stats.setRedSeatIconVisible(true);
			}
		}
		else {
			if (color.equals("black")){
				stats.setBlackSeatIconVisible(false);
				stats.setRedSeatIconVisible(true);
			}
			else {
				stats.setBlackSeatIconVisible(true);
				stats.setRedSeatIconVisible(false);
			}
		}
		repaint();
	}

	public void setBoardState(byte[][] boardState) {
		board.setBoard_state(boardState);
		states.add(boardState);
	}

	public void setColor(String color) {
		this.color = color;
		if (color.equals("black")){
			board.setBoard_state(myTable.getBoardState());
			board.setOppositeColor("red");
		}
		else if (!isObserver()){
			board.setOppositeColor("black");
		}
		board.setColor(color);
	}

	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
		ResultScreen result = new ResultScreen(gameStatus);
	    int reply = JOptionPane.showConfirmDialog(this, "Would you like to save a replay of this game?", "Replay?", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
        	replayFile = new ReplayFile();
        	String fileName = JOptionPane.showInputDialog(this, "What would you like to name the replay: ", "Name?"); 
			fileName += ReplayFile.fileExtension;
        	replayFile.writeFile(fileName, states);
        }
	}

	public void setBlackSeatTaken(Integer blackSeatTaken) {
		this.blackSeatTaken = blackSeatTaken;
	}

	public void setBlackSeatMoves(Integer blackSeatMoves) {
		this.blackSeatMoves = blackSeatMoves;
	}

	public void setObserver(boolean observer) {
		this.observer = observer;
	}

	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}

	public void setBlackSeatLeft(Integer blackSeatLeft) {
		this.blackSeatLeft = blackSeatLeft;
	}

	public void setRedSeatMoves(Integer redSeatMoves) {
		this.redSeatMoves = redSeatMoves;
	}

	public void setRedSeatLeft(Integer redSeatLeft) {
		this.redSeatLeft = redSeatLeft;
	}

	public void setStats(){
		setRedSeatLeft(board.getRedLeft());
		setBlackSeatTaken(board.getBlackTaken());
		setRedSeatTaken(board.getRedTaken());
		setBlackSeatLeft(board.getBlackLeft());
		
		stats.getBlackSeatMoves().setText(blackSeatMoves.toString());
		stats.getRedSeatMoves().setText(redSeatMoves.toString());
		stats.getBlackSeatLeft().setText(blackSeatLeft.toString());
		stats.getRedSeatLeft().setText(redSeatLeft.toString());
		stats.getBlackSeatTaken().setText(redSeatTaken.toString());
		stats.getRedSeatTaken().setText(blackSeatTaken.toString());
	}

	public void setRedSeatTaken(Integer redSeatTaken) {
		this.redSeatTaken = redSeatTaken;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Board getBoard() {
		return board;
	}
	
	public void setHintedTile(Tile hint){
		this.hintedTile = hint;
	}

	public void changeColor(String string, String string2) {
		changed = !changed;
		board.setChanged(changed);
		board.changeColor(string, string2);
		stats.changeColor(changed);
		repaint();
	}
}
