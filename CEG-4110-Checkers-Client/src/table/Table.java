package table;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import RMIConnection.Interfaces.RMIServerInterface;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;


/*
 * Displays a table before a match. Each player's ready status is indicated by a picture. Clicking the picture
 * will set the status to ready. Only one button is clickable per client, the left one. Clicking it will notify the server.
 * When the opponent becomes ready, the opponentReady method should be called.
 */
public class Table extends JPanel {

	private RMIServerInterface server;
	private ReadyButton opponentState;
	private ReadyButton clientState;
	/**
	 * Create the panel.
	 */
	public Table(final RMIServerInterface server) {
		
		this.server  = server;
		
		setLayout(null);
		
		clientState = new ReadyButton();
		clientState.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clientState.makeReady();
				try {
					server.playerReady("TemporaryString"); //should be some sort of user information
					
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		clientState.setBounds(53, 167, 64, 63);
		add(clientState);
		
		opponentState = new ReadyButton();

		opponentState.setIcon(new ImageIcon("res\\xMark.png"));
		opponentState.setBounds(300, 167, 64, 63);
		add(opponentState);
		
		JLabel lblNewLabel = new JLabel("Person one");
		lblNewLabel.setVerticalAlignment(JLabel.BOTTOM);
		lblNewLabel.setHorizontalAlignment(JLabel.CENTER);
		lblNewLabel.setIcon(new ImageIcon("res\\playerIcon.jpg"));
		lblNewLabel.setBounds(53, 50, 130, 63);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Person Two");
		lblNewLabel_1.setIcon(new ImageIcon("res\\playerIcon.jpg"));
		lblNewLabel_1.setBounds(300, 50, 130, 63);
		add(lblNewLabel_1);

	}
	
	/*
	 * call to set the opponent ready
	 */
	public void opponentReady() {
		opponentState.makeReady();
	}
	
	public boolean getReady() {
		return this.clientState.getReady();
	}
}
