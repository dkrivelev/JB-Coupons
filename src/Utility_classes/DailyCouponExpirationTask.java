package Utility_classes;

import java.sql.Date;
import java.util.GregorianCalendar;

import Beans.Coupon;
import Connection.ConnectionPool;
import Connection.CouponSystemException;
import DAO.CompanyCouponDBDAO;
import DAO.CompanyDBDAO;
import DAO.CouponDBDAO;
import DAO.CustomerCouponDBDAO;

public class DailyCouponExpirationTask implements Runnable {
	
	private final CouponDBDAO couponDBDAO;
	private final CustomerCouponDBDAO custcoupDBDAO;
	private final CompanyCouponDBDAO compcoupDBDAO;
	private boolean quit;
	private final ConnectionPool cp;
	
	

	public DailyCouponExpirationTask() {
		super();
		this.cp = ConnectionPool.getInstance();
		this.couponDBDAO = new CouponDBDAO(cp);
		this.custcoupDBDAO = new CustomerCouponDBDAO(cp);
		this.compcoupDBDAO = new CompanyCouponDBDAO(cp);
		this.quit = false;
		
		
	}

	@Override
	public void run() {
	while (!quit) {
		Date today = new Date(GregorianCalendar.getInstance().getTimeInMillis());
		try {
		for (Coupon expiredCoupon : couponDBDAO.getCouponByEndDate(today)) {
			
			custcoupDBDAO.deleteCoupon(expiredCoupon.getId());
			compcoupDBDAO.deleteCouponFromAllCompanies(expiredCoupon.getId());
			couponDBDAO.remove(expiredCoupon);
			Thread.sleep(86400000);
		}
		}catch (InterruptedException | CouponSystemException e) {
			throw new CouponSystemException("Daily deletion task of coupons failed");
		}
		
	}
	}
	
	public void end_daily_task() {
		quit = true;
	}
}


