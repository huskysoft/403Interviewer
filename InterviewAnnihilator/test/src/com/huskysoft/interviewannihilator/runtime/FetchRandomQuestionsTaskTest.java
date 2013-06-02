/**
 * Tests for the FetchRandomQuestionsTask.
 */
package com.huskysoft.interviewannihilator.runtime;

import java.util.List;

import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.util.UIConstants;

import junit.framework.TestCase;

public class FetchRandomQuestionsTaskTest extends TestCase {

	public FetchRandomQuestionsTaskTest(String name) {
		super(name);
	}
	
	/**
	 * Tests that task can be constructed
	 * 
	 * @label Black-box testing
	 */
	public void testCstr(){
		FetchRandomQuestionsTask task = 
				new FetchRandomQuestionsTask(null);
		assertNotNull(task);
	}
	
	/**
	 * Tests that the questions are being loaded
	 * correctly. 
	 * 
	 * @ black-box
	 */
	public void testFetchQuestions(){
		
		FetchRandomQuestionsTask task = new FetchRandomQuestionsTask(null);
		List<Question> questionList = task.doInBackground();
		
		assertNotNull(questionList);
		
		// Might fail if there are not enough questions in the database
		assertEquals(UIConstants.DEFAULT_QUESTIONS_TO_LOAD,
				questionList.size());
		
	}
}

