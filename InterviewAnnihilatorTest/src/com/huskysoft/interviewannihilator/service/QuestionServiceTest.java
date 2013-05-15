/**
 * The class contains tests for the QuestionService class
 * 
 * @author Bennett Ng, 5/9/2013
 */

package com.huskysoft.interviewannihilator.service;

import java.io.IOException;

import org.json.JSONException;
import com.huskysoft.interviewannihilator.model.NetworkException;
import com.huskysoft.interviewannihilator.util.PaginatedQuestions;
import com.huskysoft.interviewannihilator.util.PaginatedSolutions;

import junit.framework.TestCase;

public class QuestionServiceTest extends TestCase {

	private QuestionService questionService;

	/**
	 * Construct new test instance
	 *
	 * @param name the test name
	 */
	public QuestionServiceTest(String name) {
		super(name);
		questionService = QuestionService.getInstance();
	}
	
	public void testGetAllQuestions() 
			throws NetworkException, JSONException, IOException {
		PaginatedQuestions questions = 
				questionService.getQuestions(null, null, 10, 0, false);
		System.out.println(questions);
	}
	
	public void testGetSolutions() 
			throws NetworkException, JSONException, IOException {
		PaginatedSolutions solutions = questionService.getSolutions(10, 10, 0);
		System.out.println(solutions);
	}

}