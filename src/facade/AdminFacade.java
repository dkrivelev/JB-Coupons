package facade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import DAO.CompanyCouponDBDAO;
import DAO.CompanyDBDAO;
import DAO.CustomerCouponDBDAO;
import DAO.CustomerDBDAO;
import DAO.DBDAO;
import Utility_classes.ConnectionPool;
import Utility_classes.CouponSystemException;
import Utility_classes.CouponSystem.ClientType;

public class AdminFacade implements CouponClientFacade {

	private final CompanyDBDAO compDBDAO;
	private final CustomerDBDAO custDBDAO;
	private final CustomerCouponDBDAO custcouponDBDAO;
	private final CompanyCouponDBDAO compcouponDBDAO;
	private final ConnectionPool cp;

	public AdminFacade() {
		super();
		this.cp = ConnectionPool.getInstance();
		this.compDBDAO = new CompanyDBDAO(cp);
		this.custDBDAO = new CustomerDBDAO(cp);
		this.custcouponDBDAO = new CustomerCouponDBDAO(cp);
		this.compcouponDBDAO = new CompanyCouponDBDAO(cp);

	}

	@Override
	public CouponClientFacade login(String name, String password, ClientType type) throws CouponSystemException {
		// TODO Auto-generated method stub
		if (name.equals("Admin") && password.equals("1234")) {
			return this;
		} else {
			throw new CouponSystemException("Invalid Credentials");
		}
	}

	public void createCompany(Company c) throws CouponSystemException {

		if (!compDBDAO.DoesNameExist(c.getCompName())) {
			long id = compDBDAO.create(c);
			c.setId(id);
		} else {
			throw new CouponSystemException("Couln't create new company");
		}

	}

	public void removeCompany(Company c) throws CouponSystemException {

		Collection<Long> allCompCompCouponsIds = compcouponDBDAO.getAllCouponsByCompany(c.getId());
		for (Long couponid : allCompCompCouponsIds) {
			custcouponDBDAO.deleteCoupon(couponid); // remove company coupons
													// from customer coupons
													// table
		}
		compcouponDBDAO.deleteAllCompanyCoupons(c.getId()); // Remove company
															// coupons from
															// company coupons
															// table
		compDBDAO.remove(c); // Remove company from company table

	}

	public void updateCompany(Company c) throws CouponSystemException {

		Company temp = compDBDAO.get(c.getId());
		if (!c.getCompName().equals(temp.getCompName())) {
			throw new CouponSystemException("Cannot update company name");
		} else {
			compDBDAO.update(c);
		}
	}

	public Company getCompany(long id) throws CouponSystemException {
		return compDBDAO.get(id);
	}

	public Collection<Company> getAllCompanies() throws CouponSystemException {
		return compDBDAO.getAll();
	}

	public void createCustomer(Customer c) throws CouponSystemException {

		if (!custDBDAO.DoesNameExist(c.getCustName())) {
			long id = custDBDAO.create(c);
			c.setId(id);
		} else {
			throw new CouponSystemException("Couln't create new customer");
		}

	}

	public void removeCustomer(Customer c) throws CouponSystemException {

		custcouponDBDAO.deleteAllCustomerCoupons(c.getId()); // delete customer
																// coupon from
																// customer
																// coupons table
		custDBDAO.remove(c); // delete customer from customers table

	}

	public void updateCustomer(Customer c) throws CouponSystemException {

		Customer temp = custDBDAO.get(c.getId());
		if (!c.getCustName().equals(temp.getCustName())) {
			throw new CouponSystemException("Cannot update customer name");
		} else {
			custDBDAO.update(c);
		}
	}

	public Customer getCustomer(long id) throws CouponSystemException {

		return custDBDAO.get(id);

	}

	public Collection<Customer> getAllCustomers() throws CouponSystemException {

		return custDBDAO.getAll();
	}
}
