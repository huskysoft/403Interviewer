/**
 * This is a class for testing QuestionService using EasyMock.
 * 
 * @author Kevin Loh, 5/28/2013
 */

package com.huskysoft.interviewannihilator.service;

import android.test.ActivityInstrumentationTestCase2;
import com.huskysoft.interviewannihilator.ui.MainActivity;
import com.huskysoft.interviewannihilator.service.QuestionService;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

public class QuestionServiceMockTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	private QuestionService questionService;
	private NetworkServiceInterface mock;

	/**
	 * Construct new test instance
	 *
	 * @param name the test name
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
		questionService = QuestionService.getInstance();
		mock = createNiceMock(NetworkServiceInterface.class);
	}
	
	public void test() {
		assertTrue(true);
	}
}