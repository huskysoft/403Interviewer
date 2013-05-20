/**
 * Main UI for the application. Displays a list of questions.
 * 
 * @author Cody Andrews, Phillip Leland, 05/01/2013
 * 
 */

package com.huskysoft.interviewannihilator.ui;

import java.util.List;

import com.huskysoft.interviewannihilator.R;
import com.huskysoft.interviewannihilator.model.*;
import com.huskysoft.interviewannihilator.runtime.*;
import com.huskysoft.interviewannihilator.util.Utility;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class MainActivity extends SlidingActivity {
	
	/**
	 * Used to pass the String question to the child activity.
	 * Will pass a Question object.
	 */
	public final static String EXTRA_MESSAGE =
			"com.huskysoft.interviewannihilator.QUESTION";
	
	/** Layout element that holds the questions */
	private LinearLayout questionLayout;
	
	private List<Question> questionList;
	
	
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
		
		// Get passed difficulty stored in transfer class
		Difficulty diff = SlideMenuInfoTransfer.diff;
		
		// Get passed category stored in Transfer class
		Category cat = SlideMenuInfoTransfer.cat;
		
		// Reset PassedDifficulty
		SlideMenuInfoTransfer.diff = null;
		SlideMenuInfoTransfer.cat = null;
		
		buildSlideMenu();
		
		if(diff == null){
			setSpinnerToSelectedValue("Difficulty", "");
		}
		
		if(cat == null){
			setSpinnerToSelectedValue("Category", "");
		}
		
		
		questionLayout = (LinearLayout) findViewById(R.id.question_layout);
		
		View loadingText = findViewById(R.id.loading_text_layout);
		loadingText.setVisibility(View.VISIBLE);
		
		if(questionList == null){
			loadQuestions(diff, cat);
		} else{
			displayQuestions();
		}
	}
	
	/**
	 * Function that will make set the currently selected spinner
	 * value to the passed in string. Used when the difficulty
	 * menu is changed from a SolutionActivity or PostSolutionActivity.
	 * 
	 * @param value Selected Spinner value
	 */
	public void setSpinnerToSelectedValue(String type, String value){
		Spinner spinner = null;
		
		if(type.equals("Difficulty")){
			spinner = (Spinner) findViewById(R.id.diff_spinner);
		} else{
			spinner = (Spinner) findViewById(R.id.category_spinner);
		}
		ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter();
		
		spinner.setSelection(myAdap.getPosition(value));
	}
	
	/**
	 * Method that returns the Difficulty Enum that is 
	 * currently selected in the Difficulty spinner input
	 * on the slide menu.
	 * 
	 * @return Difficulty Enum
	 */
	public Difficulty getCurrentDifficultySetting(){
		Spinner spinner = (Spinner) findViewById(R.id.diff_spinner);
		String difficulty = spinner.getSelectedItem().toString();
		
		if (difficulty.equals(Utility.ALL)){
			return null;
		}
		
		return Difficulty.valueOf(difficulty.toUpperCase());
	}
	
	/**
	 * Method that returns the Category Enum that is
	 * currently selected in the Category spinner input
	 * on the slide menu
	 * 
	 * @ return Category Enum
	 */
	public Category getCurrentCategorySetting(){
		Spinner spinner = (Spinner) findViewById(R.id.category_spinner);
		String category = spinner.getSelectedItem().toString();
		if(category.equals(Utility.ALL)){
			return null;
		}
		
		category = category.replaceAll("\\s", "");
		return Category.valueOf(category.toUpperCase());
	}
	
	/**
	 * Helper method that builds the slide menu on the current activity.
	 */
	public void buildSlideMenu(){
		SlidingMenu menu = getSlidingMenu();
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = (int) ((double) metrics.widthPixels);
		menu.setBehindOffset((int)
				(width * SlideMenuInfoTransfer.SLIDE_MENU_WIDTH));
		
		Spinner diffSpinner = (Spinner) findViewById(R.id.diff_spinner);
		ArrayAdapter<CharSequence> adapter = 
				ArrayAdapter.createFromResource(this,
				R.array.difficulty, 
				android.R.layout.simple_spinner_item);
		
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		
		// Apply the adapter to the spinner
		diffSpinner.setAdapter(adapter);
		
		Spinner categorySpinner = 
			(Spinner) findViewById(R.id.category_spinner);
		ArrayAdapter<CharSequence> catAdapter = 
				ArrayAdapter.createFromResource(this,
				R.array.category, 
				android.R.layout.simple_spinner_item);
		
		catAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		
		categorySpinner.setAdapter(catAdapter);
	}
	
	/**
	 * Click handler for the slide-in menu difficulty selection.
	 * Will repopulate the list of questions with new questions
	 * that have the selected difficulty.
	 * 
	 * @param v Button View
	 */
	public void adjustSettings(View v){
		Difficulty diff = getCurrentDifficultySetting();
		Category cat = getCurrentCategorySetting();
		toggle();
		
		// Clear current Questions
		questionLayout.removeAllViews();
		
		// Switch back to the loading view
		this.switchView();
		
		loadQuestions(diff, cat);
	}
	
	/**
	 * Changes from the loading view to the question list view and vice versa
	 */
	public void switchView(){
		// Switch views
		ViewSwitcher switcher =
				(ViewSwitcher) findViewById(R.id.main_activity_view_switcher);
		switcher.showNext();
	}
	
	/**
	 * Shows loading text and starts loading questions
	 * 
	 * @param diff
	 * @param cat 
	 */
	private void loadQuestions(Difficulty diff, Category cat){			
		// Populate questions list. This makes a network call.
		new FetchQuestionsTask(this, diff, cat).execute();
	}
	
	/**
	 * Sets the questions to be displayed (does not display them).
	 * @param questions
	 */
	public void setQuestions(List<Question> questions){
		questionList = questions;
	}
	
	/**
	 * Displays a formatted list of questions
	 * 
	 * @param questions
	 */
	@SuppressLint("NewApi")
	public void displayQuestions() {
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 0.75f);
		
		//TODO: Move to XML or constants file - haven't yet figured out how
		llp.setMargins(40, 10, 40, 10);
		llp.gravity = 1;  // Horizontal Center
		
		
		if(questionList == null || questionList.size() <= 0){
			TextView t = new TextView(this);
			
			t.setText("There doesn't seem to be any questions.");
			// special look?
			t.setLayoutParams(llp);
			questionLayout.addView(t);
		}else{
			for(int i = 0; i < questionList.size(); i++){
				Question question = questionList.get(i);
				if(question != null && question.getText() != null){
					
					String questionText = question.getTitle();
					
					TextView t = new TextView(this);
					
					t.setLayoutParams(llp);
					t.setId(question.getQuestionId());
					t.setTag(question);
					t.setText(questionText);	
					
					// to make it work on older versions use this instead of
					// setBackground
					t.setBackgroundDrawable(getResources().
							getDrawable(R.drawable.listitem));
					
					t.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							openQuestion(v);
						}
					});
			
					questionLayout.addView(t);
				}
			}
		}
	}
	
	/**
	 * Pops up a dialog menu with "Retry" and "Cancel" options when a network
	 * operation fails.
	 */
	public void onNetworkError(){		
		// Create a dialog
		new AlertDialog.Builder(this).setTitle(R.string.retryDialog_title)
		.setPositiveButton(R.string.retryDialog_retry,
		new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				loadQuestions(null, null);
			}
		})
		.setNegativeButton(R.string.retryDialog_cancel,
		new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		})
		.create().show();
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
