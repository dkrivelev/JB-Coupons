package DAO;

import java.sql.Connection;
import java.sql.SQLException;

import Connection.ConnectionPool;
import Connection.CouponSystemException;

public class DBDAO {

	public static void returnConnectionToPool(Connection con) throws CouponSystemException, SQLException {
		if (con != null) {
			ConnectionPool.getInstance().returnConnection(con);
		}
	}
	
}
