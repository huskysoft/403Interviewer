/**
 * This class contains tests for the NetworkService class
 * 
 * @author Bennett Ng, 5/9/2013
 */

package com.huskysoft.interviewannihilator.service;

import com.huskysoft.interviewannihilator.model.NetworkException;

import junit.framework.TestCase;


public class NetworkServiceTest extends TestCase {
	
	private NetworkService networkService;

	/**
	 * Construct new test instance
	 *
	 * @param name the test name
	 */
	public NetworkServiceTest(String name) {
		super(name);
		this.networkService = NetworkService.getInstance();
	}
	
	public void testGetAllQuestions() throws NetworkException {
		assertNotNull(networkService.getQuestions(null, null, 10, 0));
	}
}
