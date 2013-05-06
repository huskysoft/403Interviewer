package com.huskysoft.interviewannihilator.ui;

import com.huskysoft.interviewannihilator.R;
import com.huskysoft.interviewannihilator.model.Question;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.content.Intent;

/**
 * Activity for viewing a question before reading solutions
 */
public class QuestionActivity extends Activity {
	
	private Question question;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);	
		
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
