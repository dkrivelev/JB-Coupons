package DAO;
import Beans.*;
import Connection.CouponSystemException;

import java.util.Collection;

public interface CustomerDAO extends BasicDAO<Customer> {
	
	Collection<Coupon> getCoupons(Customer c) throws CouponSystemException;
	Collection<Customer> getAll() throws CouponSystemException;
	long IsValidPassword(String custName, String password) throws CouponSystemException;
	
	}
