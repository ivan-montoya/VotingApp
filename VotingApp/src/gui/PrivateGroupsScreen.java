package gui;

import java.awt.BorderLayout;
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
import objects.Group;
import objects.RegisteredUser;
import objects.VotingThread;

public class PrivateGroupsScreen extends JFrame{
	
	/**Used to create components for each Thread*/
	private Map<JPanel, JButton> myThreadComponents;
	private DatabaseObject myDatabase;
	private RegisteredUser myUser;
	
	public PrivateGroupsScreen(DatabaseObject database, RegisteredUser theUser) {
		
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
		
		
		JPanel groupPanel = new JPanel();
		GridLayout grid = new GridLayout(myThreadComponents.size(), 2, 5, 10);
		
		
		groupPanel.setLayout(grid);
		if (myThreadComponents.size() == 0)
		{
			groupPanel.add(new JLabel("No Groups Found."));
		}
		else {
			for (JPanel title: myThreadComponents.keySet()) {
				groupPanel.add(title);
				groupPanel.add(myThreadComponents.get(title));
			}
		}

		this.setTitle("View Public Threads");
		
		this.add(new JLabel("My Private Groups:"), BorderLayout.NORTH);
		this.add(groupPanel, BorderLayout.CENTER);
		this.add(createAddGroupButton(), BorderLayout.SOUTH);
		
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
		for (Group group: myDatabase.getPrivateGroups(myUser)) {
			JPanel threadPanel = new JPanel();
			JLabel tempLabel = new JLabel("Group Name:    " + group.getGroupName());
			JLabel tempLabel2 = new JLabel("Created by:      " + group.getAdminName());
			
			
			threadPanel.setLayout(new GridLayout(2, 1));
			
			threadPanel.add(tempLabel);
			threadPanel.add(tempLabel2);
			
			threadPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			myThreadComponents.put(threadPanel, createViewButton(group));
		}
	}
	
	private JButton createViewButton(Group theGroup) {
		JButton tempButton = new JButton("View Private Group");
		tempButton.addActionListener(viewButtonAction(theGroup));
		
		return tempButton;
	}
	
	private ActionListener viewButtonAction(Group theGroup) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CurrentGroupScreen(myDatabase, theGroup,  myUser);
			}
		};
	}
	
	private JButton createAddGroupButton() {
		JButton tempButton = new JButton("Create New Private Group");
		tempButton.addActionListener(newPrivateGroupButtonAction());
		
		return tempButton;
	}
	
	private ActionListener newPrivateGroupButtonAction() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CreateGroupScreen(myDatabase, myUser);
			}
		};
	}

}
