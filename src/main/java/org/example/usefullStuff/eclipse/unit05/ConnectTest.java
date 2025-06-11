package org.example.usefullStuff.eclipse.unit05;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		testConnection("fabian","fabian");
		

	}

	public static void testConnection(String user, String pwd) {
		String a = "jdbc:sqlserver://185.119.119.126:1433;databaseName=SP_Deutsch;integratedSecurity=false;encrypt=true;trustServerCertificate=true;";
	
		System.out.println("Connecting to database: " + a);
	
		try {
		Connection con = DriverManager.getConnection(a, user, pwd);
		

		
		if(con != null) {
			System.out.println(con.getMetaData());
			System.out.println("OK");
		}else {
			System.out.println("Keine Verbindnug m�glich");
		}
		
		
		
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getErrorCode());
		

		}
	}

}
