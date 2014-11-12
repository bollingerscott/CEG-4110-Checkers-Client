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
 * will set the status to ready. Only one button is clickable per client. Clicking it will notify the server.
 * When the opponent becomes ready, the opponentReady method should be called.
 * 
 * 
 * 
 * TODO: add red and black elements
 */
public class TableScreen {

	private RMIServerInterface server;
	private ReadyButton redState;
	private ReadyButton blackState;
	private String userName;
	private JFrame frame;
	private int tableID;
	private String blackSeat;
	private String redSeat;

	/**
	 * Create the panel.
	 */
	public TableScreen(final RMIServerInterface rmiServer, String username, int tid,
			String blackPlayer, String redPlayer) {
		
		tableID = tid;
		userName = username;
		this.server = rmiServer;
		frame = new JFrame();
		frame.setTitle("Table " + tableID);
		frame.setLayout(null);
		frame.setBounds(100, 100, 500, 300);
		blackState = new ReadyButton();
		
		blackState.setBounds(53, 167, 64, 63);
		frame.add(blackState);

		redState = new ReadyButton();

		redState.setBounds(300, 167, 64, 63);
		frame.add(redState);

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

		this.update(blackPlayer, redPlayer);
		
		frame.setVisible(true);
	}

	/*
	 * call to set the opponent ready
	 */
	public void opponentReady() {
		redState.makeReady();
	}

	public boolean getReady() {
		return this.blackState.getReady();
	}
	
	/*
	 * call this when the opponent leaves the table. This resets the enemies ready state.
	 * 
	 * Definitely a hack right now
	 */
	public void oppLeft() {
		redState = new ReadyButton();

		redState.setBounds(300, 167, 64, 63);
		frame.add(redState);
	}
	
	/*
	 * the client wants to leave the table. The server is notified
	 * 
	 * This should probably be left to the lobby
	 */
	public void leave() throws RemoteException {
		server.leaveTable(userName);
		
	}
	
	/*
	 * properly sets names as well as button listeners
	 * 
	 * assumes the ReadyButtons have been initialized
	 */
	public void update(String blackSeat, String redSeat) {
		this.blackSeat = blackSeat;
		this.redSeat = redSeat;
		if (blackSeat.equals("-1")) {
			blackSeat = "Empty";
		}
		if (redSeat.equals("-1")) {
			redSeat = "Empty";
		}
		
		//update UI
		redState.setText(redSeat);
		blackState.setText(blackSeat);
		
		//remove any previous actionListeners
		for (ActionListener al : redState.getActionListeners()) {
			redState.removeActionListener(al);
		}
		for (ActionListener al : blackState.getActionListeners()) {
			blackState.removeActionListener(al);
		}
		
		//add appropriate actionListeners
		if (redSeat.equals(this.userName)) {
			redState.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					redState.makeReady();
					try {
						server.playerReady(userName);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			});
		}
		else if (blackSeat.equals(this.userName)) {
			blackState.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					blackState.makeReady();
					try {
						server.playerReady(userName);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			});
		}
		else {
			System.out.println("Broken! no player on table with userName");
		}
		
	}
	// TODO

	// Add default close operation of leaving table, probably using
	// server.leaveTable?
	
	// Add a leave table button that does the same action

	// Add a close window function that will be called
	// in gameStart() in lobbyWindow, for when both users have readied up and
	// game has started

	// Add more space for each users name, longer names are getting cut off
	
	// change, so you Don't directly change color of the button, have that done on server Calls
	
	//Add colors to each side
}
