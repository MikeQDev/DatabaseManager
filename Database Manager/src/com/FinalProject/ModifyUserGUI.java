package com.FinalProject;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ModifyUserGUI extends JFrame{
	private JLabel userLabel;
	private JTextField userField;
	private JButton modifyButton, closeButton;
	private JPanel inputPanel, buttonPanel, typePanel;
	private ButtonGroup bGroup;
	private JRadioButton adminRadio, userRadio;
	private ImageIcon modifyUserIcon = new ImageIcon(getClass().getResource("img/b_usredit.png"));
	public ModifyUserGUI(){
		setTitle("Modify user privileges");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(modifyUserIcon.getImage());
		buildInputPanel();
		buildTypePanel();
		buildButtonPanel();
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	private void buildInputPanel(){
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(2,1));
		userLabel = new JLabel("Username:", SwingConstants.CENTER);
		userField = new JTextField(10);
		userField.setHorizontalAlignment(JTextField.CENTER);
		inputPanel.add(userLabel);
		inputPanel.add(userField);
		add(inputPanel, BorderLayout.NORTH);
	}
	private void buildTypePanel(){
		typePanel = new JPanel();
		typePanel.setBorder(BorderFactory.createTitledBorder("Account type"));
		adminRadio = new JRadioButton("Admin");
		userRadio = new JRadioButton("User");
		bGroup = new ButtonGroup();
		bGroup.add(adminRadio);
		bGroup.add(userRadio);
		JPanel adminPanel = new JPanel();
		JPanel userPanel = new JPanel();
		adminPanel.add(adminRadio);
		userPanel.add(userRadio);
		typePanel.add(adminPanel);
		typePanel.add(userPanel);
		adminRadio.doClick();
		add(typePanel, BorderLayout.CENTER);
	}
	private void buildButtonPanel(){
		buttonPanel = new JPanel();
		modifyButton = new JButton("Modify user");
		closeButton = new JButton("Close");
		modifyButton.setMnemonic(KeyEvent.VK_M);
		closeButton.setMnemonic(KeyEvent.VK_C);
		modifyButton.addActionListener(new ModifyListener());
		closeButton.addActionListener(new CloseListener());
		buttonPanel.add(modifyButton);
		buttonPanel.add(closeButton);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	private class ModifyListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			DBInteracter dI = new DBInteracter(HomeGUI.dbUser, HomeGUI.dbPass);
			String u = userField.getText();
			if(!u.equals("")){
				if(!HomeGUI.dbUser.equalsIgnoreCase(userField.getText())){
					if(adminRadio.isSelected())
						dI.modifyUser(u, 1);
					else if(userRadio.isSelected())
						dI.modifyUser(u, 0);
				}else{
					JOptionPane.showMessageDialog(null,"Currently logged in as user '"+userField.getText()+"', unable to modify privileges.","Error",JOptionPane.WARNING_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(null, "User field cannot be empty.", "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	private class CloseListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
	}
}
