package game;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/*
 * Defines a tile
 * 
 * @author Scott Bollinger
 */
@SuppressWarnings("serial")
public class Tile extends JPanel implements MouseListener {
	
	private String color;
	private Image tile;
	private boolean occupied;
	private Checker_Piece piece;
	private int coordX, coordY;
	private boolean enable = false;
	private boolean changed = false;
	
	public Tile(String color, boolean changed){
		this.color = color;
		this.changed = changed;
		if (color.toLowerCase() == "red"){
			setTile(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/red_tile.jpg")));
		}
		else if (color.toLowerCase() == "black") {
			setTile(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/black_tile.jpg")));
		}
		else if (color.toLowerCase() == "white") {
			setTile(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/white_tile.jpg")));
		}
		else if (color.toLowerCase() == "brown") {
			setTile(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/brown_tile2.jpg")));
		}
		else {
			System.out.println("Bad color");
		}	
		addMouseListener(this);
	}
	
	public void clicked(MouseEvent e){

	}
	
	public String getColor() {
		return color;
	}
	
	public int getCoordX() {
		return coordX;
	}
	
	public int getCoordY() {
		return coordY;
	}

	public Checker_Piece getPiece() {
		return piece;
	}

	public Image getTile() {
		return tile;
	}

	public boolean isEnable() {
		return enable;
	}

	public boolean isOccupied(){
		return occupied;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		clicked();
	}
	
	public void clicked(){
		setTile(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/clicked_tile.jpg")));
		getPiece().setPiece(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/clicked_checker.png")));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (isOccupied()){
			setBackground(Color.green);
		}
		else {
			setBackground(Color.red);
		}
		
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
	
	public void reset(){
		if (color.equals("black")){
			setTile(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/black_tile.jpg")));
		}
		else {
			setTile(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/brown_tile2.jpg")));
		}
		if (getPiece() != null) {
			if (getPiece().getColor().equals("red")){
				if (!changed){
					if (getPiece().getType().equalsIgnoreCase("regular")){
						getPiece().setPiece(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/red_checker.png")));
					}
					else {
						getPiece().setPiece(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/red_king_checker.png")));
					}
				}
				else {
					if (getPiece().getType().equalsIgnoreCase("regular")){
						getPiece().setPiece(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/white_checker.png")));
					}
					else {
						getPiece().setPiece(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/white_king_checker.png")));
					}
				}
			}
			else if (getPiece().getColor().equals("black")){
				if (!changed){
					if (getPiece().getType().equalsIgnoreCase("regular")){
						getPiece().setPiece(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/black_piece.png")));
					}
					else {
						getPiece().setPiece(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/black_king_piece.png")));
					}
				}
				else {
					if (getPiece().getType().equalsIgnoreCase("regular")){
						getPiece().setPiece(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/brown_checker.png")));
					}
					else {
						getPiece().setPiece(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/brown_king_checker.png")));
					}
				}
			}
		}	
	}

	public String setColor(String color) {
		return color;
	}

	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}

	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
		if (enable){
			setTile(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/enabled_tile.jpg")));
		}
		else {
			if (color.equals("black")){
				setTile(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/black_tile.jpg")));
			}
			else if (color.equals("brown")){
				setTile(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/brown_tile2.jpg")));
			}
		}
	}

	public void setOccupied(boolean status, Checker_Piece piece){
		this.occupied = status;
		this.piece = piece;
	}

	public void setTile(Image tile) {
		this.tile = tile;
	}


}
