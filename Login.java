package gui;

import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frmSteamChoice;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSteamChoice = new JFrame();
		frmSteamChoice.setResizable(false);
		frmSteamChoice.setTitle("Steam Choice Login");
		frmSteamChoice.setBounds(100, 100, 321, 140);
		frmSteamChoice.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSteamChoice.getContentPane().setLayout(null);
		
		JButton btnLogin = new JButton("Login");
		frmSteamChoice.getRootPane().setDefaultButton(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = textField.getText();
				char[] password = passwordField.getPassword();
				if(username.length() == 0){
					JOptionPane.showMessageDialog(frmSteamChoice, "Please enter username");
					return;
				}
				if(password.length == 0){
					JOptionPane.showMessageDialog(frmSteamChoice, "Please enter password");
					return;
				}
				try{
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "cam", "cam123");
				PreparedStatement myStmt = con.prepareStatement("select * from accounts where username = ?");
				myStmt.setString(1, username);
				ResultSet r = myStmt.executeQuery();
				while(r.next()){
					if(r.getString("password").equals(new String(password))){
						JOptionPane.showMessageDialog(frmSteamChoice, "Login Success");
						frmSteamChoice.dispose();
						MainScreen.main(username, Integer.parseInt(r.getString("id")));
					}
					else
					{
						JOptionPane.showMessageDialog(frmSteamChoice, "Incorrect Username/Password");
					}
				
				}
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
				
			}
		});
		btnLogin.setBounds(20, 70, 89, 23);
		frmSteamChoice.getContentPane().add(btnLogin);
		
		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSteamChoice.dispose();
				CreateAccount.main(null);
			}
		});
		btnCreateAccount.setBounds(140, 70, 154, 23);
		frmSteamChoice.getContentPane().add(btnCreateAccount);
		
		textField = new JTextField();
		textField.setBounds(96, 11, 198, 20);
		frmSteamChoice.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(10, 14, 76, 14);
		frmSteamChoice.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 45, 76, 14);
		frmSteamChoice.getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(96, 42, 198, 20);
		frmSteamChoice.getContentPane().add(passwordField);
	}
}
