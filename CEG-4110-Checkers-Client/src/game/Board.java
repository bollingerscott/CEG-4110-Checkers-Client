package game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/*
 * Defines the board
 * Handles user interaction with the board
 * Gets move coordinates
 * Draws tiles to draw board
 * Flips the board if color is black
 * 
 * @author Scott Bollinger
 */
@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener{

	private final int LENGTH = 8;
	private Tile[][] board;
	private Integer blackLeft = 0, blackTaken = 0, redLeft = 0, redTaken = 0;
	private boolean click = false;
	private final int TILE_LENGTH = 50;
	private int fr, fc, tr, tc;
	private Tile clickedTile;
	private byte[][] board_state = new byte[][]{
			{0,1,0,1,0,1,0,1},
			{1,0,1,0,1,0,1,0},
			{0,1,0,1,0,1,0,1},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{2,0,2,0,2,0,2,0},
			{0,2,0,2,0,2,0,2},
			{2,0,2,0,2,0,2,0}};
	private boolean moving = false;
	private boolean flip;
	private String color;
	private String oppositeColor;


	public Board(boolean flip){
		super();
		this.flip = flip;
		setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		board = new Tile[LENGTH][LENGTH];
		for (int i = 0; i < LENGTH; i++){
			for (int j = 0; j < LENGTH; j++){
				if (((i % 2 == 0) && (j % 2 == 0)) || ((i % 2 != 0) && (j % 2 != 0))){
					board[i][j] = new Tile("red");
				}
				else {
					board[i][j] = new Tile("black");
				}
			}
		}
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		rigidArea.setBackground(Color.GRAY);
		rigidArea.setBounds(0, 0, 402, 402);
		add(rigidArea);

		readBoardState();
	}

	private void enable(Tile tile, boolean enable) {
		if (tile.getPiece().getType().equals("king")){
			try {
				if (!board[tile.getCoordY()+1][tile.getCoordX()-1].isOccupied()) {
					board[tile.getCoordY()+1][tile.getCoordX()-1].setEnable(enable);
				}
				else if (board[tile.getCoordY()-1][tile.getCoordX()-1].getPiece().getColor().equals(oppositeColor) && !(board[tile.getCoordY()+2][tile.getCoordX()-2].isOccupied())){
					board[tile.getCoordY()+2][tile.getCoordX()-2].setEnable(enable);
				}
			}
			catch (IndexOutOfBoundsException ex){}
			try {
				if (!board[tile.getCoordY()+1][tile.getCoordX()+1].isOccupied()) {
					board[tile.getCoordY()+1][tile.getCoordX()+1].setEnable(enable);
				}
				else if (board[tile.getCoordY()+1][tile.getCoordX()+1].getPiece().getColor().equals(oppositeColor) && !(board[tile.getCoordY()+2][tile.getCoordX()+2].isOccupied())){
					board[tile.getCoordY()+2][tile.getCoordX()+2].setEnable(enable);
				}
			}
			catch (IndexOutOfBoundsException ex){}
		}
		try {
			if (!board[tile.getCoordY()-1][tile.getCoordX()-1].isOccupied()) {
				board[tile.getCoordY()-1][tile.getCoordX()-1].setEnable(enable);
			}
			else if (board[tile.getCoordY()-1][tile.getCoordX()-1].getPiece().getColor().equals(oppositeColor) && !(board[tile.getCoordY()-2][tile.getCoordX()-2].isOccupied())){
				board[tile.getCoordY()-2][tile.getCoordX()-2].setEnable(enable);
			}
		}
		catch (IndexOutOfBoundsException ex){}
		try {
			if (!board[tile.getCoordY()-1][tile.getCoordX()+1].isOccupied()) {
				board[tile.getCoordY()-1][tile.getCoordX()+1].setEnable(enable);
			}
			else if (board[tile.getCoordY()-1][tile.getCoordX()+1].getPiece().getColor().equals(oppositeColor) && !(board[tile.getCoordY()-2][tile.getCoordX()+2].isOccupied())){
				board[tile.getCoordY()-2][tile.getCoordX()+2].setEnable(enable);
			}
		}
		catch (IndexOutOfBoundsException ex){}

	}

	public Integer getBlackLeft() {
		return blackLeft;
	}

	public Integer getBlackTaken() {
		return blackTaken;
	}

	public Tile[][] getBoard() {
		return board;
	}

	public byte[][] getBoard_state() {
		return board_state;
	}

	public String getColor() {
		return color;
	}

	public int getFc() {
		return fc;
	}

	public int getFr() {
		return fr;
	}

	public String getOppositeColor() {
		return oppositeColor;
	}

	public Integer getRedLeft() {
		return redLeft;
	}

	public Integer getRedTaken() {
		return redTaken;
	}

	public int getTc() {
		return tc;
	}

	public int getTr() {
		return tr;
	}

	public boolean isFlip() {
		return flip;
	}

	public boolean isMoving() {
		return moving;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//Find tile clicked
		int x = e.getX() - getLocation().x;
		int y = e.getY() - getLocation().y;
		int i = 0;
		int j = 0;
		int coordX = 0;
		int coordY = 0;
		while (i+TILE_LENGTH < x){
			coordX++;
			i += TILE_LENGTH;
		}
		while (j+TILE_LENGTH < y){
			coordY++;
			j += TILE_LENGTH;
		}
		Tile tile = board[coordY][coordX];
		assert(!moving);
		//if first click on tile with your piece on it
		if (!click && tile.isOccupied() && tile.getPiece().getColor().equals(color)) {
			click = true;
			tile.mouseClicked(e);//set tile to clicked
			if (isFlip()){
				fr = 7-coordY;
				fc = 7-coordX;
			}
			else {
				fr = coordY;
				fc = coordX;
			}
			clickedTile = tile;
			moving = false;
			enable(tile, true);
		}
		//if clicked on same tile reset it
		else if (clickedTile == tile){
			click = false;
			tile.reset();
			moving = false;
			fc = fr = -1;
			enable(tile, false);
		}
		//if clicked for second time and tile is not occupied
		else if (!tile.isOccupied() && click && tile.isEnable()){
			clickedTile.reset();
			click = false;
			if (isFlip()){
				tr = 7-coordY;
				tc = 7-coordX;
			}
			else {
				tr = coordY;
				tc = coordX;
			}
			moving = true;
			enable(clickedTile, false);
		}
		else {
			moving = false;
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
	public void mouseReleased(MouseEvent arg0) {

	}

	@Override
	protected void paintComponent(Graphics g){
		int x = 0;
		int y = -TILE_LENGTH;
		for (int i = 0; i < LENGTH; i++){
			y += TILE_LENGTH;
			x = 0;
			for (int j = 0; j < LENGTH; j++){
				Tile currentTile = board[i][j];
				g.drawImage(currentTile.getTile(), x, y, null);
				if (currentTile.isOccupied()){
					currentTile.getPiece().paintComponent(g);
				}
				currentTile.setCoordX(j);
				currentTile.setCoordY(i);
				x += TILE_LENGTH;
			}
		}
	}

	private void readBoardState(){
		redLeft = blackLeft = 0;
		int x = 0;
		int y = -TILE_LENGTH;
		int length = board_state.length;
		for (int i = 0; i < length; i++){
			y += TILE_LENGTH;
			x = 0;
			for (int j = 0; j < length; j++){
				byte state;
				if (!isFlip()){
					state = board_state[i][j];
				}
				else {
					state = board_state[(length-1)-i][(length-1)-j];
				}
				Tile tile = board[i][j];
				if (state == 0){
					tile.setOccupied(false, null);
				}
				else if (state == 1){
					tile.setOccupied(true, new Checker_Piece("black", "regular", x, y));
					blackLeft++;
				}
				else if (state == 2){
					tile.setOccupied(true, new Checker_Piece("red", "regular", x, y));
					redLeft++;
				}
				else if (state == 3){
					tile.setOccupied(true, new Checker_Piece("black", "king", x, y));
					blackLeft++;
				}
				else if (state == 4){
					tile.setOccupied(true, new Checker_Piece("red", "king", x, y));
					redLeft++;
				}
				x += TILE_LENGTH;
			}
		}
		redTaken = blackTaken = 0;
		redTaken = 12 - redLeft;
		blackTaken = 12 - blackLeft;
	}

	public void setBlackLeft(Integer left) {
		this.blackLeft = left;
	}

	public void setBlackTaken(Integer taken) {
		this.blackTaken = taken;
	}

	public void setBoard(Tile[][] board) {
		this.board = board;
	}

	public void setBoard_state(byte[][] board_state) {
		this.board_state = board_state;
		readBoardState();
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setFc(int fc) {
		this.fc = fc;
	}

	public void setFlip(boolean flip) {
		this.flip = flip;
	}

	public void setFr(int fr) {
		this.fr = fr;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public void setOppositeColor(String oppositeColor) {
		this.oppositeColor = oppositeColor;
	}

	public void setRedLeft(Integer opponentLeft) {
		this.redLeft = opponentLeft;
	}

	public void setRedTaken(Integer opponentTaken) {
		this.redTaken = opponentTaken;
	}
	

	public void setTc(int tc) {
		this.tc = tc;
	}

	public void setTr(int tr) {
		this.tr = tr;
	}
}

