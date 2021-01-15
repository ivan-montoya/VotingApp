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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.DatabaseObject;
import objects.Group;
import objects.GroupMember;
import objects.RegisteredUser;

public class CreateGroupScreen extends JFrame {

	private static final long serialVersionUID = 7284166146613755476L;
	private JTextField myName;
	private JPanel myPanel;
	private List<JTextField> myMembers;
	private DatabaseObject myDatabase;
	private RegisteredUser myUsername;
	
	public CreateGroupScreen(DatabaseObject theDatabase, RegisteredUser theUser) {
		myDatabase = theDatabase;
		myUsername = theUser;
		myPanel = new JPanel();
		myMembers = new LinkedList<JTextField>();
		
		this.constructJFrame();
		this.setJFrameDetails();
	}
	
	private void constructJFrame() {
		JPanel namePanel = createNamePanel();
		JTextField memberField = new JTextField(25);
		myMembers.add(memberField);
		
		JPanel optionPanel = new JPanel(new FlowLayout());
		optionPanel.add(new JLabel("Member 1:"));
		optionPanel.add(memberField);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2));
		buttonPanel.add(addMemberButton());
		buttonPanel.add(createGroupButton());
		
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));
		myPanel.add(namePanel);
		myPanel.add(optionPanel);
		
		this.add(myPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	private void setJFrameDetails() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		ImageIcon titleImage = new ImageIcon("Images/wsu_logo.png");
		
		this.setIconImage(titleImage.getImage());
		this.setTitle("Create Private Group");
		this.pack();
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
	
	public JButton addMemberButton() {
		JButton addMemberButton = new JButton("Add Member");
		addMemberButton.addActionListener(addMemberAction());
		
		return addMemberButton;
	}
	
	private ActionListener addMemberAction() {
		return  new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField memberField = new JTextField(25);
				myMembers.add(memberField);
				
				JPanel optionPanel = new JPanel(new FlowLayout());
				optionPanel.add(new JLabel("Member " + myMembers.size() + ":"));
				
				optionPanel.add(memberField);
				myPanel.add(optionPanel);
				
				revalidate();
				pack();
			}
		};
	}

	private JButton createGroupButton() {
		JButton createGroupButton = new JButton("Create Private Group");
		createGroupButton.addActionListener(createGroupAction());
		
		return createGroupButton;
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
