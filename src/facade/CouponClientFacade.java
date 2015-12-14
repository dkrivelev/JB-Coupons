package facade;

import Utility_classes.CouponSystem;
import Utility_classes.CouponSystemException;
import Utility_classes.CouponSystem.ClientType;

public interface CouponClientFacade {
	
	CouponClientFacade login(String name,String password, ClientType type) throws CouponSystemException;
	
}
