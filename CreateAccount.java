package gui;

import java.awt.EventQueue;
import java.sql.*;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateAccount {

	private JFrame frmSteamChoice;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateAccount window = new CreateAccount();
					window.frmSteamChoice.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CreateAccount() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSteamChoice = new JFrame();
		frmSteamChoice.setResizable(false);
		frmSteamChoice.setTitle("Steam Choice");
		frmSteamChoice.setBounds(100, 100, 300, 170);
		frmSteamChoice.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSteamChoice.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(114, 11, 160, 20);
		frmSteamChoice.getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(114, 42, 160, 20);
		frmSteamChoice.getContentPane().add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(114, 73, 160, 20);
		frmSteamChoice.getContentPane().add(passwordField_1);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(10, 14, 94, 14);
		frmSteamChoice.getContentPane().add(lblUsername);
		
		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					//jdbc:mysql://hostname:port/databasename
				
				
					String username = textField.getText();
					char[] password = passwordField.getPassword();
					char[] passCon = passwordField_1.getPassword();
					if(username.length() == 0){
						JOptionPane.showMessageDialog(frmSteamChoice, "No username entered");
						return;
					}
					if(password.length == 0){
						JOptionPane.showMessageDialog(frmSteamChoice, "No password entered");
						return;
					}
					
					if(!Arrays.equals(password, passCon)){
						JOptionPane.showMessageDialog(frmSteamChoice,"Passwords do not match");
						return;
					}
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "cam", "cam123");
					PreparedStatement myStmt = con.prepareStatement("select * from accounts where username = ?");
					myStmt.setString(1, username);
					ResultSet result = myStmt.executeQuery();
					while(result.next()){
						if(result.getString("username").equals(username)){
							JOptionPane.showMessageDialog(frmSteamChoice, "Username already taken");
							return;
						}
					}
					myStmt.clearBatch();
					myStmt = con.prepareStatement("INSERT INTO `database`.`accounts` (`username`, `password`) VALUES (?, ?)");
					myStmt.setString(1, username);
					myStmt.setString(2, new String(password));
					@SuppressWarnings("unused")
					int set = myStmt.executeUpdate();
					JOptionPane.showMessageDialog(frmSteamChoice, "Account creation successful");
					frmSteamChoice.dispose();
					Login.main(null);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
				
			}
		});
		btnCreateAccount.setBounds(10, 104, 133, 23);
		frmSteamChoice.getContentPane().add(btnCreateAccount);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSteamChoice.dispose();
				Login.main(null);
			}
		});
		btnCancel.setBounds(153, 104, 89, 23);
		frmSteamChoice.getContentPane().add(btnCancel);
		
		JLabel lblNewLabel = new JLabel("Password");
		lblNewLabel.setBounds(10, 45, 94, 14);
		frmSteamChoice.getContentPane().add(lblNewLabel);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setBounds(10, 76, 104, 14);
		frmSteamChoice.getContentPane().add(lblConfirmPassword);
	}

}
