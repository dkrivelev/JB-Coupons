package Utility_classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAO.QueryType;

public class dbMethods {
	
	public static long getIntFromResultset(PreparedStatement pstmt) throws SQLException {
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getResultSet();
		return rs.getLong(1);
		
	}
	
	
	
	public void executeQuery(int setAmount, QueryType type, String query, Connection con)
		
		
		
		Connection con = null;
		try {
			con = cp.
		}

}
