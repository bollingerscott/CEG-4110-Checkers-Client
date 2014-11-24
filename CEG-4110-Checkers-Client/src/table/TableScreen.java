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
import java.awt.Font;

/*
 * Displays a table before a match. Each player's ready status is indicated by a picture. Clicking the picture
 * will set the status to ready. Only one button is clickable per client. Clicking it will notify the server.
 * When the opponent becomes ready, the opponentReady method should be called.
 * 
 */
public class TableScreen {

	private RMIServerInterface server;
	private ReadyButton readyButton;
	private String userName;
	private String opponentName;
	private JFrame frame;
	private int tableID;

	private Table table;

	JLabel opponentLabel;
	JLabel myLabel;

	/**
	 * Create the panel.
	 */
	public TableScreen(final RMIServerInterface rmiServer, String username, int tid, Table table) {
		this.table = table;

		tableID = tid;
		this.userName = username;
		if (table.getBlackseat().equalsIgnoreCase(username)){
			opponentName = table.getRedseat();
		}
		else {
			opponentName = table.getBlackseat();
		}
		this.server = rmiServer;
		frame = new JFrame();
		frame.setTitle("Table " + tableID);
		frame.getContentPane().setLayout(null);
		frame.setBounds(100, 100, 500, 300);
		readyButton = new ReadyButton();

		readyButton.setBounds(197, 164, 64, 63);
		frame.getContentPane().add(readyButton);

		myLabel = new JLabel(username);
		myLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		myLabel.setHorizontalAlignment(SwingConstants.CENTER);
		myLabel.setIcon(new ImageIcon("res\\playerIcon.jpg"));
		myLabel.setBounds(53, 50, 130, 63);
		frame.getContentPane().add(myLabel);

		opponentLabel = new JLabel(opponentName);
		opponentLabel.setIcon(new ImageIcon("res\\playerIcon.jpg"));
		opponentLabel.setBounds(300, 50, 130, 63);
		frame.getContentPane().add(opponentLabel);

		JLabel lblVs = new JLabel("VS");
		lblVs.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblVs.setBounds(217, 11, 44, 45);
		frame.getContentPane().add(lblVs);

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
		return this.readyButton.getReady();
	}
	
	public void setRead(boolean ready){
		readyButton.setRead(ready);
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
	 * 
	 * updates information for UI from the table
	 */
	public void update() {
		if (table.getBlackseat().equalsIgnoreCase(this.userName)){
			opponentName = table.getRedseat();
		}
		else {
			opponentName = table.getBlackseat();
		}
		if (userName.equals("-1")) {
			myLabel.setText("Empty");
		}
		else {
			myLabel.setText(userName);

		}
		if (opponentName.equals("-1")) {
			opponentLabel.setText("Empty");
		}
		else {
			opponentLabel.setText(opponentName);
		}


		//remove any previous actionListeners
		for (ActionListener al : readyButton.getActionListeners()) {
			readyButton.removeActionListener(al);
		}

		readyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				readyButton.makeReady();
				try {
					server.playerReady(userName);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});

	}

	/*
	 * should be called when a game has started or the user left the table
	 */
	public void close() {
		this.frame.dispose();
	}
}
