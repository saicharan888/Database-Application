package com.wolfinn.model;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.wolfinn.dao.ReportDAO;
import com.wolfinn.dao.ReportDAOtest;

public class Managerclass {
	
	//manager options
	public void manageroptions(int hotelId) throws SQLException
	{
		System.out.println("Welcome Manager to the system");
		System.out.println("1. Tasks/Operations \n");
		System.out.println("2. Service Records \n");
		System.out.println("3. Billing Accounts \n");
		System.out.println("4. Reports \n");
		System.out.println("Press any other key to Exit \n");
		
		BillingAccounts billaccts=new BillingAccounts();
		ServiceStaffClass servClass=new ServiceStaffClass();
		TasksClass tasksClass=new TasksClass();

		Scanner scan= new Scanner(System.in);
		int option= scan.nextInt();
		
		if (option==1)
		{
			tasksClass.taskoptions("manager",hotelId);
		}
		else if(option==2)
		{
		   servClass.servicestffOptions(hotelId);;
		}
		else if(option==3)
		{
	       billaccts.getBillDetails(hotelId);

		}
		else if(option==4)
		{
			reportOptions(hotelId,"Manager");
		}
		else
		{
			System.exit(0);
		}
		scan.close();
			
	}

	public void Executivemanageroptions() throws SQLException
	{
		System.out.println("Welcome Executive Manager to the system");
		System.out.println("1. Tasks/Operations \n");
		System.out.println("2. Service Records \n");
		System.out.println("3. Billing Accounts/Check-out \n");
		System.out.println("4. Reports \n");
		System.out.println("Press any other key to Exit \n");
		
		Scanner scan= new Scanner(System.in);
		int option= scan.nextInt();
		TasksClass taskc=new TasksClass();
		BillingAccounts billaccts=new BillingAccounts();
		ServiceStaffClass servClass=new ServiceStaffClass();
		TasksClass tasksClass=new TasksClass();

		if (option==1)
		{
			taskc.taskoptions("EM",0);
			
		}
		else if(option==2)
		{
			System.out.println("Please enter Hotel id");
			Scanner scan2= new Scanner(System.in);

            int hotelId=scan2.nextInt();
            
			servClass.servicestffOptions(hotelId);

		}
		else if(option==3)
		{
			System.out.println("Please enter Hotel id");
			Scanner scan2= new Scanner(System.in);

            int hotelId=scan2.nextInt();
            
			billaccts.getBillDetails(hotelId);
		}
		else if(option==4)
		{
			Scanner scan2= new Scanner(System.in);
			System.out.println("Please enter Hotel id");
            int hotelId=scan2.nextInt();
			reportOptions(hotelId,"ExecutiveManager");
		}
		else
		{
			System.exit(0);
		}
			
	}
	
	public void reportOptions(int hotelId,String role)
	{

		System.out.println("Select one from below");
		System.out.println("1. Occupancy Report by Hotel\n2. Occupancy Report by Room Type\n3. Occupancy Report by Date Range\n4. Occupancy Report by City"
				+ "\n5. Information on staff grouped by their role\n6. Information on all staff members serving the customer during the stay\n7. Revenue report for a Hotel in a date range");
		Scanner scan = new Scanner(System.in);
		int input= scan.nextInt();
		ReportHelper rh = new ReportHelper();
		switch (input) {
			
		case 1: System.out.println("Occupancy Report by Hotel ");
			try {
				rh.report1(hotelId, role);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 2: System.out.println("Occupancy Report by Room Type");
			try {
				rh.report2(hotelId, role);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case 3: System.out.println("Occupancy Report by Date Range");
			try {
				
				@SuppressWarnings("resource")
				Scanner scanner = new Scanner(System.in);

				System.out.println("Enter the Start Date in 12/3/2018 format = month/day/year");
				java.util.Date start_date = new java.util.Date(scanner.next());		// might throw exception of parsing date to string
				java.sql.Date sql_start_Date = new java.sql.Date(start_date.getTime());
				System.out.println(start_date+"is start date");
				System.out.println("Enter the End Date in 4/2/2018 format -> month/date/year");
				java.util.Date end_date = new java.util.Date(scanner.next());	
				java.sql.Date sql_end_Date = new java.sql.Date(end_date.getTime());
				System.out.println(end_date+" is end date"+"sql end date is "+sql_end_Date);
				rh.report3(sql_start_Date, sql_end_Date);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case 4: System.out.println("Occupancy Report by City");
			try {
				rh.report4();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 5: System.out.println("Information on staff grouped by their role");
			try {
				rh.report5(Integer.valueOf(hotelId), role);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 6: System.out.println("Information on all staff members serving the customer during the stay");
			try {
				System.out.println("Please enter reservation id of the customer");
				Scanner scanner = new Scanner(System.in);
				int reservationId = scanner.nextInt();
				rh.report6(reservationId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 7: System.out.println("Revenue report for a Hotel in a date range");
			try {
				
				@SuppressWarnings("resource")
				Scanner scanner = new Scanner(System.in);
				
				System.out.println("Enter the Start Date in 12/3/2018 format = month/day/year");
				java.util.Date start_date = new java.util.Date(scanner.next());		// might throw exception of parsing date to string
				    java.sql.Date sql_start_Date = new java.sql.Date(start_date.getTime());
				System.out.println(start_date+"is start date");
				System.out.println("Enter the End Date in 4/2/2018 format -> month/date/year");
				java.util.Date end_date = new java.util.Date(scanner.next());	 
				java.sql.Date sql_end_Date = new java.sql.Date(end_date.getTime());
				System.out.println(end_date+" is end date"+"sql end date is "+sql_end_Date);	
				rh.report7(sql_start_Date, sql_end_Date, hotelId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		default:
			System.out.println("invalid input, exiting");
		}
		/*ReportDAO reportDAO=new ReportDAO();
		try {
			reportDAO.staffOfHotel(Integer.valueOf(hotelId), "Manager" );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	

}
