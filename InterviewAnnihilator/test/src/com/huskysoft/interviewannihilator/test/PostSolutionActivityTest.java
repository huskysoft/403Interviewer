/**
 * A test case for PostSolutionActivity.
 * 
 * @author Kevin Loh, 6/1/2013
 */

package com.huskysoft.interviewannihilator.test;

import com.huskysoft.interviewannihilator.R;
import com.huskysoft.interviewannihilator.ui.PostSolutionActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

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
		
		// Sets up the elements on the post solution page
		mActivity = getActivity();
		mSolution = (EditText) mActivity.findViewById(R.id.edit_solution);
		mPost = (Button) mActivity.findViewById(R.id.send_solution);
	}
	
	public void testPreCondition() {
		// Checks that the solution text box and post button is shown
		assertTrue(mSolution.isShown());
		assertTrue(mPost.isShown());
		
		// Checks that the solution text box is empty
		assertEquals("", mSolution.getText().toString());

		// Checks that the post button is not grayed out
		assertTrue(mPost.isEnabled());
	}
	
	
}
