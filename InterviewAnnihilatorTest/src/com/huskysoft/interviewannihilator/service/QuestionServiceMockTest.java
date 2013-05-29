/**
 * This is a class for testing QuestionService using EasyMock.
 * 
 * @author Kevin Loh, 5/28/2013
 */

package com.huskysoft.interviewannihilator.service;

import java.io.IOException;

import android.test.ActivityInstrumentationTestCase2;
import com.huskysoft.interviewannihilator.ui.MainActivity;
import com.huskysoft.interviewannihilator.util.TestHelpers;
import com.huskysoft.interviewannihilator.model.NetworkException;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.model.UserInfo;
import com.huskysoft.interviewannihilator.service.QuestionService;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

public class QuestionServiceMockTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private QuestionService questionService;
	private NetworkServiceInterface mockNetworkService;

	/**
	 * Construct new test instance
	 * 
	 * @param name
	 *            the test name
	 */
	public QuestionServiceMockTest() {
		super("com.huskysoft.interviewannihilator.ui", MainActivity.class);
	}

	/**
	 * Perform pre-test initialization
	 * 
	 * @throws Exception
	 * 
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mockNetworkService = createNiceMock(NetworkServiceInterface.class);
		questionService = QuestionService.getInstance();
		UserInfo userInfo = TestHelpers.createDummyUserInfo();
		questionService.setUserInfo(userInfo);
	}

	/**
	 * MOCK TEST!!!! This is our mock test, to ensure the correct methods
	 * of networkService are getting called when a question is posted
	 * 
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws NetworkException
	 */
	public void testPostQuestion() throws JsonGenerationException,
			JsonMappingException, IOException, NetworkException {
		// Create the question and its json string
		Question question = TestHelpers.createDummyQuestion(0);
		ObjectMapper mapper = new ObjectMapper();
		String questionStr = mapper.writeValueAsString(question);
		
		// Create and activate the mock networkService object
		expect(mockNetworkService.postQuestion(questionStr)).andReturn("0");
		replay(mockNetworkService);
		questionService.setNetworkService(mockNetworkService);
		
		int result = questionService.postQuestionMock(question);
		assertEquals(0, result);
		
		verify(mockNetworkService);
	}
}