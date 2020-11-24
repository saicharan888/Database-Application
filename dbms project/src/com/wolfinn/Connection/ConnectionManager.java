package com.wolfinn.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	
	 	private static String urlstring = "jdbc:mariadb://localhost:3306/test";    
	    private static String driverName = "org.mariadb.jdbc.Driver";   
	    private static String username = "root";   
	    private static String password = "sachinnihcas";
	    private static Connection con;

	    public static Connection getConnection() {
	        try {
	            Class.forName(driverName);
	            try {
	                con = DriverManager.getConnection(urlstring, username, password);
	            } catch (SQLException ex) {
	                System.out.println("Failed to create the database connection."); 
	            }
	        } catch (ClassNotFoundException ex) {
	            // log an exception. for example:
	            System.out.println("Driver not found."); 
	        }
	        return con;
	    }

}
