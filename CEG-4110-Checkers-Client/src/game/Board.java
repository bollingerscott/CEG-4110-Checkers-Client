package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;

public class Board extends JPanel implements MouseListener{
	
	private final int LENGTH = 8;
	private Tile[][] board;
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
	
	
	public Board(){
		super();
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
	
	@Override
	protected void paintComponent(Graphics g){
		int x = 0;
		int y = -TILE_LENGTH;
		for (int i = 0; i < LENGTH; i++){
			y += TILE_LENGTH;
			x = 0;
			for (int j = 0; j < LENGTH; j++){
				Tile currentTile = board[i][j];
				currentTile.paintComponent(g);
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
		System.out.println(x + " " + y);
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
		System.out.println(coordX + " " + coordY);
		Tile tile = board[coordY][coordX];
		assert(!moving);
		//if first click on tile with piece on it
		if (!click && tile.isOccupied()) {
			click = true;
			tile.mouseClicked(e);//set tile to clicked
			fr = coordY;
			fc = coordX;
			clickedTile = tile;
			moving = false;
		}
		//if clicked on same tile reset it
		else if (clickedTile == tile){
			click = false;
			tile.reset();
			moving = false;
			fc = fr = -1;
		}
		//if clicked for second time and tile is not occupied
		else if (!tile.isOccupied() && click){
			clickedTile.reset();
			click = false;
			tr = coordY;
			tc = coordX;
			moving = true;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
		int x = 0;
		int y = -TILE_LENGTH;
		int length = board_state.length;
		for (int i = 0; i < length; i++){
			y += TILE_LENGTH;
			x = 0;
			for (int j = 0; j < length; j++){
				byte state = board_state[i][j];
				Tile tile = board[i][j];
				if (state == 0){
					tile.setOccupied(false, null);
				}
				else if (state == 1){
					tile.setOccupied(true, new Checker_Piece("black", x, y));
				}
				else {
					tile.setOccupied(true, new Checker_Piece("red", x, y));
				}
				x += TILE_LENGTH;
			}
		}
	}
}

