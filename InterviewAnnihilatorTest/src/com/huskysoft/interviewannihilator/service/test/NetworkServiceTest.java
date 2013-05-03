package com.huskysoft.interviewannihilator.service.test;

import org.junit.BeforeClass;
import org.junit.Test;

import com.huskysoft.interviewannihilator.model.NetworkException;
import com.huskysoft.interviewannihilator.service.NetworkService;

import junit.framework.TestCase;

public class NetworkServiceTest extends TestCase {
	
	private NetworkService networkService;
	
	@BeforeClass
	public void setUp() {
		this.networkService = NetworkService.getInstance();
	}
	

	@Test
	public void testGetAllQuestions() throws NetworkException {
		System.out.println(networkService.getQuestions(null, null, 10, 0));
	}
}
