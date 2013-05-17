/**
 * The screen for when users attempt to post a solution
 * 
 * @author Cody Andrews, 05/14/2013
 */

package com.huskysoft.interviewannihilator.ui;

import com.huskysoft.interviewannihilator.R;
import com.huskysoft.interviewannihilator.model.Difficulty;
import com.huskysoft.interviewannihilator.model.NetworkException;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.model.Solution;
import com.huskysoft.interviewannihilator.service.QuestionService;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import android.os.Bundle;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class PostSolutionActivity extends SlidingActivity {
	/** The question being answered **/
	private Question question;
	
	/** Reference to activity used in click handler for slide-in menu */
	private PostSolutionActivity context;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_solution);
		setBehindContentView(R.layout.activity_menu);
		getActionBar().setHomeButtonEnabled(true);
		
		buildSlideMenu();
		context = this;
		
		// Get intent
		Intent intent = getIntent();
		question = (Question) intent.getSerializableExtra(
				QuestionActivity.EXTRA_MESSAGE);
		
		//setup question view
		TextView tv = (TextView) findViewById(R.id.question_view);
		tv.setText(question.getText());
	}

	/**
	 * Helper method that builds and populates the slide in menu.
	 */
	public void buildSlideMenu(){
		SlidingMenu menu = getSlidingMenu();
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = (int) ((double) metrics.widthPixels);
		menu.setBehindOffset((int) (width * SlideMenuInfoTransfer.SLIDE_MENU_WIDTH));
		
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
				String diffStr = spinner.getSelectedItem().toString();
				
				toggle();
				
				Intent intent = new Intent(context, MainActivity.class);
				if (diffStr == null || diffStr.length() == 0) {
					SlideMenuInfoTransfer.diff = null;
				} else {
					SlideMenuInfoTransfer.diff = 
							Difficulty.valueOf(diffStr.toUpperCase());
				}
				
				startActivity(intent);
			}
		});
		
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
			toggle();
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
			// Complains that i need to log the error, 
			// not sure how to do that
			outcome= -1;
		} catch (Exception e){
			// TODO: probably should do something useful here
			finish();
		}
		displayMessage(outcome);
	}
	
	/**
	* Pops up a window for the user to interact with the 
    * results of posting their solution.
    * 
    * @param status The state of the solution, which should 
    * 		 be passed as one of the following:
    *              1 if the solution was successfully posted
    *              0 if the solution was not valid upon trying to post
    *              Any other number to indicate a network error
    */
	private void displayMessage(int status){
		// custom dialog
		final Dialog dialog = new Dialog(this);
		if (status == 1 || status == 0){
			dialog.setContentView(R.layout.alertdialogcustom);
		}else{
			dialog.setContentView(R.layout.retrydialogcustom);
		}

		// set the custom dialog components - text, buttons
		TextView text = (TextView) dialog.findViewById(R.id.dialog_text);
		if (status == 1){
			text.setText(R.string.successDialog_title);
			Button dialogButton = (Button) 
					dialog.findViewById(R.id.dialogButtonOK);
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();   //It would look really cool for the solutions
								//to update b4 the user returns
				}
			});
		}else if (status == 0){
			text.setText(R.string.badInputDialog_title);
			Button dialogButton = (Button) 
					dialog.findViewById(R.id.dialogButtonOK);
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
		}else{
			text.setText(R.string.retryDialog_title);
			Button dialogButton = (Button) 
					dialog.findViewById(R.id.button_retry);
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					sendSolution();
				}
			});
			dialogButton = (Button) dialog.findViewById(R.id.button_cancel);
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
		}
		dialog.show();
	}
	
	
}
