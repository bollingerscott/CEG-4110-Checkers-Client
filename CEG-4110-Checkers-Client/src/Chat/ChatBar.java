package Chat;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
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
	private JTextField textField;
	private RMIServerInterface server;
	private JButton btnNewButton;
	private JTextArea textArea;
	
	
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
				//grab text, parse it to figure out if it is public or private, and send it appropriately.
				String str = textField.getText();
				textField.setText("");
				
				//if private, send privae
				String[] arr = str.split(" ");
				if (arr[0].charAt(0) == '@') {
					String message = str.substring(arr[0].length());
					String recipient = arr[0].substring(1);
					try {
						server.sendMsg(recipient, message);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {//else send public
					try {
						server.sendMsg_All(str);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		add(btnNewButton, BorderLayout.EAST);
		
		textField = new JTextField();
		add(textField, BorderLayout.SOUTH);
		textField.setColumns(10);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		add(textArea, BorderLayout.CENTER);

	}
	
	
	/*
	 * currently appends a new message from
	 */
	public void addMessage(String user, String msg) {
		if (msg.charAt(msg.length() - 1) != '\n') {//other clients might or might not send messages with a new line char
			msg += "\n";
		}
		String newLine = user + ": " + msg;
		textArea.setText(textArea.getText() + msg);
	}
	

}
