package com.huskysoft.interviewannihilator.ui;

import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.huskysoft.interviewannihilator.model.Difficulty;
import com.huskysoft.interviewannihilator.ui.MainActivity;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mActivity;
	private Spinner mSpinner;

	@SuppressWarnings("deprecation")
	public MainActivityTest() {
		super("com.huskysoft.interviewannihilator.ui", MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);

		mActivity = getActivity();

		mSpinner =
			(Spinner) mActivity.findViewById(
			com.huskysoft.interviewannihilator.R.id.diff_spinner);
	}
	
	/**
	 * Tests the preconditions for the MainActivity.
	 * 
	 * @label white-box test
	 */
	public void testPreConditions() {
		LinearLayout ll = (LinearLayout) mActivity.findViewById(com.huskysoft.interviewannihilator.R.id.question_layout);
		assertEquals(0, ll.getChildCount());
		assertEquals(4, mSpinner.getCount());
	}
	
	/**
	 * Tests the getCurrentDifficultySetting method.
	 * Checks to make sure the method returns null which
	 * represents the "all" selection.
	 * 
	 * @label white-box test
	 */
	public void testInitialDifficultySelection(){
		Difficulty diff = mActivity.getCurrentDifficultySetting();
		assertNull(diff);
	}
	
	public void testDifficultySelection(){
		Spinner s = (Spinner) mActivity.findViewById(com.huskysoft.interviewannihilator.R.id.diff_spinner);
		
		mActivity.runOnUiThread(
			new Runnable() {
				public void run() {
					mSpinner.requestFocus();
					mSpinner.setSelection(3);
				} // end of run() method definition
			} // end of anonymous Runnable object instantiation
		);
		
		this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
	    for (int i = 1; i <= 3; i++) {
	      this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
	    } // end of for loop

	    this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
	    
	    int mPos = mSpinner.getSelectedItemPosition();
	    String mSelection = (String) mSpinner.getItemAtPosition(mPos);

	    Difficulty diff = mActivity.getCurrentDifficultySetting();
	    String returnedDiff = diff.toString();
	    
	    assertEquals(mSelection.toUpperCase(), returnedDiff);
	}
	
	
}
