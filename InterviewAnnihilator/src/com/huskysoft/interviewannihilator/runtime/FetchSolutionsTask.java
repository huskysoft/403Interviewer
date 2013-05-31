/**
 * Asynchronous thread designed to load solutions from the database.
 * On completion, populates QuestionActivity with TextViews containing
 * solutions.
 * 
 * @author Cody Andrews, 05/01/2013
 */

package com.huskysoft.interviewannihilator.runtime;

import java.util.List;
import android.os.AsyncTask;
import android.util.Log;

import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.model.Solution;
import com.huskysoft.interviewannihilator.service.QuestionService;
import com.huskysoft.interviewannihilator.ui.QuestionActivity;
import com.huskysoft.interviewannihilator.util.PaginatedSolutions;

public class FetchSolutionsTask extends AsyncTask<Void, Void, List<Solution>>{

	private QuestionActivity context;
	private Question question;
	private Exception exception;

	/**
	 * 
	 * @param context reference to SolutionActivity
	 * @param question question to find solutions for
	 */
	public FetchSolutionsTask(QuestionActivity context, Question question){
		this.context = context;
		this.question = question;
	}

	/**
	 * This is the main function of the AsyncTask thread. This will populate
	 * solutionList with Solutions so that they may be displayed afterwards.
	 */
	@Override
	protected List<Solution> doInBackground(Void... params) {
		QuestionService questionService = QuestionService.getInstance();
		List<Solution> solutionList = null;
		
		try {
			PaginatedSolutions paginatedSolutions =
					questionService.getSolutions(
					question.getQuestionId(), 10, 0);

			solutionList = paginatedSolutions.getSolutions();
		} catch (Exception e){
			Log.e("FetchSolutionsTask", "" + e.getMessage());
			exception = e;
			this.cancel(true);
		}

		return solutionList;
	}

	/** 
	 * This event fires when this AsyncTask is cancelled.
	 */
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
	protected void onPostExecute(List<Solution> solutionList){
		context.addSolutionList(solutionList);
	}
}