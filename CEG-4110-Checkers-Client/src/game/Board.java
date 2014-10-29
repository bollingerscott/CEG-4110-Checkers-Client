package game;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;

public class Board extends JPanel{

	private final int LENGTH = 8;
	private Tile[][] board;
	private final int TILE_LENGTH = 50;
	private byte[][] board_state = new byte[][]{
									{0,1,0,1,0,1,0,1},
									{1,0,1,0,1,0,1,0},
									{0,1,0,1,0,1,0,1},
									{0,0,0,0,0,0,0,0},
									{0,0,0,0,0,0,0,0},
									{2,0,2,0,2,0,2,0},
									{0,2,0,2,0,2,0,2},
									{2,0,2,0,2,0,2,0}};
	
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
				if (((i % 2 == 0) && (j % 2 == 0)) || ((i % 2 != 0) && (j % 2 != 0))){
					//g.setColor(Color.RED);
					g.drawImage(currentTile.getTile(), x, y, null);
				}
				else {
					g.setColor(Color.BLACK);
					g.fillRect(x, y, TILE_LENGTH, TILE_LENGTH);
				}
				//g.fillRect(x, y, TILE_LENGTH, TILE_LENGTH);
				if (currentTile.isOccupied()){
					currentTile.getPiece().paintComponent(g);
				}
				currentTile.setCoordX(j);
				currentTile.setCoordY(i);
				x += TILE_LENGTH;
			}
		}
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

