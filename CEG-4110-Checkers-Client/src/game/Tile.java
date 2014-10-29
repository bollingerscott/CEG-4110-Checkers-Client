package game;

import java.awt.*;

public class Tile extends Rectangle {
	
	private String color;
	private Image tile;
	private boolean occupied;
	private Checker_Piece piece;
	private int coordX, coordY;
	
	public Tile(String color){
		this.color = color;
		if (color.toLowerCase() == "red"){
			setTile(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/red_tile.jpg")));
		}
		else if (color.toLowerCase() == "black") {
			//setTile(Toolkit.getDefaultToolkit().createImage(getClass().getResource("/black_Tile.png")));
		}
		else {
			System.out.println("Bad color");
		}	
	}
	
	public String getColor() {
		return color;
	}
	
	public String setColor(String color) {
		return color;
	}
	
	public boolean isOccupied(){
		return occupied;
	}
	
	public void setOccupied(boolean status, Checker_Piece piece){
		this.occupied = status;
		this.piece = piece;
	}

	public Image getTile() {
		return tile;
	}

	public void setTile(Image tile) {
		this.tile = tile;
	}

	public Checker_Piece getPiece() {
		return piece;
	}

	public int getCoordX() {
		return coordX;
	}

	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}

	public int getCoordY() {
		return coordY;
	}

	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}


}
