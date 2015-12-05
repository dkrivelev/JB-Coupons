package Connection;

import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ConnectionPool {

	private HashSet<Connection> cons;
	private int max_connections = 5;
	private static String dbName = "CouponSystemDB";
	private static String dbURL = "jdbc:derby://localhost:1527/" + dbName;
	private static String driverName = "org.apache.derby.jdbc.ClientDriver40";

	private final static ConnectionPool instance = new ConnectionPool();
	
	private ConnectionPool() {

		cons = new HashSet<>();
		
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e1) {
			throw new CouponSystemException("No db driver found."); 
		}

		try {
			for (int i = 0; i < max_connections; i++) {
				cons.add(DriverManager.getConnection(dbURL));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Created maximum amount of connections: " + max_connections);

	}
	
	public static ConnectionPool getInstance() {
		return instance;
	}
	
	public synchronized Connection getConnection() {
		
		while (cons.isEmpty()) {
			
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		Iterator<Connection> iterator = cons.iterator();
		Connection con = iterator.next();
		iterator.remove();
		return con;
			
			
		}
	
	public synchronized void returnConnection(Connection con) {
		cons.add(con);
		notifyAll();	
	}
	
	public void CloseAllConnections() throws SQLException {
		for (Connection connection : cons) {
			connection.close();			
		}
		System.out.println("Closed a total amount of " + cons.size() + " connections");
		
		if (cons.size() != max_connections) {
			throw new CouponSystemException("Not all connections returned to the pool");
			
		}
	}
	
		

}
