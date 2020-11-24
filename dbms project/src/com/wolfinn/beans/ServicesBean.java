package com.wolfinn.beans;

public class ServicesBean {
	
	private String Service_name;
	private int Service_id;
	private double Service_price;
	public String getService_name() {
		return Service_name;
	}
	public void setService_name(String service_name) {
		this.Service_name = service_name;
	}
	public int getService_id() {
		return Service_id;
	}
	public void setService_id(int service_id) {
		this.Service_id = service_id;
	}
	public double getService_price() {
		return Service_price;
	}
	public void setService_price(double service_price) {
		this.Service_price = service_price;
	}


}
