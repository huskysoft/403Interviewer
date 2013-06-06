/**
 * An enum describing the different difficulty levels a question can
 * be classified as
 * 
 * @author Dan Sanders, 4/29/13
 *
 */

package com.huskysoft.interviewannihilator.model;

import java.util.Locale;

import com.huskysoft.interviewannihilator.util.UIConstants;

public enum Difficulty {
	EASY, MEDIUM, HARD;
	
	/**
	 * Method used to help display the difficulty in 
	 * the correct language.
	 * @return
	 */
	public String toString(Locale locale){
		if(locale.getLanguage().equals(UIConstants.SPANISH_CODE)){
			switch(this) {
				case EASY:
					return "Fácil";
				case MEDIUM:
					return "Medio";
				case HARD:
					return "Duro";
				default:
					return this.toString();
			}
		}else{
			return this.toString();
		}
	}
}
