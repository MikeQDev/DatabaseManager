package com.FinalProject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class UpdateGUI extends JFrame{
	private JLabel idLabel, firstLabel, lastLabel, phoneLabel,
				emailLabel, streetLabel, cityLabel,
				stateLabel, zipLabel, birthLabel;
	private JTextField idField, firstField, lastField, phoneField,
				emailField, streetField, cityField,
				stateField, zipField, birthField;
	private String fName, lName, phone, email, addr, city, state, ZIP, date;
	private JButton updateRecordButton, cancelButton;
	private JPanel inputPanel, buttonPanel;
	private String dbUser, dbPass;
	private int recordID;
	private DBInteracter dI = HomeGUI.dI;
	private ImageIcon updateIcon = new ImageIcon(getClass().getResource("img/update.png"));
	public UpdateGUI(String user, String pass){
		dbUser = user;
		dbPass = pass;
		String o = JOptionPane.showInputDialog(null, "Enter ID to update", "Update", JOptionPane.OK_CANCEL_OPTION);
		if(o!=null){
			recordID = Integer.parseInt(o);
			createGUI();
		}
	}
	public void createGUI(){
		setTitle("Updater");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//dI = new DBInteracter(dbUser, dbPass);
		if(dI.getInfo(recordID)!=0){
			buildInputPanel();
			buildButtonPanel();
			setIconImage(updateIcon.getImage());
			pack();
			setLocationRelativeTo(null);
			setResizable(false);
			setVisible(true);
		}else{
			dispose();
		}
	}
	private void buildInputPanel(){
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(10,2,2,2));
		
		idLabel = new JLabel("ID");
		firstLabel = new JLabel("First Name");
		lastLabel = new JLabel("Last Name");
		phoneLabel = new JLabel("Phone (###-###-####)");
		emailLabel = new JLabel("eMail");
		streetLabel = new JLabel("Address");
		cityLabel = new JLabel("City");
		stateLabel = new JLabel("State");
		zipLabel = new JLabel("ZIP");
		birthLabel = new JLabel("Birthdate (YYYY/M/D)");
		
		idField = new JTextField(dI.getID(), 8);
		idField.setEnabled(false);
		firstField = new JTextField(dI.getFirst());
		lastField = new JTextField(dI.getLast());
		phoneField = new JTextField(dI.getPhone());
		emailField = new JTextField(dI.getEmail());
		streetField = new JTextField(dI.getAddress());
		cityField = new JTextField(dI.getCity());
		stateField = new JTextField(dI.getState());
		zipField = new JTextField(dI.getZIP());
		birthField = new JTextField(dI.getDate());
		
		inputPanel.add(idLabel);
		inputPanel.add(idField);
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
	private void buildButtonPanel(){
		buttonPanel = new JPanel();
		updateRecordButton = new JButton("Update record");
		cancelButton = new JButton("Close");
		updateRecordButton.setMnemonic(KeyEvent.VK_U);
		cancelButton.setMnemonic(KeyEvent.VK_C);
		updateRecordButton.addActionListener(new UpdateListener());
		cancelButton.addActionListener(new CancelListener());
		buttonPanel.add(updateRecordButton);
		buttonPanel.add(cancelButton);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	private class UpdateListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			//DBInteracter dI = new DBInteracter(dbUser, dbPass);
			int id = Integer.parseInt(idField.getText());
			fName = firstField.getText();
			lName = lastField.getText();
			phone = phoneField.getText();
			email = emailField.getText();
			addr = streetField.getText();
			city = cityField.getText();
			state = stateField.getText().toUpperCase();
			ZIP = zipField.getText();
			date = birthField.getText();
			if(isValidPhone()&&isValidZIP()&&isValidEmail()&&isValidState()&&isNotEmpty())
				dI.updateRecord(id, fName, lName, phone, email, addr, city, state, ZIP, date);
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
	private boolean isValidState(){
		if(state.length()!=2){
			JOptionPane.showMessageDialog(null,"State must be 2 characters long.","Error",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		for(int i=0; i<state.length(); i++){
			if(!Character.isAlphabetic(state.charAt(i))){
				JOptionPane.showMessageDialog(null,"State can only contain letters.","Error",JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		return true;
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
		if(phone.length()!=12){
			JOptionPane.showMessageDialog(null,"Invalid phone number entered. Must be 12 digits.","Error",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(phone.charAt(3)!='-'||phone.charAt(7)!='-'){
			JOptionPane.showMessageDialog(null,"Invalid phone number entered. Don't forget dashes.","Error",JOptionPane.WARNING_MESSAGE);
			return false;
			}
		for(int i=0; i<3; i++){
			if(!Character.isDigit(phone.charAt(i))){
				JOptionPane.showMessageDialog(null,"Phone numbers can only contain digits.","Error",JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		for(int i=4; i<7; i++){
			if(!Character.isDigit(phone.charAt(i))){
				JOptionPane.showMessageDialog(null,"Phone numbers can only contain digits.","Error",JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		for(int i=8; i<phone.length(); i++){
			if(!Character.isDigit(phone.charAt(i))){
				JOptionPane.showMessageDialog(null,"Phone numbers can only contain digits.","Error",JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		return true;
	}
	private class CancelListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			dispose();
		}
	}
}
