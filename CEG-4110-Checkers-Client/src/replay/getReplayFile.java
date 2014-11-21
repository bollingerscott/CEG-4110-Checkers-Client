package replay;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;

/*
 * launches a JFrame with a combo box and a start button. The comboBox is
 * populated with all of the files in the working directory that have the 
 * appropriate file extension. A user can choose one and lauch a replay from
 * it.
 */
public class getReplayFile extends JFrame {

	private JPanel contentPane;
	private File selectedFile = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					getReplayFile frame = new getReplayFile();
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
	public getReplayFile() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 181);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//set content for JComboBox
		File dir = new File(".");
		File[] filesList = dir.listFiles();
		ArrayList<File> fileList = new ArrayList();
		for (File file : filesList) {
		    if (file.isFile() && file.toString().endsWith(replayFile.fileExtension)) {
		        fileList.add(file);
		    }
		}
		JComboBox<File> comboBox = new JComboBox<File>(filesList);
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JComboBox cb = (JComboBox)arg0.getSource();
		        File file = (File)cb.getSelectedItem();
				
		        selectedFile = file;
			}
			
		});
		
		
		contentPane.add(comboBox, BorderLayout.WEST);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (selectedFile != null) {
					try {
						ReplayFrame rFrame = new ReplayFrame(replayFile.readFile(selectedFile));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			
		});
		contentPane.add(btnNewButton, BorderLayout.CENTER);
		

	}

}