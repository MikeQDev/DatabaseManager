package com.FinalProject;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

public class OptionGUI extends JFrame{
	
	private JPanel refreshPanel, headerColorPanel;
	private JLabel refreshLabel;
	private JTextField refreshField;
	private JCheckBox updateCheck;
	private JButton updateRefreshButton, changeBGColorButton, changeForeColorButton;
	public static Color selectedBack, selectedFore;
	private ImageIcon colorIcon = new ImageIcon(getClass().getResource("img/s_theme.png"));
	private ImageIcon settingsIcon = new ImageIcon(getClass().getResource("img/b_tblops.png"));
	private ImageIcon refreshIcon = new ImageIcon(getClass().getResource("img/s_reload.png"));
	public OptionGUI(){
		setTitle("Options");
		setIconImage(settingsIcon.getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		buildRefreshPanel();
		buildColorPanel();
		if(HomeGUI.rfTimer.isRunning()){
			updateCheck.doClick();
			refreshField.setEnabled(true);
			updateRefreshButton.setEnabled(true);
		}else{
			refreshField.setEnabled(false);
			updateRefreshButton.setEnabled(false);
		}
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	private void buildColorPanel(){
		headerColorPanel = new JPanel();
		headerColorPanel.setBorder(BorderFactory.createTitledBorder("Table header color"));
		changeBGColorButton = new JButton("Background color", colorIcon);
		changeForeColorButton = new JButton("Font color", colorIcon);
		changeBGColorButton.setMnemonic(KeyEvent.VK_B);
		changeForeColorButton.setMnemonic(KeyEvent.VK_F);
		changeForeColorButton.addActionListener(new ChangeColorListener());
		changeBGColorButton.addActionListener(new ChangeColorListener());
		headerColorPanel.add(changeForeColorButton);
		headerColorPanel.add(changeBGColorButton);
		add(headerColorPanel, BorderLayout.SOUTH);
	}
	private void buildRefreshPanel(){
		refreshPanel = new JPanel();
		refreshPanel.setLayout(new GridLayout(2,1));
		JPanel updatePanel  = new JPanel();
		JPanel inputPanel = new JPanel();
		updateCheck = new JCheckBox("Enable auto-refresh");
		refreshLabel = new JLabel("Refresh interval (ms)",refreshIcon,SwingConstants.CENTER);
		refreshField = new JTextField(Integer.toString(HomeGUI.refreshTime),4);
		refreshField.setHorizontalAlignment(JTextField.CENTER);
		updateRefreshButton = new JButton("Update");
		updateCheck.setMnemonic(KeyEvent.VK_N);
		updateRefreshButton.setMnemonic(KeyEvent.VK_U);
		updateRefreshButton.addActionListener(new UpdateRefreshListener());
		updateCheck.addActionListener(new UpdateEnabledListener());
		updatePanel.add(updateCheck);
		inputPanel.add(refreshLabel);
		inputPanel.add(refreshField);
		inputPanel.add(updateRefreshButton);
		refreshPanel.add(updatePanel);
		refreshPanel.add(inputPanel);
		add(refreshPanel, BorderLayout.CENTER);
	}
	private class UpdateEnabledListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(updateCheck.isSelected()){
				HomeGUI.timerRestart();
				refreshField.setEnabled(true);
				updateRefreshButton.setEnabled(true);
				HomeGUI.rfTimer.start();
			}else{
				refreshField.setEnabled(false);
				updateRefreshButton.setEnabled(false);
				HomeGUI.rfTimer.stop();
			}
		}
	}
	private class UpdateRefreshListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				HomeGUI.rfTimer.stop();
				HomeGUI.refreshTime = Integer.parseInt(refreshField.getText());
				HomeGUI.rfTimer.setDelay(Integer.parseInt(refreshField.getText()));
				HomeGUI.timerRestart();
			}catch(NumberFormatException ex){
				JOptionPane.showMessageDialog(null,"Refresh time must be numeric","Error",JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	private class ChangeColorListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==changeBGColorButton)
				selectedBack = JColorChooser.showDialog(null, "Select header background color", Color.RED);
			else if(e.getSource()==changeForeColorButton)
				selectedFore = JColorChooser.showDialog(null, "Select header text color", Color.RED);
		}
	}
}
