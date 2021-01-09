package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.DatabaseObject;
import objects.Candidate;
import objects.Group;
import objects.GroupMember;
import objects.GroupThread;
import objects.RegisteredUser;
import objects.VotingThread;

public class CurrentGroupScreen extends JFrame{
	
	private JTable myFriendsTable;
	private JTable myThreadsTable;
	private Group myGroup;
	private RegisteredUser myUser;
	private DatabaseObject myDatabase;
	
	public CurrentGroupScreen(DatabaseObject theDatabase, Group theGroup, RegisteredUser theUser)
	{
		myDatabase = theDatabase;
		myGroup = theGroup;
		myUser = theUser;
		
		constructJFrame();
	}
	
	private void constructJFrame() {
		myFriendsTable = createFriendsTable();
		myThreadsTable = createThreadsTable();
		
		JPanel tablePanel = new JPanel();
		
		tablePanel.add(myFriendsTable);
		tablePanel.add(myThreadsTable);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(createAddMemberButton(myGroup));
		buttonPanel.add(createViewMemberButton(myGroup));
		buttonPanel.add(createViewGroupThreadButton(myGroup));
		
		this.add(new JLabel("Group Name: " + myGroup.getGroupName()), BorderLayout.NORTH);
		this.add(tablePanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
        this.setVisible(true);
	}
	
	private JTable createFriendsTable()
	{
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable tempTable = new JTable(tableModel);
		
		List<GroupMember> members = myDatabase.getGroupMembers(myGroup);
		
		tableModel.addColumn("Group Members");
		
		for (GroupMember member: members) {
			tableModel.insertRow(0, new Object[] {member.getUsername()});
		}
		
		return tempTable;
	}
	
	private JTable createThreadsTable()
	{
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable tempTable = new JTable(tableModel);
		
		List<GroupThread> threads = myDatabase.getGroupThreads(myGroup);
		
		tableModel.addColumn("Thread Name");
		tableModel.addColumn("Thread Creator");
		
		if (threads.size() == 0) {
			tableModel.insertRow(0, new Object[] {"To display", " "});
			tableModel.insertRow(0, new Object[] {"No Threads", " "});
			
		} else {
			for (GroupThread thread: threads) {
				tableModel.insertRow(0, new Object[] {thread.getTitle(), thread.getThreadCreator()});
			}
		}
		
		return tempTable;
	}
	
	private JButton createAddMemberButton(Group theGroup) {
		JButton tempButton = new JButton("Add Member");
		tempButton.addActionListener(addMemberButtonAction(theGroup));
		
		return tempButton;
	}
	
	private ActionListener addMemberButtonAction(Group theGroup) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//new CurrentGroupScreen(myDatabase, theGroup,  myUser);
			}
		};
	}
	
	private JButton createViewMemberButton(Group theGroup) {
		JButton tempButton = new JButton("View Member Profile");
		tempButton.addActionListener(viewMemberButtonAction());
		
		return tempButton;
	}
	
	private ActionListener viewMemberButtonAction() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int cell = myFriendsTable.getSelectedRow();
				
				if (cell == -1)
				{
					JOptionPane.showMessageDialog(null, "Must select member to view profile!");
				} else {
					RegisteredUser member = myDatabase.getRegisteredUser((String) myFriendsTable.getModel().getValueAt(cell, 0));
					new MemberProfileScreen(member);
				}

			}
		};
	}
	
	private JButton createViewGroupThreadButton(Group theGroup) {
		JButton tempButton = new JButton("View Group Thread");
		tempButton.addActionListener(viewGroupThreadButtonAction(theGroup));
		
		return tempButton;
	}
	
	private ActionListener viewGroupThreadButtonAction(Group theGroup) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//new CurrentGroupScreen(myDatabase, theGroup,  myUser);
			}
		};
	}
}
