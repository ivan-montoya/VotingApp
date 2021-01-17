package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import database.DatabaseObject;
import objects.RegisteredUser;
import objects.VotingThread;

public class MyThreadsScreen extends JFrame{

	private static final long serialVersionUID = 6926040991323789520L;
	private Map<JPanel, JButton> myThreadComponents;
	private DatabaseObject myDatabase;
	private RegisteredUser myUser;
	
	public MyThreadsScreen(DatabaseObject database, RegisteredUser theUser) {
		
		myDatabase = database;
		myThreadComponents = new HashMap<JPanel, JButton>();
		myUser = theUser;
		
		this.setThreads();
		this.constructJFrame();
		this.setJFrameDetails();
	}
	
	private void constructJFrame() {
		
		GridLayout grid = new GridLayout(myThreadComponents.size(), 2, 5, 10);
		
		this.setLayout(grid);
		
		for (JPanel title: myThreadComponents.keySet()) {
			this.add(title);
			this.add(myThreadComponents.get(title));
		}
	}
	
	private void setJFrameDetails() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		ImageIcon titleImage = new ImageIcon("Images/wsu_logo.png");
		
		this.setIconImage(titleImage.getImage());
		this.setTitle("View Threads");
		this.pack();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setVisible(true);
	}
	
	private void setThreads() {
		for (VotingThread thread: myDatabase.getUserThreads(myUser)) {
			String status;
			
			if (thread.getOpenStatus().equals("TRUE"))
				status = "Open";
			else 
				status = "Closed";
			
			JPanel threadPanel = new JPanel();
			JLabel threadLabel = new JLabel("Thread Title:      " + thread.getTitle());
			JLabel dateLabel = new JLabel("Date Created:      " + thread.getDate().toString());
			JLabel statusLabel = new JLabel("Status:           " + status);
			JButton viewButton = createViewButton(thread);
			
			threadPanel.setLayout(new GridLayout(3, 1));
			threadPanel.add(threadLabel);
			threadPanel.add(dateLabel);
			threadPanel.add(statusLabel);
			threadPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			myThreadComponents.put(threadPanel, viewButton);
		}
	}
	
	private JButton createViewButton(VotingThread theThread) {
		JButton viewButton = new JButton("View Thread");
		viewButton.addActionListener(viewButtonAction(theThread));
		
		return viewButton;
	}
	
	private ActionListener viewButtonAction(VotingThread theThread) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CurrentUserThreadScreen(theThread, myDatabase);
				dispose();
			}
		};
	}
}
