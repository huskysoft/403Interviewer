/**
 * The class contains tests for the UserInfo class
 * 
 * @author Bennett Ng, 5/14/2013
 */

package com.huskysoft.interviewannihilator.model;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.huskysoft.interviewannihilator.util.TestHelpers;

import junit.framework.TestCase;

public class UserInfoTest extends TestCase {

	private ObjectMapper mapper;
	private UserInfo userInfo;

	/**
	 * Construct new test instance
	 *
	 * @param name the test name
	 */
	public UserInfoTest(String name) {
		super(name);
		mapper = new ObjectMapper();
	}
	
	public void setUp() {
		userInfo = TestHelpers.createDummyUserInfo();
	}
	
	/**
	 * This tests the construction and usage of userInfo, its translation to
	 * json to send over the network, and back
	 * 
	 * @label white-box test
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void testUserInfoRoundTrip() throws JsonGenerationException, 
			JsonMappingException, IOException {		
		String json = mapper.writeValueAsString(userInfo);
		UserInfo clone = mapper.readValue(json, UserInfo.class);
		assertEquals(userInfo, clone);
	}
	
	/**
	 * Test the clear() method of UserInfo.
	 * 
	 * @label white-box test
	 */
	public void testClear() {
		// make sure userInfo is populated
		assertNotNull(userInfo.getUserEmail());
		assertNotNull(userInfo.getUserId());
		assertFalse(userInfo.getFavoriteQuestions().isEmpty());
		assertFalse(userInfo.getViewedQuestions().isEmpty());
		assertFalse(userInfo.getVotedQuestions().isEmpty());
		assertFalse(userInfo.getVotedSolutions().isEmpty());
		
		// clear and verify
		userInfo.clear();
		assertNotNull(userInfo.getUserEmail());
		assertNotNull(userInfo.getUserId());
		assertTrue(userInfo.getFavoriteQuestions().isEmpty());
		assertTrue(userInfo.getViewedQuestions().isEmpty());
		assertTrue(userInfo.getVotedQuestions().isEmpty());
		assertTrue(userInfo.getVotedSolutions().isEmpty());
	}
	
	/**
	 * Test creation and deletion of a favorite Question
	 * 
	 * @label white-box test
	 */
	public void testFavoriteQuestionRoundTrip() {
		int questionId = 1234;
		
		// clear existing favorites
		userInfo.getFavoriteQuestions().clear();
		assertNull(userInfo.whenFavoriteQuestion(questionId));
		
		// mark favorite
		userInfo.markFavoriteQuestion(questionId);
		assertNotNull(userInfo.whenFavoriteQuestion(questionId));
		assertNotNull(userInfo.getFavoriteQuestions().get(questionId));
		
		// clear favorite
		assertTrue(userInfo.clearFavoriteQuestion(questionId));
		assertNull(userInfo.whenFavoriteQuestion(questionId));
		assertNull(userInfo.getFavoriteQuestions().get(questionId));
		
		// verify clear
		assertFalse(userInfo.clearFavoriteQuestion(questionId));
	}
	
	/**
	 * Test the hashCode and equals methods of UserInfo
	 * 
	 * @label white-box test
	 */
	public void testHashCodeEquals() {
		UserInfo clone = new UserInfo();
		assertFalse(userInfo.hashCode() == clone.hashCode());
		
		clone.setUserEmail(userInfo.getUserEmail());
		clone.setUserId(userInfo.getUserId());
		clone.setFavoriteQuestions(userInfo.getFavoriteQuestions());
		clone.setViewedQuestions(userInfo.getViewedQuestions());
		clone.setVotedQuestions(userInfo.getVotedQuestions());
		clone.setVotedSolutions(userInfo.getVotedSolutions());
		assertTrue(userInfo.hashCode() == clone.hashCode());
		assertEquals(userInfo, clone);
	}
	
	/**
	 * Test the upvote, downvote, and novote Question methods of UserInfo
	 * 
	 * @label white-box test
	 */
	public void testVoteQuestion() {
		int questionId = 1234;
		userInfo.novoteQuestion(questionId);
		
		assertNull(userInfo.getQuestionVote(questionId));
		userInfo.upvoteQuestion(questionId);
		assertTrue(userInfo.getQuestionVote(questionId));
		userInfo.downvoteQuestion(questionId);
		assertFalse(userInfo.getQuestionVote(questionId));
		userInfo.novoteQuestion(questionId);
		assertNull(userInfo.getQuestionVote(questionId));		
	}
	
	
	/**
	 * Test the upvote, downvote, and novote Solution methods of UserInfo
	 * 
	 * @label white-box test
	 */
	public void testVoteSolution() {
		int solutionId = 1234;
		userInfo.novoteSolution(solutionId);
		
		assertNull(userInfo.getSolutionVote(solutionId));
		userInfo.upvoteSolution(solutionId);
		assertTrue(userInfo.getSolutionVote(solutionId));
		userInfo.downvoteSolution(solutionId);
		assertFalse(userInfo.getSolutionVote(solutionId));
		userInfo.novoteSolution(solutionId);
		assertNull(userInfo.getSolutionVote(solutionId));		
	}
}
