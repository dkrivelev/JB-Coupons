package facade;

import Connection.CouponSystem.ClientType;
import DAO.CompanyCouponDBDAO;
import DAO.CompanyDBDAO;
import DAO.CouponDBDAO;
import DAO.CustomerCouponDBDAO;
import DAO.CustomerDBDAO;
import DAO.DBDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import Beans.Coupon;
import Beans.CouponType;
import Beans.Customer;
import Connection.ConnectionPool;
import Connection.CouponSystemException;

public class CustomerFacade implements CouponClientFacade{
	
	private final CouponDBDAO coupDBDAO;
	private final CustomerDBDAO custDBDAO;
	private final CustomerCouponDBDAO custcouponDBDAO;
	private final ConnectionPool cp;
	private Customer customer = null;
	
	

	public CustomerFacade() throws CouponSystemException {
		super();
		this.cp = ConnectionPool.getInstance();
		this.coupDBDAO = new CouponDBDAO(cp);
		this.custDBDAO = new CustomerDBDAO(cp);
		this.custcouponDBDAO = new CustomerCouponDBDAO(cp);
		
	}

	@Override
	public CouponClientFacade login(String name, String password, ClientType type) throws CouponSystemException {
		
		
		long id = custDBDAO.IsValidPassword(name, password);
		if (id != 0) {
			customer = custDBDAO.get(id);
			return this;
		}else {
			throw new CouponSystemException("One of the credentials is not valid");
		}
	}
	
	public void purchaseCoupon(Coupon c) throws CouponSystemException {
		
		Connection con = null;
		try {
			con = cp.getConnection();
			String query = "select count(coupon_id) as count from customer_coupon where cust_id=? and coupon_id=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, customer.getId());
			pstmt.setLong(2, c.getId());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				if (rs.getInt("count") > 0) {
					throw new CouponSystemException("cannot purchase coupon that has alread been purchased");
				}else {
					Coupon temp_coupon = coupDBDAO.get(c.getId());
					if (temp_coupon.getAmount()==0) {
						throw new CouponSystemException("cannot purchase coupon - no more left");
					}else { // to do - check that coupon is not expired
						custcouponDBDAO.createCoupon(customer.getId(), c.getId());
						temp_coupon.setAmount(c.getAmount()-1);
						coupDBDAO.update(temp_coupon);
						
					}
				}
			}
		}catch (SQLException e) {
			throw new CouponSystemException("Could not purchase Coupon");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public Collection<Coupon> getAllPurchasedCoupons() throws CouponSystemException {
		Collection<Coupon> result = new ArrayList<>();
		for (Long couponId : custcouponDBDAO.getAllCouponsByCustomer(customer.getId())) {
			result.add(coupDBDAO.get(couponId));
		}
		return result;
	}

	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type) throws CouponSystemException {
		Collection<Coupon> result = new ArrayList<>();
		for (Long couponId : custcouponDBDAO.getAllCouponsByCustomer(customer.getId())) {
			if (coupDBDAO.get(couponId).getType().equals(type)) {
			result.add(coupDBDAO.get(couponId));
			}
		}
		return result;
	}
	
	public Collection<Coupon> getAllPurchasedCouponsByPrice(Double price) throws CouponSystemException {
		Collection<Coupon> result = new ArrayList<>();
		for (Long couponId : custcouponDBDAO.getAllCouponsByCustomer(customer.getId())) {
			if (coupDBDAO.get(couponId).getPrice() < price) {
			result.add(coupDBDAO.get(couponId));
			}
		}
		return result;
	}
}

	