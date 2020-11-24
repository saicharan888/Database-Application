package com.wolfinn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.wolfinn.beans.Customer;
import com.wolfinn.beans.CustomerDetails;

import com.wolfinn.Connection.ConnectionManager;

public class CustomerDAO {

	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	public boolean insertCustomerDetails(CustomerDetails customer){

		try {
			ps = conn.prepareStatement("INSERT into Customer_details(SSN,Customer_email,Customer_name,DOB,Phone_number,Address) VALUES (?,?,?,?,?,?);");
			ps.setString(1, customer.getSsn());
			ps.setString(2, customer.getCustomerEmail());
			ps.setString(3, customer.getCustomerName());
			ps.setString(4, customer.getDob());
			ps.setString(5, customer.getPhoneNumber());
			ps.setString(6, customer.getAddress());
			
			ps.executeUpdate();
			ps.close();
			insertCustomer(customer.getSsn());
			
		} catch (SQLException e) {
			System.out.println("SQLException");
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception");
		}
		return false;
	}
	
	public boolean insertCustomer(String ssn){

		try {
			conn = ConnectionManager.getConnection();

			ps = conn.prepareStatement("INSERT into Customer(SSN) values (?);");
			ps.setString(1, ssn);
			
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("SQLException");
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception");
		}
		finally{
		      //finally block used to close resources
		      try{
		         if(ps!=null)
		            conn.close();
		      }catch(SQLException se){
		      }// do nothing
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }
		return false;
	}
	
	
	public boolean deleteCustomer(String ssn){

		try {
			conn = ConnectionManager.getConnection();

			ps = conn.prepareStatement("DELETE FROM Customer_details WHERE SSN = ?");
			ps.setString(1, ssn);
			
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("Error while deleting Customer");
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception");
		}
		finally{
		      //finally block used to close resources
		      try{
		         if(ps!=null)
		            conn.close();
		      }catch(SQLException se){
		      }// do nothing
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }
		return false;
	}
	
	public boolean updateCustomerDetails(CustomerDetails customer, String existingSsn){

		try {
			conn = ConnectionManager.getConnection();

			ps = conn.prepareStatement("UPDATE Customer_details SET SSN = ?,Customer_email = ?,Customer_name = ?,DOB = ?,Phone_number = ?,Address = ? WHERE SSN = ?");
			ps.setString(1, customer.getSsn());
			ps.setString(2, customer.getCustomerEmail());
			ps.setString(3, customer.getCustomerName());
			ps.setString(4, customer.getDob());
			ps.setString(5, customer.getPhoneNumber());
			ps.setString(6, customer.getAddress());
			ps.setString(7, existingSsn);
			
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("Error while updating customer info");
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception");
		}
		finally{
		      //finally block used to close resources
		      try{
		         if(ps!=null)
		            conn.close();
		      }catch(SQLException se){
		      }// do nothing
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }
		return false;
	}
	
	
	public CustomerDetails checkCustomerInfo(int customerId){
		CustomerDetails custObj = new CustomerDetails();
		PreparedStatement ps=null;
		try {
			conn = ConnectionManager.getConnection();

			ps = conn.prepareStatement("SELECT Customer_email,Customer_name,DOB,Phone_number,Address FROM Customer_details"
					+ " WHERE SSN IN (SELECT SSN FROM Customer WHERE Customer_id = ?)");
			ps.setInt(1, customerId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				
				
				custObj.setCustomerName(rs.getString("Customer_name"));
				custObj.setCustomerEmail(rs.getString("Customer_email"));
				custObj.setDob(rs.getString("DOB"));
				custObj.setPhoneNumber(rs.getString("Phone_number"));
				custObj.setAddress(rs.getString("Address"));
						
			}
			
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("Error while updating room info");
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception");
		}
		finally{
		      //finally block used to close resources
		      try{
		         if(ps!=null)
		            conn.close();
		      }catch(SQLException se){
		      }// do nothing
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }		return custObj;
	}
}
