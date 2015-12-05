package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import Beans.Coupon;
import Connection.ConnectionPool;
import Connection.CouponSystemException;

public class CompanyCouponDBDAO implements CompanyCouponDAO {
	
	private final ConnectionPool cp;
	
	public CompanyCouponDBDAO(ConnectionPool cp) {
		this.cp = cp;
		cp = ConnectionPool.getInstance();
	}

	@Override
	public void createCoupon(long COMP_ID, long COUPON_ID) throws CouponSystemException {
		
		Connection con = null;
		try {
			con = cp.getConnection();
			String query = "insert into COMPANY_COUPON(COMP_ID,COUPON_ID) values(?,?)";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, COMP_ID);
			pstmt.setLong(2, COUPON_ID);
			pstmt.executeUpdate();
			cp.returnConnection(con);
		} catch (SQLException e) {
			throw new CouponSystemException("Could not create company in company coupon table");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public Collection<Long> getAllCouponsByCompany(long COMP_ID) throws CouponSystemException {

		Connection con = null;
		Collection<Long> result = new ArrayList<>();
		try {
			con = cp.getConnection();
			String query = "select COUPON_ID from COMPANY_COUPON where COMP_ID=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, COMP_ID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(rs.getLong("coupon_id"));
			}
			cp.returnConnection(con);
		} catch (SQLException e) {
			throw new CouponSystemException("Could not get all company coupons from company coupon table");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
		

	@Override
	public void deleteCouponByCompany(long COMP_ID, long COUPON_ID) throws CouponSystemException {
		
		Connection con = null;
		try {
			con = cp.getConnection();
			String query = "delete from COMPANY_COUPON where COMP_ID=? and COUPON_ID=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, COMP_ID);
			pstmt.setLong(2, COUPON_ID);
			pstmt.executeUpdate();
			cp.returnConnection(con);
		} catch (SQLException e) {
			throw new CouponSystemException("Could not delete coupon from company coupon table");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteAllCompanyCoupons(long COMP_ID) throws CouponSystemException {
		
		Connection con = null;
		try {
			con = cp.getConnection();
			String query = "delete from COMPANY_COUPON where COMP_ID=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, COMP_ID);
			pstmt.executeUpdate();
			cp.returnConnection(con);
		} catch (SQLException e) {
			throw new CouponSystemException("Could not delete all company coupons from company coupon table");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void deleteCouponFromAllCompanies(long COUPON_ID) throws CouponSystemException {
		
		Connection con = null;
		try {
			con = cp.getConnection();
			String query = "delete from COMPANY_COUPON where COUPON_ID=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, COUPON_ID);
			pstmt.executeUpdate();
			cp.returnConnection(con);
		} catch (SQLException e) {
			throw new CouponSystemException("Could not delete coupon from company coupon table");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}


	
	

}
