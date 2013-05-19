/**
 * The class contains tests for the Difficulty Enum
 * 
 * @author Dan Sanders, 5/19/2013
 */

package com.huskysoft.interviewannihilator.model;

import junit.framework.TestCase;

public class DifficultyTest extends TestCase {

	/**
	 * Construct new test instance
	 *
	 * @param name the test name
	 */
	public DifficultyTest(String name) {
		super(name);
	}
	
	/**
	 * This tests the construction and usage of the enum Difficulty
	 * 
	 * @label white-box test
	 */
	public void testDifficultySetting() {
		Difficulty easy = Difficulty.EASY;
		Difficulty medium = Difficulty.MEDIUM;
		Difficulty hard = Difficulty.HARD;
		Difficulty[] allValues = Difficulty.values();
		assertEquals(easy, allValues[0]);
		assertEquals(medium, allValues[1]);
		assertEquals(hard, allValues[2]);
	}	
}