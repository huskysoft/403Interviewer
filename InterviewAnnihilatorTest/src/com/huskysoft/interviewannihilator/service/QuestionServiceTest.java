/**
 * The class contains tests for the QuestionService class
 * 
 * @author Bennett Ng, 5/9/2013
 */

package com.huskysoft.interviewannihilator.service;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;

import android.os.Environment;

import com.huskysoft.interviewannihilator.model.NetworkException;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.model.UserInfo;
import com.huskysoft.interviewannihilator.util.PaginatedQuestions;
import com.huskysoft.interviewannihilator.util.PaginatedSolutions;
import com.huskysoft.interviewannihilator.util.TestHelpers;
import com.huskysoft.interviewannihilator.util.Utility;

import junit.framework.Assert;
import junit.framework.TestCase;

public class QuestionServiceTest extends TestCase {

	private QuestionService questionService;

	/**
	 * Construct new test instance
	 * 
	 * @param name
	 *            the test name
	 */
	public QuestionServiceTest(String name) {
		super(name);
		questionService = QuestionService.getInstance();
	}

	/**
	 * Tests that postQuestion throws IllegalArgumentException when given a null
	 * question. (Black box test)
	 * 
	 * @throws IOException
	 * @throws JSONException
	 * @throws NetworkException
	 */
	public void testpostQuestionNull() throws NetworkException, JSONException,
			IOException {
		try {
			QuestionService service = QuestionService.getInstance();
			service.postQuestion(null);
			Assert.fail("Posting null question should not be allowed.");
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
	public void testpostQuestionEmptyText() throws NetworkException,
			JSONException, IOException {
		try {
			QuestionService service = QuestionService.getInstance();
			Question question = TestHelpers.createDummyQuestion(0);
			question.setText("");
			service.postQuestion(question);
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
	public void testpostQuestionEmptyTitle() throws NetworkException,
			JSONException, IOException {
		try {
			QuestionService service = QuestionService.getInstance();
			Question question = TestHelpers.createDummyQuestion(0);
			question.setTitle("");
			service.postQuestion(question);
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
	public void testpostQuestionNullCategory() throws NetworkException,
			JSONException, IOException {
		try {
			QuestionService service = QuestionService.getInstance();
			Question question = TestHelpers.createDummyQuestion(0);
			question.setCategory(null);
			service.postQuestion(question);
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
	public void testpostQuestionNullDifficulty() throws NetworkException,
			JSONException, IOException {
		try {
			QuestionService service = QuestionService.getInstance();
			Question question = TestHelpers.createDummyQuestion(0);
			question.setDifficulty(null);
			service.postQuestion(question);
			Assert.fail("Posting question with null difficulty is disallowed.");
		} catch (IllegalArgumentException e) {
		}
	}
}
