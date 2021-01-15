package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import database.DatabaseObject;
import objects.Group;
import objects.GroupMember;

public class AddMemberScreen extends JFrame {

	private static final long serialVersionUID = 789352548694833481L;
	JTextField myUsername;
	JButton myButton;
	DatabaseObject myDatabase;
	Group myGroup;
	
	public AddMemberScreen(DefaultTableModel theModel, DatabaseObject theDatabase, Group theGroup) {
		myGroup = theGroup;
		myDatabase = theDatabase; 
		myButton = acceptButton(theModel);
		
		this.constructJFrame();
		this.setJFrameDetails();
	}
	
	private void constructJFrame() {
		myUsername = new JTextField(50);
		
		this.add(new JLabel("Username"), BorderLayout.NORTH);
		this.add(myUsername, BorderLayout.CENTER);
		this.add(myButton, BorderLayout.SOUTH);
	}
	
	private void setJFrameDetails() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		ImageIcon titleImage = new ImageIcon("Images/wsu_logo.png");
		
		this.setIconImage(titleImage.getImage());
		this.pack();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setVisible(true);
	}
	
	private JButton acceptButton(DefaultTableModel theModel) {
		JButton acceptButton = new JButton("Accept");
		acceptButton.addActionListener(acceptButtonAction(theModel));
		
		return acceptButton;
	}
	
	private ActionListener acceptButtonAction(DefaultTableModel theModel) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(myDatabase.hasRegisteredUser(myUsername.getText())) {
					theModel.insertRow(0, new Object[] {myUsername.getText()});
					myDatabase.addGroupMember(new GroupMember(myGroup.getGroupName(), myGroup.getAdminName(), myUsername.getText()));
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Error: User does not exist!");
				}
			}
		};
	}
}
