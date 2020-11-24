package com.wolfinn.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.wolfinn.Connection.ConnectionManager;
import com.wolfinn.beans.GeneralReportDetails;
import com.wolfinn.beans.StaffReportDetails;

public class ReportDAO {
	// connect to db, initially Update your user info alone here
	
	//	private static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/skatta3"; // Using SERVICE_NAME

	Connection connection = null;
    static Statement statement = null;
	static ResultSet result = null;
	public String query;
	
	private List<GeneralReportDetails> addToGeneralResultDetails(ResultSet rs) throws SQLException {
		
		List<GeneralReportDetails> totalList = new ArrayList<GeneralReportDetails>();
		
		
		while(rs.next()){
			GeneralReportDetails rd1=new GeneralReportDetails();
			rd1.setHotel_id(hasColumn(rs,"Hotel_id")? rs.getString("Hotel_id"): "null");
			rd1.setHotel_name(hasColumn(rs,"Hotel_name")? rs.getString("Hotel_name") : null );
			rd1.setOccupancy(hasColumn(rs,"Occupancy")? rs.getString("Occupancy") : null );
			rd1.setPercentage_occupancy(hasColumn(rs,"Percent_Occupied")? String.valueOf(rs.getFloat("Percent_Occupied")) : null);
			rd1.setRoom_type(hasColumn(rs,"Category")? rs.getString("Category") : null );
			rd1.setStart_date(hasColumn(rs,"Start_date")? String.valueOf(rs.getString("Start_date")) : null );
			rd1.setEnd_date(hasColumn(rs,"End_date")? String.valueOf(rs.getString("End_date")) : null );
			rd1.setCity(hasColumn(rs,"City")? rs.getString("City") : null );
			rd1.setRevenue(hasColumn(rs,"Revenue")? rs.getFloat("Revenue") : 0 );
			totalList.add(rd1);
		}
		return totalList;
	}
	
	public static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int columns = rsmd.getColumnCount();
	    for (int x = 1; x <= columns; x++) {
	    	//System.out.println(rsmd.getColumnName(x));
	        if (columnName.equalsIgnoreCase(rsmd.getColumnName(x))) {
	            return true;
	        }
	    }
	    return false;
	}
	
	private List<StaffReportDetails> addToStaffResultDetails(ResultSet rs) throws SQLException {
		
		List<StaffReportDetails> totalList = new ArrayList<StaffReportDetails>();
		while(rs.next()){
			StaffReportDetails rd2=new StaffReportDetails();
			rd2.setStaff_id(hasColumn(rs, "Staff_id")? String.valueOf(rs.getInt(("Staff_id"))) : "null");
			rd2.setHotel_id(hasColumn(rs,"Hotel_id")? rs.getString("Hotel_id"): "null");
			rd2.setStaff_name(hasColumn(rs,"Staff_name")? rs.getString("Staff_name"): "null");
			rd2.setJob_title(hasColumn(rs,"Job_title")? rs.getString("Job_title"): "null");
			rd2.setDepartment(hasColumn(rs,"Department")? rs.getString("Department"): "null");
			rd2.setAge(hasColumn(rs,"Age")? rs.getInt("Age") : 0);
			rd2.setPhone_number(hasColumn(rs,"Phone_number")? rs.getString("Phone_number"): "null");
			rd2.setAddress(hasColumn(rs,"address")? rs.getString("address"): "null");
			totalList.add(rd2);
		}
		return totalList;
	}
	
	/* All the below methods throw error when an exception occurs while quering data, hence the calling/caller function of below should handle 
	 the error with a try catch or should throw the exception to higher method */
	
	public List<GeneralReportDetails> reportOccupancyByHotel(int hotelId, String role) throws SQLException {
		query = "select h1.Hotel_id,hotel_name,count(*) as 'Occupancy',(count(*)*100/(select count(*) from Room r join Hotel h on r.hotel_id=h.hotel_id where h.Hotel_id = h1.hotel_id)) as Percent_Occupied from Room r join Hotel h1 "
				+ "on r.hotel_id=h1.hotel_id where Availability='Occupied' group by h1.Hotel_id";
		if(role.equalsIgnoreCase("Manager")) {
			query += " having h1.hotel_id="+hotelId;
		}
		connection = ConnectionManager.getConnection();
		statement=connection.createStatement();
		result = statement.executeQuery(query);
		connection.close();
		return addToGeneralResultDetails(result);
	}
	
	public List<GeneralReportDetails> reportOccupancyByRoomType(int hotelId, String role) throws SQLException {
		
		query = "select category as 'Room_type', count(*) as 'Occupancy', (count(*)*100/(select count(*) from Room r2 where r2.category=r1.category)) as "
				+ "'Percent_Occupied' from Room r1 where availability='Occupied' group by category;";
		/*if(role.equalsIgnoreCase("Manager")) {
			query += " having r1.hotel_id="+hotelId;
		}*/
		connection = ConnectionManager.getConnection();
		statement=connection.createStatement();
		result = statement.executeQuery(query);
		connection.close();
		
		return addToGeneralResultDetails(result);
	}

	public List<GeneralReportDetails> reportOccupancyByDateRange(Date start_date, Date end_date ) throws SQLException {
		// start date = 2018-01-01 12:00:01, end date = 2018-01-31 12:30:00
		//System.out.println(start_date+", "+end_date+" are dates");
		query = "SELECT '"+start_date+"' as 'Start_date','"+end_date+"' as 'End_date', count(*) AS 'Occupancy', (count(*)*100/(SELECT count(*) FROM (SELECT distinct r1.room_no, r1.hotel_id FROM Room r1) as t2))"
				+ " as 'Percent_Occupied' FROM (SELECT distinct c.room_no, c.hotel_id FROM Check_in_info c INNER JOIN (SELECT reservation_id FROM Reservation WHERE (checkin_time> '"+start_date+"' AND checkin_time<'"+end_date+"') OR "
				+ "(checkout_time> '"+start_date+"' AND checkout_time <'"+end_date+"')  OR (checkin_time<'"+start_date+"' AND checkout_time>'"+end_date+"')) AS d WHERE d.reservation_id = c.reservation_id) AS tabel1";
		connection = ConnectionManager.getConnection();
		statement=connection.createStatement();
		result = statement.executeQuery(query);
		connection.close();
		
		return addToGeneralResultDetails(result);
	}
	
	public List<GeneralReportDetails> reportOccupancyByCity() throws SQLException{
		query = "select city, count(*) as 'Occupancy',(count(city)*100/(select count(*) from Room r1 JOIN Hotel h1 on r1.hotel_id = h1.hotel_id where h1.city = h.City) ) "
				+ "as 'Percent_Occupied' from Room r join Hotel h on r.Hotel_id = h.Hotel_id where Availability='Occupied' group by city";
		connection = ConnectionManager.getConnection();
		statement=connection.createStatement();
		result = statement.executeQuery(query);
		connection.close();
		
		return addToGeneralResultDetails(result);
	}
	
	public List<StaffReportDetails> staffOfHotel(int hotelId, String role) throws SQLException{
		query = "select * from Staff s ";
		if(role.equalsIgnoreCase("Manager")) {
			query += "where s.hotel_id= "+hotelId;
		}
		query += " order by job_title;";
		connection = ConnectionManager.getConnection();
		statement=connection.createStatement();
		result = statement.executeQuery(query);
		connection.close();
		
		return addToStaffResultDetails(result);
	}
	
	public List<StaffReportDetails> staffServingCustomer(int reservationId) throws SQLException{
		query = "(select Staff_name,Job_title,s.Staff_id,address from Reservation r join Services_used su on r.reservation_id=su.reservation_id join Staff s on su.staff_id=s.staff_id where r.reservation_id="+reservationId+") "
				+ "union (select Staff_name,Job_title,s.staff_id,address from Reservation r join Check_in_info cii on r.reservation_id=cii.reservation_id join Staff s on s.staff_id=cii.staff_id where r.reservation_id="+reservationId+");";
		connection = ConnectionManager.getConnection();
		statement=connection.createStatement();
		result = statement.executeQuery(query);
		connection.close();
		
		return addToStaffResultDetails(result);
	}
	public List<GeneralReportDetails> RevenueByDateRange(Date start_date, Date end_date, int hotel_id) throws SQLException{
		System.out.println(start_date+", "+end_date+" are dates");
		query = "select '"+start_date+"' as 'Start_date','"+ end_date +"' as 'End_date',cii.Hotel_id, sum(amount) as Revenue from Billing_info bi join Check_in_info cii on bi.billing_id=cii.billing_id "
				+ "join Reservation r on r.reservation_id = cii.reservation_id where cii.hotel_id="+hotel_id+" and ((r.checkin_time> '"+start_date+"' AND r.checkin_time<'"+end_date+"') OR (r.checkout_time>'"+start_date+"' AND "
				+ "r.checkout_time <'"+end_date+"')  OR (r.checkin_time<'"+start_date+"' AND r.checkout_time>'"+end_date+"'));";
		connection = ConnectionManager.getConnection();
		statement=connection.createStatement();
		result = statement.executeQuery(query);
		connection.close();
		
		return addToGeneralResultDetails(result);
	}
	
}
