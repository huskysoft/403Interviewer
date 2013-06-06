
/**
 * Asynchronous thread designed to post questions to the database.
 * On completion or failure, displays message.
 * 
 * @author Justin Robb, 05/22/2013
 */

package com.huskysoft.interviewannihilator.runtime;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.huskysoft.interviewannihilator.R;
import com.huskysoft.interviewannihilator.model.NetworkException;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.model.Solution;
import com.huskysoft.interviewannihilator.service.QuestionService;
import com.huskysoft.interviewannihilator.ui.PostQuestionActivity;
import com.huskysoft.interviewannihilator.util.Utility;

public class PostQuestionsTask extends AsyncTask<Void, Void, Integer>{

	private PostQuestionActivity context;
	private Question question;
	private Solution solution;
	private Exception exception;


	/**
	 * Create a new PostQuestionTask.
	 * 
	 * @param context reference to the parent PostQuestionActivity
	 */
	public PostQuestionsTask(PostQuestionActivity context, 
			Question question, Solution solution) {
		Utility.ensureNotNull(solution, "Solution");
		Utility.ensureNotNull(question, "Question");
		this.context = context;
		this.solution = solution;
		this.question = question;
	}


	/**
	 * This is the main function of the AsyncTask thread. This will populate
	 * create the new Question and Solution
	 * 
	 * @return the ID of the newly-created Question, or -1 on failure
	 */
	@Override
	protected Integer doInBackground(Void... result) {
		QuestionService questionService = QuestionService.getInstance();
		int questionId;
		
		// attempt to post the Question
		try {
			
			questionId = questionService.postQuestion(question);
		} catch (Exception e) {
			Log.e("FetchSolutionsTask failed to post Question: ", 
					"" + e.getMessage());
			exception = e;
			this.cancel(true);
			return -1;
		}
		
		// attempt to post the Solution
		try {
			solution.setQuestionId(questionId);
			questionService.postSolution(solution);
		} catch (Exception e) {
			// post failed; roll back the Question
			try {
				questionService.deleteQuestion(questionId);
			} catch (NetworkException netException) {
				Log.e("FetchSolutionsTask failed to roll back Question: ", 
						"" + netException.getMessage());
			}
			
			// log the posting error and return failure ID
			Log.e("FetchSolutionsTask failed to post Solution: ", 
					"" + e.getMessage());
			exception = e;
			this.cancel(true);
			return -1;
		}
		return questionId;
	}

	@Override
	protected void onCancelled(){
		//TODO: handle specific error cases
		if(exception != null && context != null){
			if (exception.getClass().equals(NetworkException.class)){
				context.switchFromLoad();
				context.displayMessage(-1, "");
			} else{
				context.switchFromLoad();
				context.displayMessage(-2, "");
			}
		}
	}

	/**
	 * This event fires when doInBackground() is complete
	 */
	@Override
	protected void onPostExecute(Integer result){
		if (context != null){
			Toast.makeText(context, 
					R.string.successDialog_title_q, 
					Toast.LENGTH_LONG).show();
			context.finish();
		}
	}
}