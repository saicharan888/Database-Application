package com.wolfinn.model;
import java.util.List;
import java.util.Scanner;

import com.wolfinn.beans.ServicesBean;
import com.wolfinn.dao.ServicesClass;

public class ServiceStaffClass {
//service staff options
	public void servicestffOptions(int hotelid)
	{
		System.out.println("Welcome Service Staff to the system");
		System.out.println("1. Service Records \n");
		System.out.println("Press any other key to Exit \n");
		
		Scanner scan= new Scanner(System.in);
		int option= scan.nextInt();
		
		if (option==1)
		{
			servicesOptions(hotelid);
		}
		else
		{
			System.exit(0);
		}
	}
	
// services used options
	public void servicesOptions(int hotelid)
	{
		System.out.println("1. Enter Services used Record \n");
		System.out.println("2. Update Service used Records \n");
		System.out.println("3. Check Services Details \n");
		System.out.println("Press any key to exit \n");
		Scanner scan= new Scanner(System.in);
		
		int option= scan.nextInt();
		ServicesClass srv=new ServicesClass();
		
		if (option==1)
		{
			int resp=srv.EnterService(hotelid);
			if(resp==1)
			{
				System.out.println("Records successfully inserted");
			}
		}
		else if(option==2)
		{
			int resp=srv.UpdateService();
			if(resp==1)
			{
				System.out.println("Records successfully updated");
			}	
		}
		else if(option==3)
		{
			List<ServicesBean> servicesDetails=srv.getServices();
			System.out.format("%16s%16s%16s", "Service_id","Service_name", "Service_price");
			System.out.printf("%n");
			for(int i=0;i<servicesDetails.size();i++)
			{
				int Service_id=servicesDetails.get(i).getService_id();
				String Service_name=servicesDetails.get(i).getService_name();
				double Service_price=servicesDetails.get(i).getService_price();
				System.out.format("%16d%16s%16.2f",Service_id,Service_name,Service_price);
				System.out.printf("%n");

			}
		}
		else
		{
			System.exit(0);
		}
	}
}
