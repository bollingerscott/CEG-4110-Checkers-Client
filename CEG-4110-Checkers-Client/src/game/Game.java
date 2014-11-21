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
import replay.replayFile;
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
	private String gameStatus = "tie";
	private boolean turn = true;
	private Image wood;
	private boolean observer;
	private Integer left, taken, opponentLeft, opponentTaken;
	private Stats stats;
	private ImageIcon myIcon;
	private ImageIcon opponentsIcon;
	private boolean flip = false;
	private boolean start = true;
	private Clip moveChecker;
	private replayFile replayFile;
	private List<byte[][]> states;
	private Tile hintedTile;

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

	public Integer getLeft() {
		return left;
	}

	public Integer getMoves() {
		return moves;
	}

	public String getOpponent() {
		return opponent;
	}

	public Integer getOpponentLeft() {
		return opponentLeft;
	}

	public Integer getOpponentMoves() {
		return opponentMoves;
	}

	public Integer getOpponentTaken() {
		return opponentTaken;
	}

	public Integer getTaken() {
		return taken;
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
		moves += 1;
		playMoveSound();
		try {
			server.move(user, fr, fc, tr, tc);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		turn = false;
	}

	@Override
	protected void paintComponent(Graphics g){
		if (myTable.isChanged() || start){
			setBoardState(myTable.getBoardState());
			myTable.setChanged(false);
			start = false;
		}
		board.paintComponent(g);
		g.drawImage(wood, 0, 0, null);
		setStats();
		if (isTurn()){
			stats.setColor1Icon(myIcon);
			stats.setColor2Icon(null);
		}
		else {
			stats.setColor1Icon(null);
			stats.setColor2Icon(opponentsIcon);
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
			this.myIcon = (new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/black_piece.png"))));
			this.opponentsIcon = (new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/red_checker.png"))));
		}
		else if (!isObserver()){
			board.setOppositeColor("black");
			this.myIcon = (new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/red_checker.png"))));
			this.opponentsIcon = (new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/black_piece.png"))));
		}
		board.setColor(color);
	}

	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
		ResultScreen result = new ResultScreen(gameStatus);
	    int reply = JOptionPane.showConfirmDialog(this, "Would you like to save a replay of this game?", "Replay?", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
        	replayFile = new replayFile();
        	String fileName = JOptionPane.showInputDialog(this, "What would you like to name the replay: ", "Name?"); 
        	try {
				replay.replayFile.writeFile(fileName, states);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
        }
	}

	public void setLeft(Integer left) {
		this.left = left;
	}

	public void setMoves(Integer moves) {
		this.moves = moves;
	}

	public void setObserver(boolean observer) {
		this.observer = observer;
	}

	public void setOpponent(String opponent) {
		this.opponent = opponent;
		stats.getName2().setText(opponent);
	}

	public void setOpponentLeft(Integer opponentLeft) {
		this.opponentLeft = opponentLeft;
	}

	public void setOpponentMoves(Integer opponentMoves) {
		this.opponentMoves = opponentMoves;
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

	public void setTaken(Integer taken) {
		this.taken = taken;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public void setUser(String user) {
		this.user = user;
		stats.getName1().setText(user);
	}

	public Board getBoard() {
		return board;
	}
	
	public void setHintedTile(Tile hint){
		this.hintedTile = hint;
	}
}
