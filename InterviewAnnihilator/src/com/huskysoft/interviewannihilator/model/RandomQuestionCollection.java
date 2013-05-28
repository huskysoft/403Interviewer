/**
 * Singleton class that holds the list of RandomQuestions.
 * At any time, this list should hold at least one
 * question.
 * 
 * @author Phillip Leland
 */

package com.huskysoft.interviewannihilator.model;

import java.util.LinkedList;
import java.util.List;

public class RandomQuestionCollection {

	
	private static RandomQuestionCollection instance;
	private List<Question> questionList;	
	
	private RandomQuestionCollection(){
		questionList = new LinkedList<Question>();
	}
	
	/**
	 * Get the singleton RandomQuestionCollection instance.
	 * 
	 * @return
	 */
	public static RandomQuestionCollection getInstance() {
		if (instance == null) {
			instance = new RandomQuestionCollection();
		}
		return instance;
	}
	
	public int getSize(){
		return questionList.size();
	}
	
	public boolean isEmpty(){
		return questionList.isEmpty();
	}
	
	public Question getQuestion(){	
		if(questionList.isEmpty()){
			return null;
		}
		Question q = questionList.remove(0);
		return q;
	}
	
	/**
	 * Appends the param list to the list of random 
	 * questions. Called by FetchRandomQuestionsTask.
	 * 
	 * @param newQuestions
	 */
	public void appendList(List<Question> newQuestions){
		questionList.addAll(newQuestions);
	}
	
}
