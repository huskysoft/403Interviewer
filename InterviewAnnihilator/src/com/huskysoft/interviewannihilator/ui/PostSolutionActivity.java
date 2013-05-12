/**
 * Post a solution.
 * 
 * @author Justin Robb
 */
package com.huskysoft.interviewannihilator.ui;

import com.huskysoft.interviewannihilator.R;
import com.huskysoft.interviewannihilator.model.NetworkException;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.model.Solution;
import com.huskysoft.interviewannihilator.service.QuestionService;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;

public class PostSolutionActivity extends Activity {
	/** The question being answered **/
	private Question question;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_solution);
		
		// Get intent
		Intent intent = getIntent();
		question = (Question) intent.getSerializableExtra(
				SolutionActivity.EXTRA_MESSAGE);
		
		//setup question view
		TextView tv = (TextView) findViewById(R.id.question_view);
		tv.setText(question.getText());
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
		getMenuInflater().inflate(R.menu.post_solution, menu);
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
	
	/** Called when the user clicks the post button */
	public void sendSolution(View view) {
		sendSolution();
	}

	/**
	* Attempts to post a solution to the database.
	*/
	private void sendSolution() {
		if (question == null)
			throw new IllegalStateException();
	
		EditText editText = (EditText) findViewById(R.id.edit_solution);
		String message = editText.getText().toString();
		int outcome = 1;   
		if (message.trim().equals("")){
			// Fail due to bad solution
			outcome = 0;
		}
		Solution solution = new Solution(question.getQuestionId(), message);
		QuestionService qs = QuestionService.getInstance();
		try{
			qs.postSolution(solution);
		} catch (NetworkException e){
			// Retry or cancel
			outcome= -1;
		} 
		alertMessage(outcome);
		if (outcome == 1){

		}
	}

	/**
	* Pops up a window for the user to interact with the 
    * results of posting their solution.
    * 
    * @param state The state of the solution, which should 
    * 		 be passed as one of the following:
    *              1 if the solution was successfully posted
    *              0 if the solution was not valid upon trying to post
    *              Any other number to indicate a network error
    */
	public void alertMessage(int state) {
		DialogInterface.OnClickListener dialogClickListener = 
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case DialogInterface.BUTTON_POSITIVE:
								// Success
								Toast.makeText(PostSolutionActivity.this, 
										"Redirecting back to solutions...", 
										Toast.LENGTH_LONG).show();			
								finish();       // Go back to solutions page 
												// Maybe attempt to update solutions
												// That would look really cool
								break;
							case DialogInterface.BUTTON_NEUTRAL:
								// Retry
								Toast.makeText(PostSolutionActivity.this, 
										"Trying again...", 
										Toast.LENGTH_LONG).show();
								sendSolution();
								break;
							case DialogInterface.BUTTON_NEGATIVE:
								// Cancel or Return
								Toast.makeText(PostSolutionActivity.this, 
										"Returning...", 
										Toast.LENGTH_LONG).show();
								break;
						}
					}
		};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if (state == 1){
			// Success
			builder.setMessage("Post Successful.")
			.setPositiveButton("Ok", dialogClickListener).show();
		} else if (state == 0){
			// Bad input
			builder.setMessage("Please submit a valid solution.")
			.setNegativeButton("Return to editor", dialogClickListener)
			.show();
		} else {
			// Network error
			builder.setMessage("Network error.")
			.setNeutralButton("Retry", dialogClickListener)
			.setNegativeButton("Cancel", dialogClickListener).show();
		}
	}
}
