package table;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/*
 * extends JLabel to be able to display images. This class is to be used in a table to display
 * wheather of not a player is ready. When not ready, the JLabel displays a red X mark. When
 * ready, the JLabel displays a green check mark. 
 * 
 * Following the specs, once a user clicks ready, they can not change the state back to not ready.
 */
public class ReadyButton extends JButton{
private boolean ready = false;

public ReadyButton() {
	this.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/xMark.png"))));
}

public boolean getReady() {
	return this.ready;
}

public void makeReady() {
	this.ready = true;
	this.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/checkMark.png"))));

}
public void setRead(boolean ready) {
	this.ready = ready;
	if (ready) {
		this.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/checkMark.png"))));
	}
	else {
		this.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/xMark.png"))));
	}
	
}
}
