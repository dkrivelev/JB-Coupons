package Utility_classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class dbMethods {
	
	public static long getIntFromResultset(PreparedStatement pstmt) throws SQLException {
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getResultSet();
		return rs.getLong(1);
		
	}

}
