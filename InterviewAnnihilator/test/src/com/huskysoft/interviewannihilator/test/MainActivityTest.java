/**
 * Tests for the MainActivity. Checks difficulty, categories,
 * adding of questions.
 * 
 * @author Phillip Leland
 */

package com.huskysoft.interviewannihilator.test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.huskysoft.interviewannihilator.R;
import com.huskysoft.interviewannihilator.model.Category;
import com.huskysoft.interviewannihilator.model.Difficulty;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.ui.MainActivity;

public class MainActivityTest extends
ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mActivity;
	private Spinner mSpinner;
	private TableLayout mTable;

	public MainActivityTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);

		mActivity = getActivity();

		mSpinner =
				(Spinner) mActivity.findViewById(
				com.huskysoft.interviewannihilator.R.id.diff_spinner);

		mTable = (TableLayout) mActivity.findViewById(
				com.huskysoft.interviewannihilator.R.id.slide_table);
	}

	/**
	 * Tests the preconditions for the MainActivity.
	 * 
	 * @label white-box test
	 */
	public void testPreConditions() {
		LinearLayout ll = (LinearLayout) mActivity.findViewById(
				com.huskysoft.interviewannihilator.R.id.question_layout);
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

	/**
	 * Tests the getCurrentDifficultySetting method.
	 * 
	 * Selects a different difficulty and tests to see if the
	 * method reports the change.
	 * 
	 * @label white-box test
	 */
	public void testDifficultySelection(){
		mActivity.runOnUiThread(
				new Runnable() {
					public void run() {
						mSpinner.requestFocus();
						mSpinner.setSelection(3);
					} // end of run() method definition
				} // end of anonymous Runnable object instantiation
				);

		this.getInstrumentation().waitForIdleSync(); // added

		this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
		for (int i = 1; i <= 3; i++) {
			this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
		} // end of for loop

		this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);

		int mPos = mSpinner.getSelectedItemPosition();
		String mSelection = (String) mSpinner.getItemAtPosition(mPos);

		Difficulty diff = mActivity.getCurrentDifficultySetting();
		String returnedDiff = diff.toString();

		assertEquals(mSelection.toUpperCase(Locale.getDefault()), returnedDiff);
	}

	/**
	 * Tests the setDifficulty method of MainActivity. Makes sure
	 * it selects the correct difficulty enum.
	 * 
	 * @label white-box
	 */
	@UiThreadTest
	public void testSetDifficultyEnum(){
		mActivity.setDifficultyToSelectedValue("HARD");

		Difficulty diff = mActivity.getCurrentDifficultySetting();
		String returnedDiff = diff.toString();

		assertEquals("HARD", returnedDiff.toUpperCase(Locale.getDefault()));

	}

	/**
	 * Test that tests that setDifficulty displays the correct
	 * value in the spinner.
	 * 
	 * @label white box
	 */
	@UiThreadTest
	public void testSetDifficultyDisplayed(){
		mActivity.setDifficultyToSelectedValue("HARD");

		int mPos = mSpinner.getSelectedItemPosition();
		String mSelection = (String) mSpinner.getItemAtPosition(mPos);

		assertEquals("HARD", mSelection.toUpperCase(Locale.getDefault()));
	}

	/**
	 * Test that checks the setting of one category. One category passed
	 * in a list to the setCategorySpinner method. Checks to see if 
	 * that the correct number of spinners is displayed.
	 * 
	 * @label white box
	 */
	@UiThreadTest
	public void testSetCategorySingleNumSpinners(){
		List<Category> cats = new LinkedList<Category>();
		cats.add(Category.COMPSCI);

		mActivity.setCategorySpinners(cats);

		int tableChildren = mTable.getChildCount();

		assertEquals(3, tableChildren);
	}

	/**
	 * Test that checks the setting of multiple categorys. 
	 * List of category passed in a list to the
	 * setCategorySpinner method. Checks to see if 
	 * that the correct number of spinners is displayed.
	 * 
	 * @label white box
	 */
	@UiThreadTest
	public void testSetCategoryMultipleNumSpinners(){
		List<Category> cats = new LinkedList<Category>();

		cats.add(Category.COMPSCI);
		cats.add(Category.BUSINESS);

		mActivity.setCategorySpinners(cats);

		int tableChildren = mTable.getChildCount();

		assertEquals(4, tableChildren);
	}

	/**
	 * Sets one category spinner. Checks to see that
	 * string value is pre-selected.
	 * 
	 * @label white box
	 */
	@UiThreadTest
	public void testSetCategorySingleSpinnerValue(){
		List<Category> cats = new LinkedList<Category>();

		cats.add(Category.COMPSCI);
		cats.add(Category.BUSINESS);

		mActivity.setCategorySpinners(cats);

		TableRow r = (TableRow) mTable.getChildAt(1);
		Spinner s = (Spinner) r.getChildAt(1);
		int mPos = s.getSelectedItemPosition();
		String mSelection = (String) s.getItemAtPosition(mPos);

		assertEquals("COMPSCI", mSelection.toUpperCase(Locale.getDefault()));
	}


	/**
	 * Sends a null list to AppendQuestionsToView and
	 * checks to make sure the correct message is displayed.
	 * 
	 * @label whitebox
	 */
	public void testAddQuestionListNullList(){
		// Clear current questions
		ViewGroup questionView =
				(ViewGroup) mActivity.findViewById(
				com.huskysoft.interviewannihilator.R.id.question_layout);

		questionView.removeAllViews();

		mActivity.addQuestionList(null);

		assertEquals(1, questionView.getChildCount());

		TextView t = (TextView) questionView.getChildAt(0);
		String message = (String) t.getText();
		String expected = mActivity.getString(R.string.no_questions_found);

		assertEquals(expected, message);
	}

	/**
	 * Populates the view with one question by using appendQuestionsToView.
	 * Makes sure that question is displayed.
	 * 
	 * @label whitebox
	 */
	public void testAddQuestionListToViewOne(){
		// Clear current questions
		ViewGroup questionView =
				(ViewGroup) mActivity.findViewById(
				com.huskysoft.interviewannihilator.R.id.question_layout);

		questionView.removeAllViews();

		Question test = new Question(
				"test", "test", Category.COMPSCI, Difficulty.EASY);
		test.setDateCreated(new Date());
		List<Question> q = new LinkedList<Question>();
		q.add(test);
		mActivity.addQuestionList(q);

		assertEquals(1, questionView.getChildCount());		
	}
}
