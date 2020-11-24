package com.wolfinn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.wolfinn.Connection.ConnectionManager;
import com.wolfinn.beans.RoomBean;
import com.wolfinn.beans.RoomClass;
import com.wolfinn.model.RoomsInfo;

public class RoomDAO {
	
	private Connection conn = null;
	private PreparedStatement ps = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	public  boolean insertRoom(RoomClass room){

		try {
			conn = ConnectionManager.getConnection();

			String tcity = "";
			Scanner scan=new Scanner(System.in);
			System.out.println(room.getHotelId());
			ps = conn.prepareStatement("Select city from Hotel where Hotel_id = ?");
			ps.setInt(1,room.getHotelId());
			rs=ps.executeQuery();
			if(rs.next())
			{
				tcity = rs.getString("city");
			
			}
			ps.close();
			rs.close();
			ps = conn.prepareStatement("Select Price from rooms_price_listing where city = ? and category = ? ");
			ps.setString(1, tcity);
			ps.setString(2,room.getCategory());
			rs=ps.executeQuery();

			if(!rs.next())
			{
				System.out.println("The nightly rate isn't available for rooms of this category, please type in the rate to continue\n");
				int trate = scan.nextInt();
				rs = stmt.executeQuery("Insert into rooms_price_listing values('"+tcity+"','"+room.getCategory()+"',"+String.valueOf(trate)+" );");
				
			}
			rs.close();
			ps = conn.prepareStatement("INSERT INTO Room (Room_no, Hotel_id, Availability, Category, Capacity) VALUES (?,?,?,?,?)");
			ps.setInt(1, room.getRoomNumber());
			ps.setInt(2, room.getHotelId());
			ps.setString(3, room.getAvailability());
			ps.setString(4, room.getCategory());
			ps.setInt(5, room.getCapacity());

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
		return false;
	}
	
	public boolean deleteRoom(int roomNumber, int hotelId){

		try {
			conn = ConnectionManager.getConnection();
			ps = conn.prepareStatement("DELETE FROM Room WHERE Hotel_id = ? AND Room_no = ?");
			ps.setInt(1, hotelId);
			ps.setInt(2, roomNumber);
			
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
	
	public boolean updateRoomInfo(RoomClass room){

		try {
			conn = ConnectionManager.getConnection();
			ps = conn.prepareStatement("UPDATE Room SET Category = ?, Capacity = ? WHERE Room_no = ? AND Hotel_id = ?");
			ps.setString(1, room.getCategory());
			ps.setInt(2, room.getCapacity());
			ps.setInt(3, room.getRoomNumber());
			ps.setInt(4, room.getHotelId());
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("Error while updating room info");
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception");
		}
		return false;
	}
	
	public boolean assignRoom(int customerid,int roomno,int hotelId,int staffId,String payType,String billingAddress,String cccDetails,String checkintime,String checkouttime){

		try {
			conn = ConnectionManager.getConnection();

			conn.setTransactionIsolation(conn.TRANSACTION_READ_UNCOMMITTED);

			conn.setAutoCommit(false);
			//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//Date date = new Date();	
			//String checkintime=dateFormat.format(date);
			Statement st = conn.createStatement();
			rs = st.executeQuery("Select category from room where room_no = "+roomno+" and hotel_id = "+hotelId+" ;");
			String catg = "";
			if(rs.next())
			{
				catg = rs.getString("Category");
			}
			
			//rs = 
			ps = conn.prepareStatement("INSERT INTO Reservation (Hotel_id, Checkin_time, Checkout_time) VALUES (?,?,?)");
			ps.setInt(1,hotelId );
			ps.setString(2, checkintime);
			ps.setString(3,checkouttime);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement("select max(reservation_id) as reservation_id from Reservation;");
			rs = ps.executeQuery();
			int reservationId=0;
			if(rs.next())
			{
			  reservationId=rs.getInt("reservation_id");
			}
			int flg=1;
			if(catg.equals("Presidential"))
			{
				StaffServices ssvt = new StaffServices();
				flg =  ssvt.AssignStaff(hotelId,reservationId);
			}
			if(flg!=1)
			{
				System.out.println("No staff available to assign to Presidential suite,hence cancelling reservation");
				throw new Exception();
			}
			ps.close();
		    rs.close(); 
			
		    ps = conn.prepareStatement("INSERT INTO billing_info (amount) VALUES ('0')");
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement("select max(billing_id) as billing_id from Billing_info;");
			rs = ps.executeQuery();
			int billingId=0;
			if(rs.next())
			{
			 billingId=rs.getInt("billing_id");
			}
			ps.close();
			
			ps = conn.prepareStatement("INSERT INTO check_in_info (Hotel_id, Staff_id, Customer_id,Room_no,Reservation_id,Billing_id) "
					+ "VALUES (?,?,?,?,?,?)");
			ps.setInt(1,hotelId );
			ps.setInt(2,staffId );
			ps.setInt(3,customerid );
			ps.setInt(4,roomno );
			ps.setInt(5,reservationId );
			ps.setInt(6,billingId );
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement("INSERT INTO payment_details (Payment_type, Billing_address, Ccc_details) "
					+ "VALUES (?,?,?)");
			ps.setString(1,payType );
			ps.setString(2,billingAddress );
			ps.setString(3,cccDetails );
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement("select max(payment_id) as payment_id from payment_details;");
			rs = ps.executeQuery();
			int paymentid=0;
			if(rs.next())
			{
				paymentid=rs.getInt("payment_id");
			}
			ps.close();
			
			ps = conn.prepareStatement("INSERT INTO payment_relation (Payment_id, Billing_id) "
					+ "VALUES (?,?)");
			ps.setInt(1,paymentid );
			ps.setInt(2,billingId );
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement("UPDATE Room SET Availability = ? WHERE Room_no = ? AND Hotel_id = ?");
			ps.setString(1, "Occupied");
			ps.setInt(2, roomno);
			ps.setInt(3, hotelId);
			ps.executeUpdate();
			ps.close();
			
           conn.commit();
			
		} catch (SQLException e) {
			System.out.println("Room assignment failed,check your input parameters");
			if (conn != null) {
				try {
					conn.rollback(); 
					conn.setAutoCommit(true);
				} catch (SQLException e1) {

					e.printStackTrace();
				}		}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception");
		}
		finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
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
	

	
	
	
	public List<RoomBean> checkRoomAvail(String category, int hotelId){
		List<RoomBean> roomlist=new ArrayList<RoomBean>();

		try {
			conn = ConnectionManager.getConnection();
			ps = conn.prepareStatement("SELECT Room_no,Hotel_id,Availability,Category,Capacity FROM Room WHERE availability='Available' AND category=? and Hotel_Id=?");
			ps.setString(1, category);
			ps.setInt(2, hotelId);
			rs = ps.executeQuery();
			while (rs.next()) {
				int room_no=rs.getInt("Room_no");
				int hotel_id=rs.getInt("Hotel_id");
				String availability = rs.getString("Availability");
                int capacity=rs.getInt("Capacity"); 
				
                RoomBean roomBean=new RoomBean();
                roomBean.setRoom_no(room_no);
                roomBean.setHotel_id(hotel_id);
                roomBean.setAvailability(availability);
                roomBean.setCategory(category);
                roomBean.setCapacity(capacity);
                roomlist.add(roomBean);
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
		return roomlist;
	}

}
