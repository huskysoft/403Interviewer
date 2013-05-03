package com.huskysoft.interviewannihilator.service;

import java.util.Collection;

import org.junit.BeforeClass;
import static org.mockito.Mockito.*;

import com.huskysoft.interviewannihilator.model.Difficulty;
import com.huskysoft.interviewannihilator.model.NetworkException;
import com.huskysoft.interviewannihilator.service.NetworkService;

import junit.framework.TestCase;

public class QuestionServiceTest extends TestCase {
	
	private static final String GET_QUESTIONS_RESPONSE = "{\"results\":\"[{\\\"questionId\\\":\\\"2\\\",\\\"authorId\\\":\\\"2\\\",\\\"qtext\\\":\\\"test question text\\\",\\\"title\\\":\\\"testy question\\\",\\\"likes\\\":\\\"0\\\",\\\"dislikes\\\":\\\"0\\\",\\\"difficulty\\\":\\\"easy\\\",\\\"category\\\":\\\"business\\\",\\\"dateCreated\\\":\\\"10514\\\"},{\\\"questionId\\\":\\\"5\\\",\\\"authorId\\\":\\\"2\\\",\\\"qtext\\\":\\\"second test question\\\",\\\"title\\\":\\\"testy question 2\\\",\\\"likes\\\":\\\"0\\\",\\\"dislikes\\\":\\\"0\\\",\\\"difficulty\\\":\\\"medium\\\",\\\"category\\\":\\\"logic\\\",\\\"dateCreated\\\":\\\"10524\\\"}]\",\"totalNumberOfResults\":\"2\",\"limit\":\"ALL\",\"offset\":\"0\"}";
	private NetworkService mockNetworkService;
	
	@SuppressWarnings("unchecked")
	@BeforeClass
	public void setUp() throws NetworkException {
		mockNetworkService = mock(NetworkService.class);
		when(mockNetworkService.getQuestions(any(Difficulty.class), 
				any(Collection.class), anyInt(), anyInt())).
				thenReturn(GET_QUESTIONS_RESPONSE);
	}
	
	public void testGetAllQuestions() {
		
	}

}
