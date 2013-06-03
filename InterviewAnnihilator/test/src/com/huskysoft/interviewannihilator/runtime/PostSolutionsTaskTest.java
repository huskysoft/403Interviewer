/**
 * The class contains tests for the PostSolutionsTask class
 * 
 * @author Justin Robb, 6/1/2013
 */

package com.huskysoft.interviewannihilator.runtime;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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

public class PostSolutionsTaskTest extends TestCase {	
	
	public PostSolutionsTaskTest(String name){
		super(name);
	}
	
	/**
	 * Tests that task can be constructed
	 * 
	 * @label Black-box testing
	 */
	public void testPostSolutionCstr(){
		Solution solution = new Solution();
		solution.setText("TEST_SOLUTION");
		PostSolutionsTask task = 
				new PostSolutionsTask(null, solution);
		assertNotNull(task);
	}
	
	/**
	 * Tests that solutions are posted correctly
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 * 
	 * @label Black-box testing
	 */
	public void testPostSolution() throws JsonGenerationException,
			JsonMappingException, IOException{
		Solution solution = new Solution();
		solution.setText("TEST_SOLUTION");
		QuestionService qs = QuestionService.getInstance();
		// get a question	
		List<Category> emptyCat = new LinkedList<Category>();
		emptyCat.add(Category.COMPSCI);
		FetchQuestionsTask fetchTask = new FetchQuestionsTask(null,
						emptyCat, Difficulty.EASY, 1, 0);
		Question question = fetchTask.doInBackground().get(0);
		solution.setQuestionId(question.getQuestionId());
		File dir = Environment.getExternalStorageDirectory();
		PostSolutionsTask task = 
				new PostSolutionsTask(null, solution);
		// assert that we can delete it (it exists in the database)
		try {
			qs.initializeUserInfo(dir, "test@TESTING.com");
			int solutionID = task.doInBackground();
			assertNotSame(-1, solutionID);
			assertTrue(qs.deleteSolution(solutionID));
		} catch (NetworkException e) {
			Log.e("PostQuestionTaskTest", e.getMessage());
			fail();
		}
	}
	
	/**
	 * Tests that bad solutions aren't posted
	 * 
	 * @label White-box testing
	 * 
	 */
	public void testBadSolution(){
		PostSolutionsTask task = 
				new PostSolutionsTask(null, null);
		int solutionID = task.doInBackground();
		assertEquals(-1, solutionID);
	}
}
