package com.wolfinn.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.security.auth.callback.ChoiceCallback;

import com.wolfinn.Connection.ConnectionManager;
import com.wolfinn.beans.BillingDetailsBean;
import com.wolfinn.dao.StaffServices;

public class BillingAccounts {
	

// getting bill details
	public void getBillDetails(int hotelId) 
	{
		Connection conn=null;
		Statement stmt=null;
		System.out.println("Enter reservation id \n");
		Scanner scan= new Scanner(System.in);
		int reservation_id= scan.nextInt();
		
		System.out.println("Enter room number \n");
		int roomno= scan.nextInt();
		
		try {
		
			 conn = ConnectionManager.getConnection();
		
			PreparedStatement ps5 = conn.prepareStatement("select checkin_time,checkout_time from reservation where reservation_id= ?");
			ps5.setInt(1,reservation_id);
			ResultSet rs3 = ps5.executeQuery();
			String checkout_time="";
			String checkintime="";
			if(rs3.next())
			{
				checkout_time=rs3.getString("checkout_time");
			  checkintime=rs3.getString("checkin_time");
			}
			ps5.close();	
			rs3.close();
									
			scan.nextLine();
			System.out.println("Do you want to provide new checkout time");
	         String checkoutFlag=scan.nextLine();   
	         
	         if(checkoutFlag.equals("Y"))
	         {
	     		System.out.println("Please provide new checkout time");

	        	 checkout_time=scan.nextLine();
	         }
		
		
		
		conn.setAutoCommit(false);
		
		PreparedStatement ps2 = conn.prepareStatement("UPDATE reservation SET checkin_time=?,checkout_time=? WHERE Reservation_id =? AND Hotel_id =?");
		ps2.setString(1, checkintime);
		ps2.setString(2, checkout_time);
		ps2.setInt(3, reservation_id);
		ps2.setInt(4, hotelId);
		ps2.executeUpdate();
		ps2.close();
		
		PreparedStatement ps3 = conn.prepareStatement("UPDATE Room SET Availability = ? WHERE Room_no = ? AND Hotel_id = ?");
		ps3.setString(1, "Available");
		ps3.setInt(2, roomno);
		ps3.setInt(3, hotelId);
		ps3.executeUpdate();
		ps3.close();
		
		PreparedStatement ps4 = conn.prepareStatement("select billing_id from check_in_info where reservation_id= ?");
		ps4.setInt(1,reservation_id);
		ResultSet rs2 = ps4.executeQuery();
		int billingId=0;
		if(rs2.next())
		{
		 billingId=rs2.getInt("billing_id");
		}
		ps4.close();
			
		
		String payment_type=getPaymentType(hotelId, reservation_id);
		
		System.out.println("Stored payment mode is "+payment_type);

		List<BillingDetailsBean> payamt = getpayAmount(checkout_time,hotelId, reservation_id,payment_type);

		System.out.println(payamt.size());
		double amountCharged = payamt.get(payamt.size()-1).getAmountCharged();
		
		PreparedStatement ps6 = conn.prepareStatement("UPDATE billing_info SET amount = ? WHERE billing_id = ? ");
		ps6.setDouble(1, amountCharged);
		ps6.setInt(2, billingId);
		ps6.executeUpdate();
		ps6.close();
		
		
		
		System.out.format("%32s%32s", "itemName", "amount_charged");
		System.out.printf("%n");
		for(int i=0;i<payamt.size();i++)
		{
			String itemName=payamt.get(i).getItemName();
			double amount_charged=payamt.get(i).getAmountCharged();		
			System.out.format("%32s%32.2f", itemName,amount_charged);
			System.out.printf("%n");

		}
		
		StaffServices staffServ=new StaffServices();
		
		conn.commit();
		
	//	staffServ.ReleaseStaff(reservation_id);

		
		}
		catch (SQLException e) {
			System.out.println("Room billing failed,check your input parameters");
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
		
	}
	// Getting payment type
	public String getPaymentType(int hotelid,int reservation_id)
	{
		String sqlQuery="select payment_type from Payment_details pd join Payment_relation pr on pd.Payment_id=pr.Payment_id"
				+ " join Check_in_info cii on cii.Billing_id=pr.Billing_id where reservation_id="+reservation_id+" and cii.Hotel_id="+hotelid+";" ;
		
		System.out.println(sqlQuery);
		String payment_type="cash";
		try{
			Connection conn1 = ConnectionManager.getConnection();
			Statement stmt = conn1.createStatement();
			ResultSet rs=stmt.executeQuery(sqlQuery);
		while(rs.next()){
			   payment_type=rs.getString("payment_type");
						}
		 				rs.close();
			}
		catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
		return payment_type;
	}
		// Getting payment amount
	public List<BillingDetailsBean> getpayAmount(String checkouttime,int hotelid,int reservation_id,String paymentType)
	{
		String roomChargesQuery="select 'Room_charges' as  'itemName' ,(TIMESTAMPDIFF(HOUR,checkin_time,'"+checkouttime+"')/24) * price as 'amount_charged' from Reservation r join "
				+ "Check_in_info cii on r.reservation_id=cii.reservation_id join Room ro on cii.room_no=ro.room_no and ro.hotel_id=cii.hotel_id join Hotel h on h.hotel_id=cii.hotel_id join "
				+ "Rooms_price_listing rpl on h.city=rpl.city and rpl.category=ro.category and r.reservation_id="+reservation_id+";" ;
		
		String serviceChargesQuery="select service_name as 'itemName', sum(service_price) as 'amount_charged' from Services s join "
							+ "	Services_used su on s.service_id=su.service_id join Reservation r on r.reservation_id=su.reservation_id "
							+ "where r.reservation_id="+reservation_id+" group by service_name;";
		List<BillingDetailsBean> billDetails=new ArrayList<BillingDetailsBean>();
		System.out.println(roomChargesQuery);

		try{
			Connection conn1 = ConnectionManager.getConnection();
			Statement stmt = conn1.createStatement();
			
			ResultSet rs2=stmt.executeQuery(serviceChargesQuery);
		double totalServiceCharges=0.0;
		double roomCharges=0.0;
		
		while(rs2.next()){
						String itemName=rs2.getString("itemName");
						double amountCharged=Double.parseDouble(rs2.getString("amount_charged"));
			            BillingDetailsBean billingBean=new BillingDetailsBean();
			            billingBean.setItemName(itemName);
			            billingBean.setAmountCharged(amountCharged);
			            billDetails.add(billingBean);
			            totalServiceCharges+=amountCharged;
			            }
						rs2.close();	
		ResultSet rs=stmt.executeQuery(roomChargesQuery);
		while(rs.next()){
						String itemName=rs.getString("itemName");
						double amountCharged=Double.parseDouble(rs.getString("amount_charged"));
						roomCharges=amountCharged;
		                BillingDetailsBean billingBean=new BillingDetailsBean();
		                billingBean.setItemName(itemName);
		                billingBean.setAmountCharged(amountCharged);
		                billDetails.add(billingBean);
						}
		 				rs.close();	
		 				
		 			
		 			double discount=0;	
		 			BillingDetailsBean billingBean=new BillingDetailsBean();
		 			double total_charges=0.0;
		 			if(paymentType.equals("hotel credit"))
		 			{
		 				discount=0.05*roomCharges;
			 			billingBean.setItemName("Discount");
				        billingBean.setAmountCharged(discount);
				        billDetails.add(billingBean);
			 			total_charges=totalServiceCharges+roomCharges-discount;

		 			}else
		 			{
			 			total_charges=totalServiceCharges+roomCharges;	
		 			}
		 			BillingDetailsBean billingBean2=new BillingDetailsBean();
		 			billingBean2.setItemName("Total payable");
			        billingBean2.setAmountCharged(total_charges);	
		 			billDetails.add(billingBean2)	;
			}
		catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
		return billDetails;
	}
		

}
