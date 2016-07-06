package gui;

import java.util.Random;
import java.sql.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JToolBar;
import javax.swing.ListModel;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowStateListener;
import java.awt.event.WindowEvent;

public class MainScreen {

	private JFrame frmSteamChoice;
	private static String username;
	private static int userid;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String _username, int _userid) {
		userid = _userid;
		username = _username;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen window = new MainScreen();
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
	public MainScreen() {
		initialize();
	}
	public void refreshTable(){
		DefaultTableModel col = new DefaultTableModel(new Object[][] {},new String[] {"Title", "Genre", "Rating", "Finished", "Play Again"}) {
			Class[] columnTypes = new Class[] {
					String.class, String.class, Integer.class, Boolean.class, Boolean.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
					false, false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return false;
				}
				
			};
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "cam", "cam123");
			PreparedStatement myQ = con.prepareStatement("select * from gamelib where userid = ?");
			myQ.setInt(1, userid);
			ResultSet result = myQ.executeQuery();
			while(result.next()){
				boolean replay = false;
				boolean finished = false;
				if(result.getInt("finished")==1){
					finished = true;
					if(result.getInt("replay") == 1){
						replay = true;
					}
				}
				col.addRow(new Object[]{result.getString("title"), result.getString("genre"), result.getInt("rating"), finished, replay});
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		table.setModel(col);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		frmSteamChoice = new JFrame();
		frmSteamChoice.addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent arg0) {
				refreshTable();
			}
		});
		frmSteamChoice.setResizable(false);
		frmSteamChoice.setTitle("Steam Choice");
		frmSteamChoice.setBounds(100, 100, 580, 299);
		frmSteamChoice.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmSteamChoice.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSteamChoice.dispose();
				Login.main(null);
			}
		});
		mnFile.add(mntmLogout);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnSync = new JMenu("Sync");
		menuBar.add(mnSync);
		
		JMenuItem mntmSyncLibrary = new JMenuItem("Sync Library");
		mntmSyncLibrary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshTable();
			}
		});
		mnSync.add(mntmSyncLibrary);
		
		JMenu mnAbout = new JMenu("Help");
		menuBar.add(mnAbout);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Created by Cameron McIlvaine \n This program is designed to store a library of games and help you decide what to play\n when you have too much or too little to chose from.");
			}
		});
		mnAbout.add(mntmAbout);
		frmSteamChoice.getContentPane().setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel("Hello, " + username);
		lblNewLabel.setBounds(10, 11, 124, 14);
		frmSteamChoice.getContentPane().add(lblNewLabel);		
		JButton btnNewButton = new JButton("Add Game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddGame game = new AddGame();
				game.setId(userid);
				game.main(userid);
				refreshTable();
				
			}
		});
		btnNewButton.setBounds(10, 36, 124, 57);
		frmSteamChoice.getContentPane().add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(144, 11, 420, 228);
		frmSteamChoice.getContentPane().add(scrollPane);
		DefaultTableModel col = new DefaultTableModel(new Object[][] {},new String[] {"Title", "Genre", "Rating", "Finished", "Play Again"}) {
			Class[] columnTypes = new Class[] {
					String.class, String.class, Integer.class, Boolean.class, Boolean.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
					false, false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return false;
				}
				
			};
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "cam", "cam123");
			PreparedStatement myQ = con.prepareStatement("select * from gamelib where userid = ?");
			myQ.setInt(1, userid);
			ResultSet result = myQ.executeQuery();
			while(result.next()){
				boolean replay = false;
				boolean finished = false;
				if(result.getInt("finished")==1){
					finished = true;
					if(result.getInt("replay") == 1){
						replay = true;
					}
				}
				col.addRow(new Object[]{result.getString("title"), result.getString("genre"), result.getInt("rating"), finished, replay});
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		table = new JTable();
		
		
		
		table.setRowSelectionAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);
		table.setRowSelectionAllowed(true);
		table.setModel(new DefaultTableModel(new Object[][] {},new String[] {"Title", "Genre", "Rating", "Finished", "Play Again"}) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, Integer.class, Boolean.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.setModel(col);
				
		JButton btnNewButton_1 = new JButton("Edit Game");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String title = (String) table.getValueAt(table.getSelectedRow(), 0); 
				EditGame.main(userid, title);
				refreshTable();
			}
		});
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.setBounds(10, 104, 124, 57);
		frmSteamChoice.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Choose Game");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frmSteamChoice, "You should play " + pickGame());
			}
		});
		btnNewButton_2.setBounds(10, 172, 124, 57);
		frmSteamChoice.getContentPane().add(btnNewButton_2);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	            // do some actions here, for example
	            // print first column value from selected row
	            btnNewButton_1.setEnabled(true);
	        }
	    });
		
	}
	private String pickGame(){
		ArrayList<String> picks = new ArrayList<String>();
		
		//"0 Action", "1 Adventure", "2 Fighting", "3 First Person Shooter", "4 Platforming", "5 Racing", "6 Real Time Strategy", "7 Role Playing Game", "8 Turn Based Strategy", "9 Simulation", "10 Sports", "11 Other"
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "cam", "cam123");
			PreparedStatement myQ = con.prepareStatement("select * from gamelib where userid = ?");
			myQ.setInt(1, userid);
			ResultSet r = myQ.executeQuery();
			while(r.next()){
				if(r.getInt("rating")<50){
					continue;
				}
				if(r.getInt("replay") == 0 && r.getInt("finished") == 1){
					continue;
				}
				picks.add(r.getString("title"));
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Random rand = new Random();
		return picks.get(rand.nextInt(picks.size()));
	}
	private int findGenre(String _genre){
		int index = 0;
		switch(_genre){
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
		return index;
	}
	private String findGenreInt(int _genre){
		switch(_genre){
			case 1: 
				return "Action";
			case 2:
				return "Adventure";
			case 3:
				return "Fighting";
			case 4:
				return "First Person Shooter";
			case 5:
				return "Platforming";
			case 6:
				return "Racing";
			case 7:
				return "Real Time Strategy";
			case 8:
				return "Role Playing Game";
			case 9:
				return "Turn Based Strategy";
			case 10:
				return "Simulation";
			case 11:
				return  "Sports";
			case 12:
				return "Other";
			case 0:
				return "";
		}
		return "";
	}
	
}

