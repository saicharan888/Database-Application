package com.wolfinn.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.wolfinn.Connection.ConnectionManager;
import com.wolfinn.beans.*;
import com.wolfinn.dao.CustomerDAO;

public class CustomerInfo {

	@SuppressWarnings("resource")
	private static Scanner scan = new Scanner(System.in);
//getting customer details
	public void getCustomerDetails()
	{
		
		boolean flag = true;
		
		System.out.println("***********Customer Options*********");
		System.out.println("1. Add New Customer");
		System.out.println("2. Update Customer Info");
		System.out.println("3. Delete Customer");
		System.out.println("4. Check Customer Details");
		System.out.println("Press other numbers to exit");
		try {
			while (flag) {
				System.out.println("Select an Option: ");
				int choice = Integer.valueOf(scan.nextLine());
				
				switch (choice) {
				case 1:System.out.println("Add New Customer Selected");
						insertNewCustomer();
					break;
	
				case 2:System.out.println("Update Customer Info Info");
						updateCustomerDetails();
					break;
				case 3:System.out.println("Delete Customer");
						deleteCustomer();
					break;
			
				case 4:System.out.println("Check customer details");
				checkCustomerDetails();
					break;
				default: System.exit(0);
					break;
				}
			}
			
		} 
		catch (SQLException e) {
			System.out.println("SQLException");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception");
		}
			
		}
	//inserting new customer
	public void insertNewCustomer() throws SQLException
	{		
		System.out.println("Enter Customer's ssn: ");
		String ssn = scan.nextLine();
		System.out.println("Enter Customer's email: ");
		String customerEmail = scan.nextLine();
		System.out.println("Enter Customer's name: ");
		String customerName = scan.nextLine();
		System.out.println("Enter Customer's date of birth: ");
		String dob = scan.nextLine();
		System.out.println("Enter Customer's Phone Number");
		String phoneNumber = scan.nextLine();
		System.out.println("Enter Custome's address: ");
		String address = scan.nextLine();
		
		CustomerDAO custDAO=new CustomerDAO();
		CustomerDetails customerObj = new CustomerDetails(ssn, customerEmail, customerName, dob, phoneNumber, address);
		
		boolean result = custDAO.insertCustomerDetails(customerObj);
		
		System.out.println("Customer successfully added");
		getCustomerDetails();
	}
	
	public void deleteCustomer() throws SQLException
	{
		
		System.out.println("Enter Customer's SSN to be deleted: ");
		String ssn = scan.nextLine();
		CustomerDAO custDAO=new CustomerDAO();
		boolean result = custDAO.deleteCustomer(ssn);
		
		System.out.println("Customer successfully deleted");
		
		getCustomerDetails();
	}
	
	public void updateCustomerDetails() throws SQLException
	{
		
		System.out.println("Enter SSN for Customer to be updated: ");
		String existingSsn = scan.nextLine();
		System.out.println("Enter new values:");
		System.out.println("Enter Customer's ssn: ");
		String ssn = scan.nextLine();
		System.out.println("Enter Customer's email: ");
		String customerEmail = scan.nextLine();
		System.out.println("Enter Customer's name: ");
		String customerName = scan.nextLine();
		System.out.println("Enter Customer's date of birth: ");
		String dob = scan.nextLine();
		System.out.println("Enter Customer's Phone Number");
		String phoneNumber = scan.nextLine();
		System.out.println("Enter Custome's address: ");
		String address = scan.nextLine();
		
		CustomerDAO custDAO=new CustomerDAO();

		CustomerDetails customerObj = new CustomerDetails(ssn, customerEmail, customerName, dob, phoneNumber, address);
		boolean result = custDAO.updateCustomerDetails(customerObj, existingSsn);
		
		System.out.println("Room successfully updated");
		
		getCustomerDetails();
	}
	
	public void checkCustomerDetails() throws SQLException
	{
		Connection conn = ConnectionManager.getConnection();
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter Customer ID: ");
		int customerId = Integer.valueOf(scan.nextLine());
		
		CustomerDAO custDAO=new CustomerDAO();
		CustomerDetails customerDetails = custDAO.checkCustomerInfo(customerId);
		conn.close();
		
		System.out.println("Following are the Customer Details");
		System.out.println("Customer's email: "+ customerDetails.getCustomerEmail());
		System.out.println("Customer's name: "+ customerDetails.getCustomerName());
		System.out.println("Customer's date of birth: "+ customerDetails.getDob());
		System.out.println("Customer's Phone Number: "+ customerDetails.getPhoneNumber());
		System.out.println("Custome's address: "+ customerDetails.getAddress());
		
		getCustomerDetails();
	}
}
