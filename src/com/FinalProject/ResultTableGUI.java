package com.FinalProject;

import java.awt.*;

import javax.swing.*;

public class ResultTableGUI extends JFrame{
	private JPanel tablePanel;
	private JTable myTable;
	private ImageIcon queryIcon = new ImageIcon(getClass().getResource("img/b_search.png"));
	public ResultTableGUI(JTable theTable, String query){
		myTable = theTable;
		setTitle("Queried results ("+query+")");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(queryIcon.getImage());
		buildTablePanel();
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	private void buildTablePanel(){
		tablePanel = new JPanel();
		JScrollPane tableScroller = new JScrollPane(myTable);
		tableScroller.setPreferredSize(new Dimension(780,283));
		tablePanel.add(tableScroller);
		add(tablePanel, BorderLayout.CENTER);
	}
}
