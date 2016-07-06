package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EditGame extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtTitle;
	private static int sliderValue = 50;
	private static int id;
	private static String title;
	private static int index = 0;
	private static int finished = 0;
	private static int replay = 0;
	private static int gameId;
	/**
	 * Launch the application.
	 */
	public static void main(int _id, String _title) {
		id = _id;
		title = _title;
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "cam", "cam123");
			PreparedStatement myQ = con.prepareStatement("select * from gamelib where userid = ? and title = ?");
			myQ.setInt(1, id);
			myQ.setString(2, title);
			ResultSet result = myQ.executeQuery();
			while(result.next()){
				gameId = result.getInt("id");
				//{"", "Action", "Adventure", "Fighting", "First Person Shooter", "Platforming", "Racing", "Real Time Strategy", "Role Playing Game", "Turn Based Strategy", "Simulation", "Sports", "Other"
				switch(result.getString("genre")){
					case "Action": 
						index = 1;
						break;
					case "Adventure":
						index = 2;
						break;
					case "Fighting":
						index = 3;
						break;
					case "First Person Shooter":
						index = 4;
						break;
					case "Platforming":
						index = 5;
						break;
					case "Racing":
						index = 6;
						break;
					case "Real Time Strategy":
						index = 7;
						break;
					case "Role Playing Game":
						index = 8;
						break;
					case "Turn Based Strategy":
						index = 9;
						break;
					case "Simulation":
						index = 10;
						break;
					case "Sports":
						index = 11;
						break;
					case "Other":
						index = 12;
						break;
					case "":
						index = 0;
						break;
				}
				
				sliderValue = result.getInt("rating");
				finished = result.getInt("finished");
				replay = result.getInt("replay");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		try {
			EditGame dialog = new EditGame();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setModal(true);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setId(int _id){
		id = _id;
	}
	/**
	 * Create the dialog.
	 */
	public EditGame() {
		setResizable(false);
		setTitle("Edit Game");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 332, 214);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(null);
		
		txtTitle = new JTextField();
		txtTitle.setBounds(66, 11, 200, 20);
		contentPanel.add(txtTitle);
		txtTitle.setColumns(10);
		txtTitle.setText(title);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "Action", "Adventure", "Fighting", "First Person Shooter", "Platforming", "Racing", "Real Time Strategy", "Role Playing Game", "Turn Based Strategy", "Simulation", "Sports", "Other"}));
		comboBox.setBounds(66, 42, 200, 20);
		contentPanel.add(comboBox);
		comboBox.setSelectedIndex(index);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(10, 14, 46, 14);
		contentPanel.add(lblTitle);
		
		JLabel lblGenre = new JLabel("Genre");
		lblGenre.setBounds(10, 45, 46, 14);
		contentPanel.add(lblGenre);
		
		JCheckBox chckbxPlayAgain = new JCheckBox("Play Again?");
		chckbxPlayAgain.setEnabled(false);
		chckbxPlayAgain.setBounds(105, 69, 97, 23);
		contentPanel.add(chckbxPlayAgain);
		if(replay == 1){
			chckbxPlayAgain.setSelected(true);
			chckbxPlayAgain.setEnabled(true);
		}
		
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
		contentPanel.add(chckbxCompleted);
		if(finished == 1){
			chckbxCompleted.setSelected(true);
		}
		
		JLabel lblEnjoyment = new JLabel("Enjoyment 50%");
		lblEnjoyment.setBounds(10, 99, 93, 14);
		contentPanel.add(lblEnjoyment);
		
		JSlider slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				sliderValue = slider.getValue();
				lblEnjoyment.setText("Enjoyment " + sliderValue + "%");
			}
		});
		slider.setMajorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setValue(sliderValue);
		slider.setBounds(115, 99, 200, 23);
		contentPanel.add(slider);
		
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
						JOptionPane.showMessageDialog(contentPanel, "Please select a genre");
						return;
					}
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "cam", "cam123");
					//INSERT INTO `database`.`gamelib` (`id`, `userid`, `title`, `genre`, `rating`, `finished`, `replay`) VALUES ('1', '10', 'test', 'Action', '50', '1', '1');

					PreparedStatement myStmt = con.prepareStatement("UPDATE `database`.`gamelib` SET `title`= ?, `genre`= ?, `rating`= ?, `finished`= ?, `replay`= ? WHERE `id`=?");
					myStmt.setInt(6, gameId);
					myStmt.setString(1, txtTitle.getText());
					myStmt.setString(2, (String)comboBox.getSelectedItem());
					myStmt.setInt(3, slider.getValue());
					myStmt.setInt(4, finished);
					myStmt.setInt(5, replay);
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
		contentPanel.add(btnOk);
		
	}

}
