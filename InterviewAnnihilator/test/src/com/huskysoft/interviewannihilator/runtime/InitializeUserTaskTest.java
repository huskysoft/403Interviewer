/**
 * The class contains tests for the InitializeUserTask class
 * 
 * @author Justin Robb, 6/1/2013
 */

package com.huskysoft.interviewannihilator.runtime;

import java.io.File;

import android.os.Environment;
import android.util.Log;

import junit.framework.TestCase;

public class InitializeUserTaskTest extends TestCase {	
	
	public InitializeUserTaskTest(String name){
		super(name);
	}
	
	/**
	 * Tests that task can be constructed
	 * 
	 * @label Black-box testing
	 */
	public void testInitializeUserCstr(){
		File dir = new File("./temp/");
		InitializeUserInfoTask task = 
				new InitializeUserInfoTask(null, dir, "test@TESTING.com");
		assertNotNull(task);
	}
	
	/**
	 * Tests that a good user is initialized
	 * 
	 * @label Black-box testing
	 */
	public void testInitializeUser(){
		File dir = Environment.getExternalStorageDirectory();
		InitializeUserInfoTask task = 
				new InitializeUserInfoTask(null, dir, "test@TESTING.com");
		try {
			int result = task.doInBackground();
			assertNotSame(-1, result);
		} catch (Exception e) {
			Log.e("PostQuestionTaskTest", e.getMessage());
			fail();
		}
	}
	
	/**
	 * Tests that a bad user is not initialized
	 * 
	 * @label Black-box testing
	 */
	public void testInitializeBadUserTest(){
		InitializeUserInfoTask task = 
				new InitializeUserInfoTask(null, null, null);
		try {
			int result = task.doInBackground();
			assertEquals(-1, result);
		} catch (Exception e) {
			Log.e("PostQuestionTaskTest", e.getMessage());
			fail();
		}
	}
}
