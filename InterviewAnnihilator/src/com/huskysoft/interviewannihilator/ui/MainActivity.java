package com.huskysoft.interviewannihilator.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import com.huskysoft.interviewannihilator.R;
import com.huskysoft.interviewannihilator.util.*;
import com.huskysoft.interviewannihilator.service.*;
import com.huskysoft.interviewannihilator.model.*;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	/*
	 * Used to pass the String question to the child activity.
	 * Will pass a Question object.
	 */
	public final static String EXTRA_MESSAGE = 
			"com.huskysoft.interviewannihilator.QUESTION";
		
	/** Layout element that holds the questions */
	private LinearLayout questionll;
	
	/** List of question elements */
	private List<String> questions;
	
	 /** service that interacts with database */
	private QuestionService databaseService;
	
	
	/**
	 * Method that populates the app when the MainActivity is created.
	 * Initializes the questions and questionll fields. Also calls
	 * the displayQuestions function.
	 */
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		questions = new LinkedList<String>(); // Will be a list of Question Objects
		questionll = (LinearLayout)findViewById(R.id.linear_layout);
		databaseService = QuestionService.getInstance();
		
		if(questions.isEmpty())
			try {
				retrieveQuestions();
			} catch (NetworkException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		displayQuestions();
		
	}
	
	/**
	 * Method that builds TextView objects for each question
	 * in the questions list. Appends the TextView objects to the
	 * main Linear Layout. 
	 */
	private void displayQuestions() {
		Iterator<String> it = questions.iterator();
		int idCount = 1;
		while(it.hasNext()){		
			TextView t = new TextView(this);
			t.setText(it.next());
			t.setTextSize(40);
			
			t.setBackgroundColor(0xfff00000);
			
			LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			llp.setMargins(40, 10, 40, 10); // llp.setMargins(left, top, right, bottom);
		   
		    llp.gravity = 1; // Horizontal Center
		    
		    t.setLayoutParams(llp);
			
			t.setId(idCount);
			t.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					openQuestion(v);
				}
			});
			
			idCount++;
			questionll.addView(t);
		}
	}
	
	/**
	 * Gathers 20 new questions. Appends questions to the 
	 * questions list.
	 * 
	 * @author Phillip Leland
	 * @throws JSONException 
	 * @throws NetworkException 
	 */
	private void retrieveQuestions() throws NetworkException, JSONException {
		PaginatedQuestions currentQuestions = 
				databaseService.getQuestions(null, null, 20, 0);
		List<Question> questionList = currentQuestions.getQuestions();
		for (int i = 0; i < questionList.size(); i++) {
			String questionText = questionList.get(i).getText();
			questions.add(questionText);
		}
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
	 * 
	 * @param view The TextView that holds the selected question.
	 */
	public void openQuestion(View view){
		Intent intent = new Intent(this, QuestionActivity.class);
		TextView tv = (TextView) view;
		String message = tv.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}
	
}
