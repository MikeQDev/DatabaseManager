package com.FinalProject;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class LoginGUI extends JFrame{
	private final String LH_URL = "jdbc:mysql://localhost/";
	private final String DB_URL = "jdbc:mysql://localhost/userdb";
	private JLabel userLabel, passLabel;
	private JButton loginButton, cancelButton;
	private JTextField userField;
	private JPanel inputPanel, buttonPanel;
	private JPasswordField pwField;
	private String dbUser, dbPass;
	private ImageIcon loginIcon = new ImageIcon(getClass().getResource("img/login.png"));
	private int incorrectLogins = 0;
	public LoginGUI(){
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(loginIcon.getImage());
		buildInputPanel();
		buildButtonPanel();
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	private void buildInputPanel(){
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(2,2));
		
		userLabel = new JLabel("Username: ", SwingConstants.CENTER);
		passLabel = new JLabel("Password: ", SwingConstants.CENTER);
		userField = new JTextField(10);		
		pwField = new JPasswordField(10);
		
		inputPanel.add(userLabel);
		inputPanel.add(userField);
		inputPanel.add(passLabel);
		inputPanel.add(pwField);
		
		add(inputPanel, BorderLayout.CENTER);
	}
	private void buildButtonPanel(){
		buttonPanel = new JPanel();
		loginButton = new JButton("Login");
		cancelButton = new JButton("Exit");
		loginButton.setMnemonic(KeyEvent.VK_L);
		cancelButton.setMnemonic(KeyEvent.VK_X);
		loginButton.addActionListener(new LoginListener());
		cancelButton.addActionListener(new ExitListener());
		buttonPanel.add(loginButton);
		buttonPanel.add(cancelButton);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	private class LoginListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			dbUser = userField.getText();
			char[] passOrig = pwField.getPassword();
			dbPass = "";
			for(char c : passOrig)
				dbPass+=c;
			try{
				createDBTable();
				Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
				JLabel textCenter = new JLabel("Logged in successfully as '"+dbUser+"'.", JLabel.CENTER);
				JOptionPane.showMessageDialog(null,textCenter,"Login Success",JOptionPane.PLAIN_MESSAGE);
				new HomeGUI(dbUser, dbPass);
				dispose();
			}catch(SQLException ex){
				incorrectLogins++;
				if(incorrectLogins<10){
					JOptionPane.showMessageDialog(null,"Invalid username or password. "+(10-incorrectLogins)+" attempts left.","Login failed",JOptionPane.WARNING_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null,"Too many incorrect logins. Exiting program.","Login failed",JOptionPane.WARNING_MESSAGE);
					System.exit(0);
				}
			}
		}
	}
	private void createDBTable(){
		try{
			Connection conn = DriverManager.getConnection(LH_URL, dbUser, dbPass);
			Statement stmt = conn.createStatement();
			stmt.execute("CREATE DATABASE userdb");
		}catch(SQLException ex){
		}
		try{
			Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			Statement stmt = conn.createStatement();
			stmt.execute("CREATE TABLE UserInfo (ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, FirstName CHAR(20), LastName CHAR(25), Phone CHAR(13), Email CHAR(30)," +
							"Birthdate DATE, StreetAddr CHAR(30), City CHAR(15), State CHAR(2), ZIP CHAR(5))");
			stmt.executeUpdate("INSERT INTO userinfo VALUES (0,'Mike','D','203-288-4642','quintz@aol.com','2013/12/6','11 Red St.', 'Meriden' ,'CT','06450')");
			conn.close();
			stmt.close();
		}catch(SQLException ex){
		}
	}
	private class ExitListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.exit(0);
		}
	}
	public static void main(String[] args) {
		new LoginGUI();
	}

}
