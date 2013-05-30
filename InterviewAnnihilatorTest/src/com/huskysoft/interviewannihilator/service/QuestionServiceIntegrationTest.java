/**
 * The class contains tests for the QuestionService class
 * 
 * @author Bennett Ng, 5/9/2013
 */

package com.huskysoft.interviewannihilator.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;

import android.os.Environment;

import com.huskysoft.interviewannihilator.model.NetworkException;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.model.Solution;
import com.huskysoft.interviewannihilator.model.UserInfo;
import com.huskysoft.interviewannihilator.util.DatabaseStub;
import com.huskysoft.interviewannihilator.util.PaginatedQuestions;
import com.huskysoft.interviewannihilator.util.PaginatedSolutions;
import com.huskysoft.interviewannihilator.util.TestHelpers;
import com.huskysoft.interviewannihilator.util.Utility;

import junit.framework.TestCase;

public class QuestionServiceIntegrationTest extends TestCase {

	private QuestionService questionService;
	private ObjectMapper mapper;

	/**
	 * Construct new test instance
	 *
	 * @param name the test name
	 */
	public QuestionServiceIntegrationTest(String name) {
		super(name);
		questionService = QuestionService.getInstance();
		mapper = new ObjectMapper();
	}

	/**
	 * testGetQuestions actually gets a number of questions from the
	 * database, and tests whether the questions retrieved and the data in the
	 * paginatedQuestions object is what it should be
	 * 
	 * @label white-box test
	 * Vertically test the ability to get Questions from the DB
	 * 
	 * @label WhiteBox
	 * @throws NetworkException
	 * @throws JSONException
	 * @throws IOException
	 */
	public void testGetQuestions() throws NetworkException, IOException {
		PaginatedQuestions questions = 
				questionService.getQuestions(null, null, 10, 0, false);
		assertNotNull(questions);
		assertEquals(Math.min(10, questions.getTotalNumberOfResults()),
				questions.getQuestions().size());
	}

	/**
	 * Give bad limit and offset to getQuestions
	 * 
	 * @label Black-box test
	 */
	public void testGetQuestionsBadArguments()
			throws NetworkException, IOException {
		try {
			questionService.getQuestions(null, null, -1, -1, false);
			fail("Should have gotten IllegalArgumentException");
		}
		catch (IllegalArgumentException e) {
			// expected
		}
	}
	
	/**
	 * Test fetching Questions which I authored
	 * @throws IOException 
	 * @throws NetworkException 
	 * 
	 * @label White-box test
	 */
	public void testGetMyQuestions() throws NetworkException, IOException {
		UserInfo uinfo = TestHelpers.createTestUserInfo();
		questionService.setUserInfo(uinfo);
		PaginatedQuestions questions = 
				questionService.getMyQuestions(null, null, 10, 0, false);
		assertNotNull(questions);
		assertEquals(Math.min(10, questions.getTotalNumberOfResults()),
				questions.getQuestions().size());
		for (Question q : questions.getQuestions()) {
			assertEquals(uinfo.getUserId(), Integer.valueOf(q.getAuthorId()));
		}
	}

	/**
	 * testGetSolutions tests if the retrieval of solutions for a given
	 * question from the database is working.
	 * 
	 * @label White-box test
	 * Vertically test the ability to get Solutions for a given Question from
	 * the DB
	 * 
	 * @label WhiteBox
	 * @throws NetworkException
	 * @throws JSONException
	 * @throws IOException
	 */
	public void testGetSolutions() throws NetworkException, IOException {
		PaginatedSolutions solutions = questionService.getSolutions(
				TestHelpers.VALID_QUESTION_ID, 10, 0);
		assertNotNull(solutions);
		assertEquals(Math.min(10, solutions.getTotalNumberOfResults()), 
				solutions.getSolutions().size());
	}

	/**
	 * Round-trip test the ability to create, read, and delete a specific \
	 * Question.
	 * 
	 * Please note that this functionality was implemented using test-driven 
	 * development (TDD)
	 * 
	 * @label Black-box test
	 * @throws NetworkException
	 * @throws JSONException
	 * @throws IOException
	 */
	public void testQuestionRoundTrip() throws NetworkException, IOException {
		// set up
		questionService.setUserInfo(TestHelpers.createTestUserInfo());
		
		// create
		Question qInit = TestHelpers.createDummyQuestion(42);
		int qId = questionService.postQuestion(qInit);
		
		// read
		List<Integer> qIdList = new ArrayList<Integer>();
		qIdList.add(qId);
		List<Question> qList = questionService.getQuestionsById(qIdList);
		assertEquals(1, qList.size());
		Question qCreated = qList.get(0);
		qInit.setDateCreated(qCreated.getDateCreated());
		qInit.setQuestionId(qId);
		assertEquals(qInit, qCreated);
		
		// delete
		boolean successDelete = questionService.deleteQuestion(qId);
		assertTrue(successDelete);
		qList = questionService.getQuestionsById(qIdList);
		assertEquals(0, qList.size());
	}

	/**
	 * Round-trip test the ability to create, read, and delete a specific \
	 * Solution.
	 * 
	 * This was also written using TDD (Test-Driven Development)
	 * 
	 * @label Black-box test
	 * @throws NetworkException
	 * @throws JSONException
	 * @throws IOException
	 */
	public void testSolutionRoundTrip() throws NetworkException, IOException {
		// set up
		questionService.setUserInfo(TestHelpers.createTestUserInfo());
		// create question
		Question qInit = TestHelpers.createDummyQuestion(42);
		int qId = questionService.postQuestion(qInit);
		// create solution
		Solution sInit = TestHelpers.createDummySolution(qId);
		int sId = questionService.postSolution(sInit);
		sInit.setSolutionId(sId);
		
		// read
		PaginatedSolutions results = questionService.getSolutions(qId, 1, 0);
		assertEquals(1, results.getTotalNumberOfResults());
		List<Solution> solutionList = results.getSolutions();
		assertEquals(1, solutionList.size());
		sInit.setDateCreated(solutionList.get(0).getDateCreated());
		assertEquals(sInit, solutionList.get(0));
		
		// delete
		boolean successDelete = questionService.deleteSolution
				(sId);
		assertTrue(successDelete);
		results = questionService.getSolutions(qId, 1, 0);
		assertEquals(0, results.getTotalNumberOfResults());
		successDelete = questionService.deleteQuestion(qId);
		assertTrue(successDelete);
		List<Integer> qList = new ArrayList<Integer>();
		qList.add(qId);
		List<Question> qListResults = questionService.getQuestionsById(qList);
		assertEquals(0, qListResults.size());
	}
	
	/**
	 * Tests the local storage of information our app will use.
	 * 
	 * @label White-box test
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws NetworkException
	 */
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
			assertTrue(file.delete());
		}
	}

	/**
	 * Tests the retrieval of userId from an email
	 * 
	 * @label White-box testing
	 * @throws NetworkException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * 
	 */
	public void testGetUserId() throws NetworkException {
		String userEmail = "dan.sanders@gmail.com";
		int expectedId = 3;
		int actualId = questionService.getUserId(userEmail);
		assertEquals(expectedId, actualId);
	}

	/**
	 * Tests the retrieval of userId from an email
	 * 
	 * @label White-box testing
	 * @throws NetworkException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * 
	 */
	public void testGetUserIdWithNull() throws NetworkException {
		try {
			questionService.getUserId(null);
			fail("Should have thrown IllegalArgumentException");
		}
		catch (IllegalArgumentException e) {
			// expected
		}
	}

	/**
	 * Tests the retrieval of questions by the given question ids
	 * 
	 * @label White-box test
	 * @throws JSONException
	 * @throws IOException
	 * @throws NetworkException
	 */
	public void testGetQuestionById() throws IOException, NetworkException {
		List<Integer> questionIds = new ArrayList<Integer>();
		questionIds.add(42);
		questionIds.add(43);
		questionIds.add(44);
		List<Question> results = questionService.getQuestionsById(questionIds);
		assertNotNull(results);
		assertEquals(42, results.get(0).getQuestionId());
		assertEquals(43, results.get(1).getQuestionId());
		assertEquals(44, results.get(2).getQuestionId());
	}
	
	/**
	 * Tests upvoting a question
	 * 
	 * @label Black-box test
	 * @throws NetworkException
	 * @throws IOException
	 */
	public void testUpVoteQuestion() throws NetworkException, IOException {
		int qId = setUpQuestion();
		boolean res = false;
		try {
			// upvote
			res = questionService.upvoteQuestion(qId);
		} finally {	
			// delete question
			questionService.deleteQuestion(qId);
		}
		
		// assert that the upvoting worked
		assertTrue(res);
	}
	
	/**
	 * Tests upvoting a question with an id that isn't in the database
	 * 
	 * @label Black-box test
	 * @throws NetworkException
	 * @throws IOException
	 */
	public void testUpVoteBadQuestion() throws NetworkException, IOException {
		setUpInfo();
		boolean res = questionService.upvoteQuestion(-1);
		assertFalse(res);
	}
	
	/**
	 * Tests downvoting a question
	 * 
	 * @label Black-box test
	 * @throws NetworkException
	 * @throws IOException
	 */
	public void testDownVoteQuestion() throws NetworkException, IOException {
		int qId = setUpQuestion();	
		boolean res = false;		
		try {
			// upvote
			res = questionService.downvoteQuestion(qId);
		} finally {
			// delete question
			questionService.deleteQuestion(qId);
		}
		
		// assert that the upvoting worked
		assertTrue(res);		
	}
	
	/**
	 * Tests downvoting a question with a questionId not in the database
	 * 
	 * @label Black-box test
	 * @throws NetworkException
	 * @throws IOException
	 */	
	public void testDownVoteBadQuestion() throws NetworkException,
			IOException {
		setUpInfo();		
		boolean res = questionService.downvoteQuestion(-1);
		assertFalse(res);
	}
	
	/**
	 * Tests upvoting a solution
	 * 
	 * @label Black-box test
	 * @throws NetworkException
	 * @throws IOException
	 */
	public void testUpVoteSolution() throws NetworkException, IOException {
		Solution sInit = setUpSolution();	
		boolean res = false;	
		try {
			// upvote
			res = questionService.upvoteSolution(sInit.getSolutionId());
		} finally {
			// delete
			questionService.deleteSolution(sInit.getSolutionId());
			questionService.deleteQuestion(sInit.getQuestionId());
		}
		
		// assert that the upvoting worked
		assertTrue(res);
	}
	
	/**
	 * Tests upvoting a solution with an id that isn't in the database
	 * 
	 * @label Black-box test
	 * @throws NetworkException
	 * @throws IOException
	 */
	public void testUpVoteBadSolution() throws NetworkException, IOException {
		setUpInfo();	
		boolean res = questionService.upvoteSolution(-1);
		assertFalse(res);
	}
	
	/**
	 * Tests downvoting a solution
	 * 
	 * @label Black-box test
	 * @throws NetworkException
	 * @throws IOException
	 */
	public void testDownVoteSolution() throws NetworkException, IOException {
		Solution sInit = setUpSolution();	
		boolean res = false;
		try {
			// upvote
			res = questionService.downvoteSolution
					(sInit.getSolutionId());
		} finally {	
			// delete
			questionService.deleteQuestion(sInit.getQuestionId());
			questionService.deleteSolution(sInit.getSolutionId());
		}
		// assert that the upvoting worked
		assertTrue(res);		
	}
	
	/**
	 * Tests downvoting a solution with a solutionId not in the database
	 * 
	 * @label Black-box test
	 * @throws NetworkException
	 * @throws IOException
	 */	
	public void testDownVoteBadSolution() throws NetworkException,
			IOException {
		setUpInfo();
		boolean res = questionService.downvoteSolution(-1);
		assertFalse(res);
	}
	
	/**
	 * STUB TEST!!!!
	 * 
	 * Test the operation of the favorite questions code, but doesn't actually
	 * connect to the database. Uses DatabaseStub instead.
	 * 
	 * @label White-box test
	 */
	public void testGetFavoriteQuestionsWithStub() {
		// populate database stub
		int totalSize = 10;
		List<Integer> toFavorite = new ArrayList<Integer>();
		toFavorite.add(3);
		toFavorite.add(5);
		Map<Integer, Question> qMap = new LinkedHashMap<Integer, Question>();
		for (int i = 0; i < totalSize; i++) {
			Question newQ = TestHelpers.createDummyQuestion(i);
			qMap.put(i, newQ);
		}
		DatabaseStub stub = 
				new DatabaseStub(qMap, new LinkedHashMap<Integer, Solution>());
		
		// initialize user info and favorite those questions
		UserInfo testInfo = TestHelpers.createTestUserInfo();
		testInfo.markFavoriteQuestion(3);
		testInfo.markFavoriteQuestion(5);
		
		List<Question> results = stub.getFavoriteQuestions(testInfo);
		assertEquals(2, results.size());
		for (int x = 0; x < results.size(); x++) {
			assertTrue(toFavorite.contains(results.get(x).getQuestionId()));
		}
	}
	
	/**
	 * Tests getting favorite questions
	 * 
	 * @label White-box test
	 * @throws NetworkException
	 * @throws IOException
	 */
	public void testGetFavoriteQuestions() throws NetworkException,
			IOException {
		int numQuestions = 3;
		// create a couple questions
		Map<Integer, Date> questionIds = new LinkedHashMap<Integer, Date>();
		for (int i = 0; i < numQuestions; i++) {
			Question qInit = TestHelpers.createDummyQuestion(42);
			int qId = questionService.postQuestion(qInit);
			questionIds.put(qId, new Date(System.currentTimeMillis()));
		}
		List<Integer> qIds = new ArrayList<Integer>(questionIds.keySet());
		List<Question> favorited;
		try {	
			// initialize user info and favorite those questions
			UserInfo testInfo = TestHelpers.createTestUserInfo();
			testInfo.setFavoriteQuestions(questionIds);
			questionService.setUserInfo(testInfo);
			
			// Get the favorited questions from DB, then delete the questions
			favorited = questionService.getFavoriteQuestions(numQuestions, 0).
					getQuestions();
			} finally {
			for (int y = 0; y < qIds.size(); y++) {
				questionService.deleteQuestion(qIds.get(y));
			}
		}
		
		// make sure the favorited questions we get back are correct
		assertEquals(numQuestions, favorited.size());
		for (int x = 0; x < favorited.size(); x++) {
			assertTrue(qIds.contains(favorited.get(x).getQuestionId()));
		}
	}
	
	private int setUpQuestion() throws NetworkException, IOException {
		setUpInfo();
		Question qInit = TestHelpers.createDummyQuestion(42);
		int qId = questionService.postQuestion(qInit);
		return qId;
	}
	
	private Solution setUpSolution() throws NetworkException, IOException {
		int qId = setUpQuestion();
		Solution sInit = TestHelpers.createDummySolution(qId);
		int sId = questionService.postSolution(sInit);
		sInit.setSolutionId(sId);
		sInit.setQuestionId(qId);
		return sInit;
	}
	
	private void setUpInfo() {
		questionService.setUserInfo(TestHelpers.createTestUserInfo());
	}
}
