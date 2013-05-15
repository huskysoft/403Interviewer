package com.huskysoft.interviewannihilator.ui;

import java.util.List;

import com.huskysoft.interviewannihilator.R;
import com.huskysoft.interviewannihilator.service.*;
import com.huskysoft.interviewannihilator.util.Utility;
import com.huskysoft.interviewannihilator.model.*;
import com.huskysoft.interviewannihilator.runtime.*;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends SlidingActivity {
	
	/*
	 * Used to pass the String question to the child activity.
	 * Will pass a Question object.
	 */
	public final static String EXTRA_MESSAGE = "com.huskysoft.interviewannihilator.QUESTION";
		
	/** Layout element that holds the questions */
	private LinearLayout questionll;
	
	/**
	 * Method that populates the app when the MainActivity is created.
	 * Initializes the questions and questionll fields. Also calls
	 * the displayQuestions function.
	 */
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.activity_menu);
		getActionBar().setHomeButtonEnabled(true);
		
		// Get passed difficulty stored in Utility class
		String passedDifficulty = Utility.DIFFICULTY_MESSAGE;
		
		// Reset PassedDifficulty
		Utility.DIFFICULTY_MESSAGE = "";
		
		buildSlideMenu();
		
		if(passedDifficulty != ""){
			setSpinnerToSelectedValue(passedDifficulty);
		}
		
		Difficulty diff = getDifficultyByString(passedDifficulty);
		
		questionll = (LinearLayout) findViewById(R.id.linear_layout);
		new FetchQuestionsTask(this, diff).execute();
	}
	
	public void setSpinnerToSelectedValue(String value){
		Spinner spinner = (Spinner) findViewById(R.id.diff_spinner);
		ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter();
		
		spinner.setSelection(myAdap.getPosition(value));
	}
	
	public Difficulty getDifficultyByString(String difficulty){
		if(difficulty.equals("Easy")){
			return Difficulty.EASY;
		}else if(difficulty.equals("Medium")){
			return Difficulty.MEDIUM;
		}
		else if(difficulty.equals("Hard")){
			return Difficulty.HARD;
		}
		else{
			return null;
		}
	}
	
	public Difficulty getCurrentDifficultySetting(){
		Spinner spinner = (Spinner) findViewById(R.id.diff_spinner);
		String difficulty = spinner.getSelectedItem().toString();
		
		return getDifficultyByString(difficulty);
	}
	
	public void buildSlideMenu(){
		SlidingMenu menu = getSlidingMenu();
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = (int) ((double) metrics.widthPixels * 0.8);
		menu.setBehindOffset((int) (width * 0.25));
		
		Spinner spinner = (Spinner) findViewById(R.id.diff_spinner);
		ArrayAdapter<CharSequence> adapter = 
				ArrayAdapter.createFromResource(this,
		        R.array.difficulty, 
		        android.R.layout.simple_spinner_item);
		
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
	}
	
	/**
	 * Click handler for the slide-in menu difficulty selection.
	 * Will repopulate the list of questions with new questions
	 * that have the selected difficulty.
	 * 
	 * @param v Button View
	 */
	public void adjustDifficulty(View v){
		Difficulty diff = getCurrentDifficultySetting();
		
		toggle();
		
		// Clear current Questions
		questionll.removeAllViews();
		new FetchQuestionsTask(this, diff).execute();
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
		
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 
				LayoutParams.WRAP_CONTENT, 0.75f);
		llp.setMargins(40, 10, 40, 10);
		
		llp.gravity = 1;  // Horizontal Center
		
		if(questions.size() <= 0){
			TextView t = new TextView(this);
			
			t.setText("There doesn't seem to be any questions.");
			t.setTextSize(20);
			// special look?
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
				// to make it work on older versions use this instead of setBackground() 
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
		intent.putExtra(EXTRA_MESSAGE, (Question) view.getTag());
		startActivity(intent);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
}
