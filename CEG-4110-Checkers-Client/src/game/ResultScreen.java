package game;


import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class ResultScreen extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ResultScreen frame = new ResultScreen("win");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ResultScreen(String status) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 498, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblImage = new JLabel("");
		lblImage.setBounds(55, 11, 400, 244);
		contentPane.add(lblImage);
		
		JButton returnToLobby = new JButton("Exit");
		returnToLobby.setBounds(45, 266, 390, 59);
		contentPane.add(returnToLobby);
		returnToLobby.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		
		ImageIcon image;
		if (status.equals("win")){
			image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/winner.jpg")));
		}
		else {
			image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/loser2.jpg")));
		}
		lblImage.setIcon(image);
		setVisible(true);
	}
}
