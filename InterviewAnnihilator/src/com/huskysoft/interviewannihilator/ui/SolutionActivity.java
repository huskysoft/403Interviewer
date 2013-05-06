package com.huskysoft.interviewannihilator.ui;

import java.util.List;

import com.huskysoft.interviewannihilator.R;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.model.Solution;
import com.huskysoft.interviewannihilator.runtime.FetchSolutionsTask;

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

public class SolutionActivity extends Activity {
	private Question question;
	private LinearLayout solutionll;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_solution);
		
		Intent intent = getIntent();
		question = (Question) intent.getSerializableExtra(MainActivity.EXTRA_MESSAGE);
		
		solutionll = (LinearLayout) findViewById(R.id.linear_layout);
		new FetchSolutionsTask(this, question).execute();
	}
	
	/**
	 * Displays a formatted list of solutions
	 * @param solutions
	 */
	public void displaySolutions(List<Solution> solutions){
		if(solutions == null){
			return;
		}
		
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);
		llp.setMargins(40, 10, 40, 10);

		llp.gravity = 1; // Horizontal Center
		
		if(solutions.size() <= 0){
			TextView t = new TextView(this);
			
			t.setText("There doesn't seem to be any solutions");
			t.setTextSize(20);
			t.setLayoutParams(llp);
			solutionll.addView(t);
		}
		for(int i = 0; i < solutions.size(); i++){
			Solution solution = solutions.get(i);
			if(solution != null && solution.getText() != null){
				String solutionText = solution.getText();
				
				TextView t = new TextView(this);
				
				t.setText(solutionText);
				t.setTextSize(20);
				t.setBackgroundDrawable(getResources().getDrawable( R.drawable.listitem));
				t.setLayoutParams(llp);
				
				t.setId(solution.getId());
				t.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// do nothing at the moment
					}
				});
		
				solutionll.addView(t);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.solution, menu);
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
	
	/** Called when the user clicks the post solution button */
	public void postSolution(View view) {
		Intent intent = new Intent(this, PostSolutionActivity.class);
		startActivity(intent);
	}

}
