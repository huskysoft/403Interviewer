package com.huskysoft.interviewannihilator.ui;

import java.util.List;

import com.huskysoft.interviewannihilator.R;
import com.huskysoft.interviewannihilator.service.*;
import com.huskysoft.interviewannihilator.model.*;
import com.huskysoft.interviewannihilator.runtime.*;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	/*
	 * Used to pass the String question to the child activity.
	 * Will pass a Question object.
	 */
	public final static String EXTRA_MESSAGE = "com.huskysoft.interviewannihilator.QUESTION";
		
	/** Layout element that holds the questions */
	private LinearLayout questionll;
	
	/** List of question elements */
	private List<String> questions;
	
	/** Service that interacts with database */
	private QuestionService databaseService;
	
	/**
	 * Method that populates the app when the MainActivity is created.
	 * Initializes the questions and questionll fields. Also calls
	 * the displayQuestions function.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		questionll = (LinearLayout) findViewById(R.id.linear_layout);		
		new FetchQuestionsTask(this).execute();
	}
	
	/**
	 * Displays a formatted list of questions
	 * @param questions
	 */
	@SuppressLint("NewApi")
	public void displayQuestions(List<Question> questions) {
		if(questions == null){
			return;
		}
		
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 0.75f);
		llp.setMargins(40, 10, 40, 10); // llp.setMargins(left, top, right, bottom);
		
		llp.gravity = 1;  // Horizontal Center
		
		if(questions.size() <= 0){
			TextView t = new TextView(this);
			
			t.setText("There doesn't seem to be any questions.");
			t.setTextSize(20);
			//special look?
			t.setLayoutParams(llp);
			questionll.addView(t);
		}
		for(int i = 0; i < questions.size(); i++){
			Question question = questions.get(i);
			if(question != null && question.getText() != null){
				
				String questionText = question.getTitle();
				
				TextView t = new TextView(this);
				
				t.setTag(question);
				t.setText(questionText);
				t.setTextSize(20);			
				//to make it work on older versions use this instead of setBackground() 
				t.setBackgroundDrawable(getResources().getDrawable( R.drawable.listitem));
				t.setLayoutParams(llp);
				
				t.setId(question.getQuestionId());
				t.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						openQuestion(v);
					}
				});
		
				questionll.addView(t);
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Function used as the onClickHandler of the Question tiles
	 * on the main menu of the application.
	 * 
	 * 
	 * @param view The TextView that holds the selected question.
	 */
	public void openQuestion(View view){
		Intent intent = new Intent(this, QuestionActivity.class);
		TextView tv = (TextView) view;
		intent.putExtra(EXTRA_MESSAGE, (Question) view.getTag());
		startActivity(intent);
	}
	
}
