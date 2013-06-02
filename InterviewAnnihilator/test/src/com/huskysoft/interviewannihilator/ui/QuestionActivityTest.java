/**
 * Tests for the QuestionActivity
 * 
 * @author Cody Andrews, 07/01/2013
 */

package com.huskysoft.interviewannihilator.ui;

import java.util.ArrayList;
import java.util.List;

import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.model.Solution;
import com.huskysoft.interviewannihilator.ui.QuestionActivity;
import com.huskysoft.interviewannihilator.util.TestHelpers;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuestionActivityTest extends
ActivityInstrumentationTestCase2<QuestionActivity> {

	private QuestionActivity mActivity;
	private Question question;
	
	/**
	 * Construct new test instance
	 *
	 * @param name the test name
	 */
	public QuestionActivityTest() {
		super(QuestionActivity.class);
	}

	/**
	 * Perform pre-test initialization
	 *
	 * @throws Exception
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		setActivityInitialTouchMode(false);
		
		// Set up a question that would be displayed
		question = TestHelpers.createDummyQuestion(0);
		Intent intent = new Intent();
		intent.putExtra("com.huskysoft.interviewannihilator.QUESTION",
				question);
		setActivityIntent(intent);
		
		mActivity = getActivity();
	}
	
	/**
	 * Tests the preconditions for QuestionActivity
	 * 
	 * @label white-box test
	 */
	public void testPreConditions() {
		// test that question view is populated
		TextView questionView = (TextView) mActivity.findViewById(
				com.huskysoft.interviewannihilator.R.id.question_text_view);
		assertNotNull(questionView.getText());
				
		// test solutions are initially empty
		LinearLayout ll = (LinearLayout) mActivity.findViewById(
				com.huskysoft.interviewannihilator.R.id.
				question_layout_solutions);
		assertEquals(0, ll.getChildCount());
	}
	
	/**
	 * Tests the addSolutionList method with null parameters
	 * 
	 * @label whitebox
	 */
	@UiThreadTest
	public void testAddSolutionListNull(){
		// Clear current solutions
		ViewGroup solutionView = (ViewGroup) mActivity.findViewById(
				com.huskysoft.interviewannihilator.R.id.
				question_layout_solutions);
		solutionView.removeAllViews();
		
		mActivity.addSolutionList(null);
		
		assertEquals(1, solutionView.getChildCount());

		TextView t = (TextView) solutionView.getChildAt(0);
		String message = (String) t.getText();
		String expected = "There doesn't seem to be any solutions";

		assertEquals(expected, message);
	}
	
	/**
	 * Tests the addSolutionList method with null parameters
	 * 
	 * @label whitebox
	 */
	@UiThreadTest
	public void testAddSolutionListMultiple(){
		final int numSolutions = 3;
		
		// Clear current solutions
		ViewGroup solutionView = (ViewGroup) mActivity.findViewById(
				com.huskysoft.interviewannihilator.R.id.
				question_layout_solutions);
		solutionView.removeAllViews();
		
		List<Solution> solutionList = new ArrayList<Solution>();
		for(int i = 0; i < numSolutions; i++){
			solutionList.add(TestHelpers.createDummySolution(i));
		}
		
		mActivity.addSolutionList(solutionList);
		
		assertEquals(numSolutions, solutionView.getChildCount());
	}

}