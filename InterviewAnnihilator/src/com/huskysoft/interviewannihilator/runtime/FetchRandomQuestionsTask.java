/**
 * Asynchronous thread designed to load questions from the database.
 * On completion, populates RandomQuestionCollection
 * 
 * @author Phillip Leland
 */

package com.huskysoft.interviewannihilator.runtime;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;


import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.service.QuestionService;
import com.huskysoft.interviewannihilator.ui.MainActivity;
import com.huskysoft.interviewannihilator.ui.QuestionActivity;
import com.huskysoft.interviewannihilator.model.RandomQuestionCollection;
import com.huskysoft.interviewannihilator.util.PaginatedQuestions;
import com.huskysoft.interviewannihilator.util.UIConstants;

public class FetchRandomQuestionsTask 
	extends AsyncTask<Void, Void, List<Question>>{

	private Exception exception;
	private Activity context;
	
	public FetchRandomQuestionsTask(Activity context){
		this.context = context;
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
					null, UIConstants.DEFAULT_QUESTIONS_TO_LOAD,
					0, true);
			questionList = currentQuestions.getQuestions();
		} catch (Exception e){
			Log.e("FetchRandomQuestionsTask", e.getMessage());
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
	 * the QuestionActivity area.
	 */
	@Override
	protected void onPostExecute(List<Question> questionList){
		Question rand = questionList.remove(0);
		RandomQuestionCollection.getInstance().appendList(questionList);
				
		Intent intent = new Intent(context, QuestionActivity.class);
		intent.putExtra(MainActivity.EXTRA_MESSAGE, rand);
		context.startActivity(intent);
	}
}