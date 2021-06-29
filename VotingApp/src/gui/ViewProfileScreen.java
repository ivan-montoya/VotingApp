package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DateFormatter;

import database.DatabaseObject;
import objects.RegisteredUser;

public class ViewProfileScreen extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private RegisteredUser myUser;
	private JTextField myUsernameField;
	private JTextField myNameField;
	private JTextField myEmailField;
	private JFormattedTextField myBirthdayField;
	
	private JButton myEdit;
	private JButton myExit;
	
	private boolean myEditing;
	private DatabaseObject myDatabase;
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private static final String NAME_PATTERN = "^[A-Z][a-z]{2,}(?: [A-Z][a-z]*)*$";
	
	public ViewProfileScreen(RegisteredUser user, DatabaseObject theDatabase) {
		
		myDatabase = theDatabase;
		myEditing = false;
		myUser = user;
		
		this.constructJFrame();
		this.setJFrameDetails();
	}
	
	private void constructJFrame() {
		DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
		DateFormatter df = new DateFormatter(date);
		JPanel userInfoPanel = new JPanel();
		GridLayout grid = new GridLayout(6, 2);
		JTextField userSinceField = new JTextField(myUser.getUserSince().toString());
		
		myUsernameField = new JTextField();
		myNameField = new JTextField();
		myEmailField = new JTextField();
		myBirthdayField = new JFormattedTextField(df);
		
		myEdit = new JButton("Edit");
		myExit = new JButton("Exit");
		myEdit.addActionListener(editListener());
		myExit.addActionListener(exitListener());
		
		myUsernameField.setText(myUser.getUser());
		myNameField.setText(myUser.getName());
		myEmailField.setText(myUser.getEmail());
		myBirthdayField.setText(myUser.getBirthday());
		
		myUsernameField.setEditable(false);
		myNameField.setEditable(false);
		myEmailField.setEditable(false);
		myBirthdayField.setEditable(false);

		userSinceField.setEditable(false);
		
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
		
		this.add(new JLabel("Your Profile"), BorderLayout.NORTH);
		this.add(userInfoPanel, BorderLayout.CENTER);
	}
	
	private void setJFrameDetails() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		ImageIcon titleImage = new ImageIcon("Images/wsu_logo.png");
		
		this.setIconImage(titleImage.getImage());
		this.pack();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
        this.setVisible(true);
	}
	
	private boolean allFieldsFilled() {
		return !myNameField.getText().isEmpty() && !myEmailField.getText().isEmpty() && !myBirthdayField.getText().isEmpty();
	}
	
	private boolean hasValidName() {
		return myNameField.getText().matches(NAME_PATTERN);
	}
	
	private boolean hasValidEmail() {
		return myEmailField.getText().matches(EMAIL_PATTERN);
	}
	
	private boolean hasValidYear() {
		int year = Integer.parseInt(myBirthdayField.getText().substring(6));
		
		return  1900 <= year && year <= 2020;
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
					if (!allFieldsFilled())
						JOptionPane.showMessageDialog(null, "Cannot leave a field empty!");
					else if (!hasValidName())
						JOptionPane.showMessageDialog(null, "Must have valid name!");
					else if (!hasValidEmail())
						JOptionPane.showMessageDialog(null, "Must have valid email!");
					else if (!hasValidYear()) 
						JOptionPane.showMessageDialog(null, "Must have valid year of birth!");

					else {
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
