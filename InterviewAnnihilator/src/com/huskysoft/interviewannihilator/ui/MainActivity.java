/**

 * Main UI for the application. Displays a list of questions.
 * 
 * @author Cody Andrews, Phillip Leland, Justin Robb 05/01/2013
 * 
 */

package com.huskysoft.interviewannihilator.ui;

import java.util.List;
import java.util.Locale;

import com.huskysoft.interviewannihilator.R;
import com.huskysoft.interviewannihilator.model.*;
import com.huskysoft.interviewannihilator.runtime.*;
import com.huskysoft.interviewannihilator.util.UIConstants;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AbstractPostingActivity {

	/**
	 * Used to pass the String question to the child activity.
	 * Will pass a Question object.
	 */
	public final static String EXTRA_MESSAGE =
			"com.huskysoft.interviewannihilator.QUESTION";
	
	/** 
	 * Number of questions currently being displayed,used to index
	 * into the db
	 */
	private int questionOffset = 0;
	
	/**
	 * Method that populates the app when the MainActivity is created.
	 * Initializes the questions and questionll fields. Also calls
	 * the displayQuestions function.
	 */
	@Override
	public synchronized void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.activity_menu);
		getActionBar().setHomeButtonEnabled(true);		
		
		// ensure user has been validated
		if (!isUserInfoLoaded()){
			this.initializeUserInfo();
		}		
		
		// Get info from transfer class
		slideMenuInfo = SlideMenuInfo.getInstance();
		Difficulty diff = slideMenuInfo.getDiff();
		List<Category> cat = slideMenuInfo.getCat();
		buildSlideMenu();
		
		if(diff == null){
			setDifficultyToSelectedValue("");
		}else{
			setDifficultyToSelectedValue(
				diff.toString(Locale.getDefault()));			
		}
		
		if(!cat.isEmpty()){
			setCategorySpinners(cat);
		}
		
		hideMainView();
		showLoadingViewOne();
		loadQuestions();
	}
	
	/**
	 * Function that will make set the currently selected difficulty spinner
	 * value to the passed in string. Used when the difficulty
	 * menu is changed from a SolutionActivity or PostSolutionActivity.
	 * 
	 * @param value Selected Spinner value
	 */
	public void setDifficultyToSelectedValue(String value){
		Spinner spinner = (Spinner) findViewById(R.id.diff_spinner);
		
		Adapter a = spinner.getAdapter();
		for (int i = 0; i < a.getCount(); i++){

			if (a.getItem(i).toString().toUpperCase().equals(
					value.toUpperCase())){

				spinner.setSelection(i);
				return;
			}
		}
	}
	
	/**
	 * Sets the category spinners of the Slide In menu
	 * to the passed list of values. Creates new 
	 * sliders for extra categories.
	 * @param cats
	 */
	@SuppressLint("NewApi")
	public void setCategorySpinners(List<Category> cats){
		String catStrUp = 
				cats.get(0).toString(Locale.getDefault()).toUpperCase();
		
		Spinner spinner = (Spinner) findViewById(R.id.category_spinner);
		Adapter a = spinner.getAdapter();
		for (int x = 0; x < a.getCount(); x++){
			
			String possible = a.getItem(x).toString().toUpperCase();
						
			if (possible.equals(catStrUp)){
				spinner.setSelection(x);
			}
		}
		
		for(int i = 1; i < cats.size(); i++){
			catStrUp = cats.get(i).toString(Locale.getDefault()).toUpperCase();

			Spinner newSpin = newCategorySpinner(catStrUp);
			TableLayout table = 
				(TableLayout) findViewById(R.id.slide_table);
			TableRow row = new TableRow(this);
			
			int pad = UIConstants.SLIDE_MENU_PADDING;
			row.setPaddingRelative(pad, pad, pad, pad);
			
			TextView categoryText = new TextView(this);
			
			categoryText.setText("Category");
			categoryText.setTextSize(UIConstants.SLIDE_MENU_TEXT_SIZE);
			
			TableRow.LayoutParams params = new TableRow.LayoutParams(
					TableRow.LayoutParams.MATCH_PARENT,
					TableRow.LayoutParams.WRAP_CONTENT);
			
			row.addView(categoryText, params);
			
			row.addView(newSpin);
			
			
			table.addView(row, table.getChildCount() - 1);
		}
		if(cats.size() > 1){ // Add Remove button
			Button removeButton = 
				(Button) findViewById(R.id.remove_category_button);
			removeButton.setVisibility(View.VISIBLE);
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
		
		int diff = spinner.getSelectedItemPosition();
		
		if (diff == 0) {
			return null;
		}
		
		return Difficulty.values()[diff - 1];
	}
	
	/**
	 * Shows loading text
	 */
	public void showLoadingViewOne(){
		View loadingText = findViewById(R.id.layout_loading);
		loadingText.setVisibility(View.VISIBLE);
	}
	
	/**
	 * Hides loading text
	 */
	public void hideLoadingViewOne(){
		View loadingText = findViewById(R.id.layout_loading);
		loadingText.setVisibility(View.GONE);
	}
	
	/**
	 * Shows loading text
	 */
	public void showLoadingViewTwo(){
		View loadingText = findViewById(R.id.layout_loading_more);
		loadingText.setVisibility(View.VISIBLE);
	}
	
	/**
	 * Hides loading text
	 */
	public void hideLoadingViewTwo(){
		View loadingText = findViewById(R.id.layout_loading_more);
		loadingText.setVisibility(View.GONE);
	}

	/**
	 * Shows main question list and buttons
	 */
	public void showMainView(){
		View mainView = findViewById(R.id.main_view);
		mainView.setVisibility(View.VISIBLE);
	}
	
	/**
	 * Hides main question list and buttons
	 */
	public void hideMainView(){
		View mainView = findViewById(R.id.main_view);
		mainView.setVisibility(View.GONE);
	}
	
	public void loadQuestions(){
		// Populate questions list. This makes a network call.
		new FetchQuestionsTask(this,
				getCurrentCategories(),
				getCurrentDifficultySetting(),
				UIConstants.DEFAULT_QUESTIONS_TO_LOAD,
				questionOffset).execute();
	}
	
	/**
	 * This is called when the "Show me more" button is pressed. 
	 * 
	 * @param v button being pressed
	 */
	public void loadMoreQuestions(View v){
		showLoadingViewTwo();
		loadQuestions();
	}
	
	/**
	 * Displays a formatted list of questions
	 * 
	 * @param questions
	 */
	public void appendQuestionsToView(List<Question> questionList) {
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 0.75f);
		
		//TODO: Move to XML or constants file - haven't yet figured out how
		llp.setMargins(40, 10, 40, 10);
		llp.gravity = 1;  // Horizontal Center
		
		ViewGroup questionView =
				(ViewGroup) findViewById(R.id.question_layout);
		
		if(questionList == null || questionList.size() <= 0){
			// No new questions
			if(questionView.getChildCount() == 0){
				// No existing questions
				TextView t = new TextView(this);
	
				t.setText(getString(R.string.no_questions_found));
				t.setTextColor(getResources().getColor(R.color.gold));
				// special look?
				t.setLayoutParams(llp);
				questionView.addView(t);
			}else{
				// Some existing questions
				Toast.makeText(getApplicationContext(), 
						R.string.toast_no_more_questions,
						Toast.LENGTH_LONG).show();
			}
		}else{
			// Increase the question offset so that next time we access the db, we
			// get the next set of questions
			questionOffset += questionList.size();
			
			for(int i = 0; i < questionList.size(); i++){
				Question question = questionList.get(i);
				if(question != null && question.getText() != null){
					
					//build text
					String questionTitle = question.getTitle();
					String questionBody = question.getText();
					String questionDiff = question.getDifficulty().
							toString(Locale.getDefault());
					String questionCat = question.getCategory().
							toString(Locale.getDefault());
					String questionDate = question.getDateCreated().toString();
					
					// abbreviate
					if (questionBody.length() > 
							UIConstants.TEXT_PREVIEW_LENGTH){
						questionBody = questionBody.substring(
								0, UIConstants.TEXT_PREVIEW_LENGTH);
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
					questionView.addView(t);
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
				loadQuestions();
				dialog.dismiss();
			}
		});
		dialogButton = (Button) dialog.findViewById(R.id.button_cancel);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				finish();
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
}

