package replay;

import game.Board;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;

/*
 * takes a list of states and displays a board with the states. The state can be 
 * changed by using a previous and next button
 */
public class ReplayFrame extends JFrame {

	private JPanel contentPane;
	private JButton prev;
	private JButton next;
	private Board board;
	
	private List<byte[][]> states;
	private int stateIndex;
	

	/**
	 * Create the frame.
	 */
	public ReplayFrame(final List<byte[][]> states) {
		
		this.states = states;
		stateIndex = 0;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
	
		board = new Board(false);
		contentPane.add(board, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
	
		next = new JButton("Next");
		prev = new JButton("Previous");
	
		panel.add(prev, BorderLayout.WEST);
		panel.add(next, BorderLayout.EAST);

		prev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (stateIndex > 0) {
					stateIndex--;
					board.setBoard_state(states.get(stateIndex));
				}
			}
		});
		
		next.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				if (stateIndex >= states.size()){
					stateIndex++;
					board.setBoard_state(states.get(stateIndex));
				}
			}
			
		});
		
	}

	
	
}
