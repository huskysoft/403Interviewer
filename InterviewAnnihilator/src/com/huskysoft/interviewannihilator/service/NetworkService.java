package com.huskysoft.interviewannihilator.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * A class that provides basic functionalities for getting a response string
 * from the server for questions and solutions.
 * 
 * @author Kevin Loh, 5/3/2013
 * 
 */
public class NetworkService {

	/**
	 * The url of our server for both solution and question.
	 */
	public static final String BASE_URL = "http://students.washington.edu/bkng/cse403/403Interviewer-php/";

	/**
	 * The url for getting all the questions.
	 */
	public static final String QUESTION_URL = BASE_URL + "getQuestions.php";

	/**
	 * The url for getting solutions. Must be parameterized with questionId.
	 */
	public static final String SOLUTION_URL = BASE_URL + "getSolutions.php";

	/**
	 * Request all the questions on the server.
	 * 
	 * @return a string with the form [q_1, q_2, ..., q_n]. Each q_i is a JSON
	 *         String; null if an exception occurs or the request fails.
	 */
	public static String requestQuestions() {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(QUESTION_URL);
			HttpResponse response = client.execute(request);

			if (response.getStatusLine().getStatusCode() != 200) {
				return null;
			}

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			String ret = "";
			String line = rd.readLine();
			while (line != null) {
				ret += line;
				line = rd.readLine();
			}

			client.getConnectionManager().shutdown();
			return ret;

		} catch (Exception e) {
			return null;
		}
	}

}
