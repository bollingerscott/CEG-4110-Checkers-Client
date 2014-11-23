package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;

public class HintButton extends JButton {

	private Game game;
	private String myColor;
	private Tile hintedTile;

	public HintButton(String myColor, final Game game){
		this.game = game;
		this.myColor = myColor;
		setText("Hint");
		addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if (game.getBoard().getClickedTile() != null){
					game.getBoard().getClickedTile().reset();
					game.getBoard().enable(game.getBoard().getClickedTile(), false);
					game.getBoard().setClick(false);
					game.getBoard().setClickedTile(null);
				}
				hint();
			}
		});
	}

	/*
	 * Super messy hint algorithm (really just finds a random move)
	 */
	public void hint(){
		if (hintedTile != null){
			hintedTile.reset();
			game.getBoard().enable(hintedTile, false);
			hintedTile = null;
		}
		Tile[][] board = game.getBoard().getBoard();
		boolean enabled = false;
		Random random = new Random();
		while (!enabled){
			int i = random.nextInt() % 7;
			if (i < 0) {
				i *= -1;
			}
			int j = random.nextInt() % 7;
			if (j < 0) {
				j *= -1;
			}
			Tile tile = board[i][j];
			if (tile.isOccupied() && (tile.getPiece().getColor().equals( myColor))){
				while (!enabled){
					int rand = random.nextInt() % 4;
					if (rand < 0){
						rand *= -1;
					}
					if (rand == 0){
						if (tile.getPiece().getType().equals("king")) {
							try {
								if (!board[tile.getCoordY() + 1][tile.getCoordX() - 1].isOccupied()) {
									board[tile.getCoordY() + 1][tile.getCoordX() - 1].setEnable(true);
									enabled = true;
								} 
								else if (!board[tile.getCoordY()+1][tile.getCoordX()-1].getPiece().getColor().equals(myColor) && !(board[tile.getCoordY()+2][tile.getCoordX()-2].isOccupied())){
									board[tile.getCoordY()+2][tile.getCoordX()-2].setEnable(true);
									enabled = true;
								}
							} catch (IndexOutOfBoundsException ex) {}
						}
					}
					else if (rand == 1 && !enabled){
						if (tile.getPiece().getType().equals("king")) {
							try {
								if (!board[tile.getCoordY() + 1][tile.getCoordX() + 1].isOccupied()) {
									board[tile.getCoordY() + 1][tile.getCoordX() + 1].setEnable(true);
									enabled = true;
								} 
								else if (!board[tile.getCoordY()+1][tile.getCoordX()+1].getPiece().getColor().equals(myColor) && !(board[tile.getCoordY()+2][tile.getCoordX()+2].isOccupied())){
									board[tile.getCoordY()+2][tile.getCoordX()+2].setEnable(true);
									enabled = true;
								}
							} catch (IndexOutOfBoundsException ex) {}
						}
					}
					else if (rand == 2 && !enabled){
						try {
							if (!board[tile.getCoordY() - 1][tile.getCoordX() - 1].isOccupied()) {
								board[tile.getCoordY() - 1][tile.getCoordX() - 1].setEnable(true);
								enabled = true;
							} 
							else if (!board[tile.getCoordY()-1][tile.getCoordX()-1].getPiece().getColor().equals(myColor) && !(board[tile.getCoordY()-2][tile.getCoordX()-2].isOccupied())){
								board[tile.getCoordY()-2][tile.getCoordX()-2].setEnable(true);
								enabled = true;
							}
						} catch (IndexOutOfBoundsException ex) {}
						}
					else {
						try {
							if (!board[tile.getCoordY() - 1][tile.getCoordX() + 1].isOccupied()) {
								board[tile.getCoordY() - 1][tile.getCoordX() + 1].setEnable(true);
								enabled = true;
							} 
							else if (!board[tile.getCoordY()-1][tile.getCoordX()+1].getPiece().getColor().equals(myColor) && !(board[tile.getCoordY()-2][tile.getCoordX()+2].isOccupied())){
								board[tile.getCoordY()-2][tile.getCoordX()+2].setEnable(true);
								enabled = true;
							}
							break;
						} catch (IndexOutOfBoundsException ex) {}
					}
					
				}
				if (enabled){
					tile.clicked();
					hintedTile = tile;
					game.setHintedTile(hintedTile);
				}
			}
		}
	}

}
