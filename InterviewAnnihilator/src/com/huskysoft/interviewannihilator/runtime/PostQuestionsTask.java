
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

public class PostQuestionsTask extends AsyncTask<Void, Void, Integer>{

	private PostQuestionActivity context;
	private Question question;
	private Solution solution;
	private Exception exception;


	/**
	 * 
	 * @param context reference to MainActivity
	 */
	public PostQuestionsTask(PostQuestionActivity context, 
			Question question, Solution solution) {
		this.context = context;
		this.solution = solution;
		this.question = question;
	}


	/**
	 * This is the main function of the AsyncTask thread. This will populate
	 * questionList with Questions so that they may be displayed afterwards.
	 * @return 
	 */
	@Override
	protected Integer doInBackground(Void... result) {
		QuestionService questionService = QuestionService.getInstance();	
		try {
			int id = questionService.postQuestion(question);
			solution.setQuestionId(id);
			questionService.postSolution(solution);
		} catch (Exception e){
			Log.e("FetchSolutionsTask", "" + e.getMessage());
			exception = e;
			this.cancel(true);
		}
		return 0;
	}

	@Override
	protected void onCancelled(){
		//TODO: handle specific error cases
		if(exception != null){
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
		Toast.makeText(context, 
				R.string.successDialog_title_q, Toast.LENGTH_LONG).show();
		context.finish();
	}
}