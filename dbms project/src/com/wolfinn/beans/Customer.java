package com.wolfinn.beans;

public class Customer {

	int customerId;
	String ssn;
	public Customer(int customerId, String ssn) {
		super();
		this.customerId = customerId;
		this.ssn = ssn;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	
	
	
}
