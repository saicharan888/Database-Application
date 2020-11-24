package com.wolfinn.beans;

public class Hotel {
	
	int hotelId;
	String hotelName;
	String hotelPhone;
	String city;
	int managerStaffId;
	String streetName;
	public Hotel(int hotelId, String hotelName, String hotelPhone, String city,
			int managerStafId, String streetName) {
		super();
		this.hotelId = hotelId;
		this.hotelName = hotelName;
		this.hotelPhone = hotelPhone;
		this.city = city;
		this.managerStaffId = managerStafId;
		this.streetName = streetName;
	}
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getHotelPhone() {
		return hotelPhone;
	}
	public void setHotelPhone(String hotelPhone) {
		this.hotelPhone = hotelPhone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getManagerStafId() {
		return managerStaffId;
	}
	public void setManagerStafId(int managerStafId) {
		this.managerStaffId = managerStafId;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

}
