/**
 * Class that helps transfer the state of the Slide Menu
 * between activities.
 */
package com.huskysoft.interviewannihilator.ui;

public class SlideMenuInfoTransfer {
	/**
	 * The percentage of the screen that is not covered up by the slide
	 * in menu.
	 */
	public static final double SLIDE_MENU_WIDTH = .25;
	
	/**
	 * Used to pass a selected difficulty back to the 
	 * MainActivity class through the slide-menu.
	 */
	public static String difficultyMessage = "";
}
