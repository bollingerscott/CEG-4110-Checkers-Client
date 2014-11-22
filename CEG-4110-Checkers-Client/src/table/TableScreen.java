package table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import RMIConnection.Interfaces.RMIServerInterface;

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
	
	private Table table;

	JLabel redLabel;
	JLabel blackLabel;
	
	/**
	 * Create the panel.
	 */
	public TableScreen(final RMIServerInterface rmiServer, String username, int tid,
			Table table) {
		this.table = table;
		
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

		blackLabel = new JLabel(table.getBlackseat());
		blackLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		blackLabel.setHorizontalAlignment(SwingConstants.CENTER);
		blackLabel.setIcon(new ImageIcon("res\\playerIcon.jpg"));
		blackLabel.setBounds(53, 50, 130, 63);
		frame.add(blackLabel);

		redLabel = new JLabel(table.getRedseat());
		redLabel.setIcon(new ImageIcon("res\\playerIcon.jpg"));
		redLabel.setBounds(300, 50, 130, 63);
		frame.add(redLabel);

		this.update();
		
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter(){
            @Override
			public void windowClosing(WindowEvent e){
            	try {
					server.leaveTable(userName);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
            }
        });
	}

	public boolean getReady() {
		return this.blackState.getReady();
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
	 * call to set the opponent ready
	 */
	public void opponentReady() {
		redState.makeReady();
	}
	
	/*
	 * properly sets names as well as button listeners
	 * 
	 * assumes the ReadyButtons have been initialized
	 * 
	 * updates information for UI from the table
	 */
	public void update() {
		if (table.getBlackseat().equals("-1")) {
			blackLabel.setText("Empty");
		}
		else {
			blackLabel.setText(table.getBlackseat());

		}
		if (table.getRedseat().equals("-1")) {
			redLabel.setText("Empty");
		}
		else {
			redLabel.setText(table.getRedseat());
		}

		
		//remove any previous actionListeners
		for (ActionListener al : redState.getActionListeners()) {
			redState.removeActionListener(al);
		}
		for (ActionListener al : blackState.getActionListeners()) {
			blackState.removeActionListener(al);
		}
		
		//add appropriate actionListeners
		if (table.getRedseat().equals(this.userName)) {
			redState.addActionListener(new ActionListener() {
				@Override
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
		else if (table.getBlackseat().equals(this.userName)) {
			blackState.addActionListener(new ActionListener() {
				@Override
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
			System.out.printf("username: %s, blackSeat: %s, redSeat: %s",userName,  table.getBlackseat(), table.getRedseat());
		}
		
	}
	
	/*
	 * should be called when a game has started or the user left the table
	 */
	public void close() {
		this.frame.dispose();
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
