package facade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import Connection.ConnectionPool;
import Connection.CouponSystem.ClientType;
import Connection.CouponSystemException;
import DAO.CompanyCouponDBDAO;
import DAO.CompanyDBDAO;
import DAO.CustomerCouponDBDAO;
import DAO.CustomerDBDAO;
import DAO.DBDAO;

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

		Connection con = null;
		try {
			con = cp.getConnection();
			String query = "select count(comp_name) as count from company where comp_name=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, c.getCompName());
			ResultSet rs = pstmt.executeQuery();
			if (rs.getInt("count")!=0) {
				throw new CouponSystemException("Couln't create company, company with that name exists");
			} else {
				long id = compDBDAO.create(c);
				c.setId(id);
			}
			cp.returnConnection(con);

		} catch (SQLException e) {
			throw new CouponSystemException("Could not create new company");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
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

		Connection con = null;
		try {
			con = cp.getConnection();
			String query = "select count(*) as cnt from CUSTOMER where CUST_NAME=?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, c.getCustName());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int count = rs.getInt("cnt");
				if (count == 0) {
					long id = custDBDAO.create(c);
					c.setId(id);
				} else {
					throw new CouponSystemException("Customer with this name already exists");
				}
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Could not create Customer");
		} finally {
			try {
				DBDAO.returnConnectionToPool(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
