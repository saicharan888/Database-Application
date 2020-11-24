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

public class ServicesClass {
	
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	public int EnterService(int Hotel_id)
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Reservation ID");
		int reservation_id = sc.nextInt();
		System.out.println("Enter the Service ID");
		int service_id = sc.nextInt();
		System.out.println("Enter the Staff ID");
		int staff_id = sc.nextInt();
		int eflg = 1;
		try
		{
			conn = ConnectionManager.getConnection();
			stmt = conn.createStatement();	
			String sqlquery="INSERT into Services_used(Hotel_Id,Staff_Id,Service_Id,Reservation_Id) values("+Hotel_id+","+staff_id+","+service_id+","+reservation_id+")";
			//System.out.println(sqlquery);
			stmt.executeQuery(sqlquery);
		}
		catch(SQLException e1)
		{
		System.out.println("Services insertion failed please check your input parameters");
		eflg = 0;
		}
		return eflg;
	}
	
	public int UpdateService()
	{
		int eflg = 1;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Service Instance ID");
		int service_inst_id = sc.nextInt();
		System.out.println("Enter the new Service ID");
		int nservice_id = sc.nextInt();
		try
		{
			conn = ConnectionManager.getConnection();
			stmt = conn.createStatement();	
			stmt.executeUpdate("UPDATE Services_used SET Service_Id = "+nservice_id+" where service_instance_id = "+service_inst_id+";");
		}
		catch(SQLException e1)
		{
		System.out.println("Services updation failed please check your input parameters");
		eflg = 0;
		}
		return eflg;
	}
	
	public int addService(int Hotel_id)
	 {
		int eflg = 1;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Service Name");
		String service_name = sc.nextLine();
		System.out.println("Enter the Service Price");
		int service_price = sc.nextInt();
		try
		{
			conn = ConnectionManager.getConnection();
			stmt = conn.createStatement();	
			stmt.executeQuery("INSERT into Services(Service_name, Service_price) values ("+service_name+","+service_price+");");
		}
		catch(SQLException e1)
		{
		System.out.println("Services addition failed please check your input parameters");
		eflg = 0;
		}
		return eflg;
	 }
	 
	public int deleteService()
	 {
		 int eflg = 1;
		 Scanner sc = new Scanner(System.in);
		 System.out.println("Enter the Service id");
		 String service_id = sc.nextLine();
		 try
			{
				conn = ConnectionManager.getConnection();
				stmt = conn.createStatement();	
				stmt.executeQuery("DELETE from Services where Service_id ="+service_id+";");
			}
			catch(SQLException e1)
			{
			System.out.println("Services deletion failed please check your input parameters");
			eflg = 0;
			}
		 return eflg;
	 }
	 
	public List<ServicesBean> getServices()
	 {
		 List<ServicesBean> ls1 = new ArrayList<ServicesBean>();
		 try
			{
				conn = ConnectionManager.getConnection();
				stmt = conn.createStatement();	
				rs= stmt.executeQuery("SELECT Service_id, Service_name, Service_price from services;");
				while(rs.next())
				 {
					ServicesBean s1 = new ServicesBean();
					 s1.setService_id(rs.getInt("Service_id"));
					 s1.setService_name(rs.getString("Service_name"));
					 s1.setService_price(rs.getFloat("Service_price"));
					 ls1.add(s1);
				 }
			}
			catch(SQLException e1)
			{
			System.out.println("Error Encountered");
			}
		 
		 return ls1;
	 }

}
