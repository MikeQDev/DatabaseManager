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
	public Connection conn;
	public DBInteracter(Connection c, String user, String pass){
		dbUser = user;
		dbPass = pass;
		conn = c;
	/*	try {
			conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error connecting to db");
		}*/
	}
	public void sqlUpdate(String query){
		Statement stmt = null;
		try{
			if(query.toUpperCase().startsWith("DROP")){
				JOptionPane.showMessageDialog(null,"Not dropping this table..","NO.",JOptionPane.WARNING_MESSAGE);
			}else{
				stmt = conn.createStatement();
				stmt.executeUpdate(query);
				JOptionPane.showMessageDialog(null,"Command ran succesfully.","Success",JOptionPane.INFORMATION_MESSAGE);
				//conn.close();
				//stmt.close();
			}
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(null,ex,"Error",JOptionPane.WARNING_MESSAGE);
		}finally{
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public boolean checkAdmin(){
		Statement stmt = null;
		try{
			//Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			stmt = conn.createStatement();
			stmt.executeUpdate("REVOKE ALL PRIVILEGES ON * . * FROM 'pma'@'localhost'");
			//conn.close();
			//stmt.close();
			return true;
		}catch(SQLException ex){
			return false;
		}finally{
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public void modifyUser(String user, int accType){
		Statement stmt = null;
		/*if(user.equals(dbUser)){
			JOptionPane.showMessageDialog(null, "Not modifying yourself, please" +
					" use a different account with administrator credentials" +
					" to modify \'"+user+"\''s user privileges.");
			return;
		}
		THIS IS NOT NEEDED.
		*/
		try{
			//Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			stmt = conn.createStatement();
			if(accType==0){
				try{
					stmt.executeUpdate("REVOKE ALL PRIVILEGES ON * . * FROM '"+user+"'@'localhost'");
					stmt.executeUpdate("REVOKE GRANT OPTION ON * . * FROM '"+user+"'@'localhost'");
					stmt.executeUpdate("GRANT SELECT ON * . * TO '"+user+"'@'localhost' WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0");
					JOptionPane.showMessageDialog(null,"User '"+user+"' is now a user.","User modified",JOptionPane.INFORMATION_MESSAGE);
				}catch(SQLException ex){
					JOptionPane.showMessageDialog(null,"An error occured while trying to change privileges for "+user+". "+user+" may not exist?","Error",JOptionPane.WARNING_MESSAGE);
				}
			}else if(accType==1){
				try{
					stmt.executeUpdate("REVOKE ALL PRIVILEGES ON * . * FROM '"+user+"'@'localhost'");
					stmt.executeUpdate("GRANT ALL PRIVILEGES ON * . * TO '"+user+"'@'localhost' WITH GRANT OPTION  MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0  MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0");
					JOptionPane.showMessageDialog(null,"User '"+user+"' is now an admin.","User modified",JOptionPane.INFORMATION_MESSAGE);
				}catch(SQLException ex){
					JOptionPane.showMessageDialog(null,"An error occured while trying to change privileges for "+user+". "+user+" may not exist?","Error",JOptionPane.WARNING_MESSAGE);
				}
			}
			//conn.close();
			//stmt.close();
		}catch(SQLException ex){
			System.err.println(ex);
		}finally{
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public void addUser(String name, String pass, int accType){
		Statement stmt = null;
		try{
			//Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			stmt = conn.createStatement();
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
					JOptionPane.showMessageDialog(null,"Admin '"+name+"' created.","User added",JOptionPane.INFORMATION_MESSAGE);
				}catch(SQLException ex){
					JOptionPane.showMessageDialog(null,"Error adding admin. (Admin may already exist?)","Error",JOptionPane.WARNING_MESSAGE);
				}
			}
			//conn.close();
			//stmt.close();
		}catch(SQLException ex){
			System.err.println(ex);
		}finally{
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public void deleteRecord(int id){
		Statement stmt = null;
		try{
			//Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			stmt = conn.createStatement();
			int results = stmt.executeUpdate("DELETE FROM UserInfo WHERE ID="+id);
			if(results!=0)
				JOptionPane.showMessageDialog(null, "ID "+id+" successfully deleted.","Record deleted",JOptionPane.PLAIN_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, "Record ID "+id+" not found.","Error",JOptionPane.WARNING_MESSAGE);
			//conn.close();
			//stmt.close();
		}catch(SQLException ex){
			System.err.println(ex);
		}finally{
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public void deleteUser(String user){
		Statement stmt = null;
		if(user.equals(dbUser)){
			JOptionPane.showMessageDialog(null, "Not deleting yourself, please" +
					" use a different account with administrator credentials" +
					" to delete \'"+user+"\'.");
			return;
		}
		try{
			//Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			stmt = conn.createStatement();
			try{
				stmt.executeUpdate("DROP USER '"+user+"'@'localhost'");
				JOptionPane.showMessageDialog(null,"User '"+user+"' deleted.","User deleted", JOptionPane.PLAIN_MESSAGE);
			}catch(SQLException ex){
				JOptionPane.showMessageDialog(null,"Unable to delete user '"+user+"'. (User may not exist?)","Error",JOptionPane.WARNING_MESSAGE);
			}
			
			//conn.close();
			//stmt.close();
		}catch(SQLException ex){
			System.err.println(ex);
		}finally{
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public boolean recordExists(int id){
		boolean status = false;
		Statement stmt = null;
		try{
			//Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery("SELECT * FROM UserInfo WHERE ID="+id);
			if(results.next())
				status = true;
			//conn.close();
			//stmt.close();
		}catch(SQLException ex){
			System.err.println(ex);
		}finally{
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return status;
	}
	public int getInfo(int id){
		int result = 0;
		Statement stmt = null;
		try{
			//Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			stmt = conn.createStatement();
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
				//conn.close();
				//stmt.close();
			}else{
				JOptionPane.showMessageDialog(null,"Record ID "+id+" not found.", "Error",JOptionPane.WARNING_MESSAGE);
			}
		}catch(SQLException ex){
			System.err.println(ex);
		}finally{
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}
	public void addRecord(String fName, String lName, String phone, String email, String addr, String city, String state, String ZIP, String date){
		Statement stmt = null;
		try{
			//Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO UserInfo VALUES (0,'"+fName+"','"+lName+"','"+phone+
								"','"+email+"','"+date+"','"+addr+"','"+city+"','"+state+"','"+ZIP+"')");
			JOptionPane.showMessageDialog(null, "Record succesfully added.");
			//conn.close();
			//stmt.close();
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(null,ex,"Error",JOptionPane.WARNING_MESSAGE);
		}finally{
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public void updateRecord(int id, String fName, String lName, String phone, String email, String addr, String city, String state, String ZIP, String date){
		Statement stmt = null;
		try{
			//Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE UserInfo SET FirstName='"+fName+"',LastName='"+lName+"',Phone='"+phone+
								"',Email='"+email+"',StreetAddr='"+addr+"',City='"+city+"',State='"+state+
								"',ZIP='"+ZIP+"',BirthDate='"+date+"' WHERE ID="+id);
			JOptionPane.showMessageDialog(null, "Record succesfully updated.");
			//conn.close();
			//stmt.close();
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(null,ex,"Error",JOptionPane.WARNING_MESSAGE);
		}finally{
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public void createQueryTable(String query){
		Statement stmt = null;
		try{
			//Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			stmt = conn.createStatement();
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
			//conn.close();
			//stmt.close();
			myTable = new JTable(tableData, tableCols);
			myTable.setEnabled(false);
			new ResultTableGUI(myTable, query);
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(null,ex,"Error",JOptionPane.WARNING_MESSAGE);
		}finally{
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public JTable getTable(){
		Statement stmt = null;
		try{
			//Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
			stmt = conn.createStatement();
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
			//conn.close();
			//stmt.close();
			myTable = new JTable(tableData, tableCols);
			myTable.setEnabled(false);
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(null, "An error occured while "+
				"attempting to access the table; maybe table does not exist?"+
					" \nPlease restart the program.");
		}finally{
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
