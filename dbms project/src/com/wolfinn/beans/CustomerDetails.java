package com.wolfinn.beans;

public class CustomerDetails {
	
	String ssn;
	String customerEmail;
	String customerName;
	String dob;
	String phoneNumber;
	String address;
	public CustomerDetails(String ssn, String customerEmail,
			String customerName, String dob, String phoneNumber, String address) {
		super();
		this.ssn = ssn;
		this.customerEmail = customerEmail;
		this.customerName = customerName;
		this.dob = dob;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}
	
	public CustomerDetails() {
		super();
		this.ssn = null;
		this.customerEmail = null;
		this.customerName = null;
		this.dob = null;
		this.phoneNumber = null;
		this.address = null;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	

}
