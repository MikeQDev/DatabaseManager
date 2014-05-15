package com.FinalProject;

import javax.swing.*;

public class AboutGUI{
	public AboutGUI(){
		JLabel text = new JLabel("<html><center>This program was created as a<br/>" +
				"final Senior study project in IST.<br/>" +
				"Programmed 100% in Java " +
				"from<br/>scratch, and includes SQL.<br/>Mike Q 2013.</center></html>");
		JOptionPane.showMessageDialog(null,text,"About",JOptionPane.INFORMATION_MESSAGE);
	}
}
