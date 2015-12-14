package facade;

import DAO.CompanyCouponDBDAO;
import DAO.CompanyDBDAO;
import DAO.CouponDBDAO;
import DAO.CustomerCouponDBDAO;
import DAO.CustomerDBDAO;
import DAO.DBDAO;
import Utility_classes.ConnectionPool;
import Utility_classes.CouponSystemException;
import Utility_classes.CouponSystem.ClientType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import Beans.Company;
import Beans.Coupon;
import Beans.CouponType;

public class CompanyFacade implements CouponClientFacade {

	private final CompanyDBDAO compDBDAO;
	private final CustomerDBDAO custDBDAO;
	private final CustomerCouponDBDAO custcouponDBDAO;
	private final CompanyCouponDBDAO compcouponDBDAO;
	private final ConnectionPool cp;
	private final CouponDBDAO couponDBDAO;
	private Company company = null;

	public CompanyFacade() {
		super();
		this.cp = ConnectionPool.getInstance();
		this.compDBDAO = new CompanyDBDAO(cp);
		this.custDBDAO = new CustomerDBDAO(cp);
		this.custcouponDBDAO = new CustomerCouponDBDAO(cp);
		this.compcouponDBDAO = new CompanyCouponDBDAO(cp);
		this.couponDBDAO = new CouponDBDAO(cp);
	}

	@Override
	public CouponClientFacade login(String name, String password, ClientType type) throws CouponSystemException {
		
		long id = compDBDAO.IsValidPassword(name, password);
		if (id != 0) {
			company = compDBDAO.get(id);
			return this;
		}else {
			throw new CouponSystemException("One of the credentials is not valid");
		}
	}


	public void createCoupon(Coupon c) throws CouponSystemException {

		boolean proceed = couponDBDAO.isTitleValid(c.getTitle());
		if (proceed) {
			long id = couponDBDAO.create(c);
			c.setId(id);
			compcouponDBDAO.createCoupon(company.getId(), id);
		} else {
			throw new CouponSystemException("Could not create new coupon");
		}

	}

	public void removeCoupon(Coupon c) throws CouponSystemException {
		
		custcouponDBDAO.deleteCoupon(c.getId()); // delete coupon from customer table
		compcouponDBDAO.deleteCouponFromAllCompanies(c.getId()); //delete coupon from company table
		couponDBDAO.remove(c); // delete coupon from coupon table
	}

	public void updateCoupon(Coupon c) throws CouponSystemException {
		
		Coupon temp = couponDBDAO.get(c.getId());
		temp.setPrice(c.getPrice());
		temp.setEndDate(c.getEndDate());
		couponDBDAO.update(temp);
		

	}
	
	public Coupon getCoupon(long id) throws CouponSystemException {
		return couponDBDAO.get(id);
	}
	
	public Collection<Coupon> getAllCoupons() throws CouponSystemException {
		return compDBDAO.getCoupons(company);
		
	}
	
	public Collection<Coupon> getCouponByType(CouponType type) throws CouponSystemException {
		
		Collection<Coupon> result = new ArrayList<>();
		for (long couponID : compcouponDBDAO.getAllCouponsByCompany(company.getId())) {
			if (couponDBDAO.get(couponID).getType().equals(type)) {
				result.add(couponDBDAO.get(couponID));
			}
		}
		return result;
			
		}
}

