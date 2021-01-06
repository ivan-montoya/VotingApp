package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import database.DatabaseObject;
import objects.VotingThread;
import objects.Candidate;

public class ThreadResultScreen extends JFrame {
	
	private static final long serialVersionUID = 7897541678794270529L;
	/**Used to create components for each Thread*/
	private Map<JPanel, JProgressBar> myThreadComponents;
	private DatabaseObject myDatabase;
	private float myTotalVotes;
	private List<Candidate> myCandidates;
	
	public ThreadResultScreen(DatabaseObject theDatabase, VotingThread theThread) {
		
		myDatabase = theDatabase;
		myThreadComponents = new HashMap<JPanel, JProgressBar>();
		
		myCandidates = myDatabase.getThreadCandidates(theThread);
		
		int tempTotal = 0;
		
		for (int i = 0; i < myCandidates.size(); i++)
			tempTotal += myCandidates.get(i).getVotes();
		
		myTotalVotes = (float) tempTotal;
		
		setThreads();
		
		start();
	}
	
	/**
	 * Sets up components for the JFrame.
	 */
	private void start() {
		
		
		GridLayout grid = new GridLayout(myThreadComponents.size() + 1, 2, 5, 10);
		
		
		this.setLayout(grid);
		
		this.add(new JLabel("Total Votes:      " + (int) myTotalVotes ));
		this.add(new JLabel("Vote Percentage:"));
		
		for (JPanel title: myThreadComponents.keySet()) {
			this.add(title);
			this.add(myThreadComponents.get(title));
		}
		this.setTitle("View Public Threads");
		

		
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
		for (Candidate candidate: myCandidates) {
			JPanel threadPanel = new JPanel();
			JLabel tempLabel = new JLabel("Candidate:    " + candidate.getDescription());
			JLabel tempLabel2 = new JLabel("Votes:        " + candidate.getVotes());
			
			threadPanel.setLayout(new GridLayout(2, 1));
			
			threadPanel.add(tempLabel);
			threadPanel.add(tempLabel2);
			
			threadPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			myThreadComponents.put(threadPanel, createProgressBar(candidate));
		}
	}
	
	private JProgressBar createProgressBar(Candidate theCandidate) {
		JProgressBar tempBar = new JProgressBar(0, (int) myTotalVotes);
		tempBar.setValue(theCandidate.getVotes());
		
		String percentage = Float.toString(Math.round((float) theCandidate.getVotes() / myTotalVotes * 10000) / 100.0f);
		
		tempBar.setStringPainted(true);
		
		tempBar.setString(percentage + "%");
		
		return tempBar;
	}

}
