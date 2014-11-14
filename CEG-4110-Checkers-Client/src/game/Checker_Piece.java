package game;

import java.awt.*;

/*
 * Defines a checker piece
 * 
 * @author Scott Bollinger
 */
@SuppressWarnings("serial")
public class Checker_Piece extends Component {
	
	private String color;
	private String type;
	private Image piece;
	private int x, y = 0;
	
	public Checker_Piece(String color, String type, int x, int y){
		this.color = color;
		this.x = x;
		this.y = y;
		this.type = type;
		if (color.toLowerCase() == "red"){
			if (type.toLowerCase().equals("regular")){
				setPiece(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/red_checker.png")));
			}
			else if (type.toLowerCase().equals("king")){
				setPiece(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/red_king_checker.png")));
			}
		}
		else if (color.toLowerCase() == "black") {
			if (type.toLowerCase().equals("regular")){
				setPiece(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/black_piece.png")));
			}
			else if (type.toLowerCase().equals("king")){
				setPiece(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/black_king_piece.png")));
			}
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
