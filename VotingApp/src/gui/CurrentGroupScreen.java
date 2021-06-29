package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.DatabaseObject;
import objects.Group;
import objects.GroupMember;
import objects.GroupThread;
import objects.RegisteredUser;
import objects.VotingThread;

public class CurrentGroupScreen extends JFrame{
	
	private static final long serialVersionUID = -3628637307657080752L;
	private JTable myFriendsTable;
	private DefaultTableModel myFriendsModel;
	private JTable myThreadsTable;
	private Group myGroup;
	private RegisteredUser myUser;
	private DatabaseObject myDatabase;
	private List<GroupThread> myThreads;
	
	public CurrentGroupScreen(DatabaseObject theDatabase, Group theGroup, RegisteredUser theUser)
	{
		myDatabase = theDatabase;
		myGroup = theGroup;
		myUser = theUser;
		
		this.constructJFrame();
		this.setJFrameDetails();
	}
	
	private void constructJFrame() {
		myFriendsTable = createFriendsTable();
		myThreadsTable = createThreadsTable();
		
		JPanel tablePanel = new JPanel();
		tablePanel.add(new JScrollPane(myFriendsTable));
		tablePanel.add(new JScrollPane(myThreadsTable));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(createAddMemberButton());
		buttonPanel.add(createViewMemberButton(myGroup));
		buttonPanel.add(createViewGroupThreadButton(myGroup));
		
		this.add(new JLabel("Group Name: " + myGroup.getGroupName()), BorderLayout.NORTH);
		this.add(tablePanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	private void setJFrameDetails() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		ImageIcon titleImage = new ImageIcon("Images/wsu_logo.png");
		
		this.setIconImage(titleImage.getImage());
		this.setTitle("Private Group");
		this.pack();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setVisible(true);
	}
	
	private JTable createFriendsTable() {
		myFriendsModel = new DefaultTableModel();
		JTable friendsTable = new JTable(myFriendsModel);
		List<GroupMember> members = myDatabase.getGroupMembers(myGroup);
		
		myFriendsModel.addColumn("Group Members");
		
		for (GroupMember member: members) {
			myFriendsModel.insertRow(0, new Object[] {member.getUsername()});
		}
		
		return friendsTable;
	}
	
	private JTable createThreadsTable()
	{
		DefaultTableModel threadsTableModel = new DefaultTableModel();
		JTable threadsTable = new JTable(threadsTableModel);
		myThreads = myDatabase.getGroupThreads(myGroup);
		
		threadsTableModel.addColumn("Thread Name");
		threadsTableModel.addColumn("Thread Creator");
		threadsTableModel.addColumn("Thread ID");
		threadsTableModel.addColumn("Thread Status");
		
		if (myThreads.size() == 0) {
			threadsTableModel.insertRow(0, new Object[] {"To display", " ", ""});
			threadsTableModel.insertRow(0, new Object[] {"No Threads", " ", ""});
			
		} else {
			for (GroupThread thread: myThreads) {
				threadsTableModel.insertRow(0, new Object[] {thread.getTitle(), thread.getThreadCreator(), thread.getThreadID()});
			}
		}
		
		return threadsTable;
	}
	
	private JButton createAddMemberButton() {
		JButton addMemberButton = new JButton("Add Member");
		addMemberButton.addActionListener(addMemberButtonAction());
		
		return addMemberButton;
	}
	
	private ActionListener addMemberButtonAction() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddMemberScreen(myFriendsModel, myDatabase, myGroup);
			}
		};
	}
	
	private JButton createViewMemberButton(Group theGroup) {
		JButton viewMemberButton = new JButton("View Member Profile");
		viewMemberButton.addActionListener(viewMemberButtonAction());
		
		return viewMemberButton;
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
		JButton viewGroupThreadButton = new JButton("View Group Thread");
		viewGroupThreadButton.addActionListener(viewGroupThreadButtonAction(theGroup));
		
		return viewGroupThreadButton;
	}
	
	private ActionListener viewGroupThreadButtonAction(Group theGroup) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int cell = myThreadsTable.getSelectedRow();
				
				if (cell == -1) {
					JOptionPane.showMessageDialog(null, "Must select thread to view!");
				} else if (myThreads.size() == 0){
					JOptionPane.showMessageDialog(null, "No Threads to view!");
				}
				else {
					VotingThread tempThread = myDatabase.getThread((String) myThreadsTable.getModel().getValueAt(cell, 1), 
							(int) myThreadsTable.getModel().getValueAt(cell, 2));
					new CurrentThreadScreen(tempThread,myDatabase,  myUser);
				}
			}
		};
	}
}


