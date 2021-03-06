package lobby;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import replay.GetReplayFile;
import table.Table;
import Client.CheckersLobby.State;
import RMIConnection.Interfaces.RMIServerInterface;

@SuppressWarnings("serial")
public class lobbyWindow extends JFrame {

	private JFrame frame;
	private JTextField chatInputField;
	private JLabel currentlyActiveTable;
	private static RMIServerInterface serverConnection;
	private ImageIcon normalTableIconE;
	private ImageIcon highlightedTableIconE;
	private ImageIcon normalTableIconH;
	private ImageIcon highlightedTableIconH;
	private ImageIcon normalTableIconF;
	private ImageIcon highlightedTableIconF;
	private JTextArea chatTextArea;
	private String myName;
	private JPanel tableListFlowPanel;
	private ArrayList<String> usersInMainChat; // List of users in main chat
	// JList for PMs
	private JScrollPane chatScrollPane;
	private static State curState; // Current state, not altered within here
	// however important for knowing if clicks
	// are possible. Sync'd from Checkers Lobby
	private boolean newTableCreation; // Used for selecting your newly created
	// table
	private boolean addedAlready = false;
	private Map<JLabel, Integer> tidHashTable; // used for extracting tid from a
	// jlabel rather than making new
	// class for JPANEL that contain
	// an ID.
	public Map<Integer, Table> tablesHashMap;
	private JButton btnObserveTable;

	private JList<String> jListOfUsers;
	private String selectedUser = ""; // Used for pm
	private JButton btnJoinTable;

	/**
	 * Init for lobby. sets table and user list so they can be edited before the
	 * table window is actually called up
	 */
	public lobbyWindow() {
		super();
		usersInMainChat = new ArrayList<String>();
		tablesHashMap = new HashMap<>();
		curState = State.notConnected;
		tidHashTable = new HashMap<JLabel, Integer>();
		newTableCreation = false;
	}

	// Adds intial tables to list before lobby window is shown
	public void addInitialTables(Map<Integer, Table> tablesHashMap2) {
		System.out.println("init tables size " + tablesHashMap2.size());
		tablesHashMap = tablesHashMap2;
		if (tableListFlowPanel != null) {
			addedAlready = true;
			int[] initTids = new int[tablesHashMap.keySet().size()];
			int i = 0;
			for (Integer value : tablesHashMap.keySet()) {
				initTids[i] = value;
				i++;
			}
			addTables(initTids);
		}
	}

	// Actually adds tables to panel
	public void addTables(int[] array) {
		System.out.println("add tables called size : " + array.length);

		for (int i = 0; i < array.length; i++) {
			// TODO change to use images that are 1/2 2/2 etc.
			final JLabel tableLabel = new JLabel();
			Table currentTable = tablesHashMap.get(array[i]);

			// GET NUMBER OF ADDING TABLE AND CURRENT TABLE
			tableLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (currentlyActiveTable != null) {
						Table oldActive = tablesHashMap.get(tidHashTable
								.get(currentlyActiveTable));
						currentlyActiveTable.setIcon(getIconForTable(
								oldActive, false));
					}
					Table newActive = tablesHashMap.get(tidHashTable
							.get(tableLabel));
					currentlyActiveTable = tableLabel;
					currentlyActiveTable.setIcon(getIconForTable(newActive,
							true));
					if (currentlyActiveTable.getIcon() != normalTableIconF
							&& currentlyActiveTable.getIcon() != highlightedTableIconF) {
						btnObserveTable.setEnabled(false);
					} else
						btnObserveTable.setEnabled(true);
					if (currentlyActiveTable.getIcon() == normalTableIconF
							|| currentlyActiveTable.getIcon() == highlightedTableIconF) {
						btnJoinTable.setEnabled(false);
					} else
						btnJoinTable.setEnabled(true);
				}
			});

			if (newTableCreation) {
				if (currentlyActiveTable != null){
					Table oldActive = tablesHashMap.get(tidHashTable.get(currentlyActiveTable));
					currentlyActiveTable.setIcon(getIconForTable(oldActive, false));
					currentlyActiveTable = null;
				}
				tableLabel.setIcon(getIconForTable(currentTable, true));
				currentlyActiveTable = tableLabel;
			} else {
				tableLabel.setIcon(getIconForTable(currentTable, false));
			}
			tableLabel.setIconTextGap(-125);
			tableLabel.setOpaque(true);
			tableLabel.setLayout(null);

			tableListFlowPanel.add(tableLabel);
			tableLabel.setText("Table " + array[i]);
			tableLabel.setFont(new Font("Serif", Font.PLAIN, 20));
			tableLabel.setForeground(Color.red);
			tableLabel
			.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			tableLabel.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

			tableListFlowPanel.updateUI();
			tidHashTable.put(tableLabel, array[i]);

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
		chatTextArea.select(Integer.MAX_VALUE, 0);
		// JScrollBar vertical = chatScrollPane.getVerticalScrollBar();
		// vertical.setValue(vertical.getMaximum());
	}

	public State getCurState() {
		return curState;
	}

	private void initialize() {

		normalTableIconE = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/unselectedTableEmpty.png")));
		highlightedTableIconE = new ImageIcon(Toolkit.getDefaultToolkit()
				.getImage(getClass().getResource("/selectedTableEmpty.png")));

		normalTableIconH = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/unselectedTableHalfFill.png")));
		highlightedTableIconH = new ImageIcon(Toolkit.getDefaultToolkit()
				.getImage(getClass().getResource("/selectedTableHalfFill.png")));

		normalTableIconF = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/unselectedFull.png")));
		highlightedTableIconF = new ImageIcon(Toolkit.getDefaultToolkit()
				.getImage(getClass().getResource("/selectedTableFull.png")));

		frame = new JFrame();
		frame.setBounds(100, 100, 1028, 665);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Lobby");

		JPanel chatPlaceHolderPanel = new JPanel();
		chatPlaceHolderPanel.setBounds(10, 11, 404, 622);
		frame.getContentPane().add(chatPlaceHolderPanel);
		chatPlaceHolderPanel.setLayout(null);

		chatInputField = new JTextField();
		chatInputField.setBounds(0, 569, 266, 42);
		chatPlaceHolderPanel.add(chatInputField);
		chatInputField.setColumns(10);
		chatInputField.addKeyListener(new KeyAdapter() {
			// Listener for ENTER key
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == 10)
					inputSubmit();
			}
		});

		JButton chatSendButton = new JButton("Send");
		chatSendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				inputSubmit();
			}
		});
		chatSendButton.setBounds(276, 569, 118, 42);
		chatPlaceHolderPanel.add(chatSendButton);

		chatScrollPane = new JScrollPane();
		chatScrollPane
		.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		chatScrollPane.setBounds(1, 1, 393, 557);
		chatPlaceHolderPanel.add(chatScrollPane);

		chatTextArea = new JTextArea();
		chatTextArea.setLineWrap(true);
		chatScrollPane.setViewportView(chatTextArea);
		chatTextArea.setEditable(false);

		chatPlaceHolderPanel.add(chatScrollPane);

		JPanel tableControlButtons = new JPanel();
		tableControlButtons.setBounds(424, 550, 578, 84);
		frame.getContentPane().add(tableControlButtons);
		tableControlButtons.setLayout(null);

		btnJoinTable = new JButton("Join Table");
		btnJoinTable.setBounds(304, 11, 127, 51);
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
		btnCreateTable.setBounds(143, 11, 149, 51);
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

		btnObserveTable = new JButton("Observe Table");
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

		btnObserveTable.setEnabled(false);
		btnObserveTable.setBounds(441, 11, 127, 51);
		tableControlButtons.add(btnObserveTable);

		JButton btnWatchReplays = new JButton("Watch Replays");
		btnWatchReplays.setBounds(0, 11, 133, 51);
		tableControlButtons.add(btnWatchReplays);
		btnWatchReplays.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GetReplayFile();
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(556, 11, 446, 539);
		frame.getContentPane().add(scrollPane);

		tableListFlowPanel = new JPanel();
		scrollPane.setViewportView(tableListFlowPanel);
		tableListFlowPanel.setLayout(new GridLayout(0, 3, 0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(424, 11, 123, 539);
		frame.getContentPane().add(scrollPane_1);
		final JPopupMenu popup = new JPopupMenu();
		JMenuItem menuItem = new JMenuItem("Send a PM");
		menuItem.addMouseListener(new MouseAdapter() {
			// Adds PM tag to the input
			public void mouseReleased(MouseEvent e) {
				chatInputField.setText("@" + selectedUser + " ");
			}
		});
		popup.add(menuItem);
		jListOfUsers = new JList<String>();
		jListOfUsers.setVisibleRowCount(50);
		jListOfUsers.setFont(new Font("Tahoma", Font.PLAIN, 12));
		jListOfUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				maybeShowPopup(e);
			}

			public void mouseReleased(MouseEvent e) {
				int index = jListOfUsers.locationToIndex(e.getPoint());
				selectedUser = usersInMainChat.get(index);
				maybeShowPopup(e);
			}

			private void maybeShowPopup(MouseEvent e) {
				if (e.isPopupTrigger())
					popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
		scrollPane_1.setViewportView(jListOfUsers);

		frame.setVisible(true);

	}

	public void inputSubmit(){
		if (chatInputField.getText().length() > 0) {
			if (curState == State.inLobby) {
				try {

					String input = chatInputField.getText();
					// Private Message
					if (input.startsWith("@")) {
						String pmInput[] = input.split("\\s", 2);
						String recp = pmInput[0].substring(1);
						String msg = pmInput[1];
						serverConnection.sendMsg(recp, msg);
						if (!recp.equals(myName))
							addTextMainLobbyWindow("[PM to " + recp
									+ "] " + ": " + msg);
					} else
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
		if (!addedAlready) {
			int[] initTids = new int[tablesHashMap.keySet().size()];
			int i = 0;
			for (Integer value : tablesHashMap.keySet()) {
				initTids[i] = value;
				i++;
			}
			addTables(initTids);
		}
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

	public void updateTableImages(Table table) {
		Integer tid = table.getTid();
		for (JLabel value : tidHashTable.keySet()) {
			if (value.getText().contains(tid.toString())) {
				if (value == currentlyActiveTable) {
					value.setIcon(getIconForTable(table, true));
				} else
					value.setIcon(getIconForTable(table, false));
			}
		}
	}

	public Icon getIconForTable(Table table, boolean selected) {
		int count = 0;
		if (!table.getBlackseat().equals("-1")) {
			count++;
		}
		if (!table.getRedseat().equals("-1")) {
			count++;
		}
		if (selected) {
			if (count == 2) {
				return highlightedTableIconF;
			} else if (count == 1) {
				return highlightedTableIconH;
			} else
				return highlightedTableIconE;
		} else {
			if (count == 2) {
				return normalTableIconF;
			} else if (count == 1) {
				return normalTableIconH;
			} else
				return normalTableIconE;
		}

	}
}
