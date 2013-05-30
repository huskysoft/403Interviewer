/**
 * Class that helps transfer the state of the Slide Menu between activities. 
 * SlideMenuInfoTransfer implements the singleton pattern.
 * 
 * @author Phil Leland, 5/15/13
 */
package com.huskysoft.interviewannihilator.ui;

import java.util.LinkedList;
import java.util.List;

import com.huskysoft.interviewannihilator.model.Category;
import com.huskysoft.interviewannihilator.model.Difficulty;

public class SlideMenuInfo {
	/**
	 * The percentage of the screen that is not covered up by the slide
	 * in menu.
	 */
	public static final double SLIDE_MENU_WIDTH = .25;
	
	private static SlideMenuInfo instance;
	private Difficulty diff;
	private List<Category> cat;
	
	private SlideMenuInfo() {	
		cat = new LinkedList<Category>();
	}
	
	/**
	 * Get the singleton SlideMenuInfoTransfer instance.
	 * 
	 * @return
	 */
	protected static SlideMenuInfo getInstance() {
		if (instance == null) {
			instance = new SlideMenuInfo();
		}
		return instance;
	}

	public Difficulty getDiff() {
		return diff;
	}

	public void setDiff(Difficulty diff) {
		this.diff = diff;
	}

	public List<Category> getCat() {
		return cat;
	}

	public void addCat(Category newCat) {
		this.cat.add(newCat);
	}
	
	public void clearCat(){
		this.cat.clear();
	}

	public void setCat(List<Category> currentCategories) {
		this.cat = currentCategories;
	}
	
}
