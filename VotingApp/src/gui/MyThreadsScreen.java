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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import database.DatabaseObject;
import objects.RegisteredUser;
import objects.VotingThread;

public class MyThreadsScreen extends JFrame{
	
	/**Used to create components for each Thread*/
	private Map<JPanel, JButton> myThreadComponents;
	private DatabaseObject myDatabase;
	private RegisteredUser myUser;
	
	public MyThreadsScreen(DatabaseObject database, RegisteredUser theUser) {
		
		myDatabase = database;
		myThreadComponents = new HashMap<JPanel, JButton>();
		myUser = theUser;
		
		setThreads();
		
		start();
	}
	
	/**
	 * Sets up components for the JFrame.
	 */
	private void start() {
		
		GridLayout grid = new GridLayout(myThreadComponents.size(), 2, 5, 10);
		
		
		this.setLayout(grid);
		for (JPanel title: myThreadComponents.keySet()) {
			this.add(title);
			this.add(myThreadComponents.get(title));
		}
		this.setTitle("View Threads");
		
		this.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
        this.setVisible(true);
	}
	
	/**
	 * Uses a List of threads to create JLabels and JButtons
	 * for each thread.
	 */
	private void setThreads() {
		for (VotingThread thread: myDatabase.getUserThreads(myUser)) {
			JPanel threadPanel = new JPanel();
			JLabel tempLabel = new JLabel("Thread Title:      " + thread.getTitle());
			JLabel tempLabel2 = new JLabel("Date Created:      " + thread.getDate().toString());
			
			String status;
			
			if (thread.getOpenStatus().equals("TRUE"))
				status = "Open";
			else 
				status = "Closed";
			
			JLabel tempLabel3 = new JLabel("Status:           " + status);
			
			threadPanel.setLayout(new GridLayout(3, 1));
			
			threadPanel.add(tempLabel);
			threadPanel.add(tempLabel2);
			threadPanel.add(tempLabel3);
			
			threadPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			myThreadComponents.put(threadPanel, createViewButton(thread));
		}
	}
	
	private JButton createViewButton(VotingThread theThread) {
		JButton tempButton = new JButton("View Thread");
		tempButton.addActionListener(viewButtonAction(theThread));
		
		return tempButton;
	}
	
	private ActionListener viewButtonAction(VotingThread theThread) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CurrentUserThreadScreen(theThread, myDatabase, myUser);
			}
		};
	}
}
