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

public class CustomerCouponDBDAO implements CustomerCouponDAO {

	private final ConnectionPool cp;

	public CustomerCouponDBDAO(ConnectionPool cp) {
		this.cp = cp;
		cp = ConnectionPool.getInstance();
	}

	@Override
	public void createCoupon(long cust_id, long coupon_id) throws CouponSystemException {
		Connection con = null;
		try {
			con = cp.getConnection();
			String query = "insert into Customer_Coupon(cust_id,coupon_id) values(?,?)";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, cust_id);
			pstmt.setLong(2, coupon_id);
			pstmt.executeUpdate();
			cp.returnConnection(con);
		} catch (SQLException e) {
			throw new CouponSystemException("Could not delete company");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public Collection<Long> getAllCouponsByCustomer(long cust_id) throws CouponSystemException {

		Connection con = null;
		Collection<Long> result = new ArrayList<>();
		try {
			con = cp.getConnection();
			String query = "select coupon_id from customer_coupon where cust_id=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, cust_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(rs.getLong("coupon_id"));
			}
			cp.returnConnection(con);
		} catch (SQLException e) {
			throw new CouponSystemException("Could not get coupons from database");
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
	public void deleteCouponByCustomer(long cust_id, long COUPON_ID) throws CouponSystemException {
		
		Connection con = null;
		try {
			con = cp.getConnection();
			String query = "delete from CUSTOMER_COUPON where CUST_ID=? and COUPON_ID=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, cust_id);
			pstmt.setLong(2, COUPON_ID);
			pstmt.executeUpdate();cp.returnConnection(con);
		} catch (SQLException e) {
			throw new CouponSystemException("Could not delete coupons from customer_coupons table");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteAllCustomerCoupons(long cust_id) throws CouponSystemException {
		
		Connection con = null;
		try {
			con = cp.getConnection();
			String query = "delete from CUSTOMER_COUPON where CUST_ID=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, cust_id);
			pstmt.executeUpdate();
			cp.returnConnection(con);
		} catch (SQLException e) {
			throw new CouponSystemException("Could not delete coupons from customer_coupons table");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void deleteCoupon(long coupon_id) throws CouponSystemException {
		
		Connection con = null;
		try {
			con = cp.getConnection();
			String query = "delete from CUSTOMER_COUPON where COUPON_ID=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, coupon_id);
			pstmt.executeUpdate();
			cp.returnConnection(con);
		} catch (SQLException e) {
			throw new CouponSystemException("Could not delete coupon from customer_coupons table");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
