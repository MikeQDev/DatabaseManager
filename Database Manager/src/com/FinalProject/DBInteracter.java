package com.FinalProject;

import java.sql.*;

import javax.swing.*;

public class DBInteracter {
	private final String DB_URL = "jdbc:mysql://localhost/userdb";
	private String dbUser, dbPass;
	private String getIDStr, getFirstStr, getLastStr, getPhoneStr, getEmailStr,
					getAddressStr, getCityStr, getStateStr, getZIPStr, getDateStr;
	private String[][] tableData;
	private String[] tableCols;
	private JTable myTable;
	public DBInteracter(String user, String password){
		dbUser = user;
		dbPass = password;
	}
	public void sqlUpdate(String query){
		try{
			if(query.toUpperCase().startsWith("DROP")){
				JOptionPane.showMessageDialog(null,"Not dropping this table..","NO.",JOptionPane.WARNING_MESSAGE);
			}else{
				Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(query);
				JOptionPane.showMessageDialog(null,"Command ran succesfully.","Success",JOptionPane.INFORMATION_MESSAGE);
				conn.close();
				stmt.close();
			}
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(null,ex,"Error",JOptionPane.WARNING_MESSAGE);
		}
	}
	public boolean checkAdmin(){
		try{
			Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("REVOKE ALL PRIVILEGES ON * . * FROM 'pma'@'localhost'");
			conn.close();
			stmt.close();
			return true;
		}catch(SQLException ex){
			return false;
		}
	}
	public void modifyUser(String user, int accType){
		try{
			Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			Statement stmt = conn.createStatement();
			if(accType==0){
				try{
					stmt.executeUpdate("REVOKE ALL PRIVILEGES ON * . * FROM 'a'@'localhost'");
					stmt.executeUpdate("REVOKE GRANT OPTION ON * . * FROM 'a'@'localhost'");
					stmt.executeUpdate("GRANT SELECT ON * . * TO 'a'@'localhost' WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0");
					JOptionPane.showMessageDialog(null,"User '"+user+"' is now a user.","User modified",JOptionPane.INFORMATION_MESSAGE);
				}catch(SQLException ex){
					JOptionPane.showMessageDialog(null,ex,"Error",JOptionPane.WARNING_MESSAGE);
				}
			}else if(accType==1){
				try{
					stmt.executeUpdate("REVOKE ALL PRIVILEGES ON * . * FROM 'a'@'localhost'");
					stmt.executeUpdate("GRANT ALL PRIVILEGES ON * . * TO 'a'@'localhost' WITH GRANT OPTION  MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0  MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0");
					JOptionPane.showMessageDialog(null,"User '"+user+"' is now an admin.","User modified",JOptionPane.INFORMATION_MESSAGE);
				}catch(SQLException ex){
					JOptionPane.showMessageDialog(null,ex,"Error",JOptionPane.WARNING_MESSAGE);
				}
			}
			conn.close();
			stmt.close();
		}catch(SQLException ex){
			System.err.println(ex);
		}
	}
	public void addUser(String name, String pass, int accType){
		try{
			Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			Statement stmt = conn.createStatement();
			if(accType==0){
				try{
					stmt.executeUpdate("CREATE USER '"+name+"'@'localhost' IDENTIFIED BY '"+pass+"';");
					stmt.executeUpdate("GRANT SELECT ON * . * TO '"+name+"'@'localhost' IDENTIFIED BY '"+pass+"' WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0 ;");
					JOptionPane.showMessageDialog(null,"User '"+name+"' created.","User added",JOptionPane.INFORMATION_MESSAGE);
				}catch(SQLException ex){
					JOptionPane.showMessageDialog(null,"Error adding user. (User may already exit?)","Error",JOptionPane.WARNING_MESSAGE);
				}
			}else if(accType==1){
				try{
					stmt.executeUpdate("CREATE USER '"+name+"'@'localhost' IDENTIFIED BY '"+pass+"';");
					stmt.executeUpdate("GRANT ALL PRIVILEGES ON * . * TO '"+name+"'@'localhost' IDENTIFIED BY '"+pass+"' WITH GRANT OPTION MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0 ;");
					JOptionPane.showMessageDialog(null,"User '"+name+"' created.","User added",JOptionPane.INFORMATION_MESSAGE);
				}catch(SQLException ex){
					JOptionPane.showMessageDialog(null,"Error adding user. (User may already exist?)","Error",JOptionPane.WARNING_MESSAGE);
				}
			}
			conn.close();
			stmt.close();
		}catch(SQLException ex){
			System.err.println(ex);
		}
	}
	public void deleteRecord(int id){
		try{
			Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			Statement stmt = conn.createStatement();
			int results = stmt.executeUpdate("DELETE FROM UserInfo WHERE ID="+id);
			if(results!=0)
				JOptionPane.showMessageDialog(null, "ID "+id+" successfully deleted.","Record deleted",JOptionPane.PLAIN_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, "Record ID "+id+" not found.","Error",JOptionPane.WARNING_MESSAGE);
			conn.close();
			stmt.close();
		}catch(SQLException ex){
			System.err.println(ex);
		}
	}
	public void deleteUser(String user){
		try{
			Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			Statement stmt = conn.createStatement();
			try{
				stmt.executeUpdate("DROP USER '"+user+"'@'localhost'");
				JOptionPane.showMessageDialog(null,"User '"+user+"' deleted.","User deleted", JOptionPane.PLAIN_MESSAGE);
			}catch(SQLException ex){
				JOptionPane.showMessageDialog(null,"Unable to delete user '"+user+"'. (User may not exist?)","Error",JOptionPane.WARNING_MESSAGE);
			}
			
			conn.close();
			stmt.close();
		}catch(SQLException ex){
			System.err.println(ex);
		}
	}
	public boolean recordExists(int id){
		boolean status = false;
		try{
			Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery("SELECT * FROM UserInfo WHERE ID="+id);
			if(results.next())
				status = true;
			conn.close();
			stmt.close();
		}catch(SQLException ex){
			System.err.println(ex);
		}
		return status;
	}
	public int getInfo(int id){
		int result = 0;
		try{
			Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery("SELECT * FROM UserInfo WHERE ID="+id);
			if(results.next()){
				getIDStr = results.getString("ID");
				getFirstStr = results.getString("FirstName");
				getLastStr = results.getString("LastName");
				getPhoneStr = results.getString("Phone");
				getEmailStr = results.getString("Email");
				getAddressStr = results.getString("StreetAddr");
				getCityStr = results.getString("City");
				getStateStr = results.getString("State");
				getZIPStr = results.getString("ZIP");
				getDateStr = results.getString("Birthdate");
				result = 1;
				conn.close();
				stmt.close();
			}else{
				JOptionPane.showMessageDialog(null,"Record ID "+id+" not found.", "Error",JOptionPane.WARNING_MESSAGE);
			}
		}catch(SQLException ex){
			System.err.println(ex);
		}
		return result;
	}
	public void addRecord(String fName, String lName, String phone, String email, String addr, String city, String state, String ZIP, String date){
		try{
			Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO UserInfo VALUES (0,'"+fName+"','"+lName+"','"+phone+
								"','"+email+"','"+date+"','"+addr+"','"+city+"','"+state+"','"+ZIP+"')");
			JOptionPane.showMessageDialog(null, "Record succesfully added.");
			conn.close();
			stmt.close();
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(null,ex,"Error",JOptionPane.WARNING_MESSAGE);
		}
	}
	public void updateRecord(int id, String fName, String lName, String phone, String email, String addr, String city, String state, String ZIP, String date){
		try{
			Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE UserInfo SET FirstName='"+fName+"',LastName='"+lName+"',Phone='"+phone+
								"',Email='"+email+"',StreetAddr='"+addr+"',City='"+city+"',State='"+state+
								"',ZIP='"+ZIP+"',BirthDate='"+date+"' WHERE ID="+id);
			JOptionPane.showMessageDialog(null, "Record succesfully updated.");
			conn.close();
			stmt.close();
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(null,ex,"Error",JOptionPane.WARNING_MESSAGE);
		}
	}
	public void createQueryTable(String query){
		try{
			Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery(query);
			ResultSetMetaData meta = results.getMetaData();
			int colCount = meta.getColumnCount();
			results.last();
			int totRows = results.getRow();
			results.first();
			tableCols = new String[colCount];
			tableData = new String[totRows][colCount];
			for(int i=0; i<colCount; i++){
				tableCols[i] = meta.getColumnLabel(i+1);
			}
			for(int rows=0; rows<totRows; rows++){
				for(int cols=0; cols<colCount; cols++){
					tableData[rows][cols] = results.getString(cols+1);
				}
				results.next();
			}
			conn.close();
			stmt.close();
			myTable = new JTable(tableData, tableCols);
			myTable.setEnabled(false);
			new ResultTableGUI(myTable, query);
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(null,ex,"Error",JOptionPane.WARNING_MESSAGE);
		}
	}
	public JTable getTable(){
		try{
			Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery("SELECT * FROM UserInfo");
			ResultSetMetaData meta = results.getMetaData();
			int colCount = meta.getColumnCount();
			results.last();
			int totRows = results.getRow();
			results.first();
			tableCols = new String[colCount];
			tableData = new String[totRows][colCount];
			for(int i=0; i<colCount; i++){
				tableCols[i] = meta.getColumnLabel(i+1);
			}
			for(int rows=0; rows<totRows; rows++){
				for(int cols=0; cols<colCount; cols++)
					tableData[rows][cols] = results.getString(cols+1);
				results.next();
			}
			conn.close();
			stmt.close();
			myTable = new JTable(tableData, tableCols);
			myTable.setEnabled(false);
		}catch(SQLException ex){
			System.err.println(ex);
		}
		return myTable;
	}
	public String getID(){
		return getIDStr;
	}
	public String getFirst(){
		return getFirstStr;
	}
	public String getLast(){
		return getLastStr;
	}
	public String getPhone(){
		return getPhoneStr;
	}
	public String getEmail(){
		return getEmailStr;
	}
	public String getAddress(){
		return getAddressStr;
	}
	public String getCity(){
		return getCityStr;
	}
	public String getState(){
		return getStateStr;
	}
	public String getZIP(){
		return getZIPStr;
	}
	public String getDate(){
		return getDateStr;
	}
}
