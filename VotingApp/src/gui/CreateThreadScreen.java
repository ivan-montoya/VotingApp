package gui;

import objects.Candidate;
import objects.Group;
import objects.RegisteredUser;
import objects.VotingThread;

import database.DatabaseObject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CreateThreadScreen extends JFrame {

	private static final long serialVersionUID = -8627110857885194937L;
	private JTextField myTitle;
	private JTextArea myDescription;
	
	private JPanel myPanel;
	private List<JTextField> myOptions;
	private JRadioButton myPrivateButton;
	private JRadioButton myPublicButton;
	
	private JComboBox myGroupsComboBox;
	private List<Group> myGroups;

	private DatabaseObject myDatabase;
	private RegisteredUser myUser;
	
	public CreateThreadScreen(RegisteredUser username, DatabaseObject database) {
		
		myDatabase = database;
		myUser = username;
		myPanel = new JPanel();
		myOptions = new LinkedList<JTextField>();
		this.constructJFrame();
		this.setJFrameDetails();
	}
	
	private void constructJFrame() {
		JPanel titlePanel = createTitlePanel();
		JPanel descriptionPanel = createDescriptionPanel();
		JPanel privatePanel = createPrivatePanel();
		JTextField optionField = new JTextField(25);
		
		JPanel optionPanel = new JPanel(new FlowLayout());
		optionPanel.add(new JLabel("Option 1:"));
		optionPanel.add(optionField);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2));
		buttonPanel.add(addOptionButton());
		buttonPanel.add(addCreateThreadButton());
		
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));
		myPanel.add(titlePanel);
		myPanel.add(descriptionPanel);
		myPanel.add(privatePanel);
		myGroupsComboBox = createGroupsBox();
		myPanel.add(myGroupsComboBox);
		myOptions.add(optionField);
		myPanel.add(optionPanel);
		
		this.add(myPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);

	}
	
	private void setJFrameDetails() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		ImageIcon titleImage = new ImageIcon("Images/wsu_logo.png");
		
		this.setIconImage(titleImage.getImage());
		this.setTitle("Create Thread");
		this.pack();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2); 
        this.setVisible(true);
	}
	
	private JPanel createPrivatePanel() {
		JPanel privatePanel = new JPanel();
		myPublicButton = new JRadioButton("Public");
		myPrivateButton = new JRadioButton("Private");
		ButtonGroup myGroup = new ButtonGroup();
		
		myPublicButton.addActionListener(publicButtonAction());
		myPrivateButton.addActionListener(privateButtonAction());
		
		myGroup.add(myPublicButton);
		myGroup.add(myPrivateButton);
		
		myPublicButton.setSelected(true);
		
		privatePanel.add(new JLabel("Privacy Status:"));
		privatePanel.add(myPublicButton);
		privatePanel.add(myPrivateButton);
		
		return privatePanel;
	}
	
	private ActionListener publicButtonAction() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (myPublicButton.isEnabled()) {
					myGroupsComboBox.setEnabled(false);
				}
			}
		};
	}
	
	private ActionListener privateButtonAction() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (myPrivateButton.isEnabled()) {
					myGroupsComboBox.setEnabled(true);
				}
			}
		};
	}
	
	private JComboBox createGroupsBox() {
		JComboBox groups = new JComboBox();
		
		myGroups = myDatabase.getPrivateGroups(myUser);
		
		if (myGroups.size() == 0) 
			groups.addItem("N/A");
		else {
			for (int i = 0; i < myGroups.size();i++) {
				groups.addItem(myGroups.get(i).getGroupName());
			}
		}		
		groups.setEnabled(false);
		
		return groups;
	}
	
	private JPanel createTitlePanel() {
		JPanel titlePanel = new JPanel();
		myTitle = new JTextField(35);
		titlePanel.add(new JLabel("Title:            ."));
		titlePanel.add(myTitle);
		
		return titlePanel;
	}
	
	private JPanel createDescriptionPanel() {
		JPanel descriptionPanel = new JPanel();
		myDescription = new JTextArea(3, 35);
		myDescription.setLineWrap(true);
		JScrollPane scroll = new JScrollPane (myDescription, 
				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		descriptionPanel.add(new JLabel("Description:"));
		descriptionPanel.add(scroll);
		
		return descriptionPanel;
	}

	public JButton addOptionButton() {
		JButton addOptionButton = new JButton("Add Option");
		addOptionButton.addActionListener(addOptionAction());
		
		return addOptionButton;
	}
	
	private ActionListener addOptionAction() {
		return  new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField tempField = new JTextField(25);
				myOptions.add(tempField);
				
				JPanel optionPanel = new JPanel(new FlowLayout());
				optionPanel.add(new JLabel("Option " + myOptions.size() + ":"));
				
				optionPanel.add(tempField);
				myPanel.add(optionPanel);
				
				revalidate();
				pack();
			}
		};
	}
	
	public JButton addCreateThreadButton() {
		JButton createButton = new JButton("Create Thread");
		createButton.addActionListener(createThreadAction());
		
		return createButton;
	}
	
	private ActionListener createThreadAction() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (hasValidCandidates()) {
					int ID = myDatabase.createID(myUser.getUser());
					// Iterates through Options and saves text
					for (int i = 0; i < myOptions.size(); i++) {
						String description = myOptions.get(i).getText();
						myDatabase.addCandidate(new Candidate(description, ID, myUser.getUser(), 0));
					}
					
					String privateStatus;
					
					if(myPrivateButton.isSelected())
						privateStatus = "TRUE";
					else
						privateStatus = "FALSE";
					
					VotingThread tempThread =  new VotingThread(myTitle.getText(), myDescription.getText(), myOptions.size(), ID,  myUser.getUser(), privateStatus);
					
					myDatabase.addVotingThread(tempThread);
					
					if (myPrivateButton.isSelected() && myGroups.size() != 0) {
						myDatabase.addGroupThread(tempThread, myGroups.get(myGroupsComboBox.getSelectedIndex()));
					}
					
					JOptionPane.showMessageDialog(null, "Thread Created.");
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "Error: Must have distinct options!");
				}
			}
		};
	}
	
	private boolean hasValidCandidates() {
		boolean isValid = true;
		
		for (int i = 0; i < myOptions.size(); i++) {
			String candidate1 = myOptions.get(i).getText();
			for(int j = i + 1; j < myOptions.size(); j++) {
				String candidate2 = myOptions.get(j).getText();
				if (candidate1.equals(candidate2)) {
					isValid = false;
				}
			}
		}
		
		return isValid;
	}
}
