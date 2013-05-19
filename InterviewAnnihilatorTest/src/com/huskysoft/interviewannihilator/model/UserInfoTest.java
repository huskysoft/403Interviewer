/**
 * The class contains tests for the UserInfo class
 * 
 * @author Bennett Ng, 5/14/2013
 */

package com.huskysoft.interviewannihilator.model;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.huskysoft.interviewannihilator.util.TestHelpers;

import junit.framework.TestCase;

public class UserInfoTest extends TestCase {

	private ObjectMapper mapper;

	/**
	 * Construct new test instance
	 *
	 * @param name the test name
	 */
	public UserInfoTest(String name) {
		super(name);
		mapper = new ObjectMapper();
	}
	
	/**
	 * This tests the construction and usage of userInfo, its translation to
	 * json to send over the network, and back
	 * 
	 * @label white-box test
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void testUserInfoRoundTrip() throws JsonGenerationException, 
			JsonMappingException, IOException {
		UserInfo userInfo = TestHelpers.createDummyUserInfo();
		String json = mapper.writeValueAsString(userInfo);
		UserInfo clone = mapper.readValue(json, UserInfo.class);
		assertEquals(userInfo, clone);
	}
	
}
