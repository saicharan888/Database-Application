package com.wolfinn.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.wolfinn.beans.RoomBean;
import com.wolfinn.beans.RoomClass;
import com.wolfinn.dao.RoomDAO;


public class RoomsInfo {
	
	@SuppressWarnings("resource")
	private static Scanner scan = new Scanner(System.in);

	public void getRoomDetails(int hotelId)
	{
		
		boolean flag = true;
		
		System.out.println("***********Room Options*********");
		System.out.println("1. Insert New Room");
		System.out.println("2. Update Room Info");
		System.out.println("3. Delete Room");
		System.out.println("4. Check Room Availability");
		System.out.println("5. Checkin/Assign Room");

		System.out.println("Press any other key to exit");
		try {
			while (flag) {
				System.out.println("Select an Option: ");
				int choice = Integer.valueOf(scan.nextLine());
				
				switch (choice) {
				case 1:System.out.println("Insert Room Selected");
						insertNewRoom(hotelId);
					break;
	
				case 2:System.out.println("Update Room Info");
						updateRoomInfo(hotelId);
					break;
				case 3:System.out.println("Delete Existing Room");
						deleteRoom(hotelId);
					break;
				case 4:System.out.println("Check Room Availability");
						checkRoomAvailability(hotelId);
					break;
				case 5:System.out.println("Assign room/Create Reservation");
				        assignRoom(hotelId);
				default: System.exit(0);;
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
	
	public void insertNewRoom(int hotelId) throws SQLException
	{
			System.out.println("Enter Room Number:\n");
			int roomNumber = scan.nextInt();
			scan.nextLine();
			System.out.println("Enter Availability (Available/Unavailable)");
			String availability = scan.nextLine();
			System.out.println("Enter Category(D/E/P)");
			String category = scan.nextLine();
			System.out.println("Enter Room Capacity: ");
			int capacity = Integer.valueOf(scan.nextLine());
			RoomClass roomobj = new RoomClass(roomNumber, hotelId, availability, category, capacity);
			
			RoomDAO roomDAO=new RoomDAO();
			boolean result = roomDAO.insertRoom(roomobj);
			
			System.out.println("Room successfully added");
			
			getRoomDetails(hotelId);
	}
	
	public void deleteRoom(int hotelId) throws SQLException
	{
		
		System.out.println("Enter Room Number to be deleted: ");
		int roomNumber = Integer.valueOf(scan.nextLine());
		RoomDAO roomDAO=new RoomDAO();
		boolean result = roomDAO.deleteRoom(roomNumber, hotelId);
		
		System.out.println("Room successfully deleted");
		
		getRoomDetails(hotelId);
	}
	
	public void updateRoomInfo(int hotelId) throws SQLException
	{		
		System.out.println("Enter Room Number for Room to be updated: ");
		int roomNo = Integer.valueOf(scan.nextLine());
		System.out.println("Enter new values:");
		System.out.println("Enter Category(D/E/P)");
		String category = scan.nextLine();
		System.out.println("Enter Room Capacity: ");
		int capacity = Integer.valueOf(scan.nextLine());
		RoomClass roomobj = new RoomClass();
		roomobj.setCategory(category);
		roomobj.setCapacity(capacity);
		roomobj.setHotelId(hotelId);
		roomobj.setRoomNumber(roomNo);
		RoomDAO roomDAO=new RoomDAO();
		boolean result = roomDAO.updateRoomInfo(roomobj);
		
		System.out.println("Room successfully updated");
		
		getRoomDetails(hotelId);
	}
	
	public void checkRoomAvailability(int hotelId) throws SQLException
	{
		System.out.println("Enter room category to check availability: ");
		String category= scan.nextLine();
		RoomDAO roomDAO=new RoomDAO();
		List<RoomBean> roomlist = roomDAO.checkRoomAvail(category,  hotelId);
		System.out.format("%16s%16s%16s%16s%16s", "Hotel_id", "Room_no","Availability","Category","Capacity");
		System.out.printf("%n");
		for(int i=0;i<roomlist.size();i++)
		{
			int hotel_id=roomlist.get(i).getHotel_id();
			int room_no=roomlist.get(i).getRoom_no();
			String availability=roomlist.get(i).getAvailability();		
			int capacity=roomlist.get(i).getCapacity();		
                
			System.out.format("%16d%16d%16s%16s%16d", hotel_id,room_no,availability,category,capacity);
			System.out.printf("%n");

		}
		getRoomDetails(hotelId);
	}
	
	public void assignRoom(int hotelId)
	{
		System.out.println("Enter customerid ");
		int customerId= scan.nextInt();
		System.out.println("Enter room number to assign ");
		int roomno= scan.nextInt();
		System.out.println("Enter your StaffId");
		int staffId = scan.nextInt();
		System.out.println("Enter Payment type");
		scan.nextLine();
		String payType = scan.nextLine();
		System.out.println("Enter Checkin time in yyyy-mm-dd hh:mm:ss format");
		String checkintime = scan.nextLine();
		System.out.println("Enter Checkout time in yyyy-mm-dd hh:mm:ss format");
		String checkouttime = scan.nextLine();
		System.out.println("Enter Billing Address");
		String billingAddress = scan.nextLine();
		System.out.println("Enter Credit card details");
		String cccDetails = scan.nextLine();
		RoomDAO roomDAO=new RoomDAO();
		roomDAO.assignRoom(customerId,roomno, hotelId,staffId,payType,billingAddress,cccDetails,checkintime,checkouttime);
		System.out.println("Room successfully assigned");
		
		getRoomDetails(hotelId);
	}
	
	
	
	
}
