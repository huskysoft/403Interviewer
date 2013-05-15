package com.huskysoft.interviewannihilator.ui;

import com.huskysoft.interviewannihilator.R;
import com.huskysoft.interviewannihilator.model.Difficulty;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.runtime.FetchQuestionsTask;
import com.huskysoft.interviewannihilator.util.Utility;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.content.Intent;

/**
 * Activity for viewing a question before reading solutions
 */
public class QuestionActivity extends SlidingActivity {
	
	private Question question;
	private QuestionActivity context;
	
	/*
	 * Used to pass a selected difficulty back to the MainActivity
	 * through using the slide-in menu
	 */
	public final static String DIFFICULTY_MESSAGE = "";
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);	
		setBehindContentView(R.layout.activity_menu);
		getActionBar().setHomeButtonEnabled(true);
		
		buildSlideMenu();
		
		// Get intent
		Intent intent = getIntent();
		question = (Question) intent.getSerializableExtra(MainActivity.EXTRA_MESSAGE);
		
		// Grab Linear Layout
		LinearLayout singleQuestionll = (LinearLayout) findViewById(R.id.linear_layout);
		
		// Create TextView that holds Question
		LinearLayout.LayoutParams llp =  new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);
		llp.setMargins(40, 10, 40, 10);

		llp.gravity = 1; // Horizontal Center

		TextView textview = new TextView(this);
		textview.setBackgroundDrawable(getResources().getDrawable( R.drawable.listitem));
		textview.setTextSize(20);
		
		
		textview.setText(question.getText());
		textview.setLayoutParams(llp);
		
		singleQuestionll.addView(textview, 0);
		
		context = this;
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
		
		// Handle onClick of Slide-Menu button
		Button button = (Button) findViewById(R.id.slide_menu_button);
		button.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	Spinner spinner = (Spinner) findViewById(R.id.diff_spinner);
				String difficulty = spinner.getSelectedItem().toString();
				
				toggle();
				
				Intent intent = new Intent(context, MainActivity.class);
				Utility.DIFFICULTY_MESSAGE = difficulty;
				startActivity(intent);
				
		    }
		});
	}
	
	/**
	 * Button handler for the "Solutions" button.
	 * Starts the solutions activity.
	 * 
	 * @param v 
	 */
	public void showSolutions(View v){
		Intent intent = new Intent(this, SolutionActivity.class);
		intent.putExtra(MainActivity.EXTRA_MESSAGE, question);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
