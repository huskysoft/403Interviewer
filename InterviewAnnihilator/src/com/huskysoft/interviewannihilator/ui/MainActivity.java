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
import com.huskysoft.interviewannihilator.runtime.*;

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
		
		questionll = (LinearLayout)findViewById(R.id.linear_layout);
		
		new FetchQuestionsTask(this).execute();
	}
	
	/**
	 * Displays a single question
	 * @param question
	 */
	public void displayQuestion(Question question) {
		
		//TODO: Change this to getText(), but it currently isn't populated
		String questionText = question.getTitle();
		
		TextView t = new TextView(this);
		
		t.setTag(question);
		t.setText(questionText);
		t.setTextSize(40);
		
		t.setBackgroundColor(0xfff00000);
		
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		llp.setMargins(40, 10, 40, 10); // llp.setMargins(left, top, right, bottom);
	   
	    llp.gravity = 1; // Horizontal Center
	    
	    t.setLayoutParams(llp);
		
		t.setId(question.getId());
		t.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openQuestion(v);
			}
		});

		questionll.addView(t);
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
		intent.putExtra(EXTRA_MESSAGE, (Question)view.getTag());
		startActivity(intent);
	}
	
}
