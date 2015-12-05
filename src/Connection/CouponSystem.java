package Connection;

import java.sql.SQLException;

import DAO.CouponDBDAO;
import Utility_classes.DailyCouponExpirationTask;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CouponClientFacade;
import facade.CustomerFacade;

public class CouponSystem {
	
	private static CouponSystem instance;
	private DailyCouponExpirationTask dcet;
	private Thread cleanupThread;
	
	public enum ClientType {
		Admin,Company,Customer
	}
	
	private CouponSystem() throws CouponSystemException {
		this.dcet = new DailyCouponExpirationTask();
		this.cleanupThread = new Thread(this.dcet);
		this.cleanupThread.start();
	}
	
	public static CouponSystem getInstance() throws CouponSystemException {
		if (instance == null) {
			instance=new CouponSystem();
		}
		return instance;
	}
	
	public CouponClientFacade login(String name,String password,ClientType type) throws CouponSystemException {
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
		this.dcet.end_daily_task();
		try {
			ConnectionPool.getInstance().CloseAllConnections();
		} catch (SQLException e) {
			throw new CouponSystemException("could not shut close all DB connections");
		}
	}
}
