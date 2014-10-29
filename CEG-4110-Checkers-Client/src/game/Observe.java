package game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class Observe extends JPanel {

	private Board board;
	
	/**
	 * Create the panel.
	 */
	public Observe() {
		setBorder(new BevelBorder(BevelBorder.RAISED, new Color(139, 69, 19), null, null, null));
		setLayout(null);
		
		board = new Board();
		board.setBorder(new BevelBorder(BevelBorder.RAISED, Color.LIGHT_GRAY, new Color(128, 128, 128), null, null));
		board.setBounds(50, 11, 402, 402);
		add(board);
		board.setLayout(null);
	}

	protected void paintComponent(Graphics g){
		board.paintComponent(g);
		g.setColor(new Color(139, 69, 19));
		g.fillRect(0, 0, 521, 424);
	}
}
