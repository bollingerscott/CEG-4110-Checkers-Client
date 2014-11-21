package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;

import RMIConnection.Interfaces.RMIServerInterface;

public class ForfeitButton extends JButton {
	
	public ForfeitButton(final RMIServerInterface server, final String user, final String opponent){
		setText("Forfeit");
		addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				try {
					server.sendMsg(opponent, "I forfeit");
					server.leaveTable(user);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
}
