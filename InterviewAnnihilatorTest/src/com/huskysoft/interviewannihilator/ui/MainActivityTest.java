package com.huskysoft.interviewannihilator.ui;

import com.huskysoft.interviewannihilator.ui.MainActivity;
import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	public MainActivityTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	private Solo solo;
	
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testAssertActivity() {
		solo.assertCurrentActivity("check current activity",
				MainActivity.class);
	}

}
