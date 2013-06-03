/**
 * A test case for PostSolutionActivity.
 * 
 * @author Kevin Loh, 6/1/2013
 */

package com.huskysoft.interviewannihilator.test;

import com.huskysoft.interviewannihilator.R;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.ui.PostSolutionActivity;
import com.huskysoft.interviewannihilator.ui.QuestionActivity;
import com.huskysoft.interviewannihilator.util.TestHelpers;

import android.app.Dialog;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PostSolutionActivityTest extends
		ActivityInstrumentationTestCase2<PostSolutionActivity> {
	
	private PostSolutionActivity mActivity;
	
	private EditText mSolution;
	
	private Button mPost;

	public PostSolutionActivityTest() {
		super(PostSolutionActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		// prepare mock Intent
		Intent intent = new Intent();
		Question question = TestHelpers.createDummyQuestion(0);
		intent.putExtra(QuestionActivity.EXTRA_MESSAGE, question);
		setActivityIntent(intent);
		
		// fetch elements from PostSolutionActivity
		mActivity = getActivity();
		mSolution = (EditText) mActivity.findViewById(R.id.edit_solution);
		mPost = (Button) mActivity.findViewById(R.id.send_solution);
	}
	
	/**
	 * Check that the PostSolutionActivity is initialized correctly.
	 * 
	 * @label black-box test
	 */
	public void testPreCondition() {
		// Checks that the solution text box and post button is shown
		assertTrue(mSolution.isShown());
		assertTrue(mPost.isShown());
		
		// Checks that the solution text box is empty
		assertEquals("", mSolution.getText().toString());

		// Checks that the post button is not grayed out
		assertTrue(mPost.isEnabled());
	}
	
	public void testDisplayMessageSuccessfulPost() {
		Dialog dialog = mActivity.displayMessage(1);
		TextView text = (TextView) dialog.findViewById(R.id.dialog_text_alert);
		assertEquals(mActivity.getString(R.string.successDialog_title),
				text.getText().toString());
	}
	
	public void testDisplayMessageUnsuccessfulPost() {
		Dialog dialog = mActivity.displayMessage(0);
		TextView text = (TextView) dialog.findViewById(R.id.dialog_text_alert);
		assertEquals(mActivity.getString(R.string.badInputDialog_solution),
				text.getText().toString());
	}
	
	public void testDisplayMessageNetworkError() {
		Dialog dialog = mActivity.displayMessage(-1);
		TextView text = (TextView) dialog.findViewById(R.id.dialog_text);
		assertEquals(mActivity.getString(R.string.retryDialog_title),
				text.getText().toString());
	}
}
