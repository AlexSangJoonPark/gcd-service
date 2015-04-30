package com.alexpark.gcd.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface GcdWSService {

	/**
	 * calculating gcd by two numbers from queue
	 * @return gcd
	 */
	@WebMethod(action="getGcd")
	public int getGcd();

	/**
	 * return gcd list what have been calculated from db
	 * @return
	 */
	@WebMethod(action="gcdList")
	public List<Integer> gcdList();
	
	/**
	 * return total gcd sum from database
	 * @return
	 */
	@WebMethod(action="gcdSum")
	public int gcdSum();
}