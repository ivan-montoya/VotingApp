package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import objects.RegisteredUser;
import database.DatabaseObject;

public class NewAccountScreen extends JFrame {
	
	private static final long serialVersionUID = 201941430448803151L;
	private String myUsername;
	private String myPassword;
	private String myName;
	private String myEmail;
	private String myDOB;
	
	private JTextField myNameField;
	private JTextField myEmailField;
	private JTextField myDOBField;
	
	private DatabaseObject myDatabase;
	
	public NewAccountScreen(String username, String password, DatabaseObject database) {
		myUsername = username;
		myPassword = password;
		myDatabase = database;
		
		myNameField = new JTextField();
		myEmailField = new JTextField();
		myDOBField = new JTextField();
		
		constructJFrame();
	}
	
	private void constructJFrame() {
		
		JPanel centerPanel = new JPanel();
		GridLayout layout = new GridLayout(3, 2);
		centerPanel.setLayout(layout);
		
		centerPanel.add(new JLabel("Name:"));
		centerPanel.add(myNameField);
		centerPanel.add(new JLabel("Email:"));
		centerPanel.add(myEmailField);
		centerPanel.add(new JLabel("Date of Birth:"));
		centerPanel.add(myDOBField);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(createRegisterButton());
		bottomPanel.add(createCancelButton());
		
		this.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		this.setTitle("Register Account");
		
		this.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
        this.setVisible(true);
	}
	
	private JButton createRegisterButton() {
		JButton registerButton = new JButton("Register Account");
		registerButton.addActionListener(registerUserAction());
		
		return registerButton;
	}
	
	private boolean allFieldsFilled() {
		return !myNameField.getText().isEmpty() && !myEmailField.getText().isEmpty() && !myDOBField.getText().isEmpty();
	}
	
	private ActionListener registerUserAction() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (allFieldsFilled()) {
					JOptionPane.showMessageDialog(null, "Registered account.");
					
					myEmail = myEmailField.getText();
					myName = myNameField.getText();
					myDOB = myDOBField.getText();
					
					myDatabase.addRegisteredUser(new RegisteredUser(myUsername, myEmail, myPassword, myName, myDOB, 0));
					
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "All fields must be filled!");
				}
			}
		};
	}
	
	private JButton createCancelButton() {
		JButton cancelButton = new JButton("Cancel Registration");
		cancelButton.addActionListener(cancelAction());
		
		return cancelButton;
	}
	
	private ActionListener cancelAction() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		};
	}
}
