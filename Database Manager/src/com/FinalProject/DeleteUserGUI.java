package com.FinalProject;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class DeleteUserGUI extends JFrame{
	private JTextField userField;
	private JLabel deleteLabel;
	private JPanel inputPanel, buttonPanel;
	private JButton deleteButton, closeButton;
	private ImageIcon delUserIcon = new ImageIcon(getClass().getResource("img/b_usrdrop.png"));
	private String user = "";
	public DeleteUserGUI(){
		setTitle("Delete user");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		buildInputPanel();
		buildButtonPanel();
		pack();
		setIconImage(delUserIcon.getImage());
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	private void buildInputPanel(){
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(2,1));
		userField = new JTextField();
		deleteLabel = new JLabel("User to delete:", SwingConstants.CENTER);
		userField.setHorizontalAlignment(JTextField.CENTER);
		inputPanel.add(deleteLabel);
		inputPanel.add(userField);
		add(inputPanel, BorderLayout.CENTER);
	}
	private void buildButtonPanel(){
		buttonPanel = new JPanel();
		deleteButton = new JButton("Delete");
		closeButton = new JButton("Close");
		closeButton.setMnemonic(KeyEvent.VK_C);
		deleteButton.setMnemonic(KeyEvent.VK_D);
		deleteButton.addActionListener(new DeleteListener());
		closeButton.addActionListener(new CloseListener());
		buttonPanel.add(deleteButton);
		buttonPanel.add(closeButton);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	private class DeleteListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			user = userField.getText();
			if(!user.equals("")){
				int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete user '"+user+"'?","Delete confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(i==0){
					DBInteracter dI = new DBInteracter(HomeGUI.dbUser, HomeGUI.dbPass);
					dI.deleteUser(user);
				}
			}else{
				JOptionPane.showMessageDialog(null, "User field cannot be empty.", "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	private class CloseListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			dispose();
		}
	}
}
