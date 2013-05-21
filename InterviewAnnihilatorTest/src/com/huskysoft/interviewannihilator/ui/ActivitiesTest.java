/**
 * Testing UI with Robotium
 * 
 * @author Victoria Wagner 5/18/2013
 */

package com.huskysoft.interviewannihilator.ui;
import com.huskysoft.interviewannihilator.ui.MainActivity;
import com.huskysoft.interviewannihilator.ui.PostSolutionActivity;
import com.huskysoft.interviewannihilator.ui.QuestionActivity;

import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.jayway.android.robotium.solo.Solo;

@SuppressLint("NewApi")
public class ActivitiesTest 
	extends ActivityInstrumentationTestCase2<MainActivity> {
	public ActivitiesTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}

	// Solo object for Robotium
	private Solo solo;
	
	/**
	 * Setting up Robotium
	 */
	protected void setUp() {
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	/**
	 * Black Box Testing
	 */
	public void testAssertActivity() {
		solo.assertCurrentActivity("check current activity",
				MainActivity.class);
	}
	
	/**
	 * Black Box Testing
	 */
	public void testClickQuestion() {
		TextView button = solo.getText(3);
		// TextView button = solo.getText("GDP");
		button.callOnClick();
		solo.assertCurrentActivity("checking going to question",
				QuestionActivity.class);
	}
	
	/**
	 * Black Box Testing
	 */
	public void testGoToQuestionAndBackToMainActivity() {
		TextView button = solo.getText(3);
		button.callOnClick();
		solo.assertCurrentActivity("checking going to question",
				QuestionActivity.class);
		solo.goBack();
		solo.assertCurrentActivity("checking going to MainActivity",
				MainActivity.class);
	}
	
	/**
	 * Black Box Testing
	 */
	public void testGoToShowSolution() {
		TextView button = solo.getText(3);
		button.callOnClick();
		solo.clickOnButton("Solutions");
		solo.assertCurrentActivity("stays at QuestionActivity",
				QuestionActivity.class);
	}
	
	/**
	 * Black Box Testing
	 */
	public void testViewSolutions() {
		String solnText = "I would recommend that we perform an on-site " +
			"test, by hiring both candidates on a freelance basis for " +
			"two weeks each.";
		TextView button = solo.getText("Hiring Dilemna");
		button.callOnClick();
		assertFalse(solo.searchText(solnText, true));
		solo.clickOnButton("Solutions");
		assertTrue(solo.searchText(solnText));
	}
	
	/**
	 * Black Box Testing
	 */
	public void testPostSolutionActivity() {
		solo.assertCurrentActivity("on MainActivity", MainActivity.class);
		TextView button = solo.getText(3);
		button.callOnClick();
		solo.assertCurrentActivity("on QuestionActivity",
				QuestionActivity.class);
		solo.clickOnButton("Solutions");
		solo.assertCurrentActivity("still on QuestionActivity",
				QuestionActivity.class);
		solo.clickOnButton("Submit a Solution");
		solo.assertCurrentActivity("on PostSolutionActivity",
				PostSolutionActivity.class);
	}
	
	/**
	 * Black Box Testing
	 */
	public void testSlideInMenu() {
		// TODO: figure out how to "click" to open the slide in menu
	}
	
	/** 
	 * Cleans up, finished using Robotium
	 */
	protected void tearDown() {
		solo.finishOpenedActivities();
	}
	
	
}
