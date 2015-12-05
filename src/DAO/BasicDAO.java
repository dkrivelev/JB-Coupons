package DAO;

import java.util.Collection;

import Connection.CouponSystemException;

public interface BasicDAO<T> { // 

	
	/**
	 * According to specification, all 3 DAO interfaces have these 5 methods in common.
	 * 
	 * 
	 */
	long create(T t) throws CouponSystemException;
	void remove(T t) throws CouponSystemException;
	void update(T t) throws CouponSystemException;
	T get(long id) throws CouponSystemException;
	Collection<T> getAll() throws CouponSystemException;
	/*boolean IsValidPassword(String compName, String password) throws CouponSystemException;*/
	
	
}
