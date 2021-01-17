/**
 * 
 */
package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import objects.Candidate;
import objects.RegisteredUser;
import objects.VotingThread;

import database.DatabaseObject;

public class CurrentThreadScreen extends JFrame {
	
	private static final long serialVersionUID = -3203442531577148798L;
	private VotingThread myThread;
	private DatabaseObject myDatabase;
	private List<JRadioButton> myCandidates;
	private RegisteredUser myUser;
	
	public CurrentThreadScreen(VotingThread theThread, DatabaseObject thedatabase, RegisteredUser theUser) {
		myThread = theThread;
		myDatabase = thedatabase;
		myUser = theUser;
		constructJFrame();
		setJFrameDetails();
		
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
	
	private void setJFrameDetails() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		ImageIcon titleImage = new ImageIcon("Images/wsu_logo.png");
		
		this.setIconImage(titleImage.getImage());
		this.setTitle("View Thread");
		this.pack();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setVisible(true);
	}
	
	private JButton createCancelButton() {
		JButton cancelButton = new JButton("Cancel Vote");
		cancelButton.addActionListener(cancelButtonAction());
		
		return cancelButton;
	}
	
	private ActionListener cancelButtonAction() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		};
	}
	
	private JButton createVoteButton() {
		JButton voteButton;
		
		if (myDatabase.checkIfUserVoted(myUser, myThread) || myThread.getOpenStatus().equals("FALSE")) {
			voteButton = new JButton("View Results");
			voteButton.addActionListener(viewResultsAction());
		} else {
			voteButton = new JButton("Submit Vote");
			voteButton.addActionListener(voteButtonAction());
		}

		return voteButton;
	}
	
	private ActionListener voteButtonAction() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String candidate;
				
				for (int i = 0; i < myCandidates.size(); i++) {
					if (myCandidates.get(i).isSelected()) {
						candidate = myCandidates.get(i).getText();
						myDatabase.updateCandidate(candidate, myThread);
						myDatabase.addVote(myUser, myThread);
						dispose();
					}
				}
			}
		};
	}
	
	private ActionListener viewResultsAction() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ThreadResultScreen(myDatabase, myThread);
			}
		};
	}
}
