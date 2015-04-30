package com.alexpark.gcd.ws;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jws.WebService;

import com.alexpark.gcd.data.GcdRepository;
import com.alexpark.gcd.jms.MessageQueue;
import com.alexpark.gcd.model.Gcd;
import com.alexpark.gcd.service.GcdRegisteration;

@WebService(endpointInterface = "com.alexpark.gcd.ws.GcdWSService", serviceName = "GcdWS")
public class GcdWSServiceImpl implements GcdWSService {

	@Inject
	private GcdRepository gcdRepo;
	@Inject
	private GcdRegisteration gcdRegi;
	
	@Inject
	private MessageQueue queue;

	@Override
	public int getGcd() {
		int gcdNumber = 0;
        try {
        	
        	// pop 2 numbers from queue
        	int i1 = queue.pop();
        	int i2 = queue.pop();
        	gcdNumber = getGcd(i1, i2);
        	
    		// save result
    		Gcd gcd = new Gcd();
    		gcd.setGcd(gcdNumber);
    		gcdRegi.register(gcd);

        } catch (Exception e) {
            // Handle generic exceptions
        }

		return gcdNumber;
	}
	
	
	/**
	 * calcuating GCD by Euclidean algorithm 
	 * @param i1
	 * @param i2
	 * @return gcd
	 */
	private int getGcd(int i1, int i2) {
		if (i2 == 0) {
			return i1;
		}
		return getGcd(i2, i1 % i2);
	}

	@Override
	public List<Integer> gcdList() {
		List<Integer> gcdList = new ArrayList<Integer>();
		List<Gcd> findAll = gcdRepo.findAll();
		for (Gcd gcd : findAll) {
			gcdList.add(gcd.getGcd());
		}
		return gcdList;
	}
	
	@Override
	public int gcdSum() {
		Long result =  gcdRepo.getGcdSum();
		return result.intValue();
	}
}