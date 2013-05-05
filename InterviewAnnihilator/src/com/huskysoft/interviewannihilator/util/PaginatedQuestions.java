/**
 * A PaginatedResults class that stores a list of questions
 * 
 * @author Dan Sanders, 05/04/2013
 */

package com.huskysoft.interviewannihilator.util;

import com.huskysoft.interviewannihilator.model.Question;

import java.util.ArrayList;
import java.util.List;

public class PaginatedQuestions extends PaginatedResults {
	
	/** List of the questions returned */
	private List<Question> questions;
	
	public PaginatedQuestions() {
		super();
		this.questions = new ArrayList<Question>();
	};
	
	public PaginatedQuestions(List<Question> questions, 
	int totalNumberOfResults, int limit, int offset) {
		super(totalNumberOfResults, limit, offset);
		this.questions = questions;
	}
	
	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	@Override
	public String toString() {
		return "PaginatedQuestions [questions=" + questions + "]";
	}

}
