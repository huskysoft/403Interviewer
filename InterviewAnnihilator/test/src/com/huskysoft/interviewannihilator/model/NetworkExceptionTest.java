/**
 * The class contains tests for the NetworkException that is thrown all over
 * our code
 * 
 * @author Dan Sanders, 5/19/2013
 */

package com.huskysoft.interviewannihilator.model;

import junit.framework.TestCase;

public class NetworkExceptionTest extends TestCase {

	/**
	 * Construct new test instance
	 *
	 * @param name the test name
	 */
	public NetworkExceptionTest(String name) {
		super(name);
	}
	
	/**
	 * This tests the construction of a null NetworkException
	 * 
	 * @label white-box test
	 */
	public void testNetworkExceptionEmptyConstructor() {
		NetworkException nullExcept = new NetworkException();
		assertNull(nullExcept.getE());
		assertNull(nullExcept.getMessage());
	}
	
	/**
	 * This tests the two-arg constructor of a NetworkException
	 * 
	 * @label white-box test
	 */
	public void testNetworkExceptionTwoArgConstructor() {
		Throwable throwerTest = new Throwable();
		NetworkException newExcept = new NetworkException
				("test message", throwerTest);
		assertNotNull(newExcept.getMessage());
		assertNotNull(newExcept.getE());
	}
	
	/**
	 * This tests the one-arg constructor of a NetworkException
	 * 
	 * @label white-box test
	 */
	public void testNetworkExceptionOneArgConstructor() {
		NetworkException newExcept = new NetworkException("test message");
		assertNotNull(newExcept.getMessage());
		assertNull(newExcept.getE());
	}
}