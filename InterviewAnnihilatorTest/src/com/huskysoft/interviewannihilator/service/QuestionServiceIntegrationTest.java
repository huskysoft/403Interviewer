package com.huskysoft.interviewannihilator.service;

import junit.framework.TestCase;

import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import com.huskysoft.interviewannihilator.model.NetworkException;
import com.huskysoft.interviewannihilator.service.QuestionService;
import com.huskysoft.interviewannihilator.util.PaginatedQuestions;

public class QuestionServiceIntegrationTest extends TestCase {

	private QuestionService questionService;
	
	@BeforeClass
	public void setUp() throws NetworkException {
		questionService = QuestionService.getInstance();
	}
	
	@Test
	public void testGetAllQuestions() throws NetworkException, JSONException {
		PaginatedQuestions questions = questionService.getQuestions(null, null, 10, 0);
		System.out.println(questions);
	}

}
