package table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/*
 * extends JLabel to be able to display images. This class is to be used in a table to display
 * wheather of not a player is ready. When not ready, the JLabel diaplays a red X mark. When
 * ready, the JLabel displays a green check mark. 
 */
public class ReadyButton extends JButton{
private boolean ready = false;

public ReadyButton() {
}

public void toggleReady() {
	ready = !ready;
	System.out.println("clicked!");
	if (ready) {
		this.setIcon(new ImageIcon("C:\\Users\\Colin\\Desktop\\checkMark.png"));
	}
	else { //not ready
		this.setIcon(new ImageIcon("C:\\Users\\Colin\\Desktop\\xMark.png"));
	}
}

public boolean getReady() {
	return this.ready;
}
}
