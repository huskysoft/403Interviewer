/**
 * An enum describing the different difficulty levels a question can
 * be classified as
 * 
 * @author Dan Sanders, 4/29/13
 *
 */

package com.huskysoft.interviewannihilator.model;

import java.util.Locale;

public enum Difficulty {
	EASY, MEDIUM, HARD;
	
	/**
	 * Method used to help display the difficulty in 
	 * the correct language.
	 * @return
	 */
	public String translate(){
		if(Locale.getDefault().getLanguage().equals("es")){
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
