package gui;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.sql.*
;public class AddGameWindow extends JFrame {

	private JPanel contentPane;
	private JTextField txtTitle;
	private  int sliderValue = 50;
	static private int id;
	static private String username;

	/**
	 * Launch the application.
	 */
	public static void main(int _id) {
		id = _id;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddGameWindow frame = new AddGameWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public int getId(){
		return id;
	}
	/**
	 * Create the frame.
	 */
	public AddGameWindow(){
		setResizable(false);
		setTitle("Add Game");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 332, 214);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtTitle = new JTextField();
		txtTitle.setBounds(66, 11, 200, 20);
		contentPane.add(txtTitle);
		txtTitle.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "Action", "Adventure", "Fighting", "First Person Shooter", "Platforming", "Racing", "Real Time Strategy", "Role Playing Game", "Turn Based Strategy", "Simulation", "Sports", "Other"}));
		comboBox.setBounds(66, 42, 200, 20);
		contentPane.add(comboBox);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(10, 14, 46, 14);
		contentPane.add(lblTitle);
		
		JLabel lblGenre = new JLabel("Genre");
		lblGenre.setBounds(10, 45, 46, 14);
		contentPane.add(lblGenre);
		
		JCheckBox chckbxPlayAgain = new JCheckBox("Play Again?");
		chckbxPlayAgain.setEnabled(false);
		chckbxPlayAgain.setBounds(105, 69, 97, 23);
		contentPane.add(chckbxPlayAgain);
		
		JCheckBox chckbxCompleted = new JCheckBox("Completed");
		chckbxCompleted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxCompleted.isSelected()){
					chckbxPlayAgain.setEnabled(true);
				}
				else{
					chckbxPlayAgain.setEnabled(false);
				}
			}
		});
		chckbxCompleted.setBounds(6, 69, 97, 23);
		contentPane.add(chckbxCompleted);
		
		JLabel lblEnjoyment = new JLabel("Enjoyment 50%");
		lblEnjoyment.setBounds(10, 99, 93, 14);
		contentPane.add(lblEnjoyment);
		
		JSlider slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				sliderValue = slider.getValue();
				lblEnjoyment.setText("Enjoyment " + sliderValue + "%");
			}
		});
		slider.setMajorTickSpacing(10);
		slider.setPaintTicks(true);
		
		slider.setBounds(115, 99, 200, 23);
		contentPane.add(slider);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					int finished = 0;
					int replay = 0;
					if(chckbxCompleted.isSelected()){
						finished = 1;
						if(chckbxPlayAgain.isSelected()){
							replay = 1;
						}
					}
					if(comboBox.getSelectedIndex() == 0){
						JOptionPane.showMessageDialog(contentPane, "Please select a genre");
						return;
					}
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "cam", "cam123");
					PreparedStatement myQ = con.prepareStatement("select * from gamelib where userid = ? and title = ?");
					myQ.setInt(1, id);
					myQ.setString(2, txtTitle.getText());
					ResultSet result = myQ.executeQuery();
					if(result.next()){
						JOptionPane.showMessageDialog(contentPane, "Game already in library");
						return;
					}
					//INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country)
					//VALUES ('Cardinal','Tom B. Erichsen','Skagen 21','Stavanger','4006','Norway');
					//INSERT INTO `database`.`gamelib` (`id`, `userid`, `title`, `genre`, `rating`, `finished`, `replay`) VALUES ('1', '10', 'test', 'Action', '50', '1', '1');

					PreparedStatement myStmt = con.prepareStatement("INSERT INTO `database`.`gamelib` (`userid`, `title`, `genre`, `rating`, `finished`, `replay`) VALUES (?,?,?,?,?,?)");
					myStmt.setInt(1, id);
					myStmt.setString(2, txtTitle.getText());
					myStmt.setString(3, (String)comboBox.getSelectedItem());
					myStmt.setInt(4, slider.getValue());
					myStmt.setInt(5, finished);
					myStmt.setInt(6, replay);
					myStmt.executeUpdate();
					dispose();
					
				}
				catch(Exception addError)
				{
					addError.printStackTrace();
				}
			}
		});
		btnOk.setBounds(10, 142, 89, 23);
		contentPane.add(btnOk);
		
		
	}
}
