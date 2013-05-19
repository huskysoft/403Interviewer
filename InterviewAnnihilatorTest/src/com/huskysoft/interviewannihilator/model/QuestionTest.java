/**
 * The class contains tests for the Question object framework
 * 
 * @author Dan Sanders, 5/19/2013
 */

package com.huskysoft.interviewannihilator.model;

import java.util.Date;

import com.huskysoft.interviewannihilator.util.TestHelpers;

import junit.framework.TestCase;

public class QuestionTest extends TestCase {

	/**
	 * Construct new test instance
	 *
	 * @param name the test name
	 */
	public QuestionTest(String name) {
		super(name);
	}
	
	/**
	 * Construct new Question test
	 * 
	 * @label white-box test
	 */
	public void testQuestionConstructor() {
		String questionText = "text";
		String questionTitle = "title";
		Question testQ = new Question(questionText, questionTitle, 
				Category.BRAINTEASER, Difficulty.MEDIUM);
		assertEquals(questionText, testQ.getText());
		assertEquals(questionTitle, testQ.getTitle());
	}
	
	/**
	 * Tests whether all the getters work
	 * 
	 * @label black-box test
	 */
	public void testQuestionGetters() {
		Question testQ = TestHelpers.createDummyQuestion(0);
		assertEquals(1, testQ.getAuthorId());
		assertEquals(Category.BRAINTEASER, testQ.getCategory());
		assertNotNull(testQ.getDateCreated());
		assertEquals(Difficulty.MEDIUM, testQ.getDifficulty());
		assertEquals(0, testQ.getQuestionId());
		assertEquals("Question no. " + 0, testQ.getTitle());
		assertEquals("Hello world" + 0, testQ.getText());
	}
	
	/**
	 * Tests whether all the setters work
	 * 
	 * @label white-box test
	 */
	public void testQuestionSetters() {
		Question testQ = TestHelpers.createDummyQuestion(0);
		testQ.setAuthorId(4);
		testQ.setCategory(Category.GAME);
		testQ.setDateCreated(new Date(3));
		testQ.setDifficulty(Difficulty.HARD);
		testQ.setQuestionId(3);
		testQ.setText("texty");
		testQ.setTitle("titly");
		assertEquals(4, testQ.getAuthorId());
		assertEquals(Category.GAME, testQ.getCategory());
		assertNotNull(testQ.getDateCreated());
		assertEquals(Difficulty.HARD, testQ.getDifficulty());
		assertEquals(3, testQ.getQuestionId());
		assertEquals("texty", testQ.getText());
		assertEquals("titly", testQ.getTitle());
	}
	
	/**
	 * Tests getters and setters of likes and dislikes
	 * 
	 * @label white-box test
	 */
	public void testLikesAndDislikes() {
		Question testQ = TestHelpers.createDummyQuestion(0);
		testQ.setLikes(5);
		testQ.setDislikes(3);
		assertEquals(5, testQ.getLikes());
		assertEquals(3, testQ.getDislikes());
	}
	
	/**
	 * Tests whether two "equal" questions are actually equal
	 * 
	 * @label white-box test
	 */
	public void testQuestionEquals() {
		Question q1 = TestHelpers.createDummyQuestion(0);
		Question q2 = TestHelpers.createDummyQuestion(0);
		// ensure dates are the same
		q1.setDateCreated(new Date(1));
		q2.setDateCreated(new Date(1));
		assertEquals(q1, q2);
	}
	
	/**
	 * Tests whether two "unequal" questions aren't considered equal to
	 * eachother
	 * 
	 * @label white-box test
	 */
	public void testQuestionNotEquals() {
		Question q1 = TestHelpers.createDummyQuestion(0);
		Question q2 = TestHelpers.createDummyQuestion(0);
		// ensure dates are the same
		q1.setDateCreated(new Date(1));
		q2.setDateCreated(new Date(1));
		q1.setTitle("different title");
		assertFalse(q1.equals(q2));
	}
	
	/**
	 * Tests whether two "equal" questions have the same hash code
	 * 
	 * @label white-box test
	 */
	public void testQuestionHashCode() {
		Question q1 = TestHelpers.createDummyQuestion(0);
		Question q2 = TestHelpers.createDummyQuestion(0);
		// ensure dates are the same
		q1.setDateCreated(new Date(1));
		q2.setDateCreated(new Date(1));
		assertEquals(q1.hashCode(), q2.hashCode());
	}
	
	/**
	 * Tests whether two "unequal" questions have different hash codes
	 * 
	 * @label white-box test
	 */
	public void testNotHashCode() {
		Question q1 = TestHelpers.createDummyQuestion(0);
		Question q2 = TestHelpers.createDummyQuestion(0);
		// ensure dates are the same
		q1.setDateCreated(new Date(1));
		q2.setDateCreated(new Date(1));
		q1.setTitle("different title");
		assertFalse(q1.hashCode() == q2.hashCode());
	}
	
	/**
	 * Tests the question toString method
	 * 
	 * @label white-box test
	 */
	public void testQuestionToString() {
		Question testQ = TestHelpers.createDummyQuestion(0);
		assertNotNull(testQ.toString());
	}
}