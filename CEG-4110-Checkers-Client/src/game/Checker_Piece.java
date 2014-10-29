package game;

import java.awt.*;

public class Checker_Piece extends Component {
	
	private String color;
	private Image piece;
	private int x, y = 0;
	
	public Checker_Piece(String color, int x, int y){
		this.color = color;
		this.x = x;
		this.y = y;
		if (color.toLowerCase() == "red"){
			setPiece(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/red_piece.png")));
		}
		else if (color.toLowerCase() == "black") {
			setPiece(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/black_piece.png")));
		}
		else {
			System.out.println("Bad color");
		}
	}
	
	protected void paintComponent(Graphics g){
		g.drawImage(piece, x, y, null);
	}
	
	public String getColor() {
		return color;
	}
	
	public String setColor(String color) {
		return color;
	}	
	
	public Image getPiece() {
		return piece;
	}

	public void setPiece(Image piece) {
		this.piece = piece;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}