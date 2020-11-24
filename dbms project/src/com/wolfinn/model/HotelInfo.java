package com.wolfinn.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.wolfinn.beans.Hotel;
import com.wolfinn.dao.HotelDAO;


public class HotelInfo {

	@SuppressWarnings("resource")
	private static Scanner scan = new Scanner(System.in);

	// getting executive manager hotel options
	public void getEMHotelDetails()
	{
		
		boolean flag = true;
		
		System.out.println("***********Hotel Options*********");
		System.out.println("1. Add New Hotel");
		System.out.println("2. Update Hotel Info");
		System.out.println("3. Delete Hotel");
		System.out.println("Press any other key to exit");
		try {
			while (flag) {
				System.out.println("Select an Option: ");
				int choice = Integer.valueOf(scan.nextLine());
				
				switch (choice) {
				case 1:System.out.println("Add New Hotel Selected");
						insertNewHotel();
					break;
	
				case 2:System.out.println("Update Hotel Info");
						updateHotelInfo();
					break;
				case 3:System.out.println("Delete Hotel");
						deleteHotel();
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
	//getting hotel manager details
	public void getManagerHotelDetails()
	{
		
		boolean flag = true;
		
		System.out.println("***********Hotel Options*********");
		System.out.println("1. Update Hotel Info");
		System.out.println("Press any other key to exit");
		try {
			while (flag) {
				System.out.println("Select an Option: ");
				int choice = Integer.valueOf(scan.nextLine());
				
				switch (choice) {
				case 1:System.out.println("Update Hotel Info");
						updateHotelInfo();
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
//inserting new hotel	
	public void insertNewHotel() throws SQLException
	{
		
		System.out.println("Enter Hotel name: ");
		String hotelName = scan.nextLine();
		System.out.println("Enter Hotel Phone Number");
		String hotelPhone = scan.nextLine();
		System.out.println("Enter City");
		String city = scan.nextLine();
		System.out.println("Enter Manager Staff Id: ");
		int managerStaffId = Integer.valueOf(scan.nextLine());
		System.out.println("Enter Street Name");
		String streetName = scan.nextLine();
		int hotelId=0;
		Hotel hotelObj = new Hotel(hotelId, hotelName, hotelPhone, city, managerStaffId, streetName);
		HotelDAO hotelDAO=new HotelDAO();
		boolean result = hotelDAO.insertHotel(hotelObj);
		
		System.out.println("Hotel successfully added");
	}
	//deleting new hotel
	public void deleteHotel() throws SQLException
	{
		
		System.out.println("Enter Hotel Id to be deleted: ");
		int hotelId = Integer.valueOf(scan.nextLine());
		HotelDAO hotelDAO=new HotelDAO();
		boolean result = hotelDAO.deleteHotel(hotelId);
		
		System.out.println("Hotel successfully deleted");
		
	}
	//Updating hotel info
	public void updateHotelInfo() throws SQLException
	{

		System.out.println("Enter Hotel id: ");
		int hotelId = scan.nextInt();
		scan.nextLine();
		System.out.println("Enter Hotel name: ");
		String hotelName = scan.nextLine();
		System.out.println("Enter Hotel Phone Number");
		String hotelPhone = scan.nextLine();
		System.out.println("Enter City");
		String city = scan.nextLine();
		System.out.println("Enter Manager Staff Id: ");
		int managerStaffId = Integer.valueOf(scan.nextLine());
		System.out.println("Enter Street Name");
		String streetName = scan.nextLine();
		Hotel hotelObj = new Hotel(hotelId, hotelName, hotelPhone, city, managerStaffId, streetName);
		HotelDAO hotelDAO=new HotelDAO();
		boolean result = hotelDAO.updateHotelInfo(hotelObj);
		
		System.out.println("Hotel successfully updated");
		
	}
}
