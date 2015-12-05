package DAO;

import java.util.Collection;

import Beans.Coupon;
import Connection.*;

public interface CustomerCouponDAO {
	
	void createCoupon(long cust_id,long coupon_id) throws CouponSystemException;
	Collection<Long> getAllCouponsByCustomer(long cust_id) throws CouponSystemException;
	void deleteCouponByCustomer(long cust_id, long COUPON_ID)  throws CouponSystemException;
	void deleteAllCustomerCoupons(long cust_id)  throws CouponSystemException;
	void deleteCoupon(long coupon_id)  throws CouponSystemException;
	

}
