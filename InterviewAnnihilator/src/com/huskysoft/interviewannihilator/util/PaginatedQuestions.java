package com.huskysoft.interviewannihilator.util;

import com.huskysoft.interviewannihilator.model.Question;
import java.util.List;

/** A PaginatedResults class that stores a list of questions */
public class PaginatedQuestions extends PaginatedResults {
	
	/** List of the questions returned */
	private List<Question> questions;
	
	PaginatedQuestions(List<Question> questions, int totalNumberOfResults, 
			int limit, int offset) {
		super(totalNumberOfResults, limit, offset);
		this.questions = questions;
	}
	
	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

}
