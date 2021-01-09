package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.DatabaseObject;
import objects.RegisteredUser;

public class MemberProfileScreen extends JFrame {
	private RegisteredUser myUser;
	
	private JTextField myUsernameField;
	private JTextField myNameField;
	private JTextField myEmailField;
	private JTextField myBirthdayField;
	
	public MemberProfileScreen(RegisteredUser user) {

		myUser = user;
		myUsernameField = new JTextField();
		myNameField = new JTextField();
		myEmailField = new JTextField();
		myBirthdayField = new JTextField();
		
		this.add(new JLabel("Member Profile"), BorderLayout.NORTH);
		
		myUsernameField.setText(myUser.getUser());
		myNameField.setText(myUser.getName());
		myEmailField.setText(myUser.getEmail());
		myBirthdayField.setText(myUser.getBirthday());
		
		myUsernameField.setEditable(false);
		myNameField.setEditable(false);
		myEmailField.setEditable(false);
		myBirthdayField.setEditable(false);
		
		JTextField userSinceField = new JTextField(user.getUserSince().toString());
		userSinceField.setEditable(false);
		
		JPanel userInfoPanel = new JPanel();
		GridLayout grid = new GridLayout(6, 2);
		userInfoPanel.setLayout(grid);
		userInfoPanel.add(new JLabel("Username:"));
		userInfoPanel.add(myUsernameField);
		userInfoPanel.add(new JLabel("Name:"));
		userInfoPanel.add(myNameField);
		userInfoPanel.add(new JLabel("Email:"));
		userInfoPanel.add(myEmailField);
		userInfoPanel.add(new JLabel("Birthday:"));
		userInfoPanel.add(myBirthdayField);
		userInfoPanel.add(new JLabel("User since:"));
		userInfoPanel.add(userSinceField);
		
		this.add(userInfoPanel, BorderLayout.CENTER);
		
		this.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
        this.setVisible(true);
	}
}
