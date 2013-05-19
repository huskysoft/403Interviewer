package com.huskysoft.interviewannihilator.ui;

//import junit.framework.TestCase;

//import org.junit.Test;

import com.jayway.android.robotium.solo.Solo;
//import com.huskysoft.interviewannihilator.ui.MainActivity;

import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity2Test extends ActivityInstrumentationTestCase2<MainActivity> {

	private Solo solo;
	
	protected void setUp() throws Exception {
		//super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public MainActivity2Test() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	public void test() {
//		assertTrue(true);
	}
	
	public void testSlideMenuOpen() {
		solo.assertCurrentActivity("checking going to MainActivity",
				MainActivity.class);
		TextView button = solo.getText(3);
		button.callOnClick();
		solo.assertCurrentActivity("checking going to question",
				QuestionActivity.class);
		solo.goBack();
		solo.assertCurrentActivity("checking going to MainActivity",
				MainActivity.class);
		
	}

	// get all the buttons and cycle through them (or just pick 1) to go to next Activity 
	
	protected void tearDown() {
		solo.finishOpenedActivities();
	}
}
