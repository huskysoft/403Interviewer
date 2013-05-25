/**
 * Singleton class that holds the list of RandomQuestions.
 * At any time, this list should hold at least one
 * question.
 * 
 * @author Phillip Leland
 */

package com.huskysoft.interviewannihilator.ui;

import java.util.LinkedList;
import java.util.List;

import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.runtime.FetchRandomQuestionsTask;

public class RandomQuestionCollection {

	
	private static RandomQuestionCollection instance;
	private List<Question> questionList;
	
	
	private RandomQuestionCollection(){
		questionList = new LinkedList<Question>();
		load();
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
	
	/**
	 * Called at beginning of every activity method.
	 * Makes sure that there are random questions
	 * loaded into the list.
	 */
	public void load(){
		if(questionList.isEmpty()){
			// Get new questions
			new FetchRandomQuestionsTask().execute();
		}
	}
	
	public int getSize(){
		return questionList.size();
	}
	
	public Question getQuestion(){
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
