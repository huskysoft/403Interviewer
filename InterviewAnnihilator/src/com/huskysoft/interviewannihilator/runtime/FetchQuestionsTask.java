/**
 * Asynchronous thread designed to load questions from the database.
 * On completion, populates MainActivity with TextViews containing questions.
 * 
 * @author Cody Andrews, 05/01/2013
 */

package com.huskysoft.interviewannihilator.runtime;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;


import com.huskysoft.interviewannihilator.model.Difficulty;
import com.huskysoft.interviewannihilator.model.NetworkException;

import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.service.QuestionService;
import com.huskysoft.interviewannihilator.ui.MainActivity;
import com.huskysoft.interviewannihilator.util.PaginatedQuestions;

/**
 * 
 * This AsyncTask is used to populate the MainActivity list of questions.
 * To use it, call new FetchQuestionsTask(this).execute();
 * 
 * @author Cody Andrews
 *
 */
public class FetchQuestionsTask extends AsyncTask<Void, Void, Void>{
	
	private QuestionService questionService;
	private MainActivity context;
	private List<Question> questionList;
	private Difficulty diff;
	private Exception exception;

	
	/**
	 * 
	 * @param context reference to MainActivity
	 */
	public FetchQuestionsTask(Activity context, Difficulty diff){
		this.context = (MainActivity) context;
		this.diff = diff;
	}
	
	
	/**
	 * This is the main function of the AsyncTask thread. This will populate
	 * questionList with Questions so that they may be displayed afterwards.
	 */
	@Override
	protected Void doInBackground(Void... params) {
		questionService = QuestionService.getInstance();
		
		try {
			PaginatedQuestions currentQuestions =
					questionService.getQuestions(null, diff, 20, 0, false);
			questionList = currentQuestions.getQuestions();
		} catch (Exception e){
			Log.e("FetchSolutionsTask", e.getMessage());
			exception = e;
			this.cancel(true);
		}
	
		
		return null;
	}
	
	@Override
	protected void onCancelled(){
		//TODO: handle specific error cases
		if(exception != null){
			context.onNetworkError();
		}
	}
	
	/**
	 * This event fires when doInBackground() is complete, and it will populate
	 * the MainActivity question area.
	 */
	@Override
	protected void onPostExecute(Void result){
		context.displayQuestions(questionList);
	}
}