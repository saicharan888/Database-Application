package com.wolfinn.model;

import java.sql.SQLException;
import java.util.Scanner;

import com.wolfinn.dao.StaffServices;

public class StaffInfo {
	
	private static Scanner scan = new Scanner(System.in);
// getting staff options for manager
	public void getStaffforManager() throws SQLException
	{
		
		boolean flag = true;
		
		System.out.println("***********Staff Options*********");
		System.out.println("1. Add Staff");
		System.out.println("2. Update Staff");
		System.out.println("3. Delete Staff");
		System.out.println("4. Check Staff");
		System.out.println("Press any other key to exit");
		try {
			while (flag) {
				System.out.println("Select an Option: ");
				int choice = Integer.valueOf(scan.nextLine());
				System.out.println("Enter Hotelid");
				int hotelId=scan.nextInt();
				StaffServices staffServ=new StaffServices();
				switch (choice) {
				case 1:System.out.println("Add Staff");
						staffServ.addStaffDetails(hotelId);
					break;
	
				case 2:System.out.println("Update Staff");
				       System.out.println("Enter Staff id");
				       int staffId=scan.nextInt();
						staffServ.UpdateStaffDetails(hotelId, staffId);
					break;
				case 3:System.out.println("Delete Staff");
				 	System.out.println("Enter Staff id");
				 	int staffId2=scan.nextInt();
					staffServ.DeleteStaff(hotelId, staffId2);
				break;
				
				case 4:System.out.println("Check Staff Details");
						staffServ.getStaff(hotelId);
				break;
			
				default: System.exit(0);
					break;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception");
		}
			
		}
	
	//getting staff options for non manager
	public void getStaffNonManager(int hotelId) throws SQLException
	{
		
		boolean flag = true;
		
		System.out.println("***********Staff Options*********");
		System.out.println("1. Check Staff");
		System.out.println("Press any other key to exit");
		try {
			while (flag) {
				System.out.println("Select an Option: ");
				int choice = Integer.valueOf(scan.nextLine());
				StaffServices staffServ=new StaffServices();
				switch (choice) {
				case 1:System.out.println("Getting Staff Details");
						staffServ.getStaff(hotelId);
					break;
			
				default: System.exit(0);
					break;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception");
		}
			
		}

}
