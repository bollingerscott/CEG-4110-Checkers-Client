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
 * appropriate file extension. A user can choose one and launch a replay from
 * it.
 */
public class GetReplayFile extends JFrame {

	private JPanel contentPane;
	private File selectedFile = null;



	/**
	 * Create the frame.
	 */
	public GetReplayFile() {
		
		super();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 80);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//set content for JComboBox
		File dir = new File("./Replays/");
		File[] filesList = dir.listFiles();
		ArrayList<File> fileList = new ArrayList();
		for (File file : filesList) {
		    if (file.isFile() && file.toString().endsWith(ReplayFile.fileExtension)) {
		        fileList.add(file);
		        System.out.println("File: " + file.toString());
		    }
		}
		JComboBox<File> comboBox = new JComboBox<File>(fileList.toArray(new File[fileList.size()]));
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JComboBox cb = (JComboBox)arg0.getSource();
		        File file = (File)cb.getSelectedItem();
				
		        selectedFile = file;
			}
			
		});
		
		
		contentPane.add(comboBox, BorderLayout.CENTER);
		
		JButton btnNewButton = new JButton("Open");
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (selectedFile != null) {
					try {
						ReplayFrame rFrame = new ReplayFrame(ReplayFile.readFile(selectedFile));
						rFrame.paintComponents(rFrame.getGraphics()); //forces a repaint. possible bug: repaint() will not work wor initial window
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			
		});
		selectedFile = fileList.get(0);
		contentPane.add(btnNewButton, BorderLayout.EAST);
		this.setVisible(true);

	}

}
