package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.DatabaseObject;
import objects.RegisteredUser;

public class ViewProfileScreen extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private RegisteredUser myUser;
	
	private JTextField myUsernameField;
	private JTextField myNameField;
	private JTextField myEmailField;
	private JTextField myBirthdayField;
	
	private JButton myEdit;
	private JButton myExit;
	
	private boolean myEditing;
	private DatabaseObject myDatabase;
	
	public ViewProfileScreen(RegisteredUser user, DatabaseObject theDatabase) {
		
		myDatabase = theDatabase;
		myEditing = false;
		myUser = user;
		myUsernameField = new JTextField();
		myNameField = new JTextField();
		myEmailField = new JTextField();
		myBirthdayField = new JTextField();
		
		myEdit = new JButton("Edit");
		myExit = new JButton("Exit");
		
		myEdit.addActionListener(editListener());
		myExit.addActionListener(exitListener());
		this.add(new JLabel("Your Profile"), BorderLayout.NORTH);
		
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
		userInfoPanel.add(myEdit);
		userInfoPanel.add(myExit);
		
		this.add(userInfoPanel, BorderLayout.CENTER);
		
		this.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
        this.setVisible(true);
	}
	
	private ActionListener editListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!myEditing) {
					myEditing = true;
					myNameField.setEditable(true);
					myEmailField.setEditable(true);
					myBirthdayField.setEditable(true);
					
					myEdit.setText("Save");
					myExit.setText("Cancel");
				} else {
					myEditing = false;
					myUser.setName(myNameField.getText());
					myUser.setEmail(myEmailField.getText());
					myUser.setBirthday(myBirthdayField.getText());
					
					myDatabase.updateRegisteredUser(myUser);
					
					myNameField.setEditable(false);
					myEmailField.setEditable(false);
					myBirthdayField.setEditable(false);
					
					myEdit.setText("Edit");
					myExit.setText("Exit");
				}

			}

		};
	}
	
	private ActionListener exitListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (myEditing) {
					myEditing = false;
					myNameField.setText(myUser.getName());
					myEmailField.setText(myUser.getEmail());
					myBirthdayField.setText(myUser.getBirthday());
					
					myNameField.setEditable(false);
					myEmailField.setEditable(false);
					myBirthdayField.setEditable(false);
					
					myEdit.setText("Edit");
					myExit.setText("Exit");
				} else {
					dispose();
				}
			}
		};
	}
}
