/**
 * Tests for the AbstractPostingActivity. 
 * Uses the MainActivity because AbstractPostingActivty
 * cannot be run on its own. 
 * 
 * @author Phillip Leland
 */

package com.huskysoft.interviewannihilator.test;

import java.util.LinkedList;
import java.util.List;

import com.huskysoft.interviewannihilator.model.Category;
import com.huskysoft.interviewannihilator.ui.MainActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

public class AbstractPostingActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {
	
	MainActivity mActivity;
	
	Spinner mSpinner;
	
	TableLayout mTable;
	
	public AbstractPostingActivityTest() {
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
	 * Tests the getCurrentCategories method at start
	 * of activity. Should return empty list.
	 * 
	 * @label black-box
	 */
	@UiThreadTest
	public void testGetCategoriesStart(){
		List<Category> cats = mActivity.getCurrentCategories();
		assertEquals(0, cats.size());
	}
	
	/**
	 * Tests the getCategories method when one category
	 * is selected. 
	 * 
	 * @label white-box
	 */
	@UiThreadTest
	public void testGetCategoriesSingle(){
		List<Category> cats = new LinkedList<Category>();
		cats.add(Category.BRAINTEASER);
		
		mActivity.setCategorySpinners(cats);
		
		List<Category> abstractCats = mActivity.getCurrentCategories();
		
		assertEquals(1, abstractCats.size());
		assertEquals(cats, abstractCats);
	}
	
	/**
	 * Tests the getCategories method when multiple categories
	 * are selected.
	 * 
	 * @label white-box
	 */
	@UiThreadTest
	public void testGetCategoriesMultiple(){
		List<Category> cats = new LinkedList<Category>();
		cats.add(Category.BRAINTEASER);
		cats.add(Category.COMPSCI);
		
		mActivity.setCategorySpinners(cats);
		
		List<Category> abstractCats = 
				mActivity.getCurrentCategories();
		
		assertEquals(2, abstractCats.size());
		assertEquals(cats, abstractCats);
	}
	
	/**
	 * Test addCategory method. Makes sure spinner is added
	 * and remove button is visible.
	 * 
	 * @label white-box
	 */
	@UiThreadTest
	public void testAddCategory(){
		assertEquals(3, mTable.getChildCount());
		
		mActivity.addCategory(mTable);
		
		assertEquals(4, mTable.getChildCount());
		
		TableRow tr = (TableRow) mTable.getChildAt(2);
		Spinner s = (Spinner) tr.getChildAt(1);
		
		int mPos = s.getSelectedItemPosition();
		String mSelection = (String) s.getItemAtPosition(mPos);
		
		assertEquals("All", mSelection);
		
		Button b = (Button) mActivity.findViewById(
				com.huskysoft.interviewannihilator.
				R.id.remove_category_button);
		
		assertEquals(View.VISIBLE, b.getVisibility()); 
	}
	
	/**
	 * Test Remove category when there is only
	 * one category. Should not remove anything.
	 * 
	 * @label white-box
	 */
	@UiThreadTest
	public void testRemoveCategoryOne(){
		mActivity.removeCategory(mTable);
		assertEquals(3, mTable.getChildCount());
		Button b = (Button) mActivity.findViewById(
				com.huskysoft.interviewannihilator.
				R.id.remove_category_button);
		
		assertEquals(View.GONE, b.getVisibility()); 
	}
	
	/**
	 * Remove category when there are 2 categories. 
	 * Should remove one and remove button.
	 * 
	 * @label white-box
	 */
	@UiThreadTest
	public void testRemoveCategoryMultiple(){
		mActivity.addCategory(mTable);
		assertEquals(4, mTable.getChildCount());
		
		Button b = (Button) mActivity.findViewById(
				com.huskysoft.interviewannihilator.
				R.id.remove_category_button);
		
		assertEquals(View.VISIBLE, b.getVisibility()); 
		
		mActivity.removeCategory(mTable);
		assertEquals(3, mTable.getChildCount());
		assertEquals(View.GONE, b.getVisibility());
		
	}
}
