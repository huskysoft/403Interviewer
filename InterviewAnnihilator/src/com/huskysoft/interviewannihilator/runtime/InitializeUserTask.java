
/**
 * Asynchronous thread designed to post solutions to the database.
 * On completion or failure, displays message.
 * 
 * @author Justin Robb, 05/22/2013
 */

package com.huskysoft.interviewannihilator.runtime;

import java.io.File;

import android.os.AsyncTask;
import android.util.Log;

import com.huskysoft.interviewannihilator.service.QuestionService;
import com.huskysoft.interviewannihilator.ui.MainActivity;

public class InitializeUserTask extends AsyncTask<Void, Void, Integer>{

	private MainActivity context;
	private Exception exception;
	private File baseDir;
	private String email;


	/**
	 * 
	 * @param context reference to MainActivity
	 */
	public InitializeUserTask(MainActivity context,File baseDir, String email) {
		this.context = context;
		this.baseDir = baseDir;
		this.email = email;
	}


	/**
	 * This is the main function of the AsyncTask thread. This post a solution.
	 * @return 
	 */
	@Override
	protected Integer doInBackground(Void... result) {
		QuestionService questionService = QuestionService.getInstance();
		try {
			questionService.initializeUserInfo(baseDir, email);
		} catch (Exception e){
			Log.e("FetchSolutionsTask", e.getMessage());
			exception = e;
			this.cancel(true);
		}
		return 0;
	}

	@Override
	protected void onCancelled() {
		//TODO: handle specific error cases
		if(exception != null){
			context.onInitializeError();
		}
	}
	
	/**
	 * This event fires when doInBackground() is complete
	 */
	@Override
	protected void onPostExecute(Integer result){
		context.userInfoSuccessFunction();
	}
}