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
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import database.DatabaseObject;
import objects.Candidate;
import objects.VotingThread;

public class CurrentUserThreadScreen extends JFrame{

	private static final long serialVersionUID = 2840786334510556222L;
	private VotingThread myThread;
	private DatabaseObject myDatabase;
	private List<JRadioButton> myCandidates;
	
	public CurrentUserThreadScreen(VotingThread theThread, DatabaseObject thedatabase) {
		myThread = theThread;
		myDatabase = thedatabase;
		
		this.constructJFrame();
		this.setJFrameDetails();
	}
	
	private void constructJFrame() {
		GridLayout mainGrid = new GridLayout(myThread.getNumCandidates() + 3, 2);
		JButton viewResultsButton = createViewResultsButton();
		JButton cancelButton = createCancelButton();
		ButtonGroup group = new ButtonGroup();
		List<Candidate> candidates = myDatabase.getThreadCandidates(myThread);
		myCandidates= new ArrayList<JRadioButton>();
		
		this.setLayout(mainGrid);
		this.add(new JLabel("Title: "));
		this.add(new JLabel(myThread.getTitle()));
		this.add(new JLabel("Description: "));
		this.add(new JLabel(myThread.getDescription()));
		
		for(int i = 0; i < candidates.size(); i++) {
			if (candidates.get(i).getDescription() != "") {
				JRadioButton candidateButton = new JRadioButton(candidates.get(i).getDescription());
				
				group.add(candidateButton);
				myCandidates.add(candidateButton);
				this.add(new JLabel("Candidate #" + (i + 1)));
				this.add(candidateButton);
			}
		}
		
		this.add(viewResultsButton);
		this.add(cancelButton);
	}
	
	private void setJFrameDetails() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		ImageIcon titleImage = new ImageIcon("Images/wsu_logo.png");
		
		this.setTitle("View My Thread");
		this.setIconImage(titleImage.getImage());
		this.pack();
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
	
	private JButton createViewResultsButton() {
		JButton viewResultsButton;
		
		if (myDatabase.checkIfThreadIsOpen(myThread)) {
			viewResultsButton = new JButton("Close Voting");
			viewResultsButton.addActionListener(closeVotingButtonAction());
		} else {
			viewResultsButton = new JButton("View Results");
			viewResultsButton.addActionListener(viewResultsAction());
		}

		
		return viewResultsButton;
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
	
	private ActionListener viewResultsAction() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ThreadResultScreen(myDatabase, myThread);
				dispose();
			}
		};
	}
}
