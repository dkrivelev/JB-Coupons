package facade;

import Connection.CouponSystem;
import Connection.CouponSystem.ClientType;
import Connection.CouponSystemException;

public interface CouponClientFacade {
	
	CouponClientFacade login(String name,String password, ClientType type) throws CouponSystemException;
	
}
