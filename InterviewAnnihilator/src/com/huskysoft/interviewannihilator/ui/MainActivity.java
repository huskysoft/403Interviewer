package com.huskysoft.interviewannihilator.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.huskysoft.interviewannihilator.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		String url = "http://students.washington.edu/bkng/cse403/foo.php";
		textView = (TextView)findViewById(R.id.textView1);
		textView.setText("Loading...");

		new PostURLTask().execute(url , null, null);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private StringBuilder inputStreamToString(InputStream is) {
		String rLine = "";
		StringBuilder answer = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		try {
			while ((rLine = rd.readLine()) != null) {
				answer.append(rLine);
			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		return answer;
	}

	private class PostURLTask extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... urls) {
			String result = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(urls[0]);
			try {				
				HttpResponse response = httpclient.execute(httppost);
				result = inputStreamToString(response.getEntity().
						getContent()).toString();					    	
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

		protected void onProgressUpdate(Void... progress) {

		}

		protected void onPostExecute(String result) {
			textView.setText(result);
		}
	}

}
