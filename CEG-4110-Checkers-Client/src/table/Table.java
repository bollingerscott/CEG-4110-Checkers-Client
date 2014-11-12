package table;

import javax.swing.JLabel;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import RMIConnection.Interfaces.RMIServerInterface;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;

/*
 * Displays a table before a match. Each player's ready status is indicated by a picture. Clicking the picture
 * will set the status to ready. Only one button is clickable per client, the left one. Clicking it will notify the server.
 * When the opponent becomes ready, the opponentReady method should be called.
 */
public class Table {

	private RMIServerInterface server;
	private ReadyButton opponentState;
	private ReadyButton clientState;
	private String userName;
	private JFrame frame;
	private int tableID;
	private String blackSeat;
	private String redSeat;

	/**
	 * Create the panel.
	 */
	public Table(final RMIServerInterface rmiServer, String username, int tid,
			String blackPlayer, String redPlayer) {
		blackSeat = blackPlayer;
		redSeat = redPlayer;
		if (blackSeat.equals("-1")) {
			blackSeat = "Empty";
		}
		if (redSeat.equals("-1")) {
			redSeat = "Empty";

		}
		tableID = tid;
		userName = username;
		this.server = rmiServer;
		frame = new JFrame();
		frame.setTitle("Table " + tableID);
		frame.setLayout(null);
		frame.setBounds(100, 100, 500, 300);
		clientState = new ReadyButton();
		clientState.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clientState.makeReady();
				try {
					server.playerReady(userName);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
		clientState.setBounds(53, 167, 64, 63);
		frame.add(clientState);

		opponentState = new ReadyButton();

		opponentState.setIcon(new ImageIcon("res\\xMark.png"));
		opponentState.setBounds(300, 167, 64, 63);
		frame.add(opponentState);

		JLabel lblNewLabel = new JLabel(blackSeat);
		lblNewLabel.setVerticalAlignment(JLabel.BOTTOM);
		lblNewLabel.setHorizontalAlignment(JLabel.CENTER);
		lblNewLabel.setIcon(new ImageIcon("res\\playerIcon.jpg"));
		lblNewLabel.setBounds(53, 50, 130, 63);
		frame.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel(redSeat);
		lblNewLabel_1.setIcon(new ImageIcon("res\\playerIcon.jpg"));
		lblNewLabel_1.setBounds(300, 50, 130, 63);
		frame.add(lblNewLabel_1);

		frame.setVisible(true);
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
	// TODO

	// Add default close operation of leaving table, probably using
	// server.leaveTable?
	
	// Add a leave table button that does the same action

	// Add a close window function that will be called in
	// in gameStart() in lobbyWindow, for when both users have readied up and
	// game has started

	// Add more space for each users name, longer names are getting cut off
	
	// change, so you Don't directly change color of the button, have that done on server Calls
	
	//Add colors to each side
}
