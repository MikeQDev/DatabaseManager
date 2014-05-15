package com.FinalProject;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class HomeGUI extends JFrame{
	private ImageIcon deleteIcon = new ImageIcon(getClass().getResource("img/b_drop.png"));
	private ImageIcon addIcon = new ImageIcon(getClass().getResource("img/b_snewtbl.png"));
	private ImageIcon settingsIcon = new ImageIcon(getClass().getResource("img/b_tblops.png"));
	private ImageIcon addUserIcon = new ImageIcon(getClass().getResource("img/b_usradd.png"));
	private ImageIcon delUserIcon = new ImageIcon(getClass().getResource("img/b_usrdrop.png"));
	private ImageIcon exitIcon = new ImageIcon(getClass().getResource("img/s_loggoff.png"));
	private ImageIcon aboutIcon = new ImageIcon(getClass().getResource("img/s_info.png"));
	private ImageIcon queryIcon = new ImageIcon(getClass().getResource("img/b_search.png"));
	private ImageIcon homeIcon = new ImageIcon(getClass().getResource("img/b_home.png"));
	private ImageIcon logoffIcon = new ImageIcon(getClass().getResource("img/b_usrcheck.png"));
	private ImageIcon updateIcon = new ImageIcon(getClass().getResource("img/update.png"));
	private ImageIcon refreshIcon = new ImageIcon(getClass().getResource("img/s_reload.png"));
	private ImageIcon modifyUserIcon = new ImageIcon(getClass().getResource("img/b_usredit.png"));
	private ImageIcon sqlUpdateIcon = new ImageIcon(getClass().getResource("img/b_sql.png"));
	
	public static String dbUser, dbPass;
	private JMenuBar mainMenuBar;
	private JMenu fileMenu, recordsMenu, usersMenu, aboutMenu;
	private JMenuItem optionItem, logoutItem, exitItem,
						addRecordItem, updateRecordItem, deleteRecordItem, sqlSearchItem, sqlUpdateItem, refreshItem,
						addUserItem, deleteUserItem, modifyUserItem,
						aboutItem;
	private JPanel tablePanel;
	public static JTable myTable;
	public static int refreshTime = 3000;
	public static Timer rfTimer;
	private Color headerColorBack, headerColorFore;
	public HomeGUI(String u, String p){
		dbUser = u;
		dbPass = p;
		setPreferredSize(new Dimension(806,354));
		setTitle("Database manager - Logged in as "+dbUser+" (ADMIN)");
		setIconImage(homeIcon.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildMenuBar();
		buildTablePanel();
		getUserPriv();
		startTimer();
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	public void getUserPriv(){
		DBInteracter dI = new DBInteracter(dbUser, dbPass);
		boolean isAdmin = dI.checkAdmin();
		if(!isAdmin)
			disableAdmins();
	}
	private void disableAdmins(){
		setTitle("Database manager - Logged in as "+dbUser+" (USER)");
		addRecordItem.setEnabled(false);
		updateRecordItem.setEnabled(false);
		deleteRecordItem.setEnabled(false);
		addUserItem.setEnabled(false);
		deleteUserItem.setEnabled(false);
		modifyUserItem.setEnabled(false);
		sqlUpdateItem.setEnabled(false);
	}
	public static void timerRestart(){
		rfTimer.stop();
		rfTimer.start();
	}
	private void startTimer(){
		rfTimer = new Timer(refreshTime, new TimerListener());
		rfTimer.start();
	}
	private void buildTablePanel(){
		headerColorBack = OptionGUI.selectedBack;
		headerColorFore = OptionGUI.selectedFore;
		tablePanel = new JPanel();
		DBInteracter dI = new DBInteracter(dbUser, dbPass);
		myTable = dI.getTable();
		JScrollPane tableScroller = new JScrollPane(myTable);
		tableScroller.setPreferredSize(new Dimension(780,283));
		tablePanel.add(tableScroller);
		myTable.getTableHeader().setReorderingAllowed(false);
		myTable.getTableHeader().setBackground(headerColorBack);
		myTable.getTableHeader().setForeground(headerColorFore);
		add(tablePanel, BorderLayout.CENTER);
	}
	private void buildMenuBar(){
		mainMenuBar = new JMenuBar();
		createFileMenu();
		createRecordsMenu();
		createUsersMenu();
		createAboutMenu();
		setJMenuBar(mainMenuBar);
	}
	private void createFileMenu(){
		fileMenu = new JMenu("File");
		optionItem = new JMenuItem("Options", settingsIcon);
		exitItem = new JMenuItem("Exit", exitIcon);
		logoutItem = new JMenuItem("Log off", logoffIcon);
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		optionItem.setMnemonic(KeyEvent.VK_O);
		exitItem.setMnemonic(KeyEvent.VK_X);
		logoutItem.setMnemonic(KeyEvent.VK_L);
		
		optionItem.addActionListener(new OptionListener());
		exitItem.addActionListener(new ExitListener());
		logoutItem.addActionListener(new LogoutListener());
		
		fileMenu.add(optionItem);
		fileMenu.add(logoutItem);
		fileMenu.add(exitItem);
		
		mainMenuBar.add(fileMenu);
	}
	private void createRecordsMenu(){
		recordsMenu = new JMenu("Records");
		addRecordItem = new JMenuItem("Add records", addIcon);
		updateRecordItem = new JMenuItem("Update records", updateIcon);
		deleteRecordItem = new JMenuItem("Delete records", deleteIcon);
		sqlSearchItem = new JMenuItem("SQL Search", queryIcon);
		sqlUpdateItem = new JMenuItem("SQL Update", sqlUpdateIcon);
		refreshItem = new JMenuItem("Refresh table", refreshIcon);
		recordsMenu.setMnemonic(KeyEvent.VK_R);
		
		addRecordItem.setMnemonic(KeyEvent.VK_A);
		updateRecordItem.setMnemonic(KeyEvent.VK_P);
		deleteRecordItem.setMnemonic(KeyEvent.VK_D);
		sqlSearchItem.setMnemonic(KeyEvent.VK_S);
		sqlUpdateItem.setMnemonic(KeyEvent.VK_U);
		refreshItem.setMnemonic(KeyEvent.VK_R);
		
		addRecordItem.addActionListener(new RecordListener());
		updateRecordItem.addActionListener(new RecordListener());
		deleteRecordItem.addActionListener(new RecordListener());
		sqlSearchItem.addActionListener(new RecordListener());
		sqlUpdateItem.addActionListener(new RecordListener());
		refreshItem.addActionListener(new RecordListener());
		
		recordsMenu.add(addRecordItem);
		recordsMenu.add(updateRecordItem);
		recordsMenu.add(deleteRecordItem);
		recordsMenu.addSeparator();
		recordsMenu.add(sqlSearchItem);
		recordsMenu.add(sqlUpdateItem);
		recordsMenu.addSeparator();
		recordsMenu.add(refreshItem);
		
		mainMenuBar.add(recordsMenu);
	}
	private void createUsersMenu(){
		usersMenu = new JMenu("Users");
		addUserItem = new JMenuItem("Add users", addUserIcon);
		deleteUserItem = new JMenuItem("Delete users", delUserIcon);
		modifyUserItem = new JMenuItem("Modify users", modifyUserIcon);
		usersMenu.setMnemonic(KeyEvent.VK_U);
		addUserItem.setMnemonic(KeyEvent.VK_A);
		deleteUserItem.setMnemonic(KeyEvent.VK_D);
		modifyUserItem.setMnemonic(KeyEvent.VK_M);
		addUserItem.addActionListener(new UserListener());
		deleteUserItem.addActionListener(new UserListener());
		modifyUserItem.addActionListener(new UserListener());
		
		usersMenu.add(addUserItem);
		usersMenu.add(deleteUserItem);
		usersMenu.addSeparator();
		usersMenu.add(modifyUserItem);
		
		mainMenuBar.add(usersMenu);
	}
	private void createAboutMenu(){
		aboutMenu = new JMenu("About");
		aboutMenu.setMnemonic(KeyEvent.VK_A);
		aboutItem = new JMenuItem("About", aboutIcon);
		aboutItem.setMnemonic(KeyEvent.VK_A);
		aboutItem.addActionListener(new AboutListener());
		aboutMenu.add(aboutItem);
		mainMenuBar.add(aboutMenu);
	}
	public static void main(String[] args) {
		try{
			new HomeGUI("dbuser","dbpassword");
		}catch(NullPointerException e){
			JOptionPane.showMessageDialog(null,"Cannot find MySQL server.. Make sure MySQL is running.","Error",JOptionPane.WARNING_MESSAGE);
		}
	}
	public void refreshTable(){
		try{
			remove(tablePanel);
		}catch(NullPointerException ex){
		}
		buildTablePanel();
		pack();
	}
	private class RecordListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==addRecordItem){
				new AddRecordGUI(dbUser, dbPass);
			}else if(e.getSource()==updateRecordItem){
				new UpdateGUI(dbUser, dbPass);
			}else if(e.getSource()==deleteRecordItem){
				new DeleteRecordGUI();
			}else if(e.getSource()==sqlSearchItem){
				new QueryGUI(dbUser, dbPass);
			}else if(e.getSource()==refreshItem){
				refreshTable();
			}else if(e.getSource()==sqlUpdateItem){
				new SQLUpdateGUI(dbUser, dbPass);
			}
		}
	}
	private class UserListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==addUserItem)
				new AddUserGUI();
			else if(e.getSource()==deleteUserItem)
				new DeleteUserGUI();
			else if(e.getSource()==modifyUserItem)
				new ModifyUserGUI();
		}
	}
	private class AboutListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			new AboutGUI();
		}
	}
	private class ExitListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?","Exit",JOptionPane.YES_NO_OPTION);
			if(response==0)
				System.exit(0);
		}
	}
	private class OptionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			new OptionGUI();
		}
	}
	private class LogoutListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to log off?","Log off",JOptionPane.YES_NO_OPTION);
			if(response==0){
				try{
					rfTimer.stop();
				}catch(NullPointerException ex){
				}
				dispose();
				JOptionPane.showMessageDialog(null,"Logged off successfully.","Log off success",JOptionPane.INFORMATION_MESSAGE);
				new LoginGUI();
			}
		}
	}
	private class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			refreshTable();
		}
	}
}
