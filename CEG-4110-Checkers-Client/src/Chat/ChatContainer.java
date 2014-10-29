package Chat;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * generic chat implementation to be used by various components. Idea is to keep the implementation
 * solid, but the button layout generic to support any design. This means individual windows will
 * have to create the components, place them wherever, and create this object from those components.
 * 
 * To be able to chat, you need a text field to enter text into, a button to 
 * submit text, and an textField to output text to. The constructor for this class
 * takes these three object containers and provides clickListeners and basic
 * functionality
 */
public class ChatContainer {

	private JButton jb;
	private JTextField jtf;
	private JTextArea jta;
	
	public ChatContainer(JButton jb, JTextField jtf, JTextArea jta) {
		this.jb = jb;
		this.jtf = jtf;
		this.jta = jta;
	}
	
	/*
	 * This method should be implemented in a clickListener for the button and should pull
	 * the text from the text field
	 * 
	 * can take care of public and private message by parsing the string. A private message
	 * begins with the "@" sign and the name of the recipient.
	 */
	public boolean sendMessage() {
		return false;
	}
	
}
