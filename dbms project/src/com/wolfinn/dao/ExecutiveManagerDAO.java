package com.wolfinn.dao;

import com.wolfinn.beans.HotelDetails;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.wolfinn.Connection.ConnectionManager;

public class ExecutiveManagerDAO {
	
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;

//getting hotel details
	public List<HotelDetails> getHotelDetails()
	{
		String sqlQuery="Select Hotel_id ,Hotel_name,Hotel_phone_no ,City ,Manager_Staff_id,Street_name from Hotel" ;
		
		List <HotelDetails> finallist=new ArrayList<HotelDetails>(); 
		try{
			conn = ConnectionManager.getConnection();
			stmt = conn.createStatement();
			rs=stmt.executeQuery(sqlQuery);
		while(rs.next()){
			String Hotel_id=rs.getString("Hotel_id");
			String Hotel_name=rs.getString("Hotel_name");
			String Hotel_phone_no=rs.getString("Hotel_phone_no");
			String City=rs.getString("City");
			String Manager_Staff_id=rs.getString("Manager_Staff_id");
			String Street_name=rs.getString("Street_name");
		    
			HotelDetails hdetails=new HotelDetails();
			hdetails.setHotel_id(Hotel_id);
			hdetails.setHotel_name(Hotel_name);
			hdetails.setHotel_phone_no(Hotel_phone_no);
			hdetails.setCity(City);
			hdetails.setManager_Staff_id(Manager_Staff_id);
			hdetails.setStreet_name(Street_name);
			
			finallist.add(hdetails);
		}
		 rs.close();
		}
		catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
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
		return finallist;
	}
}
