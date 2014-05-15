package com.FinalProject;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class SQLUpdateGUI extends JFrame{
	private JTextArea textArea;
	private JPanel buttonPanel, textPanel;
	private JButton submitButton, clearButton, closeButton;
	private String dbUser, dbPass;
	private ImageIcon sqlUpdateIcon = new ImageIcon(getClass().getResource("img/b_sql.png"));
	
	public SQLUpdateGUI(String user, String pass){
		dbUser = user;
		dbPass = pass;
		setTitle("SQL Update");
		setIconImage(sqlUpdateIcon.getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		buildTextPanel();
		buildButtonPanel();
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	private void buildTextPanel(){
		textPanel = new JPanel();
		textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(450,100));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane textScroller = new JScrollPane(textArea);
		textPanel.add(textScroller);
		add(textPanel, BorderLayout.CENTER);
	}
	private void buildButtonPanel(){
		buttonPanel = new JPanel();
		submitButton = new JButton("Run");
		clearButton = new JButton("Clear");
		closeButton = new JButton("Close");
		submitButton.setMnemonic(KeyEvent.VK_R);
		clearButton.setMnemonic(KeyEvent.VK_L);
		closeButton.setMnemonic(KeyEvent.VK_C);
		submitButton.addActionListener(new SubmitListener());
		clearButton.addActionListener(new ClearListener());
		closeButton.addActionListener(new CloseListener());
		
		buttonPanel.add(submitButton);
		buttonPanel.add(clearButton);
		buttonPanel.add(closeButton);
		
		add(buttonPanel, BorderLayout.SOUTH);
	}
	private class SubmitListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			DBInteracter dI = new DBInteracter(dbUser, dbPass);
			dI.sqlUpdate(textArea.getText());
		}
	}
	private class ClearListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			textArea.setText("");
		}
	}
	private class CloseListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			dispose();
		}
	}
}
