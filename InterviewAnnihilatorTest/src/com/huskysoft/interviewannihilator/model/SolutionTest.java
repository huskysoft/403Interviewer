/**
 * The class contains tests for the Solution object framework
 * 
 * @author Dan Sanders, 5/19/2013
 */

package com.huskysoft.interviewannihilator.model;

import java.util.Date;

import com.huskysoft.interviewannihilator.util.TestHelpers;

import junit.framework.TestCase;

public class SolutionTest extends TestCase {

	/**
	 * Construct new test instance
	 *
	 * @param name the test name
	 */
	public SolutionTest(String name) {
		super(name);
	}

	/**
	 * Construct new Solution test
	 * 
	 * @label white-box test
	 */
	public void testSolutionConstructor() {
		String solutionText = "text";
		Solution testS = new Solution(0, solutionText);
		assertEquals(solutionText, testS.getText());
		assertEquals(0, testS.getQuestionId());
	}
	
	/**
	 * Tests whether all the getters work
	 * 
	 * @label black-box test
	 */
	public void testSolutionGetters() {
		Solution testS = TestHelpers.createDummySolution(0);
		assertEquals(1, testS.getAuthorId());
		assertEquals("Some solution" + 0, testS.getText());
		assertEquals(0, testS.getQuestionId());
		assertEquals(0, testS.getSolutionId());
		assertNotNull(testS.getDateCreated());
	}
	
	/**
	 * Tests whether all the setters work
	 * 
	 * @label white-box test
	 */
	public void testSolutionSetters() {
		Solution testS = TestHelpers.createDummySolution(0);
		testS.setSolutionId(8);
		testS.setDateCreated(new Date(8));
		testS.setAuthorId(8);
		testS.setQuestionId(8);
		testS.setText("8");
		assertEquals(8, testS.getAuthorId());
		assertEquals("8", testS.getText());
		assertEquals(8, testS.getQuestionId());
		assertEquals(8, testS.getSolutionId());
		assertNotNull(testS.getDateCreated());
	}
	
	/**
	 * Tests getters and setters of likes and dislikes
	 * 
	 * @label white-box test
	 */
	public void testLikesAndDislikes() {
		Solution testS = TestHelpers.createDummySolution(0);
		testS.setLikes(5);
		testS.setDislikes(3);
		assertEquals(5, testS.getLikes());
		assertEquals(3, testS.getDislikes());
	}
	
	/**
	 * Tests whether two "equal" Solutions are actually equal
	 * 
	 * @label white-box test
	 */
	public void testSolutionEquals() {
		Solution sOne = TestHelpers.createDummySolution(0);
		Solution sTwo = TestHelpers.createDummySolution(0);
		// ensure dates are the same
		sOne.setDateCreated(new Date(1));
		sTwo.setDateCreated(new Date(1));
		// Note we assert true that they are equal to ensure that
		// the Solution equals() method is called, not Object equals()
		// or ==
		assertTrue(sOne.equals(sTwo));
	}
	
	/**
	 * Tests whether two "unequal" Solutions aren't considered equal to
	 * eachother
	 * 
	 * @label white-box test
	 */
	public void testSolutionNotEquals() {
		Solution sOne = TestHelpers.createDummySolution(0);
		Solution sTwo = TestHelpers.createDummySolution(0);
		// ensure dates are the same
		sOne.setDateCreated(new Date(1));
		sTwo.setDateCreated(new Date(1));
		sOne.setText("different text");
		assertFalse(sOne.equals(sTwo));
	}
	
	/**
	 * Tests whether two "equal" Solutions have the same hash code
	 * 
	 * @label white-box test
	 */
	public void testSolutionHashCode() {
		Solution sOne = TestHelpers.createDummySolution(0);
		Solution sTwo = TestHelpers.createDummySolution(0);
		// ensure dates are the same
		sOne.setDateCreated(new Date(1));
		sTwo.setDateCreated(new Date(1));
		assertEquals(sOne.hashCode(), sTwo.hashCode());
	}
	
	/**
	 * Tests whether two "unequal" Solutions have different hash codes
	 * 
	 * @label white-box test
	 */
	public void testNotHashCode() {
		Solution sOne = TestHelpers.createDummySolution(0);
		Solution sTwo = TestHelpers.createDummySolution(0);
		// ensure dates are the same
		sOne.setDateCreated(new Date(1));
		sTwo.setDateCreated(new Date(1));
		sOne.setText("different text");
		assertFalse(sOne.hashCode() == sTwo.hashCode());
	}
	
	/**
	 * Tests the solution toString method
	 * 
	 * @label white-box test
	 */
	public void testSolutionToString() {
		Solution testS = TestHelpers.createDummySolution(0);
		assertNotNull(testS.toString());
	}
}