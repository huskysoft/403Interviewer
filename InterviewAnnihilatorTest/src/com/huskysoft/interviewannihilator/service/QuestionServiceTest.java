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
import com.huskysoft.interviewannihilator.model.UserInfo;
import com.huskysoft.interviewannihilator.util.PaginatedQuestions;
import com.huskysoft.interviewannihilator.util.PaginatedSolutions;
import com.huskysoft.interviewannihilator.util.TestHelpers;
import com.huskysoft.interviewannihilator.util.Utility;

import junit.framework.TestCase;

public class QuestionServiceTest extends TestCase {

	private QuestionService questionService;
	private ObjectMapper mapper;

	/**
	 * Construct new test instance
	 *
	 * @param name the test name
	 */
	public QuestionServiceTest(String name) {
		super(name);
		questionService = QuestionService.getInstance();
		mapper = new ObjectMapper();
	}
	
	public void testGetAllQuestions() 
			throws NetworkException, JSONException, IOException {
		PaginatedQuestions questions = 
				questionService.getQuestions(null, null, 10, 0, false);
		assertNotNull(questions);
		assertEquals(Math.min(10, questions.getTotalNumberOfResults()),
				questions.getQuestions().size());
		System.out.println(questions);
	}
	
	public void testGetSolutions() 
			throws NetworkException, JSONException, IOException {
		PaginatedSolutions solutions = questionService.getSolutions(10, 10, 0);
		assertNotNull(solutions);
		assertEquals(Math.min(10, solutions.getTotalNumberOfResults()), 
				solutions.getSolutions().size());
		System.out.println(solutions);
	}
	
	public void testReadWriteUserInfo() throws JsonGenerationException, 
			JsonMappingException, IOException, NetworkException {		
		// write UserInfo to a file
		UserInfo userInfo = TestHelpers.createDummyUserInfo();
		String json = mapper.writeValueAsString(userInfo);
		File path = Environment.getExternalStorageDirectory();
		File file = new File(path, Utility.USER_INFO_FILENAME);
		Utility.writeStringToFile(file, json);
		try {
			// load UserInfo in QuestionService
			questionService.initializeUserInfo(path, userInfo.getUserEmail());
			
			// modify userInfo and write changes
			questionService.clearAllFavorites();
			questionService.writeUserInfo();
			
			// read from file and verify changes
			String str = Utility.readStringFromFile(file);
			UserInfo clone = mapper.readValue(str, UserInfo.class);
			assertFalse(userInfo.equals(clone));
			userInfo.getFavoriteQuestions().clear();
			assertEquals(userInfo, clone);
		} finally {
			file.delete();
		}
	}

	
}
