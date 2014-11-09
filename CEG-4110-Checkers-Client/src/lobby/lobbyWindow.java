package lobby;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Arrays;
import javax.swing.Icon;
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
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.ScrollPaneConstants;

import RMIConnection.Interfaces.RMIServerInterface;

public class lobbyWindow {

	private JFrame frame;
	private JTextField chatInputField;
	private JLabel currentlyActiveTable;
	private static RMIServerInterface serverConnection;
	private ImageIcon normalTableIcon;
	private ImageIcon highlightedTableIcon;
	private JTextArea chatTextArea;
	private String myName;
	JPanel tableListFlowPanel;
	private ArrayList usersInMainChat;
	private ArrayList<Integer> listOfTables;

	/**
	 * Create the application.
	 * 
	 * @wbp.parser.entryPoint
	 */

	public void startWindow(RMIServerInterface server, String name) {
		serverConnection = server;
		myName = name;
		initialize();
	
		int[] myIntArray = new int[listOfTables.size()];
		for (int i = 0; i < listOfTables.size(); i++) {
			myIntArray[i] = listOfTables.get(i);
		}
		addTables(myIntArray);
	}

	public lobbyWindow() {
		super();
		usersInMainChat = new ArrayList();
		listOfTables = new ArrayList<Integer>();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		normalTableIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/unselectedTable.jpg")));
		highlightedTableIcon = new ImageIcon(Toolkit.getDefaultToolkit()
				.getImage(getClass().getResource("/selectedTable.jpg")));
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
		chatSendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chatInputField.getText().length() > 0) {
					try {
						serverConnection.sendMsg_All(chatInputField.getText());
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("Caught error sending message?");

					} finally {
						chatInputField.setText("");
					}
				}
			}
		});
		chatSendButton.setBounds(276, 579, 118, 32);
		chatPlaceHolderPanel.add(chatSendButton);

		chatTextArea = new JTextArea();
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
		listOfUsers.setEditable(false);
		listOfUsers.setBackground(Color.LIGHT_GRAY);
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
		btnCreateTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					serverConnection.makeTable(myName);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}

			}
		});

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
		scrollingTableList
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollingTableList
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollingTableList.setBounds(556, 11, 446, 538);
		frame.getContentPane().add(scrollingTableList);

		tableListFlowPanel = new JPanel();
		scrollingTableList.setViewportView(tableListFlowPanel);
		tableListFlowPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		frame.setVisible(true);

	}

	public void addInitialTables(int[] array) {
		for (int i : array) {
			listOfTables.add(i);
		}
	}

	public void addTables(int[] array) {
		for (int i = 0; i < array.length; i++) {
			final JLabel table = new JLabel("");
			table.setIcon(normalTableIcon);
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (currentlyActiveTable != null) {
						currentlyActiveTable.setIcon(normalTableIcon);
					}
					currentlyActiveTable = table;
					table.setIcon(highlightedTableIcon);
				}
			});
			tableListFlowPanel.add(table);
		}
	}

	public void addTextMainLobbyWindow(String string) {
		if (chatTextArea != null) {
			if (chatTextArea.getText().length() == 0) {
				chatTextArea.setText(string);
			} else
				chatTextArea.setText(chatTextArea.getText() + "\n" + string);
		}
	}
}
