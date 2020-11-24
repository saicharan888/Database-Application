package com.wolfinn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.wolfinn.Connection.ConnectionManager;
import com.wolfinn.beans.Hotel;

public class HotelDAO {

	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	//inserting hotel
	public boolean insertHotel(Hotel hotel){

		try {
			conn = ConnectionManager.getConnection();

			ps = conn.prepareStatement("INSERT INTO Hotel (Hotel_name, Hotel_phone_no, City, Manager_Staff_id, Street_name) VALUES (?,?,?,?,?)");
			ps.setString(1, hotel.getHotelName());
			ps.setString(2, hotel.getHotelPhone());
			ps.setString(3, hotel.getCity());
			ps.setInt(4, hotel.getManagerStafId());
			ps.setString(5, hotel.getStreetName());

			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("Insertion failed check your input parameters");
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception");
		}
		return false;
	}
	//deleting hotel
	public boolean deleteHotel(int hotelId){

		try {
			conn = ConnectionManager.getConnection();

			PreparedStatement ps = conn.prepareStatement("DELETE FROM Hotel WHERE Hotel_id = ?");
			ps.setInt(1, hotelId);
			
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("Error while deleting Room");
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception");
		}
		return false;
	}
//updating hotel	
	public boolean updateHotelInfo(Hotel hotel){

		try {
			conn = ConnectionManager.getConnection();

			PreparedStatement ps = conn.prepareStatement("UPDATE Hotel SET Hotel_name = ?, Hotel_phone_no = ?, City = ?, Manager_Staff_id = ?, Street_name = ? WHERE Hotel_id = ?");
			
			ps.setString(1, hotel.getHotelName());
			ps.setString(2, hotel.getHotelPhone());
			ps.setString(3, hotel.getCity());
			ps.setInt(4, hotel.getManagerStafId());
			ps.setString(5, hotel.getStreetName());
			ps.setInt(6, hotel.getHotelId());
			
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("Hotel info updation failed check your input parameters");
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception");
		}
		return false;
	}
}
