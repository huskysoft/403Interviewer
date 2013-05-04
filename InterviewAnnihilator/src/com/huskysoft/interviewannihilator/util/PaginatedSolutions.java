package com.huskysoft.interviewannihilator.util;

import com.huskysoft.interviewannihilator.model.Solution;
import java.util.List;

/** A PaginatedResults class that stores a list of solutions */
public class PaginatedSolutions extends PaginatedResults {
	
	/** List of the solutions returned */
	private List<Solution> solutions;
	
	PaginatedSolutions(List<Solution> solutions, int totalNumberOfResults, 
			int limit, int offset) {
		super(totalNumberOfResults, limit, offset);
		this.solutions = solutions;
	}
	
	public List<Solution> getSolutions() {
		return solutions;
	}

	public void setSolutions(List<Solution> solutions) {
		this.solutions = solutions;
	}

	@Override
	public String toString() {
		return "PaginatedSolutions [solutions=" + solutions + "]";
	}

}
