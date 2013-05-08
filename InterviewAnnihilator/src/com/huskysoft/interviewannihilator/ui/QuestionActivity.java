/**
 * 
 * After a question is clicked on MainActivity, this activity is brought up.
 * It display the question clicked, and a hidden list of solutions that pop
 * up when a "Solutions" button is clicked.
 * 
 * @author Cody Andrews, Phillip Leland
 * 
 */

package com.huskysoft.interviewannihilator.ui;

import java.util.ArrayList;
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

public class QuestionActivity extends Activity {
	
	private Question question;
	private LinearLayout linearLayout;
	private List<TextView> solutionTextViews;
	private boolean solutionsLoaded;
	private boolean showSolutionsPressed;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);	
		
		// Get intent
		Intent intent = getIntent();
		question = (Question) intent.getSerializableExtra(MainActivity.EXTRA_MESSAGE);
		
		// Grab Linear Layout
		linearLayout = (LinearLayout) findViewById(R.id.linear_layout);
		
		// Create TextView that holds Question
		LinearLayout.LayoutParams llp =  new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);
		llp.setMargins(40, 10, 40, 10);
		llp.gravity = 1; // Horizontal Center

		TextView textview = new TextView(this);
		textview.setBackgroundDrawable(getResources().getDrawable( R.drawable.listitem));
		textview.setText(question.getText());
		textview.setLayoutParams(llp);
		
		//Add question to layout
		linearLayout.addView(textview, 0);
		
		
		//Start loading solutions
		solutionsLoaded = false;
		showSolutionsPressed = false;
		solutionTextViews = new ArrayList<TextView>();
		new FetchSolutionsTask(this,question).execute();
	}
	
	/**
	 * Appends a list of solutions to a hidden list.
	 * If the showSolutions button has already been pressed, it will reveal
	 * the solutions upon completion. If the button has not been pressed,
	 * solutions will be hidden.
	 * @param solutions
	 */
	public synchronized void addSolutions(List<Solution> solutions){
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
			t.setLayoutParams(llp);
			linearLayout.addView(t);
		}
		for(int i = 0; i < solutions.size(); i++){
			Solution solution = solutions.get(i);
			if(solution != null && solution.getText() != null){
				String solutionText = solution.getText();
				
				TextView t = new TextView(this);
				
				t.setText(solutionText);
				t.setBackgroundDrawable(getResources().getDrawable( R.drawable.listitem));
				t.setLayoutParams(llp);
				t.setId(solution.getId());
				//Hide solutions
				t.setVisibility(TextView.INVISIBLE);
				
				solutionTextViews.add(t);
				linearLayout.addView(t);
			}
		}
		
		solutionsLoaded = true;
		if(showSolutionsPressed){
			revealSolutions();
		}
	}

	
	/**
	 * Button handler for the "Solutions" button.
	 * Attempts to reveal solutions. If it cannot (solutions have not been
	 * loaded yet), it will set a flag to 
	 * 
	 * @param v 
	 */
	public synchronized void showSolutionsPressed(View v){
		if(!showSolutionsPressed){
			if(solutionsLoaded){
				revealSolutions();
			}
			else{
				showSolutionsPressed = true;
			}
		}
	}
	
	/**
	 * Reveals solutions. Should only be called once solutions are loaded.
	 */
	private void revealSolutions(){
		for(TextView tv : solutionTextViews){
			tv.setVisibility(TextView.VISIBLE);
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
