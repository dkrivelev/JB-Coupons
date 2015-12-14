package Utility_classes;

import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;


public class ConnectionPool {

	/**
	 * This class is used to create and maintain a connection pool to the DB.
	 * The class is a singleton so that only one ConnectionPool per system will exist.
	 */
	
	private HashSet<Connection> cons;
	private int max_connections;
	private static String dbName = "CouponSystemDB";
	private static String dbURL = "jdbc:derby://localhost:1527/" + dbName;
	private static String driverName = "org.apache.derby.jdbc.ClientDriver40";

	private static ConnectionPool instance = null;
	
	private ConnectionPool(int max_connections) {

		this.max_connections = max_connections;
		
		cons = new HashSet<>(); /* all connection will be stored in this hashset */
		
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e1) {
			throw new CouponSystemException("No db driver found."); 
		}

		/**
		 * We add an amount of connections to the connection pool equivalent to max_connections
		 */
		
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
		if (instance == null) {
			instance = new ConnectionPool(5); 
		}
		
		return instance;
	}
	
	public synchronized Connection getConnection() {
		
		/**
		 * Caller of the method receives a connection, 
		 * and removes it from the available connection hashset.
		 */
		
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
