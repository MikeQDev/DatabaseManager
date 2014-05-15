package com.FinalProject;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class TaskGUIOLD extends JFrame{
	private final String DB_URL = "jdbc:mysql://localhost/coffeedb";
	private String dbUser;
	private String dbPass;
	private JButton addRecordsButton, viewRecordsButton, updateRecordsButton,
						deleteRecordsButton, addUsersButton;
	private JPanel taskPanel;
	public TaskGUIOLD(String user, String pass){
		dbUser = user;
		dbPass = pass;
		setTitle("Database management - Logged in as "+dbUser);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildTaskPanel();
		pack();
		setResizable(false);
		setVisible(true);
	}
	private void buildTaskPanel(){
		taskPanel = new JPanel();
		taskPanel.setLayout(new GridLayout(3,2,10,10));
		
		addRecordsButton = new JButton("Add records");
		viewRecordsButton = new JButton("View records");
		updateRecordsButton = new JButton("Update records");
		deleteRecordsButton = new JButton("Delete records");
		addUsersButton = new JButton("Add DB users");
		
		addUsersButton.addActionListener(new TaskListener());
		
		taskPanel.add(addRecordsButton);
		taskPanel.add(viewRecordsButton);
		taskPanel.add(updateRecordsButton);
		taskPanel.add(deleteRecordsButton);
		taskPanel.add(addUsersButton);
		
		add(taskPanel, BorderLayout.CENTER);
	}
	private class TaskListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==addRecordsButton){
				
			}else if(e.getSource()==viewRecordsButton){
				
			}else if(e.getSource()==updateRecordsButton){
				
			}else if(e.getSource()==deleteRecordsButton){
				
			}else if(e.getSource()==addUsersButton){
				new AddUserGUI();
			}
		}
	}
	public static void main(String[] args){
		new TaskGUIOLD("uzer","pazzword");
	}
}
