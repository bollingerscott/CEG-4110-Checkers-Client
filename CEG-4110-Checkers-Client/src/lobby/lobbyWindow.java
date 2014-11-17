package lobby;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Client.CheckersLobby.State;
import RMIConnection.Interfaces.RMIServerInterface;

public class lobbyWindow extends JFrame {

	private JFrame frame;
	private JTextField chatInputField;
	private JLabel currentlyActiveTable;
	private static RMIServerInterface serverConnection;
	private ImageIcon normalTableIcon;
	private ImageIcon highlightedTableIcon;
	private JTextArea chatTextArea;
	private String myName;
	private JPanel tableListFlowPanel;
	private ArrayList<String> usersInMainChat; // List of users in main chat
	private ArrayList<Integer> listOfTables; // List of table IDS (integers)
	// JList for PMs
	private static State curState; // Current state, not altered within here
									// however important for knowing if clicks
									// are possible. Sync'd from Checkers Lobby
	private boolean newTableCreation; // Used for selecting your newly created
										// table
	private Map<JLabel, Integer> tidHashTable; // used for extracting tid from a
												// jlabel rather than making new
												// class for JPANEL that contain
												// an ID.
	private JList<String> jListOfUsers;

	/**
	 * Init for lobby. sets table and user list so they can be edited before the
	 * table window is actually called up
	 */
	public lobbyWindow() {
		super();
		usersInMainChat = new ArrayList<String>();
		listOfTables = new ArrayList<Integer>();
		curState = State.notConnected;
		tidHashTable = new HashMap<JLabel, Integer>();
		newTableCreation = false;
	}

	// Adds intial tables to list before lobby window is shown
	public void addInitialTables(int[] array) {
		System.out.println("init tables size " + array.length);
		for (int i : array) {
			listOfTables.add(i);
		}
		addTables(array);
		System.out.println(listOfTables.size());
	}

	// Actually adds tables to panel
	public void addTables(int[] array) {
		System.out.println("add tables called size : " + array.length);
		for (int i = 0; i < array.length; i++) {
			final JLabel table = new JLabel();

			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (curState == State.inLobby) {
						if (currentlyActiveTable != null) {
							currentlyActiveTable.setIcon(normalTableIcon);
						}
						currentlyActiveTable = table;
						table.setIcon(highlightedTableIcon);
					}
				}
			});
			tidHashTable.put(table, array[i]);
			if (newTableCreation) {
				table.setIcon(highlightedTableIcon);
				newTableCreation = false;
				if (currentlyActiveTable != null) {
					currentlyActiveTable.setIcon(normalTableIcon);
				}
				currentlyActiveTable = table;
			} else
				table.setIcon(normalTableIcon);
			tableListFlowPanel.add(table);
			tableListFlowPanel.updateUI();
		}
	}

	// Adds to main table screen
	public void addTextMainLobbyWindow(String string) {
		if (chatTextArea != null) {
			if (chatTextArea.getText().length() == 0) {
				chatTextArea.setText(string);
			} else
				chatTextArea.setText(chatTextArea.getText() + "\n" + string);
		}
	}
	public State getCurState() {
		return curState;
	}

	/**
	 * Test GIT COmment Initialize the contents of the frame.
	 * 
	 * @wbp.parser.entryPoint
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
		frame.setTitle("Lobby");

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
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (chatInputField.getText().length() > 0) {
					if (curState == State.inLobby) {
						try {
							serverConnection.sendMsg_All(chatInputField
									.getText());
						} catch (RemoteException e) {
							e.printStackTrace();
							System.out.println("Caught error sending message?");

						}
					}
					chatInputField.setText("");

				}
			}
		});
		chatSendButton.setBounds(276, 579, 118, 32);
		chatPlaceHolderPanel.add(chatSendButton);

		chatTextArea = new JTextArea();
		chatTextArea.setEditable(false);
		chatTextArea.setBounds(0, 0, 394, 576);
		chatPlaceHolderPanel.add(chatTextArea);

		JPanel tableControlButtons = new JPanel();
		tableControlButtons.setBounds(556, 560, 446, 73);
		frame.getContentPane().add(tableControlButtons);
		tableControlButtons.setLayout(null);

		JButton btnJoinTable = new JButton("Join Table");
		btnJoinTable.setBounds(10, 11, 145, 43);
		tableControlButtons.add(btnJoinTable);// TODO Table logic?
		btnJoinTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (curState.equals(State.inLobby)
							&& currentlyActiveTable != null) {
						int tid = tidHashTable.get(currentlyActiveTable);
						serverConnection.joinTable(myName, tid);
						// Should queue up lobby window server messages to
						// handle
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}

			}
		});

		JButton btnCreateTable = new JButton("Create Table");
		btnCreateTable.setBounds(165, 11, 127, 43);
		tableControlButtons.add(btnCreateTable);
		btnCreateTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (curState.equals(State.inLobby)) {
						serverConnection.makeTable(myName);
						// Should que up lobby window server messages to handle
						newTableCreation = true;
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}

			}
		});

		JButton btnObserveTable = new JButton("Observe Table");
		btnObserveTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentlyActiveTable != null) {
					try {
						int tid = tidHashTable.get(currentlyActiveTable);
						System.out.println(tid);
						serverConnection.observeTable(myName, tid);
						// Should que up lobby window server messages to handle
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnObserveTable.setBounds(309, 11, 127, 43);
		tableControlButtons.add(btnObserveTable);

		JButton btnWatchReplays = new JButton("Watch Replays");
		btnWatchReplays.setBounds(697, 644, 196, 42);
		frame.getContentPane().add(btnWatchReplays);
		// TODO stretch goal of replays window. Don't focus now.

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(556, 11, 446, 539);
		frame.getContentPane().add(scrollPane);

		tableListFlowPanel = new JPanel();
		scrollPane.setViewportView(tableListFlowPanel);
		tableListFlowPanel.setLayout(new GridLayout(0, 5, 0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(424, 11, 123, 622);
		frame.getContentPane().add(scrollPane_1);

		jListOfUsers = new JList<String>();
		jListOfUsers.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_1.setViewportView(jListOfUsers);

		frame.setVisible(true);

	}

	// Sets users before lobby has been intialized.
	public void setUsers(ArrayList<String> lobbyUserList) {
		usersInMainChat = lobbyUserList;
	}

	/**
	 * Starts window, not done in constructor because constructor called
	 * intially. This is because table list and user list were being sent before
	 * the lobby was made causing issues
	 * */

	public void startWindow(RMIServerInterface server, String name,
			Client.CheckersLobby.State curState) {
		serverConnection = server;
		myName = name;
		lobbyWindow.curState = curState;
		initialize();
		updateUsers();
	}

	public void syncState(State a) {
		curState = a;
	}

	// Updates users based on list. Called when window is intialized.
	public void updateUsers() {
		DefaultListModel<String> model = new DefaultListModel<String>();
		for (String userName : usersInMainChat) {
			model.addElement(userName);
		}
		jListOfUsers.setModel(model);

	}
}
