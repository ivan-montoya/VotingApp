package gui;

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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import database.DatabaseObject;
import objects.Group;
import objects.GroupMember;
import objects.RegisteredUser;

public class CreateGroupScreen extends JFrame {
	/* READ THIS! IMPORTANT
	 * 
	 * This class should save the information in
	 * the JTextFields inside a new Thread then
	 * pass that Thread to MainScreen.
	 */
	private JTextField myName;
	
	private JPanel myPanel;
	private JButton myCreateButton;
	
	private int myOptionsAmount;
	private List<JTextField> myMembers;
	private JRadioButton myPrivateButton;
	private JRadioButton myPublicButton;

	private DatabaseObject myDatabase;
	private RegisteredUser myUsername;
	
	public CreateGroupScreen(DatabaseObject theDatabase, RegisteredUser theUser) {
		
		myDatabase = theDatabase;
		myUsername = theUser;
		myPanel = new JPanel();
		myMembers = new LinkedList<JTextField>();
		createJFrame();
	}
	
	/**
	 * Sets up and starts JFrame Window.
	 */
	private void createJFrame() {
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));
		
		JPanel namePanel = createNamePanel();
		myPanel.add(namePanel);
		
		JPanel optionPanel = new JPanel(new FlowLayout());
		
		JTextField tempField = new JTextField(25);
		myMembers.add(tempField);
		optionPanel.add(new JLabel("Member 1:"));
		optionPanel.add(tempField);
		myPanel.add(optionPanel);
		
		// More Components
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2));
		buttonPanel.add(addMemberButton());
		buttonPanel.add(addCreateGroupButton());
		this.add(myPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.setTitle("Create Private Group");
		
		this.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
        this.setVisible(true);
	}
	
	private JPanel createNamePanel() {
		JPanel namePanel = new JPanel();
		myName = new JTextField(35);
		namePanel.add(new JLabel("Group Name:            "));
		namePanel.add(myName);
		
		return namePanel;
	}
	
	/**
	 * Sets necessary elements for the Add option button.
	 */
	public JButton addMemberButton() {
		JButton addOptionButton = new JButton("Add Member");
		addOptionButton.addActionListener(addMemberAction());
		
		return addOptionButton;
	}
	
	private ActionListener addMemberAction() {
		return  new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField tempField = new JTextField(25);
				myMembers.add(tempField);
				
				JPanel optionPanel = new JPanel(new FlowLayout());
				optionPanel.add(new JLabel("Member " + myMembers.size() + ":"));
				
				optionPanel.add(tempField);
				myPanel.add(optionPanel);
				
				revalidate();
				pack();
			}
		};
	}
	
	/**
	 * Sets up Create Thread button.
	 */
	public JButton addCreateGroupButton() {
		JButton createButton = new JButton("Create Private Group");
		createButton.addActionListener(createGroupAction());
		
		return createButton;
	}
	
	private ActionListener createGroupAction() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (hasValidMembers()) {
					
					myDatabase.addGroup(new Group(myName.getText(), myUsername.getUser()));
					
					for (int i = 0; i < myMembers.size(); i++) {
						String member = myMembers.get(i).getText();
						myDatabase.addGroupMember(new GroupMember(myName.getText(), myUsername.getUser(), member));
					}
					
					myDatabase.addGroupMember(new GroupMember(myName.getText(), myUsername.getUser(), myUsername.getUser()));

					
					JOptionPane.showMessageDialog(null, "Group Created.");
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "Error: Must have valid member names!");
				}

			}
		};
	}
	
	private boolean hasValidMembers() {
		boolean isValid = true;
		
		for (int i = 0; i < myMembers.size(); i++) {
			if (!myDatabase.hasRegisteredUser(myMembers.get(i).getText())) {
				isValid = false;
				break;
			}
		}
		
		return isValid;
	}

}
