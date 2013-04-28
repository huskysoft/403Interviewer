package com.huskysoft.interviewannihilator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.huskysoft.interviewannihilator.QUESTION";
	
	public TextView textView;
	public LinearLayout ll;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		String url = "http://students.washington.edu/bkng/cse403/foo.php";
		//textView = (TextView)findViewById(R.id.textView1);
		//textView.setText("Loading...");

		//new PostURLTask().execute(url , null, null);
		ll = (LinearLayout)findViewById(R.id.linear_layout);
		
		TextView[] t = new TextView[10];
		for(int i = 0; i < 10; i++){
			
			t[i] = new TextView(this);
			t[i].setText("Question " + (i + 1));
			t[i].setTextSize(40);
			
			
			LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		    llp.setMargins(0, 10, 0, 10); // llp.setMargins(left, top, right, bottom);
		    llp.gravity = 1; //Horizontal Center
		    
		    t[i].setLayoutParams(llp);
			
			t[i].setId(i);
			t[i].setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					openQuestion(v);
				}
			});
			
			ll.addView(t[i]);
		}
	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void openQuestion(View view){
		Intent intent = new Intent(this, QuestionActivity.class);
		TextView tv = (TextView) view;
		String message = tv.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
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
				result = inputStreamToString(response.getEntity().getContent()).toString();					    	
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
