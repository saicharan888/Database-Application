package com.wolfinn.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.wolfinn.Connection.ConnectionManager;
import com.wolfinn.beans.ServicesBean;
import com.wolfinn.beans.StaffBean;

public class StaffServices {
	
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	public int addStaffDetails(int Hotel_Id)
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("\nEnter the Staff details\n");
		System.out.println("Enter the Staff_name");
		String staffName = sc.nextLine();
		System.out.println("Enter the Job_title");
		String jobTitle = sc.nextLine();
		System.out.println("Enter the Department");
		String department = sc.nextLine();
		System.out.println("Enter the Age");
		String age = sc.nextLine();
		System.out.println("Enter the Phone_number");
		String phone = sc.nextLine();
		System.out.println("Enter the Address");
		String address = sc.nextLine();
		int eflg = 1;
		try
		{
			conn = ConnectionManager.getConnection();
			stmt = conn.createStatement();	
			rs= stmt.executeQuery("INSERT into Staff(Hotel_Id, Staff_name, Job_title, Department, Age, Phone_number, address) "
					+ "values ("+String.valueOf(Hotel_Id)+","+staffName+","+jobTitle+","+department+","+age+","+phone+","+address+");");
			
		}
		catch(SQLException e1)
		{
			eflg = 0;
			System.out.println("Insertion failed as data provided is inconsistent with the database");
		}
		return eflg;
	}
	
	public int UpdateStaffDetails(int Hotel_Id, int Staff_Id)
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("\n Enter the fields you would like to update, leave blank to retain old values\n");
		System.out.println("Enter the new Staff_name");
		String nStaffName = sc.nextLine();
		System.out.println("Enter the new Job_title");
		String nJobTitle = sc.nextLine();
		System.out.println("Enter the new Department");
		String nDepartment = sc.nextLine();
		System.out.println("Enter the new Age");
		String nAge = sc.nextLine();
		System.out.println("Enter the new Phone_number");
		String nPhone = sc.nextLine();
		System.out.println("Enter the new Address");
		String nAddress = sc.nextLine();
		int flg=0;
		String qbegin = "UPDATE Staff set ";
		String qend = "where staff_id = "+String.valueOf(Staff_Id)+" and Hotel_id = "+String.valueOf(Hotel_Id)+";";
		if(nStaffName.length()>0)
		{ if(flg>0)
		{
			qbegin+=" , ";
		}
			flg++;
			qbegin = qbegin + "Staff_name = "+nStaffName+" ";
		}
		if(nJobTitle.length()>0)
		{
			if(flg>0)
			{
				qbegin+=" , ";
			}
			flg++;
			qbegin = qbegin + "Job_title = "+nJobTitle+" ";
		}
		if(nDepartment.length()>0)
		{
			if(flg>0)
			{
				qbegin+=" , ";
			}
			flg++;
			qbegin = qbegin + "Department = "+nDepartment+" ";
		}
		if(nAge.length()>0)
		{
			if(flg>0)
			{
				qbegin+=" , ";
			}
			flg++;
			qbegin = qbegin + "Age = "+nStaffName+" ";
		}
		if(nPhone.length()>0)
		{
			if(flg>0)
			{
				qbegin+=" , ";
			}
			flg++;
			qbegin = qbegin + "Phone_number = "+nPhone+" ";
		}
		if(nAddress.length()>0)
		{
			if(flg>0)
			{
				qbegin+=" , ";
			}
			flg++;
			qbegin = qbegin + "Address = "+nStaffName+" ";
		}
		int eflg = 1;
		try
		{
			conn = ConnectionManager.getConnection();
			stmt = conn.createStatement();	
			String sqlquery = qbegin+qend;
			System.out.println(sqlquery);
			stmt.executeQuery(sqlquery);
		}
		catch(SQLException e)
		{
			eflg = 0;
			System.out.println("Update failed as data provided is inconsistent with the database");
		}
		
		return eflg;
	}
	
	public void getStaff(int Hotel_Id)
	{
		List<StaffBean> ls1 = new ArrayList<StaffBean>();
		 try
			{
				conn = ConnectionManager.getConnection();
				stmt = conn.createStatement();	
				rs= stmt.executeQuery("SELECT Staff_id, Staff_name, Job_title, Department, Age, Phone_number, Address from Staff where hotel_id = "+String.valueOf(Hotel_Id)+" ;");
				while(rs.next())
				 {
					//System.out.println("At least 1 tuple exists");
					StaffBean sb = new StaffBean();
					 sb.setStaffId(rs.getInt("Staff_id"));
					 sb.setStaffName(rs.getString("Staff_name"));
					 sb.setJobTitle(rs.getString("Job_title"));
					 sb.setDepartment(rs.getString("Department"));
					 sb.setAge(rs.getString("Age"));
					 sb.setPhoneNumber(rs.getString("Phone_number"));
					 sb.setAddress(rs.getString("Address"));
					 ls1.add(sb);
				 }
			}
			catch(SQLException e1)
			{
				System.out.println("Data fetch failed as data provided is inconsistent with the database");
				System.exit(1);
			}
		 
		    System.out.format("%5s%15s%15s%15s%4s%10s%20s", "ID","Name","Title", "Dept.", "Age", "Phone", "Address");
			System.out.printf("%n");
			for(int i=0;i<ls1.size();i++)
			{
				int staffid=ls1.get(i).getStaffId();
				String name = ls1.get(i).getStaffName();
				String title = ls1.get(i).getJobTitle();
				String dept = ls1.get(i).getDepartment();
				String age = ls1.get(i).getAge();
				String phone = ls1.get(i).getPhoneNumber();
				String address = ls1.get(i).getAddress();
				System.out.format("%5s%15s%15s%15s%4s%10s%20s",staffid,name,title,dept,age,phone,address);
				System.out.printf("%n");

			}
	}
	
	public int DeleteStaff(int Hotel_Id, int Staff_Id)
	{
		int eflg = 1;
		try
		{
			conn = ConnectionManager.getConnection();
			stmt = conn.createStatement();	
			String sqlquery = "DELETE from Staff where Staff_id = "+String.valueOf(Staff_Id)+" and Hotel_id = "+String.valueOf(Hotel_Id)+" ; ";
			System.out.println(sqlquery);
			stmt.executeQuery(sqlquery);
		}
		catch(SQLException e)
		{
			eflg = 0;
			System.out.println("Deletion failed as data provided is inconsistent with the database");
		}
		
		return eflg;
	}

	public int AssignStaff(int Hotel_Id, int Reservation_Id)
	{
		int eflg = 1;
		try
		{
			conn = ConnectionManager.getConnection();
			stmt = conn.createStatement();	
			String query = "SELECT Staff_id from Staff where Department = 'Catering' and Hotel_id = "+String.valueOf(Hotel_Id)+" and Serving_premium = 0; ";
			ResultSet rs1 = stmt.executeQuery(query);
			query = "SELECT Staff_id from Staff where Department = 'Service Staff' and Hotel_id = "+String.valueOf(Hotel_Id)+" and Serving_premium = 0; ";
			ResultSet rs2 = stmt.executeQuery(query);
			if(rs1.next() && rs2.next())
			{
				int id1 = rs1.getInt("Staff_id");
				int id2 = rs2.getInt("Staff_id");
				stmt.executeQuery("UPDATE Staff set Serving_premium = "+String.valueOf(Reservation_Id)+" where Staff_id = "+String.valueOf(id1)+" and Hotel_id = "+String.valueOf(Hotel_Id)+";");
				stmt.executeQuery("UPDATE Staff set Serving_premium = "+String.valueOf(Reservation_Id)+" where Staff_id = "+String.valueOf(id2)+" and Hotel_id = "+String.valueOf(Hotel_Id)+";");
				String temp = "INSERT into Services_used(Hotel_id,Staff_id,service_id,reservation_id) values("+String.valueOf(Hotel_Id)+","+String.valueOf(id1)+","+String.valueOf(-1)+","+String.valueOf(Reservation_Id)+");"; 
				stmt.executeQuery(temp);
				temp = "INSERT into Services_used(Hotel_id,Staff_id,service_id,reservation_id) values("+String.valueOf(Hotel_Id)+","+String.valueOf(id2)+","+String.valueOf(-1)+","+String.valueOf(Reservation_Id)+");";
				stmt.executeQuery(temp);
			}
			else
			{
				eflg = 0;
				System.out.println("Insufficient staff, allocation not possible");
			}
		}
		catch(SQLException e)
		{
			eflg = 0;
			System.out.println("Query failed as data provided is inconsistent with the database");
		}
		
		return eflg;
	}

	public int ReleaseStaff(int Reservation_Id)
	{
		int eflg = 1;
		
		try
		{
			conn = ConnectionManager.getConnection();
			stmt = conn.createStatement();	
			String query = "Update Staff set Serving_premium = 0  where Serving_premium = "+String.valueOf(Reservation_Id)+";";
			stmt.executeUpdate(query);
		}
		catch(SQLException e)
		{
			eflg = 0;
			System.out.println("Query failed as data provided is inconsistent with the database");
		}
		

		
		return eflg;
	}
}
