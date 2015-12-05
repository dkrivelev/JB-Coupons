package a;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Demo5 {

	public static void main(String[] args) {

		String driverName = "org.apache.derby.jdbc.ClientDriver40";
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		System.out.println("driver class loaded");

		String url = "jdbc:derby://localhost:1527/db1";

		try (Connection con = DriverManager.getConnection(url);) {

			System.out.println("connection to db1 established");

			Statement stmt = con.createStatement();
			String sql = "SELECT * FROM person";
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData meta = rs.getMetaData();
			int colmnCount = meta.getColumnCount();

			while (rs.next()) { // iterate rows

				for (int i = 1; i <= colmnCount; i++) { // iterate columns
					System.out.print(rs.getObject(i) + ", ");
				}

				System.out.println();

				// int id = rs.getInt(1);
				// String name = rs.getString(2);
				// int age = rs.getInt(3);
				//
				// System.out.println(id + ", " + name + ", " + age);
			}

			System.out.println("success: " + sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("connection to db1 closed");
	}

}
