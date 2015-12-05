package Test;

import Connection.CouponSystem;
import Connection.CouponSystem.ClientType;
import facade.AdminFacade;

public class Test {
	
	CouponSystem cs = CouponSystem.getInstance();
	AdminFacade af = (AdminFacade) cs.login("admin", "1234", ClientType.Admin);
	
	
}