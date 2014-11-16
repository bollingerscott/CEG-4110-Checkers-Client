package game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
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
	private boolean flip = false;
	private String color = "red";
	private String oppositeColor = "black";


	public Board(boolean flip){
		super();
		this.flip = flip;
		setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		board = new Tile[LENGTH][LENGTH];
		for (int i = 0; i < LENGTH; i++){
			for (int j = 0; j < LENGTH; j++){
				if (((i % 2 == 0) && (j % 2 == 0)) || ((i % 2 != 0) && (j % 2 != 0))){
					if (!isFlip()){
						board[i][j] = new Tile("red");
					}
					else {
						board[i][j] = new Tile("black");
					}
				}
				else {
					if (!isFlip()){
						board[i][j] = new Tile("black");
					}
					else {
						board[i][j] = new Tile("red");
					}
				}
			}
		}
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		rigidArea.setBackground(Color.GRAY);
		rigidArea.setBounds(0, 0, 402, 402);
		add(rigidArea);

		readBoardState();
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
			fr = coordY;
			fc = coordX;
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
			tr = coordY;
			tc = coordX;
			moving = true;
			enable(clickedTile, false);
		}
		else {
			moving = false;
		}
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

	public Integer getBlackLeft() {
		return blackLeft;
	}

	public void setBlackLeft(Integer left) {
		this.blackLeft = left;
	}

	public Integer getBlackTaken() {
		return blackTaken;
	}

	public void setBlackTaken(Integer taken) {
		this.blackTaken = taken;
	}

	public Integer getRedLeft() {
		return redLeft;
	}

	public void setRedLeft(Integer opponentLeft) {
		this.redLeft = opponentLeft;
	}

	public Integer getRedTaken() {
		return redTaken;
	}

	public void setRedTaken(Integer opponentTaken) {
		this.redTaken = opponentTaken;
	}

	public int getFr() {
		return fr;
	}

	public void setFr(int fr) {
		this.fr = fr;
	}

	public int getFc() {
		return fc;
	}

	public void setFc(int fc) {
		this.fc = fc;
	}

	public int getTr() {
		return tr;
	}

	public void setTr(int tr) {
		this.tr = tr;
	}

	public int getTc() {
		return tc;
	}

	public void setTc(int tc) {
		this.tc = tc;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public Tile[][] getBoard() {
		return board;
	}

	public void setBoard(Tile[][] board) {
		this.board = board;
	}

	public byte[][] getBoard_state() {
		return board_state;
	}

	public void setBoard_state(byte[][] board_state) {
		this.board_state = board_state;
		readBoardState();
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
					state = board_state[(length-1)-i][j];
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

	public boolean isFlip() {
		return flip;
	}

	public void setFlip(boolean flip) {
		this.flip = flip;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	

	public String getOppositeColor() {
		return oppositeColor;
	}

	public void setOppositeColor(String oppositeColor) {
		this.oppositeColor = oppositeColor;
	}
}

