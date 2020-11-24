package com.wolfinn.model;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.wolfinn.beans.GeneralReportDetails;
import com.wolfinn.beans.StaffReportDetails;
import com.wolfinn.dao.ReportDAO;

public class ReportHelper {
	
	public void report1(int hotelId, String role) throws SQLException {
		
		ReportDAO re = new ReportDAO();
		List<GeneralReportDetails> result = re.reportOccupancyByHotel(hotelId, role);
		if(result.size()>0)
		{
		System.out.format("%32s%32s%32s%32s", "Hotel_Id", "Hotel_Name", "Occupancy", "Percentage_Occupied");
		for(GeneralReportDetails list: result) {
			System.out.println();
			System.out.format("%32s%32s%32s%20s", list.hotel_id, list.hotel_name, list.occupancy, list.percentage_occupancy);
		}
		}else
		{
			System.out.println("No rooms occuppied for hotelid "+hotelId);
		}
	}
	
	public void report2(int hotelId, String role) throws SQLException {
			
			ReportDAO re = new ReportDAO();
			List<GeneralReportDetails> result = re.reportOccupancyByRoomType(hotelId, role);
			System.out.format("%32s%32s%32s", "Room_Type", "Occupancy", "Percentage_Occupied");
			for(GeneralReportDetails list: result) {
				System.out.println();
				System.out.format("%32s%32s%32s", list.room_type, list.occupancy, list.percentage_occupancy);
			}
		}
	public void report3(Date start_date, Date end_date) throws SQLException {
		
		ReportDAO re = new ReportDAO();
		List<GeneralReportDetails> result = re.reportOccupancyByDateRange(start_date, end_date);
		System.out.format("%32s%32s%32s%32s", "Start_Date", "End_Date", "Occupancy", "Percentage_Occupied");
		for(GeneralReportDetails list: result) {
			System.out.println();
			System.out.format("%32s%32s%32s%32s", list.start_date, list.end_date, list.occupancy, list.percentage_occupancy);
		}
	}
	public void report4() throws SQLException {
		
		ReportDAO re = new ReportDAO();
		List<GeneralReportDetails> result = re.reportOccupancyByCity();
		System.out.format("%32s%32s%32s", "City", "Occupancy", "Percentage_Occupied");
		for(GeneralReportDetails list: result) {
			System.out.println();
			System.out.format("%32s%32s%32s", list.city, list.occupancy, list.percentage_occupancy);
		}
	}
	
	// Staff detailed reports
	public void report5(int hotelId, String role) throws SQLException {
		ReportDAO re = new ReportDAO();
		List<StaffReportDetails> result = re.staffOfHotel(hotelId, role);
		System.out.format("%32s%32s%32s%32s%32s%10s%32s%32s", "Staff_id", "Hotel_id", "Staff_name", "Job_title", "Department", "Age", "Phone_number", "Address");
		for(StaffReportDetails list: result) {
			System.out.println();
			System.out.format("%32s%32s%32s%32s%32s%10d%32s%32s", list.Staff_id, list.hotel_id, list.staff_name, list.job_title, list.department, list.age, list.phone_number, list.address);
		}
	}
	public void report6(int reservationId) throws SQLException {
		
		ReportDAO re = new ReportDAO();
		List<StaffReportDetails> result = re.staffServingCustomer(reservationId);
		System.out.format("%32s%32s%32s%32s", "Staff_name", "Job_title", "Staff_Id", "Address");
		for(StaffReportDetails list: result) {
			System.out.println();
			System.out.format("%32s%32s%32s%32s", list.staff_name, list.job_title, list.Staff_id, list.address);
		}
	}
	public void report7(Date start_date, Date end_date, int hotel_id) throws SQLException {
		
		ReportDAO re = new ReportDAO();
		List<GeneralReportDetails> result = re.RevenueByDateRange(start_date, end_date, hotel_id);
		System.out.format("%32s%32s%32s%32s", "Start_Date", "End_Date", "Hotel_id", "Revenue");
		for(GeneralReportDetails list: result) {
			System.out.println();
			System.out.format("%32s%32s%32s%30.2f", list.start_date, list.end_date, list.hotel_id, list.revenue);
		}
	}
}