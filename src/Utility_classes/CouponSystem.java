package Utility_classes;

import java.sql.SQLException;

import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CouponClientFacade;
import facade.CustomerFacade;

public class CouponSystem {
	
	private static CouponSystem instance = null;
	private DailyCouponExpirationTask dailyTask;
	private Thread cleanupThread;
	
	public enum ClientType {
		Admin,Company,Customer
	}
	
	private CouponSystem() throws CouponSystemException {
		this.dailyTask = new DailyCouponExpirationTask();
		this.cleanupThread = new Thread(this.dailyTask);
		this.cleanupThread.start();
	}
	
	public static CouponSystem getInstance() throws CouponSystemException {
		if (instance == null) {
			instance=new CouponSystem();
		}
		return instance;
	}
	
	public CouponClientFacade login(String name,String password,ClientType type) throws CouponSystemException {
		/**
		 * General login method.
		 * Instantiates a new Facade, according to the type of client it receives.
		 * 
		 * Returns - 
		 * A Facade object which can be used to call all the facade methods,
		 * as it has already been authenticated. 
		 * (If authentication is successful: 
		 * "Customer" object of customer facade , and "Company" object of company facade, cease to be null,
		 * Therefore all methods have an actual object to work with, and not a null pointer)  
		 * 
		 */
		CouponClientFacade result = null;
		switch (type) {
		case Customer:
			result = new CustomerFacade();
			break;
		case Company:
			result = new CompanyFacade();
			break;
		case Admin:
			result = new AdminFacade();
			break;
	}
		return result.login(name, password, type);

	}
	public void shutdown() throws CouponSystemException {
		this.dailyTask.end_daily_task();
		try {
			ConnectionPool.getInstance().CloseAllConnections();
		} catch (SQLException e) {
			throw new CouponSystemException("could not shut close all DB connections");
		}
	}
}
