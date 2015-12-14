package DAO;

import java.util.Collection;

import Beans.Coupon;
import Beans.CouponType;
import Utility_classes.CouponSystemException;

public interface CouponDAO extends BasicDAO<Coupon> {

	
	Collection<Coupon> getCouponByType(CouponType type) throws CouponSystemException;
	
}
