/**
 * The class contains tests for the QuestionService class
 * 
 * @author Bennett Ng, 5/9/2013
 */

package com.huskysoft.interviewannihilator.service;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import com.huskysoft.interviewannihilator.model.NetworkException;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.model.Solution;
import com.huskysoft.interviewannihilator.util.TestHelpers;
import junit.framework.Assert;
import junit.framework.TestCase;

public class QuestionServiceTest extends TestCase {

	private QuestionService questionService;
	private ObjectMapper mapper;

	/**
	 * Construct new test instance
	 * 
	 * @param name
	 *            the test name
	 */
	public QuestionServiceTest(String name) {
		super(name);
		questionService = QuestionService.getInstance();
		mapper = new ObjectMapper();
	}

	/**
	 * Tests that postQuestion throws IllegalArgumentException when given a null
	 * question. (Black box test)
	 * 
	 * @throws IOException
	 * @throws JSONException
	 * @throws NetworkException
	 */
	public void testPostQuestionNull() throws NetworkException, JSONException,
			IOException {
		try {
			questionService.postQuestion(null);
			Assert.fail("Posting null question should not be allowed.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests that postQuestion throws IllegalArgumentException when given a
	 * question object with an null text field. (White box test)
	 * 
	 * @throws IOException
	 * @throws JSONException
	 * @throws NetworkException
	 */
	public void testPostQuestionNullText() throws NetworkException,
			JSONException, IOException {
		try {
			Question question = TestHelpers.createDummyQuestion(0);
			question.setText(null);
			questionService.postQuestion(question);
			Assert.fail("Posting question with empty text is disallowed.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests that postQuestion throws IllegalArgumentException when given a
	 * question object with an null title field. (White box test)
	 * 
	 * @throws IOException
	 * @throws JSONException
	 * @throws NetworkException
	 */
	public void testPostQuestionNullTitle() throws NetworkException,
			JSONException, IOException {
		try {
			Question question = TestHelpers.createDummyQuestion(0);
			question.setTitle(null);
			questionService.postQuestion(question);
			Assert.fail("Posting question with empty title is disallowed.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests that postQuestion throws IllegalArgumentException when given a
	 * question object with an empty text field. (White box test)
	 * 
	 * @throws IOException
	 * @throws JSONException
	 * @throws NetworkException
	 */
	public void testPostQuestionEmptyText() throws NetworkException,
			JSONException, IOException {
		try {
			Question question = TestHelpers.createDummyQuestion(0);
			question.setText("");
			questionService.postQuestion(question);
			Assert.fail("Posting question with empty text is disallowed.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests that postQuestion throws IllegalArgumentException when given a
	 * question object with an empty title field. (White box test)
	 * 
	 * @throws IOException
	 * @throws JSONException
	 * @throws NetworkException
	 */
	public void testPostQuestionEmptyTitle() throws NetworkException,
			JSONException, IOException {
		try {
			Question question = TestHelpers.createDummyQuestion(0);
			question.setTitle("");
			questionService.postQuestion(question);
			Assert.fail("Posting question with empty title is disallowed.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests that postQuestion throws IllegalArgumentException when given a
	 * question object with a null category field. (White box test)
	 * 
	 * @throws IOException
	 * @throws JSONException
	 * @throws NetworkException
	 */
	public void testPostQuestionNullCategory() throws NetworkException,
			JSONException, IOException {
		try {
			Question question = TestHelpers.createDummyQuestion(0);
			question.setCategory(null);
			questionService.postQuestion(question);
			Assert.fail("Posting question with null category is disallowed.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests that postQuestion throws IllegalArgumentException when given a
	 * question object with a null difficulty field. (White box test)
	 * 
	 * @throws IOException
	 * @throws JSONException
	 * @throws NetworkException
	 */
	public void testPostQuestionNullDifficulty() throws NetworkException,
			JSONException, IOException {
		try {
			Question question = TestHelpers.createDummyQuestion(0);
			question.setDifficulty(null);
			questionService.postQuestion(question);
			Assert.fail("Posting question with null difficulty is disallowed.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests that postSolution throws IllegalArgumentException when given a null
	 * solution. (Black box test)
	 * 
	 * @throws IOException
	 * @throws JSONException
	 * @throws NetworkException
	 */
	public void testPostSolutionNull() throws NetworkException, JSONException,
			IOException {
		try {
			questionService.postSolution(null);
			Assert.fail("Posting null solution should not be allowed.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests that postSolution throws IllegalArgumentException when given a
	 * solution object with an null text field. (White box test)
	 * 
	 * @throws IOException
	 * @throws JSONException
	 * @throws NetworkException
	 */
	public void testPostSolutionNullText() throws NetworkException,
			JSONException, IOException {
		try {
			Solution solution = TestHelpers.createDummySolution(0);
			solution.setText(null);
			questionService.postSolution(solution);
			Assert.fail("Posting solution with null text is disallowed.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests that postSolution throws IllegalArgumentException when given a
	 * solution object with an empty text field. (White box test)
	 * 
	 * @throws IOException
	 * @throws JSONException
	 * @throws NetworkException
	 */
	public void testPostSolutionEmptyText() throws NetworkException,
			JSONException, IOException {
		try {
			Solution solution = TestHelpers.createDummySolution(0);
			solution.setText("");
			questionService.postSolution(solution);
			Assert.fail("Posting solution with empty text is disallowed.");
		} catch (IllegalArgumentException e) {
		}
	}
}
