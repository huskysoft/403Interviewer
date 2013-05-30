/**
 * Main UI for the application. Displays a list of questions.
 * 
 * @author Cody Andrews, Phillip Leland, Justin Robb 05/01/2013
 * 
 */

package com.huskysoft.interviewannihilator.ui;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import com.huskysoft.interviewannihilator.R;
import com.huskysoft.interviewannihilator.model.Category;
import com.huskysoft.interviewannihilator.model.Difficulty;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.model.RandomQuestionCollection;
import com.huskysoft.interviewannihilator.runtime.FetchRandomQuestionsTask;
import com.huskysoft.interviewannihilator.runtime.InitializeUserTask;
import com.huskysoft.interviewannihilator.util.UIConstants;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public abstract class AbstractPostingActivity extends SlidingActivity{
		
	/** Do we have to initialize this user? **/
	private static boolean initializedUser = false;
	
	/** Do we have to initialize this user? **/
	private static boolean tryInitialize = true;
	
	public static boolean isInitializedUser() {
		return initializedUser;
	}

	public static void setInitializedUser(boolean initializedUser) {
		AbstractPostingActivity.initializedUser = initializedUser;
	}

	public static boolean isTryInitialize() {
		return tryInitialize;
	}

	public static void setTryInitialize(boolean tryInitialize) {
		AbstractPostingActivity.tryInitialize = tryInitialize;
	}

	/** Shared SlideMenuInfo object */
	protected SlideMenuInfo slideMenuInfo;
	
	@SuppressLint("NewApi")
	@Override
	public synchronized void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	

		// Get info from transfer class
		slideMenuInfo = SlideMenuInfo.getInstance();
	}
	
	/////////////////////////sliding menu stuff/////////////////////////
	
	/**
	 * Cycles through the current categories selected in the Slide
	 * In Menu and places them in a list.
	 * 
	 * @return List of selected Categories
	 */
	public List<Category> getCurrentCategories(){
		TableLayout table = (TableLayout) findViewById(R.id.slide_table);
		List<Category> categories = new LinkedList<Category>();
		
		for(int i = 1; i < table.getChildCount() - 1; i++){
			TableRow row = (TableRow) table.getChildAt(i);
			Spinner catSpinner = (Spinner) row.getChildAt(1);
			
			String categoryStr = 
					catSpinner.getSelectedItem()
					.toString().replaceAll("\\s", "");
						
			if (categoryStr == null || categoryStr.length() == 0 ||
				categoryStr.equals(UIConstants.ALL)){
				categories.clear();
			} else{
				categories.add(
					Category.valueOf(categoryStr.toUpperCase()));
			}
		}
		return categories;
	}
	
	/**
	 * Builds a new Spinner object for a new Category.
	 * Sets the selected value to the passed in parameter.
	 * 
	 * Called by MainActivity
	 * 
	 * @param selected
	 * @return Spinner object.
	 */
	public Spinner newCategorySpinner(String selected){
		Spinner newCategory = new Spinner(this);
		
		ArrayAdapter<CharSequence> adapter = 
				ArrayAdapter.createFromResource(this,
				R.array.category, 
				android.R.layout.simple_spinner_item);
		
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		
		// Apply the adapter to the spinner
		newCategory.setAdapter(adapter);
		
		if(!selected.equals("")){
			Adapter a = newCategory.getAdapter();
			for (int x = 0; x < a.getCount(); x++){
				String possible = a.getItem(x).toString().toUpperCase();
				if (possible.equals(selected.toUpperCase())){
					newCategory.setSelection(x);
				}
			}
		}
		
		return newCategory;
	}
	
	/**
	 * Add another category spinner in the slide menu
	 */
	@SuppressLint("NewApi")
	public void addCategory(View v){
		TableLayout table = (TableLayout) findViewById(R.id.slide_table);
		
		if(table.getChildCount() < Category.values().length + 3){
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
			
			row.addView(newCategorySpinner(""), params);
			
			// Add new row before the buttons
			table.addView(row, table.getChildCount() - 1);
			
			// Set Remove button visible
			Button removeButton = 
				(Button) findViewById(R.id.remove_category_button);
			removeButton.setVisibility(View.VISIBLE);
		}
		
	}
	
	/**
	 * OnClick handler for the remove category button of the
	 * slide menu.
	 * 
	 * @param v
	 */
	public void removeCategory(View v){
		TableLayout table = (TableLayout) findViewById(R.id.slide_table);
		int baseRows = UIConstants.BASE_NUM_MENU_ROWS;
		
		if(table.getChildCount() > baseRows){
			table.removeViewAt(table.getChildCount() - (baseRows - 1));
		}
		if(table.getChildCount() == baseRows){
			Button removeButton = 
				(Button) findViewById(R.id.remove_category_button);
			removeButton.setVisibility(View.GONE);
		}
	}
	
	/**
	 * Set up the Slide menu
	 */
	public void buildSlideMenu(){
		SlidingMenu menu = getSlidingMenu();
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = (int) ((double) metrics.widthPixels);
		menu.setBehindOffset((int) 
				(width * SlideMenuInfo.SLIDE_MENU_WIDTH));
		
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
		
		
		Spinner categorySpinner = 
				(Spinner) findViewById(R.id.category_spinner);
			ArrayAdapter<CharSequence> catAdapter = 
					ArrayAdapter.createFromResource(this,
					R.array.category, 
					android.R.layout.simple_spinner_item);
			
			catAdapter.setDropDownViewResource(
					android.R.layout.simple_spinner_dropdown_item);
			
			categorySpinner.setAdapter(catAdapter);
			
		// Handle onClick of Slide-Menu button
		Button button = (Button) findViewById(R.id.slide_menu_button);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				slideMenuInfo.setCat(getCurrentCategories());
				
				Spinner diffSpinner = (Spinner) findViewById(R.id.diff_spinner);
				String diffStr = diffSpinner.getSelectedItem().toString();
				
				if (diffStr == null || diffStr.length() == 0 ||
					diffStr.equals(UIConstants.ALL)) {
					slideMenuInfo.setDiff(null);
				} else {
					slideMenuInfo.setDiff(
							Difficulty.valueOf(diffStr.toUpperCase()));
				}
				
				toggle();
				Intent intent = new Intent(getApplicationContext(), 
						MainActivity.class);
				startActivity(intent);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post_solution, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean ret;
		switch (item.getItemId()) {
			case android.R.id.home:
				toggle();
				ret = true;
				break;
				
			case R.id.random_question:
				if(RandomQuestionCollection.getInstance().isEmpty()){
					new FetchRandomQuestionsTask(this).execute();
				} else {
					Question rand = RandomQuestionCollection.
							getInstance().getQuestion();
					Intent intent = new Intent(this, QuestionActivity.class);
					intent.putExtra(MainActivity.EXTRA_MESSAGE, rand);
					startActivity(intent);
				}
				ret = true;
				break;
				
			default:
				ret = super.onOptionsItemSelected(item);
		}
		return ret;
	}
	
	/**
	 * Called when the user clicks on button to post a question
	 * 
	 * @param v The TextView that holds the selected question. 
	 */
	public void postQuestion(View v) {
		if (initializedUser) {
			Intent intent = new Intent(this, PostQuestionActivity.class);
			startActivity(intent);
		} else {
			// helpful message
			onValidationIssue();
		}
	}
	
	/**
	 * Displays a message explaining why a user can't post something
	 */
	public void onValidationIssue() {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.alertdialogcustom);
		// set the custom dialog components - text, buttons
		TextView text = (TextView) dialog.findViewById(R.id.dialog_text_alert);
		text.setText(getString(R.string.userInfoHelp_title));
		Button dialogButton = (Button) 
				dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, send the solution
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
	
	//////////////////User Validation stuff//////////////////////////
	
	/**
	 * Attempts to initialize the user's information on database
	 * 
	 */
	public void initializeUserInfo(){
		File dir = getFilesDir();
		new InitializeUserTask(this, dir, "Anon@example.com").execute();
	}
	
	/**
	 * Lets the application know that user info is initialized and user can post
	 * 
	 */
	public void userInfoSuccessFunction(){
		setInitializedUser(true);
	}
	
	/**
	 * Explains to the user the concept of validation
	 * and asks them if they want to retry
	 */
	public void onInitializeError(){
		setInitializedUser(false);
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.retrydialogcustom);
		// set the custom dialog components - text, buttons
		TextView text = (TextView) dialog.findViewById(R.id.dialog_text);
		text.setText(getString(R.string.userInfoError_title));
		Button dialogButton = (Button) 
				dialog.findViewById(R.id.button_retry);
		dialogButton.setText(getString(R.string.userInfoHelp_retry));
		// if button is clicked, send the solution
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), 
						R.string.toast_retry, Toast.LENGTH_LONG).show();
				initializeUserInfo();
			}
		});
		dialogButton = (Button) dialog.findViewById(R.id.button_cancel);
		dialogButton.setText(getString(R.string.userInfoHelp_cancel));
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), 
						R.string.toast_return, Toast.LENGTH_LONG).show();
				tryInitialize = false;
				dialog.dismiss();
			}
		});
		dialog.show();
	}
}

