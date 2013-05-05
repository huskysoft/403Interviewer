/**
 * A PaginatedResults class that stores a list of solutions
 * 
 * @author Dan Sanders, 05/04/2013
 */

package com.huskysoft.interviewannihilator.util;

import com.huskysoft.interviewannihilator.model.Solution;

import java.util.ArrayList;
import java.util.List;

public class PaginatedSolutions extends PaginatedResults {
	
	/** List of the solutions returned */
	private List<Solution> solutions;
	
	public PaginatedSolutions() {
		super();
		this.solutions = new ArrayList<Solution>();
	}
	
	public PaginatedSolutions(List<Solution> solutions, 
	int totalNumberOfResults, int limit, int offset) {
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
