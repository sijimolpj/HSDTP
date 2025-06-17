package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class DatabaseConnector {
	
	static Connection con;
	java.sql.Statement st;
	static int idd[]=new int[200000];
	static ArrayList<Long> Id=new ArrayList<Long>();
	static ArrayList<String> wordlist=new ArrayList<String>();
	public DatabaseConnector(String db)
	{
		try
		{
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://127.0.0.1/"+ db +"","root","");
				 
		}
		catch(Exception r)
		{
		JOptionPane.showMessageDialog(null,"U shud CreateDatabase: DatabaseEror","Log Analyser",JOptionPane.ERROR_MESSAGE);
		r.printStackTrace();
		}
	}
	public Connection getConnectionToDB()
	{
		try
		{
		st=con.createStatement();
		System.out.println("Connection created");
		 
		}
		catch(Exception r)
		{
		JOptionPane.showMessageDialog(null,"U shud CreateDatabase: DatabaseEror","Log Analyser",JOptionPane.ERROR_MESSAGE);
		r.printStackTrace();
		}
		return con;
	}
	public void insert(Long did,String word)
	{
		try{
		//int id;
		PreparedStatement pr=con.prepareStatement("insert into symptomrevdb(D_ID,SymptomWord) values(?,?)");
		//pr.setLong(1,id);
		pr.setLong(1, did);
		pr.setString(2, word);
		
		pr.executeUpdate();
		System.out.println("inserted");
		}
		catch(SQLException e)
		{
			System.out.println(e);
			}
	
	}
	
	
	public String returnDiseaseName(int did)
	{
		
		String word = null;
		String Qry="select diseaseName from diseasetbl where D_ID=?";
		PreparedStatement PS=null;
		ResultSet RS1 = null;
		try {
			PS = con.prepareStatement(Qry);
			
			PS.setLong(1,did);
			RS1=PS.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	try {
			if(RS1.next())
			{
				word=RS1.getString(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return word;
	}
	
	
	public void returnDid() throws SQLException
	{	PreparedStatement PS=null;
		String Qry="select D_ID from diseasetbl";
		ResultSet RS1 = null;
		try {
			PS = con.prepareStatement(Qry);
			RS1=PS.executeQuery();
			
			while(RS1.next())
			{
				 
				 Id.add(RS1.getLong(1));
			}
			System.out.println("id done.."+Id);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}
		public ArrayList<Long> returnDidfromSymptomDB() throws SQLException
		{	PreparedStatement PS=null;
			String Qry="select D_ID from symptomdefinitiondb";
			ResultSet RS1 = null;
			try {
				PS = con.prepareStatement(Qry);
				RS1=PS.executeQuery();
				
				while(RS1.next())
				{
					 
					 Id.add(RS1.getLong(1));
				}
				System.out.println("id done.."+Id);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
			return Id;
	}
		public String returnSymptomSentence(Long id)
		{
			String sent = null;
			//int i=0;
			String Qry="select symptomDef from symptomdefinitiondb WHERE D_ID=? ";
			PreparedStatement PS=null;
			ResultSet RS1 = null;
			try {
				PS = con.prepareStatement(Qry);
				
				PS.setLong(1,id);
				RS1=PS.executeQuery();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		try {
				while(RS1.next())
				{
					sent=RS1.getString(1);
					//return true;
					//i++;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return sent;
		}
	

	/*public static void main(String[] args) throws SQLException {
		DatabaseConnector d=new DatabaseConnector("symptomdb");
		d.returnDid();
		String dname=d.returnDiseaseName(1);
	
		System.out.println(dname);
	}
	*/
}
	


