package com.FinalProject;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class AddUserGUI extends JFrame{
	private JLabel userLabel, passLabel, confPassLabel;
	private JTextField userField;
	private JPasswordField passField, confPassField;
	private JButton addButton, cancelButton;
	private ButtonGroup radioGroup;
	private JRadioButton userRadio, adminRadio;
	private JPanel inputPanel, buttonPanel, radioPanel;
	private String userName, password;
	private ImageIcon addUserIcon = new ImageIcon(getClass().getResource("img/b_usradd.png"));
	private DBInteracter dI = HomeGUI.dI;
	public AddUserGUI(){
		setTitle("Create new DB user");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(addUserIcon.getImage());
		buildInputPanel();
		buildRadioPanel();
		buildButtonPanel();
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	private void buildInputPanel(){
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(3,2));
		userLabel = new JLabel("Username: ");
		passLabel = new JLabel("Password: ");
		confPassLabel = new JLabel("Confirm password: ");
		userField = new JTextField(10);
		passField = new JPasswordField(10);
		confPassField = new JPasswordField(10);
		
		inputPanel.add(userLabel);
		inputPanel.add(userField);
		inputPanel.add(passLabel);
		inputPanel.add(passField);
		inputPanel.add(confPassLabel);
		inputPanel.add(confPassField);
		
		add(inputPanel, BorderLayout.NORTH);
	}
	private void buildRadioPanel(){
		radioPanel = new JPanel();
		radioPanel.setBorder(BorderFactory.createTitledBorder("Account type"));
		radioGroup = new ButtonGroup();
		JPanel adminPanel = new JPanel();
		JPanel userPanel = new JPanel();
		userRadio = new JRadioButton("User");
		adminRadio = new JRadioButton("Admin");
		
		adminRadio.doClick();
		
		radioGroup.add(userRadio);
		radioGroup.add(adminRadio);
		
		userPanel.add(userRadio);
		adminPanel.add(adminRadio);
		
		radioPanel.add(adminPanel);
		radioPanel.add(userPanel);
		add(radioPanel, BorderLayout.CENTER);
	}
	private boolean comparePasswords(){
		String firstPass = "";
		String secondPass = "";
		for(char c : passField.getPassword())
			firstPass+=c;
		for(char c : confPassField.getPassword())
			secondPass+=c;
		if(firstPass.equals(secondPass)){
			password = firstPass;
			userName = userField.getText();
			return true;
		}else{
			return false;
		}
	}
	private void buildButtonPanel(){
		buttonPanel = new JPanel();
		addButton = new JButton("Add user");
		cancelButton = new JButton("Close");
		addButton.setMnemonic(KeyEvent.VK_A);
		cancelButton.setMnemonic(KeyEvent.VK_C);
		cancelButton.addActionListener(new CancelListener());
		addButton.addActionListener(new AddListener());
		buttonPanel.add(addButton);
		buttonPanel.add(cancelButton);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	private class AddListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			if(!userField.getText().equals("")){
				if(comparePasswords()){
					//dI = new DBInteracter(HomeGUI.dbUser, HomeGUI.dbPass);
					if(userRadio.isSelected())
						dI.addUser(userName, password, 0);
					else if(adminRadio.isSelected())
						dI.addUser(userName, password, 1);
					}else{
						JOptionPane.showMessageDialog(null,"Passwords did not match. User not added.","Error",JOptionPane.WARNING_MESSAGE);
					}
			}else{
				JOptionPane.showMessageDialog(null,"Please enter a username.","Error",JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	private class CancelListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			comparePasswords();
			dispose();
		}
	}
}
