package game;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.ComponentOrientation;
import java.awt.Rectangle;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ObserveWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ObserveWindow frame = new ObserveWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ObserveWindow() {
		setTitle("Observe");
		setResizable(false);
		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 769, 636);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Observe observe = new Observe();
		observe.setBounds(10, 11, 523, 425);
		contentPane.add(observe);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 444, 521, 108);
		contentPane.add(scrollPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 560, 435, 37);
		contentPane.add(scrollPane_1);
		
		JButton button = new JButton("Send");
		button.setForeground(Color.BLACK);
		button.setFont(new Font("Tahoma", Font.PLAIN, 18));
		button.setBackground(Color.LIGHT_GRAY);
		button.setBounds(457, 560, 76, 37);
		contentPane.add(button);
		
		JLabel label = new JLabel("Player 1");
		label.setForeground(Color.LIGHT_GRAY);
		label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label.setBounds(543, 11, 86, 29);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBackground(Color.LIGHT_GRAY);
		textField.setBounds(543, 51, 208, 37);
		contentPane.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBackground(Color.LIGHT_GRAY);
		textField_1.setBounds(680, 99, 71, 35);
		contentPane.add(textField_1);
		
		JLabel label_1 = new JLabel("# of Moves");
		label_1.setForeground(Color.LIGHT_GRAY);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_1.setBounds(543, 99, 132, 35);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("Pieces Left");
		label_2.setForeground(Color.LIGHT_GRAY);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_2.setBounds(543, 145, 127, 37);
		contentPane.add(label_2);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBackground(Color.LIGHT_GRAY);
		textField_2.setBounds(680, 145, 71, 37);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBackground(Color.LIGHT_GRAY);
		textField_3.setBounds(680, 193, 71, 37);
		contentPane.add(textField_3);
		
		JLabel label_3 = new JLabel("Pieces Taken");
		label_3.setForeground(Color.LIGHT_GRAY);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_3.setBounds(543, 192, 132, 37);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("VS");
		label_4.setForeground(Color.RED);
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 24));
		label_4.setBounds(637, 258, 38, 24);
		contentPane.add(label_4);
		
		JLabel label_5 = new JLabel("Player 2");
		label_5.setForeground(Color.LIGHT_GRAY);
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_5.setBounds(543, 295, 86, 29);
		contentPane.add(label_5);
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		textField_4.setColumns(10);
		textField_4.setBackground(Color.LIGHT_GRAY);
		textField_4.setBounds(543, 331, 208, 37);
		contentPane.add(textField_4);
		
		JLabel label_6 = new JLabel("# of Moves");
		label_6.setForeground(Color.LIGHT_GRAY);
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_6.setBounds(543, 379, 132, 35);
		contentPane.add(label_6);
		
		textField_5 = new JTextField();
		textField_5.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField_5.setEditable(false);
		textField_5.setColumns(10);
		textField_5.setBackground(Color.LIGHT_GRAY);
		textField_5.setBounds(680, 379, 71, 35);
		contentPane.add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField_6.setEditable(false);
		textField_6.setColumns(10);
		textField_6.setBackground(Color.LIGHT_GRAY);
		textField_6.setBounds(680, 420, 71, 35);
		contentPane.add(textField_6);
		
		JLabel label_7 = new JLabel("Pieces Left");
		label_7.setForeground(Color.LIGHT_GRAY);
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_7.setBounds(543, 419, 127, 37);
		contentPane.add(label_7);
		
		JLabel label_8 = new JLabel("Pieces Taken");
		label_8.setForeground(Color.LIGHT_GRAY);
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_8.setBounds(543, 462, 132, 37);
		contentPane.add(label_8);
		
		textField_7 = new JTextField();
		textField_7.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField_7.setEditable(false);
		textField_7.setColumns(10);
		textField_7.setBackground(Color.LIGHT_GRAY);
		textField_7.setBounds(680, 463, 71, 35);
		contentPane.add(textField_7);
	}
}
