package com.FinalProject;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class DeleteRecordGUI extends JFrame{
	private JLabel deleteLabel;
	private JButton deleteButton, closeButton;
	private JPanel inputPanel, buttonPanel;
	private JTextField idField;
	private int deleteID = -1;
	private ImageIcon deleteIcon = new ImageIcon(getClass().getResource("img/b_drop.png"));
	public DeleteRecordGUI(){
		setTitle("Delete record");
		buildInputPanel();
		buildButtonPanel();
		pack();
		setIconImage(deleteIcon.getImage());
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	private void buildInputPanel(){
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(2,1));
		deleteLabel = new JLabel("Delete record ID:", SwingConstants.CENTER);
		idField = new JTextField();
		idField.setHorizontalAlignment(JTextField.CENTER);
		inputPanel.add(deleteLabel);
		inputPanel.add(idField);
		add(inputPanel, BorderLayout.CENTER);
	}
	private void buildButtonPanel(){
		buttonPanel = new JPanel();
		closeButton = new JButton("Close");
		deleteButton = new JButton("Delete");
		closeButton.setMnemonic(KeyEvent.VK_C);
		deleteButton.setMnemonic(KeyEvent.VK_D);
		closeButton.addActionListener(new CloseListener());
		deleteButton.addActionListener(new DeleteListener());
		buttonPanel.add(deleteButton);
		buttonPanel.add(closeButton);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	private class DeleteListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				deleteID = Integer.parseInt(idField.getText());
				int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete record ID "+deleteID+"?",
						"Delete comfirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				if(i==0){
					DBInteracter dI = new DBInteracter(HomeGUI.dbUser, HomeGUI.dbPass);
					dI.deleteRecord(deleteID);
				}
			}catch(NumberFormatException ex){
				JOptionPane.showMessageDialog(null, "Error processing inputed ID.", "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	private class CloseListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			dispose();
		}
	}
}
