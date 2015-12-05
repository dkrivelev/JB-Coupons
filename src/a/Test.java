package a;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

public class Test {
	
	public static void main(String[] args) {
		
		String driverName = "org.apache.derby.jdbc.ClientDriver40";
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("driver class loaded");
		

		}
		
	}


