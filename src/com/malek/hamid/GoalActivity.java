package com.malek.hamid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class GoalActivity extends Activity {
	// -- Form Elements --------------------------
	private EditText weight;
	private EditText duration;
	private Spinner policy;
	private Spinner timeScale;
	private Button submit;
	private DatabaseHandler db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.goal);
		db = new DatabaseHandler(getApplicationContext());
		
		// -- Form Elements Initialization ---------------
		weight = (EditText) findViewById(R.id.goal_weight);
		duration = (EditText) findViewById(R.id.goal_duration);
		policy = (Spinner) findViewById(R.id.spinner_lose_or_gain);
		timeScale = (Spinner) findViewById(R.id.spinner_goal_duration_scale);
		submit = (Button) findViewById(R.id.save_goal);
		
		submit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}