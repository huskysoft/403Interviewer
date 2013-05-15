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
	
	public void testUserInfoRoundTrip() throws JsonGenerationException, 
			JsonMappingException, IOException {
		UserInfo userInfo = TestHelpers.createDummyUserInfo();
		String json = mapper.writeValueAsString(userInfo);
		UserInfo clone = mapper.readValue(json, UserInfo.class);
		assertEquals(userInfo, clone);
	}

	
}
