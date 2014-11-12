package game;

import java.awt.*;
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
	
	public Tile(String color){
		this.color = color;
		if (color.toLowerCase() == "red"){
			setTile(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/red_tile.jpg")));
		}
		else if (color.toLowerCase() == "black") {
			setTile(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/black_tile.jpg")));
		}
		else {
			System.out.println("Bad color");
		}	
		addMouseListener(this);
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

	@Override
	public void mouseClicked(MouseEvent e) {
		setTile(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/clicked_tile.jpg")));
		getPiece().setPiece(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/clicked_checker.png")));
	}
	
	public void clicked(MouseEvent e){

	}
	
	public void reset(){
		setTile(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/black_tile.jpg")));
		if (getPiece().getColor().equals("red")){
			getPiece().setPiece(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/red_checker.png")));
		}
		else {
			getPiece().setPiece(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/black_piece.png")));
		}
		
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

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
		if (enable){
			setTile(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/enabled_tile.jpg")));
		}
		else {
			setTile(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/black_tile.jpg")));
		}
	}


}
