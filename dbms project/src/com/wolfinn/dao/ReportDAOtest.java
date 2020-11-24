package com.wolfinn.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.wolfinn.beans.ReportDetails1;
import com.wolfinn.beans.ReportDetails2;

public class ReportDAOtest {

	Connection connection = null;
    private Statement statement = null;
	private ResultSet result = null;
	public String query;
	
	public List<ReportDetails1> addToResultDetails1(ResultSet rs) throws SQLException {
		
		List<ReportDetails1> totalList = new ArrayList<ReportDetails1>();
		
		while(rs.next()){
			ReportDetails1 rd1=new ReportDetails1();
			rd1.setHotel_id(rs.getString("Hotel_id"));
			rd1.setHotel_name(rs.getString("Hotel_name"));
			rd1.setOccupancy(rs.getString("Occupancy"));
			rd1.setPercentage_occupancy(rs.getString("Percent_Occupied"));
			rd1.setRoom_type(rs.getString("Room_type"));
			rd1.setStart_date(rs.getString("Start_date"));
			rd1.setEnd_date(rs.getString("End_date"));
			rd1.setCity(rs.getString("City"));
			rd1.setRevenue(rs.getFloat("Revenue"));
			totalList.add(rd1);
		}
		return totalList;
	}
	
	private List<ReportDetails2> addToResultDetails2(ResultSet rs) throws SQLException {
		
		List<ReportDetails2> totalList = new ArrayList<ReportDetails2>();
		while(rs.next()){
			ReportDetails2 rd2=new ReportDetails2();
			rd2.setStaff_id(rs.getString("Staff_id"));
			rd2.setHotel_id(rs.getString("hotel_id"));
			rd2.setStaff_name(rs.getString("staff_name"));
			rd2.setJob_title(rs.getString("job_title"));
			rd2.setDepartment(rs.getString("department"));
			rd2.setAge(rs.getInt("age"));
			rd2.setPhone_number(rs.getString("phone_number"));
			rd2.setAddress(rs.getString("address"));
			totalList.add(rd2);
		}
		return totalList;
	}
	public List<ReportDetails1> reportOccupancyByHotel() throws SQLException {
		query = "select h1.Hotel_id,hotel_name,count(*) as Total_occupancy,(count(*)*100/(select count(*) from Room r join Hotel h on r.hotel_id=h.hotel_id where h.Hotel_id = h1.hotel_id)) as Percent_occupied from Room r join Hotel h1 "
				+ "on r.hotel_id=h1.hotel_id where Availability='Occupied' group by h1.Hotel_id;";

		result = statement.executeQuery(query);
		return addToResultDetails1(result);
	}
	private List<ReportDetails1> reportOccupancyByRoomType() throws SQLException {
		
		query = "select category as Room_type, count(*) as 'Occupancy', (count(*)*100/(select count(*) from Room r2 where r2.category=r1.category)) as 'Percent occupied' from Room r1 where availability='Occupied' group by category;";
		result = statement.executeQuery(query);
		
		return addToResultDetails1(result);
	}
	

	private List<ReportDetails1> reportOccupancyByDateRange(Date start_date, Date end_date ) throws SQLException {
		// start date = 2018-01-01 12:00:01, end date = 2018-01-31 12:30:00
		query = "SELECT "+start_date+" as 'Start_date',"+end_date+" as 'End_date', count(*) AS 'Occupancy', (count(*)*100/(SELECT count(*) FROM (SELECT distinct r1.room_no, r1.hotel_id FROM Room r1) as t2))"
				+ " as 'Percent occupied' FROM (SELECT distinct c.room_no, c.hotel_id FROM Check_in_info c INNER JOIN (SELECT reservation_id FROM Reservation WHERE (checkin_time> "+start_date+" AND checkin_time<"+end_date+") OR "
				+ "(checkout_time> "+start_date+" AND checkout_time <"+end_date+")  OR (checkin_time<"+start_date+" AND checkout_time>"+end_date+")) AS d WHERE d.reservation_id = c.reservation_id) AS tabel1";
		
		result = statement.executeQuery(query);
		return addToResultDetails1(result);
	}
	
	private List<ReportDetails1> reportOccupancyByCity() throws SQLException{
		query = "select city, count(*) as 'Occupancy',(count(city)*100/(select count(*) from Room r1 JOIN Hotel h1 on r1.hotel_id = h1.hotel_id where h1.city = h.City) ) "
				+ "as 'Percent Occupied' from Room r join Hotel h on r.Hotel_id = h.Hotel_id where Availability='Occupied' group by city";
		
		result = statement.executeQuery(query);
		return addToResultDetails1(result);
	}
	
	private List<ReportDetails2> staffServingCustomer(int reservationId) throws SQLException{
		query = "(select Staff_name,Job_title,s.Staff_id,address from Reservation r join Services_used su on r.reservation_id=su.reservation_id join Staff s on su.staff_id=s.staff_id where r.reservation_id="+reservationId+") "
				+ "union (select Staff_name,Job_title,s.staff_id,address from Reservation r join Check_in_info cii on r.reservation_id=cii.reservation_id join Staff s on s.staff_id=cii.staff_id where r.reservation_id="+reservationId+");";
		
		result = statement.executeQuery(query);
		return addToResultDetails2(result);
	}
	private List<ReportDetails2> RevenueByDateRange(Date start_date, Date end_date, int hotel_id) throws SQLException{
		query = "select "+start_date+" as 'Start date',"+ end_date +" as 'End date',cii.Hotel_id, sum(amount) as Revenue from Billing_info bi join Check_in_info cii on bi.billing_id=cii.billing_id "
				+ "join Reservation r on r.reservation_id = cii.reservation_id where cii.hotel_id="+hotel_id+" and ((r.checkin_time> "+start_date+" AND r.checkin_time<"+end_date+") OR (r.checkout_time>"+start_date+" AND "
				+ "r.checkout_time <"+end_date+")  OR (r.checkin_time<"+start_date+" AND r.checkout_time>"+end_date+"));";
		
		result = statement.executeQuery(query);
		return addToResultDetails2(result);
	}
	// write one more method
	
	

	
}
