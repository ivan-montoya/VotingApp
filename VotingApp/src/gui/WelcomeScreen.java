/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;

import javax.swing.*;

import objects.RegisteredUser;
import database.DatabaseObject;

public class WelcomeScreen extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	/**Holds Register account components.*/
	private JPanel myRegisterPanel;
	/**Holds Log in components*/
	private JPanel myLogInPanel;
	/**Holds Register Button.*/
	private JButton myRegisterButton;
	/**Holds Log in button*/
	private JButton myLogButton;
	/**Text field that holds new user name.*/
	private JTextField myRegisterAccount;
	/**Text field that holds new Password.*/
	private JPasswordField myRegisterPassword;
	/**Text field used to confirm Password is the same.*/
	private JPasswordField myConfirmPassword;
	/**Field used to find existing Account.*/
	private JTextField myLogAccount;
	/**Field used to get Password associated with account.*/
	private JPasswordField myLogPassword;
	
	private JCheckBox myRegisterCheckBox;
	
	private DatabaseObject myDatabase;

	public WelcomeScreen() {
		myRegisterPanel = new JPanel();
		myLogInPanel = new JPanel();
		
		myDatabase = new DatabaseObject();
		
		this.constructJFrame();
		this.setJFrameDetails();
	}
	
	/**
	 * Sets up components inside the JFrame as well as setting the JFrame
	 * settings.
	 */
	public void constructJFrame() {
		this.addWelcomeLabel();
		this.addRegisterPanel();
		this.addLogInPanel();
		
	}
	
	private void setJFrameDetails() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		ImageIcon titleImage = new ImageIcon("Images/wsu_logo.png");
		
		this.setIconImage(titleImage.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Log in");
		this.pack();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setResizable(false);
        this.setVisible(true);
	}
	
	private void addWelcomeLabel() {
		JLabel welcomeLabel = new JLabel("Welcome to the Voting App!");
		this.add(welcomeLabel, BorderLayout.NORTH);
		welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
		welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		welcomeLabel.setFont(new Font("TimesRoman", Font.BOLD, 15));
	}
	
	private void addRegisterPanel() {
		myRegisterAccount = new JTextField();
		myRegisterPassword = new JPasswordField();
		myConfirmPassword = new JPasswordField();
		
		// Sets up Register account components
		GridLayout grid = new GridLayout(4,2);
		
		myRegisterPanel.setLayout(grid);
		myRegisterPanel.add(new JLabel("Username:"));
		myRegisterAccount.setColumns(15);
		myRegisterPanel.add(myRegisterAccount);
		myRegisterPanel.add(new JLabel("Password:"));
		myRegisterPassword.setColumns(15);
		myRegisterPanel.add(myRegisterPassword);
		myRegisterPanel.add(new JLabel("Confirm Password:"));
		myConfirmPassword.setColumns(15);
		myRegisterPanel.add(myConfirmPassword);
		
		this.setRegisterButton();
		myRegisterPanel.add(myRegisterButton);
		
		this.setShowPasswordBox();
		myRegisterPanel.add(myRegisterCheckBox);
		
		this.add(myRegisterPanel, BorderLayout.WEST);
		myRegisterPanel.setBorder(BorderFactory.createTitledBorder("Register Account"));
	}
	
	/**
	 * Creates button used to register a new account. Here is where the program controls
	 * registering an account.
	 */
	public void setRegisterButton() {
		myRegisterButton = new JButton("Register Account");
		myRegisterButton.addActionListener( new ActionListener() {
			
			/**
			 * Checks if information is valid. If it is, then the information
			 * is saved in the database. If not, one of the error messages should display.
			 */
			public void actionPerformed(ActionEvent e) {
				// These statements just show messages to the user
				char[] registerPassword = myRegisterPassword.getPassword();
				char[] confirmPassword = myConfirmPassword.getPassword();
				
				if (myRegisterAccount.getText().length() < 6) {
					JOptionPane.showMessageDialog(null, "Username must contain at least 6 characters!");
				} else if (registerPassword.length < 8) {
					JOptionPane.showMessageDialog(null, "Password must be at least 8 characters!");
				} else if (!Arrays.equals(registerPassword, confirmPassword)) {
					JOptionPane.showMessageDialog(null, "Password fields must match!");
				} else if (myDatabase.hasRegisteredUser(myRegisterAccount.getText())) {
					JOptionPane.showMessageDialog(null, "Username is already being used!");
				} else {
					new NewAccountScreen(myRegisterAccount.getText(), new String (registerPassword), myDatabase);
					
					// Empties fields
					myRegisterAccount.setText(null);
					myRegisterPassword.setText(null);
					myConfirmPassword.setText(null);
				}
			}
		});
	}
	
	private void addLogInPanel() {
		myLogAccount = new JTextField();
		myLogPassword = new JPasswordField();
		
		// Sets up Log In components
		GridLayout logGrid = new GridLayout(3, 2);
		logGrid.setVgap(10);;
		myLogInPanel.setLayout(logGrid);
		myLogInPanel.add(new JLabel("Username:"));
		myLogAccount.setColumns(15);
		myLogInPanel.add(myLogAccount);
		
		myLogInPanel.add(new JLabel("Password:"));
		myLogPassword.setColumns(15);
		myLogInPanel.add(myLogPassword);
		
		this.setLogInButton();
		myLogInPanel.add(myLogButton); 
		
		this.add(myLogInPanel, BorderLayout.EAST);
		myLogInPanel.setBorder(BorderFactory.createTitledBorder("Log into Account"));
	}
	
	/**
	 * Creates button used to Log into account. This is where the program checks 
	 * the database for the account associated with the username and password.
	 */
	public void setLogInButton() {
		myLogButton = new JButton("Submit");
		myLogButton.addActionListener(logInAction()); 
	}
	
	private ActionListener logInAction() {
		return new  ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isCorrectLogin()) {
					JOptionPane.showMessageDialog(null, "Invalid Username or password");
				} else {
					new MainScreen(myDatabase.getRegisteredUser(myLogAccount.getText()), myDatabase);
					dispose();
				}
			}
		};
	}
	
	private void setShowPasswordBox() {
		myRegisterCheckBox = new JCheckBox("Show Password");
		myRegisterCheckBox.addItemListener(showPasswordBoxAction());
	}
	
	private ItemListener showPasswordBoxAction() {
		return new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		        if (e.getStateChange() != ItemEvent.SELECTED) {
		            myRegisterPassword.setEchoChar('*');
		            myConfirmPassword.setEchoChar('*');
		        } else {
		             myRegisterPassword.setEchoChar((char) 0);
		             myConfirmPassword.setEchoChar((char) 0);
		        }
		    }
		};
	}

	private boolean isCorrectLogin() {
		boolean isValidUser = false;
		RegisteredUser user = myDatabase.getRegisteredUser(myLogAccount.getText());

		if (user != null) {
			String account = user.getUser();
			char[] password = user.getPassword().toCharArray();
			
			if (myLogAccount.getText().equals(account) && Arrays.equals(password, myLogPassword.getPassword()))
				isValidUser = true;
		}
		
		return isValidUser;
	}
}
