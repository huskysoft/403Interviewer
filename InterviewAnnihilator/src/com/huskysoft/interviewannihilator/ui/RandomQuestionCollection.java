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
	 * Get the singleton SlideMenuInfoTransfer instance.
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
	
	public void appendList(List<Question> newQuestions){
		questionList.addAll(newQuestions);
	}
	
}
