package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import database.DatabaseObject;
import objects.VotingThread;
import objects.Candidate;

public class ThreadResultScreen extends JFrame {
	
	private static final long serialVersionUID = 7897541678794270529L;
	private Map<JPanel, JProgressBar> myThreadComponents;
	private DatabaseObject myDatabase;
	private float myTotalVotes;
	private List<Candidate> myCandidates;
	
	public ThreadResultScreen(DatabaseObject theDatabase, VotingThread theThread) {
		int tempTotal = 0;
		
		myDatabase = theDatabase;
		myThreadComponents = new HashMap<JPanel, JProgressBar>();
		myCandidates = myDatabase.getThreadCandidates(theThread);
		
		for (int i = 0; i < myCandidates.size(); i++)
			tempTotal += myCandidates.get(i).getVotes();
		
		myTotalVotes = (float) tempTotal;
		
		this.setCandidates();
		this.constructJFrame();
		this.setJFrameDetails();
	}
	
	/**
	 * Sets up components for the JFrame.
	 */
	private void constructJFrame() {
		
		
		GridLayout grid = new GridLayout(myThreadComponents.size() + 1, 2, 5, 10);
		
		
		this.setLayout(grid);
		
		this.add(new JLabel("Total Votes:      " + (int) myTotalVotes ));
		this.add(new JLabel("Vote Percentage:"));
		
		for (JPanel title: myThreadComponents.keySet()) {
			this.add(title);
			this.add(myThreadComponents.get(title));
		}

	}
	
	private void setJFrameDetails() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		ImageIcon titleImage = new ImageIcon("Images/wsu_logo.png");
		
		this.setIconImage(titleImage.getImage());
		this.setTitle("Thread Results");
		this.pack();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setVisible(true);
	}
	
	private void setCandidates() {
		for (Candidate candidate: myCandidates) {
			JPanel candidatePanel = new JPanel();
			JLabel candidateLabel = new JLabel("Candidate:    " + candidate.getDescription());
			JLabel votesLabel = new JLabel("Votes:        " + candidate.getVotes());
			
			candidatePanel.setLayout(new GridLayout(2, 1));
			candidatePanel.add(candidateLabel);
			candidatePanel.add(votesLabel);
			candidatePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			myThreadComponents.put(candidatePanel, createProgressBar(candidate));
		}
	}
	
	private JProgressBar createProgressBar(Candidate theCandidate) {
		JProgressBar progressBar = new JProgressBar(0, (int) myTotalVotes);
		String percentage = Float.toString(Math.round((float) theCandidate.getVotes() / myTotalVotes * 10000) / 100.0f);
		
		progressBar.setValue(theCandidate.getVotes());
		progressBar.setStringPainted(true);
		progressBar.setString(percentage + "%");
		
		return progressBar;
	}

}
