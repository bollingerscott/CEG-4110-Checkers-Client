package Chat;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import RMIConnection.Interfaces.RMIServerInterface;

/*
 * replaces the ChatContainer. Provides a more solid implementation of chat functionality. Extends JPanel so 
 * the whole module can be placed within a larger GUI (e.g. the lobby). Takes an RMIServerInterface object to 
 * send messages with. Provides a method to take text inputs from the RMI server. Some sort of callback is needed
 * to receive messages.
 */
public class ChatBar extends JPanel {
	
	private JTextField inputField;
	private RMIServerInterface server;
	private JButton btnNewButton;
	private JTextArea outputArea;
	private String curState;
	private String opponent;
	private boolean observer;
	private String userName;

	/**
	 * Create the panel.
	 */
	public ChatBar(final RMIServerInterface server) {
		this.server = server;

		setLayout(new BorderLayout(0, 0));

		btnNewButton = new JButton("Send!");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				inputSubmit();
			}
		});
		add(btnNewButton, BorderLayout.EAST);

		inputField = new JTextField();
		add(inputField, BorderLayout.SOUTH);
		inputField.setColumns(10);
		inputField.addKeyListener(new KeyAdapter() {
			// Listener for ENTER key
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == 10)
					inputSubmit();
			}
		});

		outputArea = new JTextArea();
		outputArea.setLineWrap(true);
		outputArea.setEditable(false);
		add(outputArea, BorderLayout.CENTER);

	}


	protected void inputSubmit() {
		//grab text, parse it to figure out if it is public or private, and send it appropriately.
		String str = inputField.getText();
		inputField.setText("");
		if (curState.equals("inLobby")){
			//if private, send private
			String[] arr = str.split(" ");
			if (arr[0].charAt(0) == '@') {
				String message = str.substring(arr[0].length());
				String recipient = arr[0].substring(1);
				try {
					server.sendMsg(recipient, message);
					addMessage(userName, message);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			else {//else send public
				try {
					server.sendMsg_All(str);

					addMessage(userName, str);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
		else if (curState.equals("onTable") || curState.equals("inGame")){
			if (!observer){
				try {
					server.sendMsg(opponent, str);	
					str = userName + ": " + str;
					addMessage(userName, str);
				}
				catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			else {
				JOptionPane.showMessageDialog(this, "As an observer you cannot chat with players in the game\nto prevent cheating", "Alert!", JOptionPane.ERROR_MESSAGE);
			}
		}
	}


	/*
	 * currently appends a new message from
	 */
	public void addMessage(String user, String msg) {
		if (msg.charAt(msg.length() - 1) != '\n') {//other clients might or might not send messages with a new line char
			msg += "\n";
		}
		String newLine = "";
		if (user.equalsIgnoreCase(opponent)){
			newLine = opponent + ": " + msg;	
		}
		else {
			newLine = msg;
		}
		outputArea.setText(outputArea.getText() + newLine);
	}


	public String getCurState() {
		return curState;
	}


	public void setCurState(String curState) {
		this.curState = curState;
	}

	public void setOpponent(String opp){
		opponent = opp;
	}

	public void setObserver(boolean ob){
		observer = ob;
	}
	
	public void setUserName(String user){
		userName = user;
	}

}
