package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;

import database.DatabaseObject;
import objects.Candidate;
import objects.Group;
import objects.GroupMember;
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
		
		//this.add(new JLabel("Group Name: " + myGroup.getGroupName()), BorderLayout.NORTH);
		this.add(tablePanel, BorderLayout.CENTER);
		this.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
        this.setVisible(true);
	}
	
	private JTable createFriendsTable()
	{
		String[] temp = {"Group Members"};
		String[][] data = getFriendsData();
		JTable tempTable = new JTable();
		
		return tempTable;
	}
	
	private String[][] getFriendsData()
	{
		List<GroupMember> members = myDatabase.getGroupMembers(myGroup);
		
		String[][] n = new String[members.size()][1];
		
		int j = 0;
		
		for (GroupMember member: members) {
			n[j][0] = member.getUsername();
			j++;
		}
		
		return n;
	}
	
	private String[][] getThreadData()
	{
		List<VotingThread> threads = myDatabase.getGroupThreads(myGroup);
		
		if (threads.size() == 0) {
			String[][] n = {{"No Threads", "zero"}, {"To display", "hero"}};
			return n;
		}
		
		String[][] n = new String[threads.size()][1];
		
		int j = 0;
		
		for (VotingThread thread: threads) {
			n[j][0] = thread.getTitle();
			j++;
		}
		
		return n;
	}
	
	private JTable createThreadsTable()
	{
		String[] temp = {"Thread Name", "Thread Creator"};
		String[][] data = getThreadData();
		JTable tempTable = new JTable();
		
		return tempTable;
	}

}
