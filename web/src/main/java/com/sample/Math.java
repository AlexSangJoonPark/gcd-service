package com.sample;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface Math {

	@WebMethod(action="getGcd")
	public int getGcd();

	@WebMethod(action="gcdList")
	public List<Integer> gcdList();
	
	@WebMethod(action="gcdSum")
	public int gcdSum();
}