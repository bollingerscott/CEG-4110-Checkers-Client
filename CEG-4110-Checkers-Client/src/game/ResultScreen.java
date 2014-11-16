package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ResultScreen extends JPanel {

	private JFrame frame;
	private Image image;
	/**
	 * Create the panel.
	 */
	public ResultScreen(String status) {
		super();
		setLayout(null);
		frame = new JFrame("You " + status);
		frame.setBounds(200, 200, 455, 306);
		frame.getContentPane().setLayout(null);
		setBounds(0, 0, frame.getSize().width, frame.getSize().height);
		frame.setVisible(true);
		frame.setFocusable(true);
		if (status.equalsIgnoreCase("win")){
			image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/winner.jpg"));
		}
		else{
			image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/loser2.jpg"));
		}
		frame.getContentPane().add(this);
		setVisible(true);
		setFocusable(true);
		repaint();
	}

	protected void paintComponent(Graphics g){
		g.drawImage(image, 0, 0, null);
	}
}
