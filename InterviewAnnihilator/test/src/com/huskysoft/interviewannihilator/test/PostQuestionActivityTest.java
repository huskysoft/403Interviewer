/**
 * A test case for PostQuestionActivity.
 * 
 * @author Kevin Loh, 5/30/2013
 */

package com.huskysoft.interviewannihilator.test;

import com.huskysoft.interviewannihilator.R;
import com.huskysoft.interviewannihilator.ui.PostQuestionActivity;

import android.app.Dialog;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class PostQuestionActivityTest extends
		ActivityInstrumentationTestCase2<PostQuestionActivity> {
	
	/** Number of categories to choose from when posting question. */
	private static final int CATEGORY_COUNT = 10;
	
	/** The activity for post question. */
	private PostQuestionActivity mActivity;
	
	/** The title text field. */
	private EditText mTitle;
	
	/** The question text field. */
	private EditText mQuestion;
	
	/** The difficulty radio group. */
	private RadioGroup mDifficulty;
	
	/** The Easy radio button. */
	private RadioButton mEasy;
	
	/** The Medium radio button. */
	private RadioButton mMedium;
	
	/** The Hard radio button. */
	private RadioButton mHard;
	
	/** The solution text field. */
	private EditText mSolution;
	
	/** The category spinner. */
	private Spinner mCategory;
	
	/** The post button. */
	private Button mPost;

	public PostQuestionActivityTest() {
		super(PostQuestionActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		
		// Sets up all the elements on the post question page
		mActivity = getActivity();
		mTitle = (EditText) mActivity.findViewById(R.id.edit_question_title);
		mQuestion = (EditText) mActivity.findViewById(R.id.edit_question);
		mDifficulty = (RadioGroup) mActivity.findViewById(R.id.goup_difficulty);
		mEasy = (RadioButton) mActivity.findViewById(R.id.difficulty_easy);
		mMedium = (RadioButton) mActivity.findViewById(R.id.difficulty_medium);
		mHard = (RadioButton) mActivity.findViewById(R.id.difficulty_hard);
		mSolution = (EditText) mActivity.findViewById(R.id.edit_solution_q);
		mCategory = (Spinner) mActivity.findViewById(
				R.id.category_spinner_question);
		mPost = (Button) mActivity.findViewById(R.id.send_question);
	}
	
	/**
	 * Tests that the page is loaded properly, showing all elements with
	 * correct initial values.
	 * 
	 * @label black-box test
	 */
	public void testPreCondition() {
		// Checks that all elements are shown
		assertTrue(mTitle.isShown());
		assertTrue(mQuestion.isShown());
		assertTrue(mDifficulty.isShown());
		assertTrue(mEasy.isShown());
		assertTrue(mMedium.isShown());
		assertTrue(mHard.isShown());
		assertTrue(mSolution.isShown());
		assertTrue(mCategory.isShown());
		assertTrue(mPost.isShown());
		
		assertEquals("", mTitle.getText().toString());
		assertEquals("", mQuestion.getText().toString());
		assertEquals("", mSolution.getText().toString());
		assertEquals(CATEGORY_COUNT, mCategory.getCount());

		assertTrue(mEasy.isChecked());
		assertTrue(mPost.isEnabled());
	}
	
	/** 
	 * Tests displayMessage displays the successful dialog when the post is
	 * successful.
	 * 
	 * @label white-box test
	 */
	public void testDisplayMessageSuccessfulPost() {
		String successDialogTitle =
				mActivity.getString(R.string.successDialog_title);
		Dialog dialog = mActivity.displayMessage(1, successDialogTitle);
		TextView text = (TextView) dialog.findViewById(R.id.dialog_text_alert);
		assertEquals(successDialogTitle, text.getText().toString());
	}
	
	/** 
	 * Tests displayMessage displays the successful dialog when the post is
	 * successful, ignoring the value of the string passed.
	 * 
	 * @label white-box test
	 */
	public void testDisplayMessageSuccessfulPostIgnoresMessage() {
		String successDialogTitle =
				mActivity.getString(R.string.successDialog_title);
		Dialog dialog = mActivity.displayMessage(1, "test");
		TextView text = (TextView) dialog.findViewById(R.id.dialog_text_alert);
		assertEquals(successDialogTitle, text.getText().toString());
	}
	
	/** 
	 * Tests displayMessage displays the given string when the post is
	 * unsuccessful.
	 * 
	 * @label white-box test
	 */
	public void testDisplayMessageUnsuccessfulPostEmptyString() {
		String message = "";
		Dialog dialog = mActivity.displayMessage(0, message);
		TextView text = (TextView) dialog.findViewById(R.id.dialog_text_alert);
		assertEquals(message, text.getText().toString());
	}
	
	/** 
	 * Tests displayMessage displays the given string when the post is
	 * unsuccessful.
	 * 
	 * @label white-box test
	 */
	public void testDisplayMessageUnsuccessfulPostRandomString() {
		String message = "test";
		Dialog dialog = mActivity.displayMessage(0, message);
		TextView text = (TextView) dialog.findViewById(R.id.dialog_text_alert);
		assertEquals(message, text.getText().toString());
	}
	
	/** 
	 * Tests displayMessage displays the retry dialog when a network error
	 * has occurred.
	 * 
	 * @label white-box test
	 */
	public void testDisplayMessageNetworkError() {
		String retryDialogTitle =
				mActivity.getString(R.string.retryDialog_title);
		Dialog dialog = mActivity.displayMessage(-1, retryDialogTitle);
		TextView text = (TextView) dialog.findViewById(R.id.dialog_text);
		assertEquals(retryDialogTitle, text.getText().toString());
	}
	
	/** 
	 * Tests displayMessage displays the retry dialog when a network error
	 * has occurred, ignoring the value of the string given.
	 * 
	 * @label white-box test
	 */
	public void testDisplayMessageNetworkErrorIgnoresMessage() {
		String retryDialogTitle =
				mActivity.getString(R.string.retryDialog_title);
		Dialog dialog = mActivity.displayMessage(-1, "test");
		TextView text = (TextView) dialog.findViewById(R.id.dialog_text);
		assertEquals(retryDialogTitle, text.getText().toString());
	}
	
	/**
	 * Tests that only the Easy difficulty is selected when given the
	 * corresponding user input.
	 * 
	 * @label black-box test
	 */
	public void testSelectEasyDifficulty() {
		mActivity.runOnUiThread(
				new Runnable() {
					public void run() {
						mEasy.requestFocus();
					}
				}
			);
		this.getInstrumentation().waitForIdleSync();
		
		this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
		
		assertTrue(mEasy.isChecked());
		assertFalse(mMedium.isChecked());
		assertFalse(mHard.isChecked());
	}

	/**
	 * Tests that only the Easy difficulty is selected when given the
	 * corresponding user input.
	 * 
	 * @label black-box test
	 */
	public void testSelectMediumDifficulty() {
		mActivity.runOnUiThread(
				new Runnable() {
					public void run() {
						mEasy.requestFocus();
					}
				}
			);
		this.getInstrumentation().waitForIdleSync();
		
		this.sendKeys(KeyEvent.KEYCODE_DPAD_RIGHT);
		this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
		
		assertFalse(mEasy.isChecked());
		assertTrue(mMedium.isChecked());
		assertFalse(mHard.isChecked());
	}

	/**
	 * Tests that only the Easy difficulty is selected when given the
	 * corresponding user input.
	 * 
	 * @label black-box test
	 */
	public void testSelectHardDifficulty() {
		mActivity.runOnUiThread(
				new Runnable() {
					public void run() {
						mEasy.requestFocus();
					}
				}
			);
		this.getInstrumentation().waitForIdleSync();
		
		this.sendKeys(KeyEvent.KEYCODE_DPAD_RIGHT);
		this.sendKeys(KeyEvent.KEYCODE_DPAD_RIGHT);
		this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
		
		assertFalse(mEasy.isChecked());
		assertFalse(mMedium.isChecked());
		assertTrue(mHard.isChecked());
	}
}
