/**
 * Main UI for the application. Displays a list of questions.
 * 
 * @author Cody Andrews, Phillip Leland, Justin Robb 05/01/2013
 * 
 */

package com.huskysoft.interviewannihilator.ui;

import java.util.List;

import com.huskysoft.interviewannihilator.R;
import com.huskysoft.interviewannihilator.model.*;
import com.huskysoft.interviewannihilator.runtime.*;
import com.huskysoft.interviewannihilator.util.UIConstants;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class MainActivity extends AbstractPostingActivity {

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
	public synchronized void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.activity_menu);
		getActionBar().setHomeButtonEnabled(true);
		
		if (!initializedUser && tryInitialize){
			this.initializeUserInfo();
		}
		
		
		// Get info from transfer class
		slideMenuInfo = SlideMenuInfo.getInstance();
		Difficulty diff = slideMenuInfo.getDiff();
		Category cat = slideMenuInfo.getCat();
		
		// Reset PassedDifficulty
		slideMenuInfo.setDiff(null);
		slideMenuInfo.setCat(null);
		
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
		
		Adapter a = spinner.getAdapter();
		for (int i = 0; i < a.getCount(); i++){
			if (a.getItem(i).toString().equals(value)){
				spinner.setSelection(i);
				return;
			}
		}
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

		String diff = spinner.getSelectedItem().toString();
		if (diff == null || diff.isEmpty() || diff.equals(UIConstants.ALL)) {
			return null;
		}
		return Difficulty.valueOf(diff.toUpperCase());
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
		if(category.equals(UIConstants.ALL)){
			return null;
		}
		
		category = category.replaceAll("\\s", "");
		return Category.valueOf(category.toUpperCase());
	}
	
	/**
	 * Helper method that builds the slide menu on the current activity.
	 */
	@Override
	public void buildSlideMenu(){
		SlidingMenu menu = getSlidingMenu();
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = (int) ((double) metrics.widthPixels);
		menu.setBehindOffset((int)
				(width * SlideMenuInfo.SLIDE_MENU_WIDTH));
		
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
	 * Sets the questions to be displayed (does not display them).
	 * @param questions
	 */
	public void setQuestions(List<Question> questions){
		questionList = questions;
	}
	
	

	public void loadQuestions(Difficulty diff, Category cat){
		// Display loading text
		LinearLayout loadingText =
				(LinearLayout) findViewById(R.id.loading_text_layout);
		loadingText.setVisibility(View.VISIBLE);

		// Populate questions list. This makes a network call.
		new FetchQuestionsTask(this, diff, cat).execute();
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
					
					//build text
					String questionTitle = question.getTitle();
					String questionBody = question.getText();
					String questionDiff = question.getDifficulty().toString();
					String questionCat = question.getCategory().toString();
					String questionDate = question.getDateCreated().toString();
					
					// abbreviate
					if (questionBody.length() > 150){
						questionBody = questionBody.substring(0, 150);
						questionBody += "...";
					}
					int pos = 0;
					SpannableStringBuilder sb = new SpannableStringBuilder();
					// title
					sb.append(questionTitle);
					sb.setSpan(new  TextAppearanceSpan(this, 
							R.style.question_title_appearance), pos, 
							sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					pos += questionTitle.length();
					
					// descriptors
					sb.append('\n');
					sb.append(questionCat);
					sb.append("\t\t\t");
					sb.append(questionDiff);
					sb.setSpan(new  TextAppearanceSpan(
							this, R.style.question_descriptors_appearance),
							pos, sb.length(), 
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					sb.append("\n\n");
					pos += questionDiff.length() + questionCat.length() + 5;
					
					// body
					sb.append(questionBody);
					sb.setSpan(new  TextAppearanceSpan(
							this, R.style.question_body_appearance), pos, 
							sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					sb.append('\n');
					pos += questionBody.length() + 1;
					// date
					sb.append('\n');
					sb.append(questionDate);
					sb.setSpan(new  TextAppearanceSpan(
							this, R.style.question_date_appearance), pos, 
							sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					
					// done
					TextView t = new TextView(this);
					t.setLayoutParams(llp);
					t.setId(question.getQuestionId());
					t.setTag(question);
					t.setText(sb);	
					// to make it work on older versions use this instead of
					// setBackground
					t.setBackground(getResources().
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
	 * 
	 * EDIT: looks the same as all other dialog boxes now.
	 * It's more cumbersome to make but consistency is important.
	 * 
	 */
	public void onNetworkError(){		
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.retrydialogcustom);
		// set the custom dialog components - text, buttons
		TextView text = (TextView) dialog.findViewById(R.id.dialog_text);
		text.setText(getString(R.string.retryDialog_title));
		Button dialogButton = (Button) 
				dialog.findViewById(R.id.button_retry);
		// if button is clicked, send the solution
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), 
						R.string.toast_retry, Toast.LENGTH_LONG).show();
				loadQuestions(null, null);
				dialog.dismiss();
			}
		});
		dialogButton = (Button) dialog.findViewById(R.id.button_cancel);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), 
						R.string.toast_return, Toast.LENGTH_LONG).show();
				dialog.dismiss();
			}
		});
		dialog.show();
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

