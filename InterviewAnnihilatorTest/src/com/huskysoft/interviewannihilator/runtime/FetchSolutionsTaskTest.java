/**
 * The class contains tests for the FetchSolutionsTask class
 * 
 * @author Cody Andrews, 5/19/2013
 */

package com.huskysoft.interviewannihilator.runtime;

import java.util.List;

import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.model.Solution;
import com.huskysoft.interviewannihilator.service.QuestionService;
import com.huskysoft.interviewannihilator.util.PaginatedQuestions;
import com.huskysoft.interviewannihilator.util.TestHelpers;
import com.huskysoft.interviewannihilator.util.UIConstants;

import junit.framework.TestCase;

public class FetchSolutionsTaskTest extends TestCase {	
	
	/**
	 * The first question found in the database, used to find solutions
	 */
	Question question;
	
	public FetchSolutionsTaskTest(String name){
		super(name);
	}
	
	public void setUp() throws Exception{
		QuestionService questionService = QuestionService.getInstance();
		
		PaginatedQuestions currentQuestions =
				questionService.getQuestions(null, null,
				UIConstants.DEFAULT_QUESTIONS_TO_LOAD, 0, false);
		List<Question> questionList = currentQuestions.getQuestions();
		
		question = questionList.get(0);
	}
	
	/**
	 * Tests that solution are loaded correctly
	 * 
	 * @label Black-box testing
	 */
	public void testLoadSolutions(){
		FetchSolutionsTask task = new FetchSolutionsTask(null, question);
		List<Solution> solutionList = task.doInBackground();
		
		assertNotNull(solutionList);
	}
}
