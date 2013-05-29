/**
 * The class contains tests for the FetchQuestionsTask class
 * 
 * @author Cody Andrews, 5/19/2013
 */

package com.huskysoft.interviewannihilator.runtime;

import java.util.List;

import com.huskysoft.interviewannihilator.model.Category;
import com.huskysoft.interviewannihilator.model.Difficulty;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.util.UIConstants;

import junit.framework.TestCase;

public class FetchQuestionsTaskTest extends TestCase {	
	
	public FetchQuestionsTaskTest(String name){
		super(name);
	}
	
	/**
	 * Tests that questions are loaded correctly
	 * 
	 * @label Black-box testing
	 */
	public void testLoadQuestions(){
		FetchQuestionsTask task = new FetchQuestionsTask(null,
				null, null, UIConstants.DEFAULT_QUESTIONS_TO_LOAD, 0);
		List<Question> questionList = task.doInBackground();
		
		assertNotNull(questionList);
		
		// Might fail if there are not enough questions in the database
		assertEquals(UIConstants.DEFAULT_QUESTIONS_TO_LOAD,
				questionList.size());
	}

	/**
	 * Tests that questions have the correct difficulty
	 * 
	 * @label Black-box testing
	 * 
	 */
	public void testDifficulty(){
		//Test medium questions
		FetchQuestionsTask task =
				new FetchQuestionsTask(null,  
						Category.COMPSCI, Difficulty.MEDIUM, 1, 0);
		List<Question> questionList = task.doInBackground();
		
		assertNotNull(questionList);
		
		for (Question q : questionList){
			assertEquals(Difficulty.MEDIUM, q.getDifficulty());
		}
	}
}
