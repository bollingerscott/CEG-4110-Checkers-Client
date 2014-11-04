package table;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Table extends JPanel {

	/**
	 * Create the panel.
	 */
	public Table() {
		setLayout(null);
		
		final ReadyButton btnNewButton = new ReadyButton();
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnNewButton.toggleReady();;
			}
		});
		btnNewButton.setIcon(new ImageIcon("res\\checkMark.png"));
		btnNewButton.setBounds(53, 167, 64, 63);
		add(btnNewButton);
		
		final ReadyButton btnNewButton_1 = new ReadyButton();
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton_1.toggleReady();
			}
		});
		btnNewButton_1.setIcon(new ImageIcon("res\\xMark.png"));
		btnNewButton_1.setBounds(300, 167, 64, 63);
		add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("Person one");
		lblNewLabel.setVerticalAlignment(JLabel.BOTTOM);
		lblNewLabel.setHorizontalAlignment(JLabel.CENTER);
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Colin\\Desktop\\playerIcon.jpg"));
		lblNewLabel.setBounds(53, 50, 130, 63);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Person Two");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\Colin\\Desktop\\playerIcon.jpg"));
		lblNewLabel_1.setBounds(300, 50, 124, 63);
		add(lblNewLabel_1);

	}
}
