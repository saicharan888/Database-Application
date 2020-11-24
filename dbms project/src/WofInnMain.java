import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.wolfinn.model.FDRClass;
import com.wolfinn.model.Managerclass;
import com.wolfinn.model.ServiceStaffClass;

public class WofInnMain {


	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		
		Managerclass manaClass=new Managerclass();
		FDRClass fdrClass=new FDRClass();
		ServiceStaffClass servClass=new ServiceStaffClass();
		
		outer:
		for(int i=0;i<3;i++)
			{
			System.out.println("Select your role: \n 1. Executive Manager \n 2. Manager \n 3. FDR \n 4. Service Staff");	
		Scanner scan= new Scanner(System.in);
		
		int role= scan.nextInt();
		
		if (role==1)
		{
			System.out.println("You selected Executive Manager role");
			manaClass.Executivemanageroptions();
			break outer;
		}
		else if(role==2)
		{
			System.out.println("You selected Manager role");
			System.out.println("Please enter Hotel id");
			Scanner scan2= new Scanner(System.in);

            int hotelId=scan2.nextInt();
            
			manaClass.manageroptions(hotelId);
			break outer;
			
		}
		else if(role==3)
		{
			System.out.println("You selected FDR role");
			System.out.println("Please enter Hotel id");
			Scanner scan2= new Scanner(System.in);

            int hotelId=scan2.nextInt();
			fdrClass.FDROptions(hotelId);
			break outer;
			
		}else if(role==4)
		{
			System.out.println("You selected Service Staff role");
			System.out.println("Please enter Hotel id");
			Scanner scan2= new Scanner(System.in);
            int hotelId=scan2.nextInt();
            servClass.servicestffOptions(hotelId);
			break outer;
			
		}
		else
		{
			
			if(i==2)
			{
				System.out.println("Max limit reached for invalid input,exiting application");
			}else
			{
				System.out.println("Please provide a valid input");
			}
			continue outer;
			
		}
		
			
		}
	}

}
