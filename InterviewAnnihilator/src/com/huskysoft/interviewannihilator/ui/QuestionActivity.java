package com.huskysoft.interviewannihilator.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.huskysoft.interviewannihilator.R;
import com.huskysoft.interviewannihilator.model.NetworkException;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.model.Solution;
import com.huskysoft.interviewannihilator.service.QuestionService;
import com.huskysoft.interviewannihilator.util.PaginatedSolutions;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class QuestionActivity extends Activity {
	
	private Question question;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);	
		
		// Get intent
		Intent intent = getIntent();
		question = (Question)intent.getSerializableExtra(MainActivity.EXTRA_MESSAGE);
		
		// Grab Linear Layout
		LinearLayout singleQuestionll = (LinearLayout)findViewById(R.id.linear_layout);
		
		// Create TextView that holds Question
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		llp.setMargins(40, 10, 40, 10); // llp.setMargins(left, top, right, bottom);
	   
	    llp.gravity = 1; // Horizontal Center
	    
		TextView textview = new TextView(this);
		textview.setTextSize(20);
		textview.setText(question.getText());
		textview.setLayoutParams(llp);
		
		singleQuestionll.addView(textview, 0);
		
		// Show the Up button in the action bar.
		// setupActionBar();
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
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
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
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
