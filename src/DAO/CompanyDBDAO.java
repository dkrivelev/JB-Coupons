package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import Beans.Company;
import Beans.Coupon;
import Beans.CouponType;
import Connection.*;
import Utility_classes.dbMethods;

public class CompanyDBDAO implements CompanyDAO {
	
	private final ConnectionPool cp;
	
	public CompanyDBDAO(ConnectionPool cp) {
		this.cp = cp;
		cp = ConnectionPool.getInstance();
	}

//	private CouponDBDAO couponDAO = new CouponDBDAO(cp);

	public long create(Company c) throws CouponSystemException {
		Connection con = null;
		long companyId;
		try {
			con = cp.getConnection();
			String query = "INSERT into Company(comp_name, password, email) VALUES (?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, c.getCompName());
			pstmt.setString(2, c.getPassword());
			pstmt.setString(3, c.getEmail());
			companyId = dbMethods.getIntFromResultset(pstmt);

//			for (Coupon currentCoupon : c.getCoupons()) {
//				createCoupon(currentCoupon, companyId); // need to create
//														// createCoupon method.
//			}

			cp.returnConnection(con);
			return companyId;
		} catch (SQLException e) {
			throw new CouponSystemException("Could not create company");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

//	public long createCoupon(Coupon coupon, long companyId) throws CouponSystemException {
//		Connection con = null;
//		long couponId = 
//	}

	@Override
	public void remove(Company c) throws CouponSystemException {

		Connection con = null;
		try {
			ConnectionPool cp = ConnectionPool.getInstance();
			con = cp.getConnection();
			String query = "delete from Company where ID=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, c.getId());
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
	public void update(Company c) throws CouponSystemException {

		Connection con = null;
		try {
			ConnectionPool cp = ConnectionPool.getInstance();
			con = cp.getConnection();
			String query = "update Company set comp_name=?,password=?,email=? where id=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, c.getCompName());
			pstmt.setString(2, c.getPassword());
			pstmt.setString(3, c.getEmail());
			pstmt.setLong(4, c.getId());
			pstmt.executeUpdate();
			cp.returnConnection(con);
		} catch (SQLException e) {
			throw new CouponSystemException("Could not update company");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public Company get(long id) throws CouponSystemException {

		Connection con = null;
		Company result = new Company(id, "", "", "");
		try {
			ConnectionPool cp = ConnectionPool.getInstance();
			con = cp.getConnection();
			String query = "select COMP_NAME,PASSWORD,EMAIL from COMPANY where ID=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				result.setCompName(rs.getString("comp_name"));
				result.setEmail(rs.getString("email"));
				result.setPassword(rs.getString("password"));
			}
			cp.returnConnection(con);
		} catch (SQLException e) {
			throw new CouponSystemException("Could not update company");
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
	public Collection<Company> getAll() throws CouponSystemException {

		Connection con = null;
		Collection<Company> result = new ArrayList<>();
		try {
			ConnectionPool cp = ConnectionPool.getInstance();
			con = cp.getConnection();
			String query = "select comp_name,password,email from company";
			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(new Company(rs.getLong("id"), rs.getString("comp_name"), rs.getString("password"), rs.getString("email")));
			}
			cp.returnConnection(con);
		} catch (SQLException e) {
			throw new CouponSystemException("Could not read companies from database");
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
	public Collection<Coupon> getCoupons(Company c) throws CouponSystemException {

		Connection con = null;
		Collection<Coupon> result = new ArrayList<>();
		try {
			ConnectionPool cp = ConnectionPool.getInstance();
			con = cp.getConnection();
			String query = "select ID,TITLE,START_DATE,END_DATE,AMOUNT,TYPE,MESSAGE,PRICE,IMAGE from Coupon cp, Company_Coupon cc where cc.coupon_id=cp.id and cc.comp_id=? ";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, c.getId());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				result.add(new Coupon(rs.getLong("ID"),rs.getString("title"), rs.getDate("START_DATE"), rs.getDate("END_DATE"), rs.getInt("amount"), CouponType.valueOf(rs.getString("TYPE")), rs.getString("message"), rs.getDouble("price"), rs.getString("image")));
			}
			cp.returnConnection(con);
		}catch (SQLException e) {
			throw new CouponSystemException("Could not read rows from coupon table");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
				
		return result;
	}

	
	public long IsValidPassword(String compName, String password) throws CouponSystemException {
			
		Connection con = null;
		long id = 0;
		try {
			con = cp.getConnection();
			String query = "select ID from Company where comp_name=? and password=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, compName);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			cp.returnConnection(con);
			if (rs.next()) {
				id = rs.getLong("ID");
			}
		}catch (SQLException e) {
			throw new CouponSystemException("Error performing login");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return id;
	}

	public boolean DoesNameExist(String compName) {
		
		Connection con = null;
		try {
			con = cp.getConnection();
			String query = "select count(comp_name) as count from company where comp_name=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, compName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.getInt("count")!=0) {
				throw new CouponSystemException("Can't create company, company with that name exists");
			}

			cp.returnConnection(con);

		} catch (SQLException e) {
			throw new CouponSystemException("Could not fetch information from company table");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
