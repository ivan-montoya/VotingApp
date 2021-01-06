package gui;

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
import javax.swing.JRadioButton;

import database.DatabaseObject;
import objects.Candidate;
import objects.RegisteredUser;
import objects.VotingThread;

public class CurrentUserThreadScreen extends JFrame{
	
	VotingThread myThread;
	
	private DatabaseObject myDatabase;
	private List<JRadioButton> myCandidates;
	private RegisteredUser myUser;
	
	public CurrentUserThreadScreen(VotingThread theThread, DatabaseObject thedatabase, RegisteredUser theUser) {
		myThread = theThread;
		myDatabase = thedatabase;
		myUser = theUser;
		constructJFrame();
		setupScreenOptions();
		
	}
	
	private void constructJFrame() {
		GridLayout mainGrid = new GridLayout(myThread.getNumCandidates() + 3, 2);
		this.setLayout(mainGrid);
		this.add(new JLabel("Title: "));
		this.add(new JLabel(myThread.getTitle()));
		this.add(new JLabel("Description: "));
		this.add(new JLabel(myThread.getDescription()));
		
		ButtonGroup group = new ButtonGroup();
		
		List<Candidate> candidates = myDatabase.getThreadCandidates(myThread);
		myCandidates= new ArrayList<JRadioButton>();
		
		for(int i = 0; i < candidates.size(); i++) {
			if (candidates.get(i).getDescription() != "") {
				JRadioButton temp = new JRadioButton(candidates.get(i).getDescription());
				group.add(temp);
				myCandidates.add(temp);
				this.add(new JLabel("Candidate #" + (i + 1)));
				this.add(temp);
			}
		}
		
		this.add(createVoteButton());
		this.add(createCancelButton());
	}
	
	private void setupScreenOptions() {
		this.setTitle(myThread.getTitle());
		
		this.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
        this.setVisible(true);
	}
	
	private JButton createCancelButton() {
		JButton tempButton = new JButton("Cancel");
		tempButton.addActionListener(CancelButtonAction());
		
		return tempButton;
	}
	
	private ActionListener CancelButtonAction() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		};
	}
	
	private JButton createVoteButton() {
		JButton tempButton;
		if (myDatabase.checkIfThreadIsOpen(myThread)) {
			tempButton = new JButton("Close Voting");
			tempButton.addActionListener(closeVotingButtonAction());
		} else {
			tempButton = new JButton("View Results");
			//tempButton.addActionListener(voteButtonAction());
		}

		
		return tempButton;
	}
	
	private ActionListener closeVotingButtonAction() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myDatabase.closeVotingOnThread(myThread);
				JOptionPane.showMessageDialog(null, "Closed Voting for Thread.");
				dispose();
			}
		};
	}
}
