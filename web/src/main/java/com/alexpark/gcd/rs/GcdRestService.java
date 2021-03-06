/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alexpark.gcd.rs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.alexpark.gcd.data.NumberRepository;
import com.alexpark.gcd.jms.MessageQueue;
import com.alexpark.gcd.model.InputNumber;
import com.alexpark.gcd.service.NumberRegistration;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the members table.
 */
@Path("/gcd")
@RequestScoped
public class GcdRestService {
	
    @Inject
    private Logger log;

    @Inject
    private NumberRepository numRepo;
    
    @Inject
    private NumberRegistration numRegi;
    
	@Inject
	private MessageQueue queue;

    /**
     * return all numbers what have been requested from database
     * @return requested numbers
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Integer> list() {
    	List<Integer> list = new ArrayList<Integer>();
    	List<InputNumber> findAll = numRepo.findAll();
    	for (InputNumber inputNumber : findAll) {
    		list.add(inputNumber.getNumber());
		}
        return list;
    }

    
    /**
     * push requested two numbers into JMS queue, 
     * then save the numbers into database.
     * 
     * @param i1
     * @param i2
     * @return status
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response push(@FormParam("i1") int i1, @FormParam("i2") int i2) {

        Response.ResponseBuilder builder = null;

        try {
        	log.info("push input params into queue [i1="+i1+", i2="+i2+"]");
        	queue.push(new int[]{i1, i2});
        	
        	InputNumber number = new InputNumber();
        	number.setNumber(i1);
        	numRegi.register(number);
        	
        	number = new InputNumber();
        	number.setNumber(i2);
        	numRegi.register(number);
        	log.info("saved input params into database successfully.");
            
        	// Create an "OK" response
            builder = Response.ok(Response.Status.OK);

        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }

}
