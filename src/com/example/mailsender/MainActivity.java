package com.example.mailsender;

import java.util.Date;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	 Button b;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try{
		setContentView(R.layout.activity_main);
		  b = (Button)findViewById(R.id.button);
		
		b.setText("sendmail");
		
		b.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				try{
				Button bt = null;
				bt.setText("");
				}catch (Exception e) {
					new sendMailTask().execute(prepareLog(e));
				}
			}
		});
		}catch (NullPointerException e) {
			new sendMailTask().execute(prepareLog(e));
		}catch (Exception e) {
			
			new sendMailTask().execute(prepareLog(e));
		}
	}

	class sendMailTask extends AsyncTask<String, Void, Void> {
		ProgressDialog progress = new ProgressDialog(MainActivity.this);
		@Override
		protected Void doInBackground(String... params) {
			String error  = params[0];
			try {   
			    GMailSender sender = new GMailSender("hydneelinfo@gmail.com", "Welcome123456789");
			    sender.sendMail("Crash Report",   
			    		error,   
			            "hydneelinfo@gmail.com",   
			            "manoj@neelinfo.com");   
			} catch (Exception e) {   
			    Log.e("SendMail", e.getMessage(), e);   
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			progress.dismiss();
			finish();
			super.onPostExecute(result);
		}
		@Override
		protected void onPreExecute() {
			progress.setTitle("Application Crashed please wait while sending the report..");
			progress.show();
			super.onPreExecute();
		}
		
	}
	
	private String prepareLog(Exception e) {
		Date date = new Date();
		String logs = "" + date + ": " + e.toString() + "\n";
		StackTraceElement[] stack = e.getStackTrace();
		for (StackTraceElement stackTraceElement : stack) {
			logs = logs + "" + date + ": " + stackTraceElement + "\n";

		}

		return logs;
	}
}

/*
try {   
    GMailSender sender = new GMailSender("hydneelinfo@gmail.com", "Welcome123456789");
    sender.sendMail("Crash Report",   
            "This is Body",   
            "hydneelinfo@gmail.com",   
            "manoj@neelinfo.com");   
} catch (Exception e) {   
    Log.e("SendMail", e.getMessage(), e);   
}
*/