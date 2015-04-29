package com.sample;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jws.WebService;

import org.jboss.as.quickstarts.jms.MessageQueue;
import org.jboss.as.quickstarts.kitchensink_ear.data.GcdRepository;
import org.jboss.as.quickstarts.kitchensink_ear.model.Gcd;
import org.jboss.as.quickstarts.kitchensink_ear.service.GcdRegisteration;

@WebService(endpointInterface = "com.sample.Math", serviceName = "GcdWS")
public class MathWS implements Math {

	@Inject
	private GcdRepository gcdRepo;
	@Inject
	private GcdRegisteration gcdRegi;
	
	@Inject
	private MessageQueue queue;

	public int getGcd() {
		int gcdNumber = -1;
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
	
	
	private int getGcd(int i1, int i2) {
		if (i2 == 0) {
			return i1;
		}
		return getGcd(i2, i1 % i2);
	}

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