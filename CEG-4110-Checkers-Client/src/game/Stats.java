package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Stats extends JPanel {
	
	private JTextField blackSeatName;
	private JTextField blackSeatMoves;
	private JTextField blackSeatTaken;
	private JTextField blackSeatLeft;
	private JTextField redSeatName;
	private JTextField redSeatMoves;
	private JTextField redSeatTaken;
	private JTextField redSeatLeft;
	private JLabel redSeatIcon;
	private JLabel blackSeatIcon;
	private ImageIcon blackSeatImage;
	private ImageIcon redSeatImage;

	/**
	 * Create the panel.
	 */
	public Stats() {
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		
		JLabel lblPlayer1 = new JLabel("Player 1");
		lblPlayer1.setForeground(Color.BLACK);
		lblPlayer1.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblPlayer1.setBounds(10, 6, 236, 40);
		add(lblPlayer1);
		
		blackSeatName = new JTextField();
		blackSeatName.setForeground(Color.BLACK);
		blackSeatName.setBackground(Color.LIGHT_GRAY);
		blackSeatName.setFont(new Font("Tahoma", Font.PLAIN, 32));
		blackSeatName.setEditable(false);
		blackSeatName.setBounds(10, 47, 200, 40);
		add(blackSeatName);
		blackSeatName.setColumns(10);
		
		JLabel lblMoves1 = new JLabel("Moves");
		lblMoves1.setForeground(Color.BLACK);
		lblMoves1.setBackground(Color.WHITE);
		lblMoves1.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblMoves1.setBounds(10, 98, 106, 40);
		add(lblMoves1);
		
		JLabel lblPiecesTaken1 = new JLabel("Taken");
		lblPiecesTaken1.setForeground(Color.BLACK);
		lblPiecesTaken1.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblPiecesTaken1.setBounds(10, 143, 106, 40);
		add(lblPiecesTaken1);
		
		JLabel lblPiecesLeft1 = new JLabel("Left");
		lblPiecesLeft1.setForeground(Color.BLACK);
		lblPiecesLeft1.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblPiecesLeft1.setBounds(10, 189, 106, 40);
		add(lblPiecesLeft1);
		
		blackSeatMoves = new JTextField();
		blackSeatMoves.setForeground(Color.BLACK);
		blackSeatMoves.setFont(new Font("Tahoma", Font.PLAIN, 24));
		blackSeatMoves.setBackground(Color.LIGHT_GRAY);
		blackSeatMoves.setEditable(false);
		blackSeatMoves.setBounds(135, 103, 75, 40);
		add(blackSeatMoves);
		blackSeatMoves.setColumns(10);
		
		blackSeatTaken = new JTextField();
		blackSeatTaken.setForeground(Color.BLACK);
		blackSeatTaken.setFont(new Font("Tahoma", Font.PLAIN, 24));
		blackSeatTaken.setBackground(Color.LIGHT_GRAY);
		blackSeatTaken.setEditable(false);
		blackSeatTaken.setColumns(10);
		blackSeatTaken.setBounds(135, 148, 75, 40);
		add(blackSeatTaken);
		
		blackSeatLeft = new JTextField();
		blackSeatLeft.setForeground(Color.BLACK);
		blackSeatLeft.setFont(new Font("Tahoma", Font.PLAIN, 24));
		blackSeatLeft.setBackground(Color.LIGHT_GRAY);
		blackSeatLeft.setEditable(false);
		blackSeatLeft.setColumns(10);
		blackSeatLeft.setBounds(135, 194, 75, 40);
		add(blackSeatLeft);
		
		JLabel lblVs = new JLabel("VS");
		lblVs.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblVs.setForeground(Color.RED);
		lblVs.setBounds(90, 245, 46, 34);
		add(lblVs);
		
		JLabel lblPlayer2 = new JLabel("Player 2");
		lblPlayer2.setForeground(Color.BLACK);
		lblPlayer2.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblPlayer2.setBounds(10, 290, 206, 40);
		add(lblPlayer2);
		
		redSeatName = new JTextField();
		redSeatName.setForeground(Color.BLACK);
		redSeatName.setBackground(Color.LIGHT_GRAY);
		redSeatName.setFont(new Font("Tahoma", Font.PLAIN, 32));
		redSeatName.setEditable(false);
		redSeatName.setColumns(10);
		redSeatName.setBounds(10, 331, 200, 40);
		add(redSeatName);
		
		JLabel lblMoves2 = new JLabel("Moves");
		lblMoves2.setForeground(Color.BLACK);
		lblMoves2.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblMoves2.setBounds(10, 382, 106, 40);
		add(lblMoves2);
		
		JLabel lblTaken2 = new JLabel("Taken");
		lblTaken2.setForeground(Color.BLACK);
		lblTaken2.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTaken2.setBounds(10, 436, 106, 40);
		add(lblTaken2);
		
		JLabel lblLeft2 = new JLabel("Left");
		lblLeft2.setForeground(Color.BLACK);
		lblLeft2.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblLeft2.setBounds(10, 487, 106, 40);
		add(lblLeft2);
		
		redSeatMoves = new JTextField();
		redSeatMoves.setForeground(Color.BLACK);
		redSeatMoves.setFont(new Font("Tahoma", Font.PLAIN, 24));
		redSeatMoves.setBackground(Color.LIGHT_GRAY);
		redSeatMoves.setEditable(false);
		redSeatMoves.setColumns(10);
		redSeatMoves.setBounds(135, 382, 75, 40);
		add(redSeatMoves);
		
		redSeatTaken = new JTextField();
		redSeatTaken.setForeground(Color.BLACK);
		redSeatTaken.setFont(new Font("Tahoma", Font.PLAIN, 24));
		redSeatTaken.setBackground(Color.LIGHT_GRAY);
		redSeatTaken.setEditable(false);
		redSeatTaken.setColumns(10);
		redSeatTaken.setBounds(135, 436, 75, 40);
		add(redSeatTaken);
		
		redSeatLeft = new JTextField();
		redSeatLeft.setForeground(Color.BLACK);
		redSeatLeft.setFont(new Font("Tahoma", Font.PLAIN, 24));
		redSeatLeft.setBackground(Color.LIGHT_GRAY);
		redSeatLeft.setEditable(false);
		redSeatLeft.setColumns(10);
		redSeatLeft.setBounds(135, 487, 75, 40);
		add(redSeatLeft);
		
		redSeatIcon = new JLabel("");
		redSeatImage = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/red_checker.png")));
		redSeatIcon.setIcon(redSeatImage);
		redSeatIcon.setBounds(135, 284, 85, 46);
		add(redSeatIcon);
		
		blackSeatIcon = new JLabel("");
		blackSeatIcon.setBounds(142, 0, 85, 46);
		blackSeatImage = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/black_piece.png")));
		blackSeatIcon.setIcon(blackSeatImage);
		add(blackSeatIcon);

	}
	
	public void setBlackSeatIconVisible(boolean visible){
		blackSeatIcon.setVisible(visible);
	}
	
	public void setRedSeatIconVisible(boolean visible){
		redSeatIcon.setVisible(visible);
	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
	}

	public JTextField getBlackSeatName() {
		return blackSeatName;
	}

	public void setBlackSeatName(JTextField blackSeatName) {
		this.blackSeatName = blackSeatName;
	}

	public JTextField getBlackSeatMoves() {
		return blackSeatMoves;
	}

	public void setBlackSeatMoves(JTextField blackSeatMoves) {
		this.blackSeatMoves = blackSeatMoves;
	}

	public JTextField getBlackSeatTaken() {
		return blackSeatTaken;
	}

	public void setBlackSeatTaken(JTextField blackSeatTaken) {
		this.blackSeatTaken = blackSeatTaken;
	}

	public JTextField getBlackSeatLeft() {
		return blackSeatLeft;
	}

	public void setBlackSeatLeft(JTextField blackSeatLeft) {
		this.blackSeatLeft = blackSeatLeft;
	}

	public JTextField getRedSeatName() {
		return redSeatName;
	}

	public void setRedSeatName(JTextField redSeatName) {
		this.redSeatName = redSeatName;
	}

	public JTextField getRedSeatMoves() {
		return redSeatMoves;
	}

	public void setRedSeatMoves(JTextField redSeatMoves) {
		this.redSeatMoves = redSeatMoves;
	}

	public JTextField getRedSeatTaken() {
		return redSeatTaken;
	}

	public void setRedSeatTaken(JTextField redSeatTaken) {
		this.redSeatTaken = redSeatTaken;
	}

	public JTextField getRedSeatLeft() {
		return redSeatLeft;
	}

	public void setRedSeatLeft(JTextField redSeatLeft) {
		this.redSeatLeft = redSeatLeft;
	}

	public ImageIcon getBlackSeatImage() {
		return blackSeatImage;
	}

	public void setBlackSeatImage(ImageIcon blackSeatImage) {
		this.blackSeatImage = blackSeatImage;
	}

	public ImageIcon getRedSeatImage() {
		return redSeatImage;
	}

	public void setRedSeatImage(ImageIcon redSeatImage) {
		this.redSeatImage = redSeatImage;
	}
	
	public void changeColor(boolean changed){
		if (changed){
			redSeatImage = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/white_checker.png")));
			blackSeatImage = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/brown_checker.png")));
		}
		else {
			redSeatImage = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/red_checker.png")));
			blackSeatImage = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/black_piece.png")));
		}
		blackSeatIcon.setIcon(blackSeatImage);
		redSeatIcon.setIcon(redSeatImage);
	}
}
