package lobbyWindowTest;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ScrollPaneConstants;

public class appWindow {

	private JFrame frame;
	private JTextField chatInputField;
	private JLabel currentlyActiveTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					appWindow window = new appWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public appWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1028, 735);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel chatPlaceHolderPanel = new JPanel();
		chatPlaceHolderPanel.setBounds(10, 11, 404, 622);
		frame.getContentPane().add(chatPlaceHolderPanel);
		chatPlaceHolderPanel.setLayout(null);

		chatInputField = new JTextField();
		chatInputField.setBounds(0, 579, 266, 32);
		chatPlaceHolderPanel.add(chatInputField);
		chatInputField.setColumns(10);

		JButton chatSendButton = new JButton("Send");
		chatSendButton.setBounds(276, 579, 118, 32);
		chatPlaceHolderPanel.add(chatSendButton);

		JTextArea chatTextArea = new JTextArea();
		chatTextArea.setText("Chat placeholder");
		chatTextArea.setEditable(false);
		chatTextArea.setBounds(0, 0, 394, 576);
		chatPlaceHolderPanel.add(chatTextArea);

		JPanel listOfUsersPanel = new JPanel();
		listOfUsersPanel.setBounds(424, 11, 122, 622);
		frame.getContentPane().add(listOfUsersPanel);
		listOfUsersPanel.setLayout(null);

		JScrollPane scrollingUserList = new JScrollPane();
		scrollingUserList.setBounds(0, 0, 122, 622);
		listOfUsersPanel.add(scrollingUserList);

		JTextArea listOfUsers = new JTextArea();
		listOfUsers.setBackground(Color.LIGHT_GRAY);
		listOfUsers
				.setText("Hyman\r\nJeanett\r\nGenevive\r\nVerona\r\nKaryl\r\nElaina\r\nQiana\r\nZachery\r\nVirgina\r\nNicki\r\nDarnell\r\nElfriede\r\nAntonetta\r\nCasie\r\nPhyliss\r\nKorey\r\nMajorie\r\nKandy\r\nOrville\r\nKrystal\r\nLaure\r\nDalila\r\nMaudie\r\nLouie\r\nWynell\r\nFredda\r\nSondra\r\nJosie\r\nMeryl\r\nMaren\r\nJazmin\r\nLatonia\r\nRoselia\r\nArletta\r\nEllyn\r\nZona\r\nCassondra\r\nRowena\r\nDwana\r\nPhoebe\r\nMaurine\r\nLillie\r\nMaurice\r\nJulietta\r\nAja\r\nInez\r\nBrittany\r\nDannie\r\nCatalina\r\nTesha");
		scrollingUserList.setViewportView(listOfUsers);

		JPanel tabelControlButtons = new JPanel();
		tabelControlButtons.setBounds(556, 560, 446, 73);
		frame.getContentPane().add(tabelControlButtons);
		tabelControlButtons.setLayout(null);

		JButton btnJoinTable = new JButton("Join Table");
		btnJoinTable.setBounds(10, 11, 145, 43);
		tabelControlButtons.add(btnJoinTable);

		JButton btnCreateTable = new JButton("Create Table");
		btnCreateTable.setBounds(165, 11, 127, 43);
		tabelControlButtons.add(btnCreateTable);

		JButton btnObserveTable = new JButton("Observe Table");
		btnObserveTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnObserveTable.setBounds(309, 11, 127, 43);
		tabelControlButtons.add(btnObserveTable);

		JButton btnWatchReplays = new JButton("Watch Replays");
		btnWatchReplays.setBounds(697, 644, 196, 42);
		frame.getContentPane().add(btnWatchReplays);
		
		JScrollPane scrollingTableList = new JScrollPane();
		scrollingTableList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollingTableList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollingTableList.setBounds(556, 11, 446, 538);
		frame.getContentPane().add(scrollingTableList);
		
		JPanel tableListFlowPanel = new JPanel();
		scrollingTableList.setViewportView(tableListFlowPanel);
		tableListFlowPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		for (int i = 0; i < 13; i++) {
			final JLabel table = new JLabel("");
			table.setIcon(new ImageIcon(appWindow.class
					.getResource("/lobbyWindowTest/FOIV6Q7GOHMB1SO.MEDIUM.jpg")));
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (currentlyActiveTable != null) {
						currentlyActiveTable.setIcon(new ImageIcon(appWindow.class
								.getResource("/lobbyWindowTest/FOIV6Q7GOHMB1SO.MEDIUM.jpg")));					}
					currentlyActiveTable = table;
					table.setIcon(new ImageIcon(
							appWindow.class
									.getResource("/lobbyWindowTest/FOIV6Q7GOHMB1SO.MEDIUM_2.jpg")));
				}
			});
			tableListFlowPanel.add(table);
		}

	}
}
