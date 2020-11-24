package com.wolfinn.model;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.wolfinn.dao.ExecutiveManagerDAO;
import com.wolfinn.beans.HotelDetails;
public class TasksClass {
	
	// tasks options
	public void taskoptions(String role,int hotelId) throws SQLException
	{
		
		System.out.println("Select one of these options");
		System.out.println("1. Hotel info \n");
		System.out.println("2. Room info \n");
		System.out.println("3. Customer Info \n");
		System.out.println("4. Staff info \n");
		System.out.println("Press any other key to exit \n");
		Scanner scan= new Scanner(System.in);
		int option= scan.nextInt();
		HotelInfo hInfo=new HotelInfo();
		RoomsInfo rInfo=new RoomsInfo();
		CustomerInfo cInfo=new CustomerInfo();
	
		if (option==1)
		{
			if (role.equals("manager"))
			{
				hInfo.getManagerHotelDetails();
			}else
			{
				hInfo.getEMHotelDetails();
			}
		}
		else if(option==2)
		{
			rInfo.getRoomDetails(hotelId);
		}
		else if(option==3)
		{
			cInfo.getCustomerDetails();
		}
		else if(option==4)
		{
			StaffInfo staffInfo=new StaffInfo();
			staffInfo.getStaffforManager();
			
		}
		else
		{
			System.exit(0);
		}
	}
	
// checking front desk tasks options	
	public void fdr_taskoptions(int hotelId) throws SQLException
	{
		
		System.out.println("Select one of these options");
		System.out.println("1. Room info \n");
		System.out.println("2. Customer Info \n");
		System.out.println("3. Staff Info \n");
		System.out.println("Press any other key to exit \n");
		Scanner scan= new Scanner(System.in);
		int option= scan.nextInt();
		HotelInfo hInfo=new HotelInfo();
		RoomsInfo rInfo=new RoomsInfo();
		CustomerInfo cInfo=new CustomerInfo();
	
		if(option==1)
		{
			rInfo.getRoomDetails(hotelId);
		}
		else if(option==2)
		{
			cInfo.getCustomerDetails();
		}
		else if(option==3)
		{
			StaffInfo staffInfo=new StaffInfo();
			staffInfo.getStaffNonManager(hotelId);
		}
		else
		{
			System.exit(0);
		}
	}
	
	//Hotel options info
	public void hotelinfo()
	{
		System.out.println("Select one of these options");
		System.out.println("1. Check hotel details \n");
		System.out.println("2. Enter hotel details \n");
		System.out.println("3. Update hotel details \n");
		System.out.println("4. Delete hotel details \n");
		Scanner scan= new Scanner(System.in);
		int option= scan.nextInt();
		
		if (option==1)
		{
			checkhoteldetails();
		}
      		
	}
// checking hotel details	
	public void checkhoteldetails()
	{
		ExecutiveManagerDAO emdao= new ExecutiveManagerDAO();
		List<HotelDetails> hdetails=emdao.getHotelDetails();
		
		System.out.format("%16s%16s%16s%16s%16s%16s", "Hotel_id", "Hotel_name", "Hotel_phone_no","City","Manager_Staff_id","Street_name");
		System.out.printf("%n");
		for(int i=0;i<hdetails.size();i++)
		{
			String Hotel_id=hdetails.get(i).getHotel_id();
			String Hotel_name=hdetails.get(i).getHotel_name();
			String Hotel_phone_no=hdetails.get(i).getHotel_phone_no();
			String City=hdetails.get(i).getCity();
			String Manager_Staff_id=hdetails.get(i).getManager_Staff_id();
			String Street_name=hdetails.get(i).getStreet_name();			
			System.out.format("%16s%16s%16s%16s%16s%16s", Hotel_id , Hotel_name, Hotel_phone_no,City,Manager_Staff_id,Street_name);
			System.out.printf("%n");

		}
	}
	

}
