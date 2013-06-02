
/**
 * Asynchronous thread designed to post solutions to the database.
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
import com.huskysoft.interviewannihilator.model.Solution;
import com.huskysoft.interviewannihilator.service.QuestionService;
import com.huskysoft.interviewannihilator.ui.PostSolutionActivity;

public class PostSolutionsTask extends AsyncTask<Void, Void, Integer>{

	private PostSolutionActivity context;
	private Solution solution;
	private Exception exception;


	/**
	 * 
	 * @param context reference to MainActivity
	 */
	public PostSolutionsTask(PostSolutionActivity context, Solution solution) {
		this.context = context;
		this.solution = solution;
	}


	/**
	 * This is the main function of the AsyncTask thread. This post a solution.
	 * @return 
	 */
	@Override
	protected Integer doInBackground(Void... result) {
		QuestionService questionService = QuestionService.getInstance();
		int retval = -1;
		try {
			retval = questionService.postSolution(solution);
		} catch (Exception e){
			Log.e("FetchSolutionsTask", "" + e.getMessage());
			exception = e;
			this.cancel(true);
		}
		return retval;
	}

	@Override
	protected void onCancelled(){
		//TODO: handle specific error cases
		if(exception != null && context != null){
			if (exception.getClass().equals(NetworkException.class)){
				context.switchFromLoad();
				context.displayMessage(-1);
			} else{
				context.switchFromLoad();
				context.displayMessage(-2);
			}
		}
	}

	/**
	 * This event fires when doInBackground() is complete
	 */
	@Override
	protected void onPostExecute(Integer r){
		if (context != null){
			Toast.makeText(context, 
					R.string.successDialog_title, 
					Toast.LENGTH_LONG).show();
			context.finish();
		}
	}
}