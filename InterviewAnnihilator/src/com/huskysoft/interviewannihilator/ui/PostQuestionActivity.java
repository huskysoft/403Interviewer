package com.huskysoft.interviewannihilator.ui;

import java.util.ArrayList;
import java.util.List;


import com.huskysoft.interviewannihilator.R;
import com.huskysoft.interviewannihilator.model.Category;
import com.huskysoft.interviewannihilator.model.Difficulty;
import com.huskysoft.interviewannihilator.model.NetworkException;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.model.Solution;
import com.huskysoft.interviewannihilator.service.QuestionService;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PostQuestionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_solution);
		
		// fill category spinner
		List<String> SpinnerArray =  new ArrayList<String>();
		for (Category c : Category.values()){
			SpinnerArray.add(c.toString());
		}
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerArray);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    Spinner Items = (Spinner) findViewById(R.id.category_select);
	    Items.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post_question, menu);
		return true;
	}
	
	public void sendQuestion(View v) {
		String category = ((Spinner) findViewById(R.id.category_select)).getSelectedItem().toString();
		Category c = Category.valueOf(category);
		String solutionText = ((EditText) findViewById(R.id.edit_solution_q)).getText().toString();
		String questionText = ((EditText) findViewById(R.id.edit_question)).getText().toString();
		String titleText = ((EditText) findViewById(R.id.edit_question_title)).getText().toString();
		
		if (titleText.trim() == ""){
			displayMessage(0, getString(R.string.badInputDialog_title));
		} else if (questionText.trim() == ""){
			displayMessage(0, getString(R.string.badInputDialog_question));
		} else if (solutionText.trim() == ""){
			displayMessage(0, getString(R.string.badInputDialog_solution));
		} else {
			Question q = new Question(questionText, titleText, Category.COMPSCI, getDifficulty());
			QuestionService qs = QuestionService.getInstance();
			Solution s = new Solution(q.getQuestionId(), solutionText);
			try {
				qs.postQuestion(q);
				qs.postSolution(s);
				displayMessage(1, getString(R.string.successDialog_title_q));
			} catch (NetworkException e) {
				displayMessage(-1, getString(R.string.retryDialog_title));
			} catch (Exception e) {
				displayMessage(-1, getString(R.string.internalError_title));
			}
		}
		
	}
	
	private Difficulty getDifficulty(){
		if (((RadioButton) findViewById(R.id.difficulty_hard)).isChecked())
			return Difficulty.HARD;
		else if (((RadioButton) findViewById(R.id.difficulty_medium)).isChecked())
			return Difficulty.MEDIUM;
		else
			return Difficulty.EASY;
	}
	
	/**
	* Pops up a window for the user to interact with the 
    * results of posting their solution.
    * 
    * @param status The state of the solution, which should 
    * 		 be passed as one of the following:
    *              1 if the user is finished on this page
    *              0 if the solution was not valid upon trying to post
    *              Any other number to indicate an error
    *              
    * @param titleId The string to display to the user, 
    * 				 telling them what was invalid          
    */
	private void displayMessage(int status, String message){
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
			text.setText(message);
			Button dialogButton = (Button) 
					dialog.findViewById(R.id.dialogButtonOK);
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(getApplicationContext(), 
							R.string.toast_return, Toast.LENGTH_LONG).show();
					finish();   //It would look really cool for the solutions
								//to update b4 the user returns
				}
			});
		}else if (status == 0){
			text.setText(message);
			Button dialogButton = (Button) 
					dialog.findViewById(R.id.dialogButtonOK);
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(getApplicationContext(), 
							R.string.toast_return, Toast.LENGTH_LONG).show();
					dialog.dismiss();
				}
			});
		}else{
			text.setText(message);
			Button dialogButton = (Button) 
					dialog.findViewById(R.id.button_retry);
			// if button is clicked, send the solution
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					sendQuestion(v);
				}
			});
			dialogButton = (Button) dialog.findViewById(R.id.button_cancel);
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(getApplicationContext(), 
							R.string.toast_retry, Toast.LENGTH_LONG).show();
					dialog.dismiss();
				}
			});
		}
		dialog.show();
	}

}
