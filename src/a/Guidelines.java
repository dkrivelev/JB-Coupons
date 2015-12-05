package a;

public class Guidelines {

	/*
	 * create Set<connection>, has getConnection method which returns connection, and removes the connection from the set.
	 * additional method:
	 * return connection - receives connection, returns nothing. 
	 * closeAllConnection - void, קורה רק בזמן שמורידים את האפליקציה
	 * 
	 * create singleton:
	 * 1. private CTOR
	 * 2. getInstance - static method which returns instance of connection pool
	 * 
	 * 
	 * we create syncronized method - getConnection() - 
	 * first make sure connection pool is not empty.
	 * then iterate over available connections, use next() method of iterator,
	 * remove connection, 
	 * and return that connection to the caller of the method.
	 * 
	 * closeAllConnections:
	 * ====================
	 * take into consideration a situation where someone doesn't return a connection. how do we handle this?
	 * how to we still close all connections?
	 * 
	 * 
	 * --- Read about thread.join()
	 * 
	 * =======================
	 * 
	 * Second phase - Beans
	 * we create beans - class just with attributes, getters and setters. - for every table.
	 * also called POJO (plain old java object).
	 * 
	 * ======================
	 * 
	 * DAO - 
	 * CRUD - create read update and delete
	 * 
	 * first we create a general interface, to facilitate a future situation in which we will not works with a database as data repository.
	 * 
	 * 1. קבלת קונקשן מהפול
	 * 2. הגדרת פקודת sql
	 * 3. יצירת סטייטמנט או פריפיירד סטייטמנט
	 * 4. execution
	 * 
	 * if we want to automatic numbering of the table, we use prepared statement with PreparedStatement.ReturnGeneratedKeys.
	 * 
	 * Facade:
	 * ========
	 * 
	 * Which clients does the system have:
	 * 1. Admin
	 * 2. Company
	 * 3. customer
	 * Each with their own capabilites.
	 * 
	 * Coupon system:
	 * ==============
	 * Startup(); - מאתחלת קונקשן פול, מתחילה את הת'רד
	 * ניתן גם לעשות את זה ב- CTOR
	 * Shutdown(); - ההיפך מסטארטאפ, סגירת קונקשן פול, עצירת הת'רד
	 * 
	 * 
	 * main test - 
	 * CouponSystem cs = CouponSystem.getInstance();
	 * we can make sure getInstanc(); will initialize connection and thread,
	 * or we can do it using a dedicated startup(); method
	 * AdminFacade af = cs.login("admin","1234",clientType.Admin)
	 * Company c = new Company();
	 * c.set... , c.set... , setpassword, setemail, setname, etc
	 * 
	 * af.addCompany(c);
	 * 
	 * ..
	 * 
	 * ..
	 * cs.shutdown();
	 * 
	 */
}
