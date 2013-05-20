package com.huskysoft.interviewannihilator.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jmock.integration.junit3.MockObjectTestCase;
import org.jmock.Expectations;

import com.huskysoft.interviewannihilator.model.NetworkException;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.util.TestHelpers;

public class QuestionServiceMockTest extends MockObjectTestCase {
	
	public QuestionServiceMockTest(String name) {
		super(name);
	}
	
	public void testOneNetworkServicePostQuestion() throws NetworkException,
			JsonGenerationException, JsonMappingException, IOException {
		final NetworkService networkService = mock(NetworkService.class);
		Question question = TestHelpers.createDummyQuestion(0);
		ObjectMapper mapper = new ObjectMapper();
		final String questionStr = mapper.writeValueAsString(question);
		checking(new Expectations() {
			{
				oneOf(networkService).postQuestion(questionStr);
			}
		});
	}
}
