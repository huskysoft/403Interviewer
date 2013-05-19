/**
 * The class contains tests for the Category Enum
 * 
 * @author Dan Sanders, 5/18/2013
 */

package com.huskysoft.interviewannihilator.model;

import junit.framework.TestCase;

public class CategoryTest extends TestCase {

	/**
	 * Construct new test instance
	 *
	 * @param name the test name
	 */
	public CategoryTest(String name) {
		super(name);
	}
	
	/**
	 * This tests the construction and usage of the enum Category
	 * 
	 * @label white-box test
	 */
	public void testCategorySetting() {
		Category compSci = Category.COMPSCI;
		Category business = Category.BUSINESS;
		Category[] allValues = Category.values();
		assertEquals(compSci, allValues[0]);
		assertEquals(business, allValues[1]);
	}	
}