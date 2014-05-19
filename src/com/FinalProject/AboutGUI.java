package com.FinalProject;

import javax.swing.*;

public class AboutGUI{
	public AboutGUI(){
		JLabel text = new JLabel("<html><center>This program was created as a<br/>" +
				"final 'senior study' project in IST.<br/>" +
				"Programmed 100% in Java " +
				"from<br/>scratch, and uses mySQL.<br/>" +
				"<br/>Last updated: May 19th, 2014<br/>"+
				"~Michael Q</center></html>");
		JOptionPane.showMessageDialog(null,text,"About",JOptionPane.INFORMATION_MESSAGE);
	}
}
