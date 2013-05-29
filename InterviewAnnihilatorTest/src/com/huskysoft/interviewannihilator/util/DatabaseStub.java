/**
 * This class represents a stub for the databse so that we can do integration
 * tests with a stub for the database, instead of the real thing
 * 
 * @author Dan Sanders, 5/28/2013
 */

package com.huskysoft.interviewannihilator.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.model.Solution;
import com.huskysoft.interviewannihilator.model.UserInfo;

public class DatabaseStub {

	/** List of questions in the stub database */
	private List<Question> questions;
	/** List of solutions in the stub database */
	private List<Solution> solutions;
	
	public DatabaseStub() {
		this.questions = new ArrayList<Question>();
		this.solutions = new ArrayList<Solution>();
	}
	
	public DatabaseStub(List<Question> questions, List<Solution> solutions) {
		this.questions = questions;
		this.solutions = solutions;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<Solution> getSolutions() {
		return solutions;
	}

	public void setSolutions(List<Solution> solutions) {
		this.solutions = solutions;
	}

	public boolean insertQuestion(Question q) {
		if (q != null) {
			questions.add(q);
			return true;
		}
		return false;
	}
	
	public boolean insertSolution(Solution s) {
		if (s != null) {
			solutions.add(s);
			return true;
		}
		return false;
	}
	
	public boolean deleteQuestion(int qId) {
		int index = questions.indexOf(qId);
		if (index < 0) {
			return false;
		}
		questions.remove(index);
		return true;
	}
	
	public boolean deleteSolution(int sId) {
		int index = solutions.indexOf(sId);
		if (index < 0) {
			return false;
		}
		solutions.remove(index);
		return true;
	}
	
	public void clearQuestions() {
		questions.clear();
	}
	
	public void clearSolutions() {
		solutions.clear();
	}
	
	public int numberOfQuestions() {
		return questions.size();
	}
	
	public int numberOfSolutions() {
		return solutions.size();
	}
	
	public List<Question> getFavoriteQuestions(UserInfo testInfo) {
		List<Question> results = new ArrayList<Question>();
		Map<Integer, Date> favorites = new LinkedHashMap<Integer, Date>
				(testInfo.getFavoriteQuestions());
		for (int i = 0; i < questions.size(); i++) {
			Question q = questions.get(i);
			if (favorites.containsKey(q.getQuestionId())) {
				results.add(q);
			}
		}
		return results;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((questions == null) ? 0 : questions.hashCode());
		result = prime * result
				+ ((solutions == null) ? 0 : solutions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatabaseStub other = (DatabaseStub) obj;
		if (questions == null) {
			if (other.questions != null)
				return false;
		} else if (!questions.equals(other.questions))
			return false;
		if (solutions == null) {
			if (other.solutions != null)
				return false;
		} else if (!solutions.equals(other.solutions))
			return false;
		return true;
	}
}
