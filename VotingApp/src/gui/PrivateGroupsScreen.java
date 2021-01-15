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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import database.DatabaseObject;
import objects.Group;
import objects.RegisteredUser;

public class PrivateGroupsScreen extends JFrame{
	
	private static final long serialVersionUID = 838303835754789248L;
	private Map<JPanel, JButton> myThreadComponents;
	private DatabaseObject myDatabase;
	private RegisteredUser myUser;
	
	public PrivateGroupsScreen(DatabaseObject database, RegisteredUser theUser) {
		
		myDatabase = database;
		myThreadComponents = new HashMap<JPanel, JButton>();
		myUser = theUser;
		
		this.setGroups();
		this.constructJFrame();
		this.setJFrameDetails();
	}
	
	private void constructJFrame() {
		GridLayout grid = new GridLayout(myThreadComponents.size(), 2, 5, 10);
		JPanel groupPanel = new JPanel();
		groupPanel.setLayout(grid);
		
		if (myThreadComponents.size() == 0)
			groupPanel.add(new JLabel("No Groups Found."));	
		else {
			for (JPanel title: myThreadComponents.keySet()) {
				groupPanel.add(title);
				groupPanel.add(myThreadComponents.get(title));
			}
		}

		this.add(new JLabel("My Private Groups:"), BorderLayout.NORTH);
		this.add(groupPanel, BorderLayout.CENTER);
		this.add(createAddGroupButton(), BorderLayout.SOUTH);
	}
	
	private void setJFrameDetails() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		ImageIcon titleImage = new ImageIcon("Images/wsu_logo.png");
		
		this.setIconImage(titleImage.getImage());
		this.setTitle("View My Groups");
		this.pack();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setVisible(true);
	}
	
	private void setGroups() {
		for (Group group: myDatabase.getPrivateGroups(myUser)) {
			JPanel groupPanel = new JPanel();
			JLabel groupNameLabel = new JLabel("Group Name:    " + group.getGroupName());
			JLabel createdByLabel = new JLabel("Created by:      " + group.getAdminName());
			
			groupPanel.setLayout(new GridLayout(2, 1));
			groupPanel.add(groupNameLabel);
			groupPanel.add(createdByLabel);
			groupPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			myThreadComponents.put(groupPanel, createViewButton(group));
		}
	}
	
	private JButton createViewButton(Group theGroup) {
		JButton viewButton = new JButton("View Private Group");
		viewButton.addActionListener(viewButtonAction(theGroup));
		
		return viewButton;
	}
	
	private ActionListener viewButtonAction(Group theGroup) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CurrentGroupScreen(myDatabase, theGroup,  myUser);
				dispose();
			}
		};
	}
	
	private JButton createAddGroupButton() {
		JButton addGroupButton = new JButton("Create New Private Group");
		addGroupButton.addActionListener(addGroupButtonAction());
		
		return addGroupButton;
	}
	
	private ActionListener addGroupButtonAction() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CreateGroupScreen(myDatabase, myUser);
				dispose();
			}
		};
	}

}
