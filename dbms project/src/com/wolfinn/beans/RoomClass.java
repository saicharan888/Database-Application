package com.wolfinn.beans;

public class RoomClass {
	
	int roomNumber;
	int hotelId;
	String availability;
	String category;
	int capacity;
	
	public RoomClass() {
			
}
	public RoomClass(int roomNumber, int hotelId, String availability,
			String category, int capacity) {
		super();
		this.roomNumber = roomNumber;
		this.hotelId = hotelId;
		this.availability = availability;
		this.category = category;
		this.capacity = capacity;
	}


	public int getRoomNumber() {
		return roomNumber;
	}


	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}


	public int getHotelId() {
		return hotelId;
	}


	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}


	public String getAvailability() {
		return availability;
	}


	public void setAvailability(String availability) {
		this.availability = availability;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public int getCapacity() {
		return capacity;
	}


	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	

}
