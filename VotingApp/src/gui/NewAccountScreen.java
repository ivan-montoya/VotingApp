package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
		
		this.constructJFrame();
		this.setJFrameDetails();
	}
	
	private void constructJFrame() {
		
		JPanel centerPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		GridLayout layout = new GridLayout(3, 2);
		JButton registerButton = createRegisterButton();
		JButton cancelButton = createCancelButton();
		
		centerPanel.setLayout(layout);
		centerPanel.add(new JLabel("Name:"));
		centerPanel.add(myNameField);
		centerPanel.add(new JLabel("Email:"));
		centerPanel.add(myEmailField);
		centerPanel.add(new JLabel("Date of Birth:"));
		centerPanel.add(myDOBField);
		
		bottomPanel.add(registerButton);
		bottomPanel.add(cancelButton);
		
		this.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.add(bottomPanel, BorderLayout.SOUTH);
		

	}
	
	private void setJFrameDetails() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		ImageIcon titleImage = new ImageIcon("Images/wsu_logo.png");
		
		this.setIconImage(titleImage.getImage());
		this.setTitle("Register Account");
		this.pack();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setVisible(true);
	}
	
	private JButton createRegisterButton() {
		JButton registerButton = new JButton("Register Account");
		registerButton.addActionListener(registerButtonAction());
		
		return registerButton;
	}
	
	private boolean allFieldsFilled() {
		return !myNameField.getText().isEmpty() && !myEmailField.getText().isEmpty() && !myDOBField.getText().isEmpty();
	}
	
	private ActionListener registerButtonAction() {
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
		cancelButton.addActionListener(cancelButtonAction());
		
		return cancelButton;
	}
	
	private ActionListener cancelButtonAction() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		};
	}
}
