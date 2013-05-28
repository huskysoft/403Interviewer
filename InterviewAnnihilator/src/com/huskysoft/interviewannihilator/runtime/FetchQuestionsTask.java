/**
 * Asynchronous thread designed to load questions from the database.
 * On completion, populates MainActivity with TextViews containing questions.
 * 
 * @author Cody Andrews, 05/01/2013
 */

package com.huskysoft.interviewannihilator.runtime;

import java.util.LinkedList;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;


import com.huskysoft.interviewannihilator.model.Category;
import com.huskysoft.interviewannihilator.model.Difficulty;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.service.QuestionService;
import com.huskysoft.interviewannihilator.ui.MainActivity;
import com.huskysoft.interviewannihilator.util.PaginatedQuestions;

public class FetchQuestionsTask extends AsyncTask<Void, Void, List<Question>>{

	private MainActivity context;
	private Difficulty diff;
	private Category cat;
	private Exception exception;
	private int numQuestions;
	private int questionOffset;

	/**
	 * 
	 * @param context reference to MainActivity
	 */
	public FetchQuestionsTask(MainActivity context, 
			Category cat,
			Difficulty diff, 
			int numQuestions,
			int questionOffset) {
		this.context = context;
		this.diff = diff;
		this.cat = cat;
		this.numQuestions = numQuestions;
		this.questionOffset = questionOffset;
	}


	/**
	 * This is the main function of the AsyncTask thread. This will populate
	 * questionList with Questions so that they may be displayed afterwards.
	 */
	@Override
	protected List<Question> doInBackground(Void... params) {
		QuestionService questionService = QuestionService.getInstance();
		List<Question> questionList = null;
		
		try {
			// Create a List of the selected Category
			List<Category> category = null;
			if(cat != null){
				category = new LinkedList<Category>();
				category.add(cat);
			}
			
			
			PaginatedQuestions currentQuestions =
					questionService.getQuestions(category,
							diff, numQuestions, questionOffset, false);
			questionList = currentQuestions.getQuestions();
		} catch (Exception e){
			Log.e("FetchSolutionsTask", e.getMessage());
			exception = e;
			this.cancel(true);
		}


		return questionList;
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
	protected void onPostExecute(List<Question> questionList){
		context.appendQuestionsToView(questionList);
		context.hideLoadingView1();
		context.hideLoadingView2();
		context.showMainView();
	}
}