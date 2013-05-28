/**
 * Class that helps transfer the state of the Slide Menu between activities. 
 * SlideMenuInfoTransfer implements the singleton pattern.
 * 
 * @author Phil Leland, 5/15/13
 */
package com.huskysoft.interviewannihilator.ui;

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
	private Category cat;
	
	private SlideMenuInfo() {	
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

	public Category getCat() {
		return cat;
	}

	public void setCat(Category cat) {
		this.cat = cat;
	}
	
}
