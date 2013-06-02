/**
 * The class contains tests for the PostQuestionsTask class
 * 
 * @author Justin Robb, 6/1/2013
 */

package com.huskysoft.interviewannihilator.runtime;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import android.os.Environment;
import android.util.Log;

import com.huskysoft.interviewannihilator.model.Category;
import com.huskysoft.interviewannihilator.model.Difficulty;
import com.huskysoft.interviewannihilator.model.NetworkException;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.model.Solution;
import com.huskysoft.interviewannihilator.service.QuestionService;

import junit.framework.TestCase;

public class PostQuestionsTaskTest extends TestCase {	
	
	public PostQuestionsTaskTest(String name){
		super(name);
	}
	
	/**
	 * Tests that task can be constructed
	 * 
	 * @label Black-box testing
	 */
	public void testPostQuestionCstr(){
		Question question = new Question("Test", 
				"Test_Posting", Category.MATH, Difficulty.HARD);
		Solution solution = new Solution();
		solution.setText("TEST_SOLUTION");
		PostQuestionsTask task = 
				new PostQuestionsTask(null, question, solution);
		assertNotNull(task);
	}
	
	/**
	 * Tests that questions are posted correctly
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 * 
	 * @label Black-box testing
	 */
	public void testPostQuestion() throws JsonGenerationException, 
			JsonMappingException, IOException{
		Question question = new Question("Test", 
				"Test_Posting", Category.MATH, Difficulty.HARD);
		Solution solution = new Solution();
		solution.setText("TEST_SOLUTION");
		QuestionService qs = QuestionService.getInstance();
		File dir = Environment.getExternalStorageDirectory();
		PostQuestionsTask task = 
				new PostQuestionsTask(null, question, solution);
		// assert that we can delete it (it exists in the database)
		try {
			qs.initializeUserInfo(dir, "test@TESTING.com");
			int questionID = task.doInBackground();
			assertNotSame(-1, questionID);
			assertTrue(qs.deleteQuestion(questionID));
		} catch (NetworkException e) {
			Log.e("PostQuestionTaskTest", e.getMessage());
			fail();
		}
	}

	/**
	 * Tests that good questions with bad solutions are not posted
	 * 
	 * @label White-box testing
	 * 
	 */
	public void testBadSolutionWithQuestion(){
		Question question = new Question("Test", 
				"Test_Posting", Category.MATH, Difficulty.HARD);
		PostQuestionsTask task = 
				new PostQuestionsTask(null, question, null);
		int questionID = task.doInBackground();
		assertEquals(-1, questionID);
	}
	
	/**
	 * Tests that bad questions are not posted
	 * 
	 * @label White-box testing
	 * 
	 */
	public void testBadQuestionWithSolution(){
		Solution solution = new Solution();
		solution.setText("TEST_SOLUTION");
		PostQuestionsTask task = 
				new PostQuestionsTask(null, null, solution);
		int questionID = task.doInBackground();
		assertEquals(-1, questionID);
	}
}
