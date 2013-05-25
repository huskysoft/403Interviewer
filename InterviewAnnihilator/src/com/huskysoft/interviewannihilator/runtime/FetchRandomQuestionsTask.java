/**
 * Asynchronous thread designed to load questions from the database.
 * On completion, populates RandomQuestionCollection
 * 
 * @author Phillip Leland
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
import com.huskysoft.interviewannihilator.ui.RandomQuestionCollection;
import com.huskysoft.interviewannihilator.util.PaginatedQuestions;

public class FetchRandomQuestionsTask 
	extends AsyncTask<Void, Void, List<Question>>{

	private Exception exception;
	
	public FetchRandomQuestionsTask() {
		
	}


	/**
	 * This is the main function of the AsyncTask thread.
	 */
	@Override
	protected List<Question> doInBackground(Void... params) {
		QuestionService questionService = QuestionService.getInstance();
		List<Question> questionList = null;
		
		try {
			
			PaginatedQuestions currentQuestions =
					questionService.getQuestions(null,
							null, 10, 0, true);
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
		
	}

	/**
	 * This event fires when doInBackground() is complete, and it will populate
	 * the MainActivity question area.
	 */
	@Override
	protected void onPostExecute(List<Question> questionList){
		RandomQuestionCollection.getInstance().appendList(questionList);
	}
}