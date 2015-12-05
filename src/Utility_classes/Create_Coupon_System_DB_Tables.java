package Utility_classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Create_Coupon_System_DB_Tables {

	private static String dbName = "CouponSystemDB";
	private static String dbURL = "jdbc:derby://localhost:1527/db1";

	public static void main(String[] args) {
		
		boolean create = true;
		boolean drop = false;
		boolean print = false;
		
		if (create) {
			createAllTables();
		}
		
		if (drop) {
			dropAllTables();
		}
		
		if (print) {
			
			String[] tables = { "Company", "Customer", "Coupon", "Customer_Coupon", "Company_Coupon" };
			for (String table : tables) {
				print(table);
				
			}
		}
		
		System.out.println(" ===== End ===== ");
		
		}

	public static void print(String tableName) {

		try (Connection con = DriverManager.getConnection(dbURL);) {

			Statement stmt = con.createStatement();
			String query = "SELECT * FROM" + tableName;
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsm = rs.getMetaData();
			int numOfColumns = rsm.getColumnCount();

			System.out.println("\n" + tableName + "table: ");

			while (rs.next()) {

				for (int i = 1; i <= numOfColumns; i++) {
					System.out.println(rs.getObject(i) + " , ");

				}

				System.out.println("success: " + query);
				System.out.println("printed entire table");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void createAllTables() {

		boolean Company = true;
		boolean Customer = true;
		boolean Coupon = true;
		boolean Customer_Coupon = true;
		boolean Company_Coupon = true;

		try (Connection con = DriverManager.getConnection(dbURL);) {
			Statement stmt = con.createStatement();
			String query = null;

			if (Company) {
				query = "create table Company(id bigint primary key generated always as identity(start with 1, increment by 1), comp_name VARCHAR (25), password VARCHAR (25), email VARCHAR (25))";
				stmt.execute(query);
				System.out.println("executed query: " + query);
			}

			if (Customer) {
				query = "create table Customer(id bigint primary key generated always as identity(start with 1, increment by 1), cust_name VARCHAR (25), password VARCHAR (25))";
				stmt.execute(query);
				System.out.println("executed query: " + query);
			}

			if (Coupon) {
				query = "create table Coupon(id bigint primary key generated always as identity(start with 1, increment by 1), title VARCHAR (25), start_date DATE, end_date Date, amount int, type VARCHAR (25), message VARCHAR (25), price DOUBLE, image VARCHAR (100)) ";
				stmt.execute(query);
				System.out.println("executed query: " + query);
			}

			if (Customer_Coupon) {
				query = "create table Customer_Coupon(cust_id bigint, coupon_id bigint, PRIMARY KEY(cust_id, coupon_id))";
				stmt.execute(query);
				System.out.println("executed query: " + query);
			}

			if (Company_Coupon) {
				query = "create table Company_Coupon(comp_id bigint, coupon_id bigint, PRIMARY KEY(comp_id, coupon_id))";
				stmt.execute(query);
				System.out.println("executed query: " + query);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void dropAllTables() {
		String[] tables = { "Company", "Customer", "Coupon", "Customer_Coupon", "Company_Coupon" };

		try (Connection con = DriverManager.getConnection(dbURL);) {
			Statement stmt = con.createStatement();
			String query = null;

			for (String table : tables) {
				query = "drop table " + table;
				stmt.executeUpdate(query);
				System.out.println("executed query: " + query);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
