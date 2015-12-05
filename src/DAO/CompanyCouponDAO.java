package DAO;

import java.util.Collection;

import Beans.Coupon;
import Connection.CouponSystemException;

public interface CompanyCouponDAO {
	
	void createCoupon(long COMP_ID ,long COUPON_ID) throws CouponSystemException;
	Collection<Long> getAllCouponsByCompany(long COMP_ID) throws CouponSystemException;
	void deleteCouponByCompany(long COMP_ID, long COUPON_ID)  throws CouponSystemException;
	void deleteAllCompanyCoupons(long COMP_ID)  throws CouponSystemException;
	void deleteCouponFromAllCompanies(long COUPON_ID)  throws CouponSystemException;
	

}
