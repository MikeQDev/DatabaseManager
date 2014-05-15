package com.FinalProject;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class AddRecordGUI extends JFrame{
	private JLabel firstLabel, lastLabel, phoneLabel,
					emailLabel, streetLabel, cityLabel,
					stateLabel, zipLabel, birthLabel;
	private JTextField firstField, lastField, phoneField,
						emailField, streetField, cityField,
						stateField, zipField, birthField;
	private JButton addRecordButton, cancelButton, clearButton;
	private JPanel inputPanel, buttonPanel;
	private String dbUser, dbPass;
	private String fName, lName, phone, email, addr,
					city, state, ZIP, date, formattedPhone;
	private ImageIcon addIcon = new ImageIcon(getClass().getResource("img/b_snewtbl.png"));
	public AddRecordGUI(String user, String pass){
		dbUser = user;
		dbPass = pass;
		setTitle("Add records");
		setIconImage(addIcon.getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		buildInputPanel();
		buildButtonPanel();
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	private boolean isValidZIP(){
		if(ZIP.length()!=5){
			JOptionPane.showMessageDialog(null,"ZIP must be 5 digits long.","Error",JOptionPane.WARNING_MESSAGE);
			return false;
		}else{
			for(int i=0; i<ZIP.length(); i++){
				if(!Character.isDigit(ZIP.charAt(i))){
					JOptionPane.showMessageDialog(null,"ZIP must be numeric.","Error",JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}
		}
		return true;
	}
	private boolean isValidEmail(){
		boolean status = false, status2 = false;
		for(int i=0; i<email.length(); i++){
			if(email.charAt(i)=='@')
				status = true;
			if(email.charAt(i)=='.')
				status2 = true;
		}
		if(status&&status2)
			return true;
		else
			JOptionPane.showMessageDialog(null,"Invalid email entered.","Error",JOptionPane.WARNING_MESSAGE);
		return false;
	}
	private boolean isValidPhone(){
		if(phone.length()!=10){
			JOptionPane.showMessageDialog(null,"Invalid phone number entered. Must be 10 digits.","Error",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		for(int i=0; i<phone.length(); i++){
			if(!Character.isDigit(phone.charAt(i))){
				JOptionPane.showMessageDialog(null,"Phone numbers can only contain digits.","Error",JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		return true;
	}
	private void buildButtonPanel(){
		buttonPanel = new JPanel();
		addRecordButton = new JButton("Add record");
		cancelButton = new JButton("Close");
		clearButton = new JButton("Clear");
		addRecordButton.addActionListener(new AddListener());
		cancelButton.addActionListener(new CancelListener());
		clearButton.addActionListener(new ClearListener());
		addRecordButton.setMnemonic(KeyEvent.VK_A);
		cancelButton.setMnemonic(KeyEvent.VK_L);
		clearButton.setMnemonic(KeyEvent.VK_C);
		buttonPanel.add(addRecordButton);
		buttonPanel.add(clearButton);
		buttonPanel.add(cancelButton);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	private class AddListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			DBInteracter dI = new DBInteracter(dbUser, dbPass);
			fName = firstField.getText();
			lName = lastField.getText();
			phone = phoneField.getText();
			email = emailField.getText();
			addr = streetField.getText();
			city = cityField.getText();
			state = stateField.getText().toUpperCase();
			ZIP = zipField.getText();
			date = birthField.getText();
			
			if(isValidPhone()&&isValidEmail()&&isValidZIP()&&isNotEmpty()){
				formattedPhone = toPhoneFormat(phone);
				dI.addRecord(fName, lName, formattedPhone, email, addr, city, state, ZIP, date);
			}
		}
	}
	private boolean isNotEmpty(){
		if(fName.equals("")||lName.equals("")||phone.equals("")||email.equals("")||addr.equals("")||city.equals("")||state.equals("")||ZIP.equals("")||date.equals("")){
			JOptionPane.showMessageDialog(null,"Please fill out all fields.","Error",JOptionPane.WARNING_MESSAGE);
			return false;
		}else{
			return true;
		}
	}
	private String toPhoneFormat(String unformatted){
		String formatted, part1, part2, part3;
		part1 = unformatted.substring(0,3);
		part2 = unformatted.substring(3,6);
		part3 = unformatted.substring(6);
		formatted = part1+"-"+part2+"-"+part3;
		return formatted;
	}
	private class CancelListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			dispose();
		}
	}
	private void buildInputPanel(){
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(9,2,2,2));
		
		firstLabel = new JLabel("First Name");
		lastLabel = new JLabel("Last Name");
		phoneLabel = new JLabel("Phone (##########)");
		emailLabel = new JLabel("eMail");
		streetLabel = new JLabel("Address");
		cityLabel = new JLabel("City");
		stateLabel = new JLabel("State");
		zipLabel = new JLabel("ZIP");
		birthLabel = new JLabel("Birthdate(YYYY/M/D)");
		
		firstField = new JTextField(8);
		lastField = new JTextField();
		phoneField = new JTextField();
		emailField = new JTextField();
		streetField = new JTextField();
		cityField = new JTextField();
		stateField = new JTextField();
		zipField = new JTextField();
		birthField = new JTextField();
		
		inputPanel.add(firstLabel);
		inputPanel.add(firstField);
		inputPanel.add(lastLabel);
		inputPanel.add(lastField);
		inputPanel.add(phoneLabel);
		inputPanel.add(phoneField);
		inputPanel.add(emailLabel);
		inputPanel.add(emailField);
		inputPanel.add(birthLabel);
		inputPanel.add(birthField);
		inputPanel.add(streetLabel);
		inputPanel.add(streetField);
		inputPanel.add(cityLabel);
		inputPanel.add(cityField);
		inputPanel.add(stateLabel);
		inputPanel.add(stateField);
		inputPanel.add(zipLabel);
		inputPanel.add(zipField);
		
		
		add(inputPanel, BorderLayout.CENTER);
	}
	private class ClearListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			firstField.setText("");
			lastField.setText("");
			phoneField.setText("");
			emailField.setText("");
			streetField.setText("");
			cityField.setText("");
			stateField.setText("");
			zipField.setText("");
			birthField.setText("");
		}
	}
}
