package com.wolfinn.model;
import java.sql.SQLException;
import java.util.Scanner;

public class FDRClass {
	
	// getting front desk options
	public void FDROptions(int hotelid) throws SQLException
	{
		System.out.println("Welcome Front desk representative to the system");
		System.out.println("1. Tasks/Operations \n");
		System.out.println("2. Billing Accounts \n");
		System.out.println("Press any other key to Exit \n");
		
		Scanner scan= new Scanner(System.in);
	
		TasksClass taskClass=new TasksClass();
		BillingAccounts billaccts=new BillingAccounts();

		int option= scan.nextInt();
		
		if (option==1)
		{
			taskClass.fdr_taskoptions(hotelid);
		}
		else if(option==2)
		{
			billaccts.getBillDetails(hotelid);
		}
		else
		{
			System.exit(0);
		}
	}

}
