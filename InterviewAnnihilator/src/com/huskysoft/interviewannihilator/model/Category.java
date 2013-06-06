/**
 * An enum describing all the different categories a question can
 * be classified as
 * 
 * @author Dan Sanders, 4/29/13
 *
 */

package com.huskysoft.interviewannihilator.model;

import java.util.Locale;

import com.huskysoft.interviewannihilator.util.UIConstants;

public enum Category {
	COMPSCI, BUSINESS, MANAGEMENT, LOGIC, ESTIMATION, BRAINTEASER,
	GAME, MATH, SCIENCE;
	
	/**
	 * Method to display the category in
	 * the correct language.
	 * @return
	 */
	public String toString(Locale locale){
		if(locale.getLanguage().equals(UIConstants.SPANISH_CODE)){
			switch(this) {
				case COMPSCI:
					return "Informática";
				case BUSINESS:
					return "Negocios";
				case MANAGEMENT:
					return "Administración";
				case LOGIC:
					return "Lógica";
				case ESTIMATION:
					return "Clasificación";
				case BRAINTEASER:
					return "Rompecabezas";
				case GAME:
					return "Juego";
				case MATH:
					return "Matemáticas";
				case SCIENCE:
					return "Ciencia";
				default:
					return this.toString();
			}
		}else{
			return this.toString();
		}
	}
}
