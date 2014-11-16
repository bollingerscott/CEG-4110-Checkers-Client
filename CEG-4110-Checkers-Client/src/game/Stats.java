package game;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JTextField;

import java.awt.Color;

@SuppressWarnings("serial")
public class Stats extends JPanel {
	
	private JTextField name1;
	private JTextField moves1;
	private JTextField taken1;
	private JTextField left1;
	private JTextField name2;
	private JTextField moves2;
	private JTextField taken2;
	private JTextField left2;
	private JLabel color2;
	private JLabel color1;

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
		
		name1 = new JTextField();
		name1.setForeground(Color.BLACK);
		name1.setBackground(Color.LIGHT_GRAY);
		name1.setFont(new Font("Tahoma", Font.PLAIN, 32));
		name1.setEditable(false);
		name1.setBounds(10, 47, 200, 40);
		add(name1);
		name1.setColumns(10);
		
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
		
		moves1 = new JTextField();
		moves1.setForeground(Color.BLACK);
		moves1.setFont(new Font("Tahoma", Font.PLAIN, 24));
		moves1.setBackground(Color.LIGHT_GRAY);
		moves1.setEditable(false);
		moves1.setBounds(135, 103, 75, 40);
		add(moves1);
		moves1.setColumns(10);
		
		taken1 = new JTextField();
		taken1.setForeground(Color.BLACK);
		taken1.setFont(new Font("Tahoma", Font.PLAIN, 24));
		taken1.setBackground(Color.LIGHT_GRAY);
		taken1.setEditable(false);
		taken1.setColumns(10);
		taken1.setBounds(135, 148, 75, 40);
		add(taken1);
		
		left1 = new JTextField();
		left1.setForeground(Color.BLACK);
		left1.setFont(new Font("Tahoma", Font.PLAIN, 24));
		left1.setBackground(Color.LIGHT_GRAY);
		left1.setEditable(false);
		left1.setColumns(10);
		left1.setBounds(135, 194, 75, 40);
		add(left1);
		
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
		
		name2 = new JTextField();
		name2.setForeground(Color.BLACK);
		name2.setBackground(Color.LIGHT_GRAY);
		name2.setFont(new Font("Tahoma", Font.PLAIN, 32));
		name2.setEditable(false);
		name2.setColumns(10);
		name2.setBounds(10, 331, 200, 40);
		add(name2);
		
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
		
		moves2 = new JTextField();
		moves2.setForeground(Color.BLACK);
		moves2.setFont(new Font("Tahoma", Font.PLAIN, 24));
		moves2.setBackground(Color.LIGHT_GRAY);
		moves2.setEditable(false);
		moves2.setColumns(10);
		moves2.setBounds(135, 382, 75, 40);
		add(moves2);
		
		taken2 = new JTextField();
		taken2.setForeground(Color.BLACK);
		taken2.setFont(new Font("Tahoma", Font.PLAIN, 24));
		taken2.setBackground(Color.LIGHT_GRAY);
		taken2.setEditable(false);
		taken2.setColumns(10);
		taken2.setBounds(135, 436, 75, 40);
		add(taken2);
		
		left2 = new JTextField();
		left2.setForeground(Color.BLACK);
		left2.setFont(new Font("Tahoma", Font.PLAIN, 24));
		left2.setBackground(Color.LIGHT_GRAY);
		left2.setEditable(false);
		left2.setColumns(10);
		left2.setBounds(135, 487, 75, 40);
		add(left2);
		
		color2 = new JLabel("");
		color2.setBounds(135, 284, 85, 46);
		add(color2);
		
		color1 = new JLabel("");
		color1.setBounds(142, 0, 85, 46);
		add(color1);

	}
	
	@Override
	protected void paintComponent(Graphics g){
		
	}

	public JTextField getName1() {
		return name1;
	}

	public JTextField getMoves1() {
		return moves1;
	}

	public JTextField getTaken1() {
		return taken1;
	}

	public JTextField getLeft1() {
		return left1;
	}

	public JTextField getName2() {
		return name2;
	}

	public JTextField getMoves2() {
		return moves2;
	}

	public JTextField getTaken2() {
		return taken2;
	}

	public JTextField getLeft2() {
		return left2;
	}

	public JLabel getColor2() {
		return color2;
	}

	public JLabel getColor1() {
		return color1;
	}
}
