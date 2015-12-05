package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import Beans.Coupon;
import Beans.CouponType;
import Connection.ConnectionPool;
import Connection.CouponSystemException;
import Utility_classes.dbMethods;

public class CouponDBDAO implements CouponDAO {

	private final ConnectionPool cp;

	public CouponDBDAO(ConnectionPool cp) {
		this.cp = cp;
		cp = ConnectionPool.getInstance();
	}

	@Override
	public long create(Coupon c) throws CouponSystemException {
		Connection con = null;
		long result;
		try {
			con = cp.getConnection();
			String query = "INSERT into Coupon(title, start_date, end_date, amount, type, message, price, image) VALUES (?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, c.getTitle());
			pstmt.setDate(2, c.getStartDate());
			pstmt.setDate(3, c.getEndDate());
			pstmt.setInt(4, c.getAmount());
			pstmt.setString(5, c.getType().name());
			pstmt.setString(6, c.getMessage());
			pstmt.setDouble(7, c.getPrice());
			pstmt.setString(8, c.getImage());
			result = dbMethods.getIntFromResultset(pstmt);
			cp.returnConnection(con);
			return result;

		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't create coupon in DB");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void remove(Coupon c) throws CouponSystemException {

		Connection con = null;
		try {
			ConnectionPool cp = ConnectionPool.getInstance();
			con = cp.getConnection();
			String query = "DELETE from Coupon where id=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, c.getId());
			pstmt.executeUpdate();
			cp.returnConnection(con);
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't delete from Coupon DB table");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void update(Coupon c) throws CouponSystemException {

		Connection con = null;
		try {
			ConnectionPool cp = ConnectionPool.getInstance();
			con = cp.getConnection();
			String query = "UPDATE Coupon set title=?,start_date=?,end_date=?,amount=?,type=?,message=?,price=?,image=? where ID=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, c.getTitle());
			pstmt.setDate(2, c.getStartDate());
			pstmt.setDate(3, c.getEndDate());
			pstmt.setInt(4, c.getAmount());
			pstmt.setString(5, c.getType().name());
			pstmt.setString(6, c.getMessage());
			pstmt.setDouble(7, c.getPrice());
			pstmt.setString(8, c.getImage());
			pstmt.setLong(9, c.getId());
			pstmt.executeUpdate();
			cp.returnConnection(con);
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't update coupon in DB");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public Coupon get(long id) throws CouponSystemException {
		Connection con = null;
		Coupon result = new Coupon(id, "", Date.valueOf("2015-01-01"), Date.valueOf("2015-01-01"), 0,
				CouponType.Camping, "", 2.05, "");
		try {
			ConnectionPool cp = ConnectionPool.getInstance();
			con = cp.getConnection();
			String query = "SELECT title, startDate, endDate, amount, type, message, price, image from Coupon where id=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				result.setTitle(rs.getString("TITLE"));
				result.setType(CouponType.valueOf(rs.getString("TYPE")));
				result.setImage(rs.getString("IMAGE"));
				result.setMessage(rs.getString("MESSAGE"));
				result.setAmount(rs.getInt("AMOUNT"));
				result.setPrice(rs.getDouble("PRICE"));
				result.setStartDate(rs.getDate("START_DATE"));
				result.setEndDate(rs.getDate("END_DATE"));
			}
			cp.returnConnection(con);
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't read a row from Coupon DB table");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	@Override
	public Collection<Coupon> getAll() throws CouponSystemException {

		Connection con = null;
		Collection<Coupon> result = new ArrayList<>();
		try {
			ConnectionPool cp = ConnectionPool.getInstance();
			con = cp.getConnection();
			String query = "SELECT ID,title, startDate, endDate, amount, type, message, price, image from Coupon";
			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(new Coupon(rs.getLong("id"), rs.getString("title"), rs.getDate("startDate"),
						rs.getDate("endDate"), rs.getInt("amount"), CouponType.valueOf(rs.getString("type")),
						rs.getString("message"), rs.getDouble("price"), rs.getString("image")));
			}
			cp.returnConnection(con);
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't read rows from Coupon DB table");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	@Override
	public Collection<Coupon> getCouponByType(CouponType type) throws CouponSystemException {
		Connection con = null;
		Collection<Coupon> result = new ArrayList<>();
		try {
			ConnectionPool cp = ConnectionPool.getInstance();
			con = cp.getConnection();
			String query = "SELECT ID,title, startDate, endDate, amount, message, price, image from Coupon where type=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, type.name());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(new Coupon(rs.getLong("id"), rs.getString("title"), rs.getDate("startDate"),
						rs.getDate("endDate"), rs.getInt("amount"), CouponType.valueOf(rs.getString("type")),
						rs.getString("message"), rs.getDouble("price"), rs.getString("image")));
			}
			cp.returnConnection(con);
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't read rows from Coupon DB table");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	public Collection<Coupon> getCouponByEndDate(Date end_date) throws CouponSystemException {

		Connection con = null;
		Collection<Coupon> result = new ArrayList<>();
		try {
			ConnectionPool cp = ConnectionPool.getInstance();
			con = cp.getConnection();
			String query = "SELECT ID,title, startDate, endDate, amount, message, price, image from Coupon where date=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setDate(1, end_date);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(new Coupon(rs.getLong("id"), rs.getString("title"), rs.getDate("startDate"),
						rs.getDate("endDate"), rs.getInt("amount"), CouponType.valueOf(rs.getString("type")),
						rs.getString("message"), rs.getDouble("price"), rs.getString("image")));
			}
			cp.returnConnection(con);
		} catch (SQLException e) {
			throw new CouponSystemException("Couldn't read rows from Coupon DB table");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	public boolean isTitleValid(String title) {

		Connection con = null;
		try {
			con = cp.getConnection();
			String query = "select count(*) as count from Coupon where title=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, title);
			ResultSet rs = pstmt.executeQuery();
			cp.returnConnection(con);
			if (rs.getInt("count") != 0) {
				return true;
			} else {
				throw new CouponSystemException("Title is inalid");
			}			
		} catch (SQLException e) {
			throw new CouponSystemException("Could not fetch coupon data from coupon table");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				e.printStackTrace();

			}

		}
	}
}
